package es.ucm.stalos.logic.states;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import es.ucm.stalos.engine.AbstractState;
import es.ucm.stalos.engine.Engine;
import es.ucm.stalos.engine.Font;
import es.ucm.stalos.engine.Image;
import es.ucm.stalos.engine.Input;
import es.ucm.stalos.engine.State;
import es.ucm.stalos.logic.Assets;
import es.ucm.stalos.logic.enums.StateCondition;
import es.ucm.stalos.logic.interfaces.ButtonCallback;
import es.ucm.stalos.logic.objects.Board;

public class GameState extends AbstractState {

    public GameState(Engine engine, int rows, int columns) {
        super(engine);
        this._rows = rows;
        this._cols = columns;
    }

//-----------------------------------------OVERRIDE-----------------------------------------------//

    @Override
    public boolean init() {
        try {
            // Board
            initBoard();

            // Buttons
            initButtons();

            // Texts
            initTexts();

        } catch (Exception e) {
            System.out.println("Error init GameState");
            System.out.println(e);
            return false;
        }
        return true;
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render() {
        _board.render(_graphics);
        renderButtons();
        renderText();
    }

    @Override
    public void handleInput() {
        List<Input.TouchEvent> events = _engine.getInput().getTouchEvents();
        for (int i = 0; i < events.size(); i++) {
            Input.TouchEvent currEvent = events.get(i);
            if (currEvent == Input.TouchEvent.touchDown) {
                int[] clickPos = {currEvent.getX(), currEvent.getY()};

                if (_situation == StateCondition.Playing && clickInsideSquare(clickPos, _giveupButtonPos, _giveupButtonSize))
                    _giveupCallback.doSomething();
                else if (_situation == StateCondition.Playing && clickInsideSquare(clickPos, _posCheckButton, _sizeButtonCheck))
                    _checkCallback.doSomething();
                else if (_situation != StateCondition.Win && clickInsideSquare(clickPos, _posBoard, _sizeBoard)) {
                    if (_situation == StateCondition.Checking && _timer != null && _timerTask != null) {
                        _timerTask.run();
                        _timer.cancel();
                        _timerTask = null;
                        _timer = null;
                    }
                    _board.handleInput(clickPos);
                } else if (_situation == StateCondition.Win && clickInsideSquare(clickPos, _backButtonPos, _backButtonSize))
                    _backCallback.doSomething();
            }
        }
    }

//-------------------------------------------MISC-------------------------------------------------//

    private void initButtons() throws Exception {
        // GIVE UP
        _giveupFont = _graphics.newFont("JosefinSans-Bold.ttf", 20, true);
        _giveupButtonSize[0] = (_graphics.getLogWidth() / 14);
        _giveupButtonSize[1] = (_graphics.getLogHeight() / 25);
        _giveupCallback = new ButtonCallback() {
            @Override
            public void doSomething() {
                State selectLevelState = new SelectLevelState(_engine);
                _engine.reqNewState(selectLevelState);
            }
        };

        // BACK BUTTON
        // BACK LEVEL
        _backFont = _graphics.newFont("JosefinSans-Bold.ttf", 20, true);
        _backButtonPos[0] = (int) (_graphics.getLogWidth() * 0.44);
        _backButtonPos[1] = (int) (_graphics.getLogHeight() * 0.93);
//        _backButtonPos[1] = (int) (_graphics.getLogHeight() * 0.95);

        _backButtonSize[0] = (int) (_graphics.getLogWidth() * 0.14);
        _backButtonSize[1] = (int) (_graphics.getLogHeight() * 0.05);
        _backCallback = new ButtonCallback() {
            @Override
            public void doSomething() {
                State selectLevel = new SelectLevelState(_engine);
                _engine.reqNewState(selectLevel);
            }
        };

        // CHECK
        _sizeButtonCheck[0] = _giveupButtonSize[0];
        _sizeButtonCheck[1] = _giveupButtonSize[1];
        _checkCallback = new ButtonCallback() {
            @Override
            public void doSomething() {
                // At first it checks the original solution
                if (_board.checkOriginalSolution()) {
                    _situation = StateCondition.Win;
                    _board.setWin(true);
                }
                // Then check for another one
                else if (_board.checkAnotherSolution()) {
                    _situation = StateCondition.Win;
                    _winText2 = "Otra solución";
                    _board.setWin(true);
                } else {
                    _situation = StateCondition.Checking;
                    showText();
                }
            }
        };
    }

    private void initTexts() throws Exception {
        // TEXT HINTS
        _fontHint = _graphics.newFont("JosefinSans-Bold.ttf", 17, true);
        _hintPos1[0] = (int) (_graphics.getLogWidth() * 0.3);
        _hintPos1[1] = (int) (_graphics.getLogHeight() * 0.2);

        _hintPos2[0] = (int) (_graphics.getLogWidth() * 0.3);
        _hintPos2[1] = (int) (_graphics.getLogHeight() * 0.25);

        // WIN TEXT
        _fontWin = _graphics.newFont("JosefinSans-Bold.ttf", 23, true);
        _winPos1[0] = (int) (_graphics.getLogWidth() * 0.3);
        _winPos1[1] = (int) (_graphics.getLogHeight() * 0.2);

        _winPos2[0] = (int) (_graphics.getLogWidth() * 0.3);
        _winPos2[1] = (int) (_graphics.getLogHeight() * 0.25);
    }

    public void renderButtons() {
        int color;
        int [] pos = new int[2];
        switch (_situation) {
            case Playing:
                // Back Button
                color = 0X000000FF;
                _graphics.setColor(color);
                _graphics.drawImage(_giveupButtonImage, _giveupButtonPos, _giveupButtonSize);
                pos[0] = _giveupButtonPos[0] + (int) (_giveupButtonSize[0] * 1.25) + 3;
                pos[1] = _giveupButtonPos[1] + (int) (_giveupButtonSize[1] * 2 / 3) + 3;
                _graphics.drawText(_giveupText, pos, _giveupFont);

                // Check Button
                _graphics.drawImage(_checkButtonImage, _posCheckButton, _sizeButtonCheck);
                pos[0] += 230;
                _graphics.drawText(_checkText, pos, _giveupFont);
                break;
            case Win:
                // Back Button
                color = 0X000000FF;
                _graphics.setColor(color);

                pos[0] = _backButtonPos[0];
                pos[1] = _backButtonPos[1] + (int)(_backButtonSize[1] * 0.65);
                _graphics.drawText(_backText, pos, _backFont);
                _graphics.drawRect(_backButtonPos, _backButtonSize);
                break;
        }
    }

    public void renderText() {
        int color;
        switch (_situation) {
            case Checking:
                // TEXT HINTS
                color = 0XFF0000FF;
                _graphics.setColor(color);
                _graphics.drawText(_textHints1, _hintPos1, _fontHint);
                _graphics.drawText(_textHints2, _hintPos2, _fontHint);
                break;
            case Win:
                // TEXT WIN
                color = 0X000000FF;
                _graphics.setColor(color);
                _graphics.drawText(_winText1, _winPos1, _fontWin);
                _graphics.drawText(_winText2, _winPos2, _fontWin);
                break;
            default:
                break;
        }
    }

    public void initBoard() {
        // Create the board
        _posBoard[0] = 20;
        _posBoard[1] = 200;
        _sizeBoard[0] = 360.0f;
        _sizeBoard[1] = 360.0f;

        _board = new Board(_rows, _cols, _posBoard, _sizeBoard);
        _board.init(_engine);
    }

    private void showText() {
        _situation = StateCondition.Checking;
        // ATTRIBUTES
        int[] mistakes = _board.countMistakes();
        _textHints1 = "Te falta " + mistakes[0] + " casillas";
        _textHints2 = "Tienes mal " + mistakes[1] + " casillas";

        // TODO: Hacer calculos para centrarlo
        // TIMER
        _timer = new Timer();
        _timeDelay = 3000;
        _timerTask = new TimerTask() {
            @Override
            public void run() {
                _board.resetWrongCells();
                _situation = StateCondition.Playing;
            }
        };
        _timer.schedule(_timerTask, _timeDelay);
    }
//----------------------------------------ATTRIBUTES----------------------------------------------//

    // Atributos del estado
    int _rows;
    int _cols;

    // Board
    Board _board;
    int[] _posBoard = new int[2];
    float[] _sizeBoard = new float[2];

    // Give Up Button attributes
    final String _giveupText = "Rendirse";
    final Image _giveupButtonImage = Assets.backArrow;
    final int[] _giveupButtonPos = {15, 50};
    final float[] _giveupButtonSize = new float[2];
    Font _giveupFont;
    ButtonCallback _giveupCallback;

    // Volver
    final String _backText = "Volver";
    final int[] _backButtonPos = new int[2];
    final float[] _backButtonSize = new float[2];
    Font _backFont;
    ButtonCallback _backCallback;

    // Check Button attributes
    final String _checkText = "Comprobar";
    final Image _checkButtonImage = Assets.lens;
    final int[] _posCheckButton = {240, 50};
    final float[] _sizeButtonCheck = new float[2];
    ButtonCallback _checkCallback;

    // Text hints
    String _textHints1 = "";
    String _textHints2 = "";
    Font _fontHint;
    final int[] _hintPos1 = new int[2];
    final int[] _hintPos2 = new int[2];
    StateCondition _situation = StateCondition.Playing;

    // WIN TEXT
    String _winText1 = "ENHORABUENA!";
    String _winText2 = "Solución original";
    final int[] _winPos1 = new int[2];
    final int[] _winPos2 = new int[2];
    Font _fontWin;
}
