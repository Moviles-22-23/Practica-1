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
import es.ucm.stalos.logic.enums.PlayingState;
import es.ucm.stalos.logic.interfaces.ButtonCallback;
import es.ucm.stalos.logic.objects.Board;

public class GameState extends AbstractState {

    public GameState(Engine engine, int rows, int columns, boolean isRandom) {
        super(engine);
        this._rows = rows;
        this._cols = columns;
        this._isRandom = isRandom;
        System.out.println("GAME MODE RANDOM: " + _isRandom);
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

            _audio.playMusic(Assets.mainTheme);

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

                /// GIVE-UP BUTTON
                if (_playState == PlayingState.Gaming && clickInsideSquare(clickPos, _giveupImagePos, _giveupButtonSize))
                    _giveupCallback.doSomething();
                    // CHECK BUTTON
                else if (_playState == PlayingState.Gaming && clickInsideSquare(clickPos, _checkImagePos, _checkButtonSize))
                    _checkCallback.doSomething();
                    // BOARD INPUT
                else if (_playState != PlayingState.Win && clickInsideSquare(clickPos, _posBoard, _sizeBoard)) {
                    //
                    if (_playState == PlayingState.Checking && _timer != null && _timerTask != null) {
                        _timerTask.run();
                        _timer.cancel();
                        _timerTask = null;
                        _timer = null;
                    }
                    _board.handleInput(clickPos);
                    _audio.play(Assets.clickSound, 1);
                }
                // BACK BUTTON WIN
                else if (_playState == PlayingState.Win && clickInsideSquare(clickPos, _backButtonPos, _backButtonSize))
                    _backCallback.doSomething();
            }
        }
    }

//-------------------------------------------MISC-------------------------------------------------//

    private void initButtons() throws Exception {
        // GIVE UP
        _giveupFont = _graphics.newFont("JosefinSans-Bold.ttf", 20, true);
        _giveupImageSize[0] = (_graphics.getLogWidth() / 14);
        _giveupImageSize[1] = (_graphics.getLogHeight() / 25);
        _giveupCallback = new ButtonCallback() {
            @Override
            public void doSomething() {
                State selectLevelState = new SelectLevelState(_engine, _isRandom);
                _engine.reqNewState(selectLevelState);
                _audio.stop(Assets.mainTheme);
                _audio.play(Assets.clickSound, 1);
            }
        };
        _giveupTextPos[0] = _giveupImagePos[0] + (int) (_giveupImageSize[0] * 1.25) + 3;
        _giveupTextPos[1] = _giveupImagePos[1] + (int) (_giveupImageSize[1] * 2 / 3) + 3;

        _giveupButtonSize[0] = 110;
        _giveupButtonSize[1] = _giveupImageSize[1];

        // BACK BUTTON
        _backFont = _graphics.newFont("JosefinSans-Bold.ttf", 20, true);
        _backButtonPos[0] = (int) (_graphics.getLogWidth() * 0.44);
        _backButtonPos[1] = (int) (_graphics.getLogHeight() * 0.93);

        _backButtonSize[0] = (int) (_graphics.getLogWidth() * 0.14);
        _backButtonSize[1] = (int) (_graphics.getLogHeight() * 0.05);
        _backCallback = new ButtonCallback() {
            @Override
            public void doSomething() {
                State selectLevel = new SelectLevelState(_engine, _isRandom);
                _engine.reqNewState(selectLevel);
                _audio.stop(Assets.mainTheme);
                _audio.play(Assets.clickSound, 1);
            }
        };

        // CHECK
        _checkImageSize[0] = _giveupImageSize[0];
        _checkImageSize[1] = _giveupImageSize[1];

        _checkTextPos[0] = _checkImagePos[0] + 32;
        _checkTextPos[1] = _giveupTextPos[1];

        _checkButtonSize[0] = 130;
        _checkButtonSize[1] = _checkImageSize[1];

        _checkCallback = new ButtonCallback() {
            @Override
            public void doSomething() {
                // At first it checks the original solution
                if (_board.checkOriginalSolution()) {
                    _playState = PlayingState.Win;
                    _board.setPos(new int[]{ _posBoard[0], _posBoard[1] - 50 });
                    _board.setWin(true);
                    _audio.play(Assets.winSound, 1);
                }
                // Then check for another one
                else if (_board.checkAnotherSolution()) {
                    _playState = PlayingState.Win;
                    _winText2 = "Otra solución";
                    _board.setPos(new int[]{ _posBoard[0], _posBoard[1] - 50 });
                    _board.setWin(true);
                    _audio.play(Assets.winSound, 1);
                } else {
                    _playState = PlayingState.Checking;
                    showText();
                }
                _audio.play(Assets.clickSound, 1);
            }
        };
    }

    private void initTexts() throws Exception {
        // TEXT HINTS
        _fontHint = _graphics.newFont("JosefinSans-Bold.ttf", 1, true);
        _hintPos1[1] = (int) (_graphics.getLogHeight() * 0.2);
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
        switch (_playState) {
            case Gaming:
                // GiveUp Button
                color = 0X000000FF;
                _graphics.setColor(color);
                _graphics.drawImage(_giveupButtonImage, _giveupImagePos, _giveupImageSize);
                _graphics.drawText(_giveupText, _giveupTextPos, _giveupFont);

                // Check Button
                _graphics.drawImage(_checkButtonImage, _checkImagePos, _checkImageSize);
                _graphics.drawText(_checkText, _checkTextPos, _giveupFont);

                break;
            case Win: {
                // Back Button
                color = 0X000000FF;
                _graphics.setColor(color);
                int[] pos = new int[2];
                pos[0] = _backButtonPos[0];
                pos[1] = _backButtonPos[1] + (int) (_backButtonSize[1] * 0.65);
                _graphics.drawText(_backText, pos, _backFont);
                break;
            }
        }
    }

    public void renderText() {
        int color;
        switch (_playState) {
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

        _board = new Board(_rows, _cols, _posBoard, _sizeBoard, _isRandom);
        _board.init(_engine);
    }

    private void showText() {
        _playState = PlayingState.Checking;
        // ATTRIBUTES
        int[] mistakes = _board.countMistakes();
        _textHints1 = "Te falta " + mistakes[0] + " casillas";
        _textHints2 = "Tienes mal " + mistakes[1] + " casillas";

        int digits = String.valueOf(mistakes[0]).length();
        if (digits == 1)
            _hintPos1[0] = (int) (_graphics.getLogWidth() * 0.35);
        else if (digits == 2)
            _hintPos1[0] = (int) (_graphics.getLogWidth() * 0.35);

        digits = String.valueOf(mistakes[1]).length();
        if (digits == 1)
            _hintPos2[0] = (int) (_graphics.getLogWidth() * 0.33);
        else if (digits == 2)
            _hintPos2[0] = (int) (_graphics.getLogWidth() * 0.33);

        // TIMER
        _timer = new Timer();
        _timeDelay = 3000;
        _timerTask = new TimerTask() {
            @Override
            public void run() {
                _board.resetWrongCells();
                _playState = PlayingState.Gaming;
            }
        };
        _timer.schedule(_timerTask, _timeDelay);
    }

//----------------------------------------ATTRIBUTES----------------------------------------------//

    // Game Mode
    boolean _isRandom;

    // Atributos del estado
    int _rows;
    int _cols;

    // Board
    Board _board;
    int[] _posBoard = new int[2];
    float[] _sizeBoard = new float[2];

    // Give Up Button
    final String _giveupText = "Rendirse";
    final Image _giveupButtonImage = Assets.backArrow;
    /**
     * GiveUpButtonIcon's position
     */
    final int[] _giveupImagePos = {15, 50};
    /**
     * GiveUpText's position
     */
    final int[] _giveupTextPos = new int[2];
    /**
     * GiveUpButtonIcon's size
     */
    final float[] _giveupImageSize = new float[2];
    /**
     * GiveUpButtonIcon + text's size
     */
    final float[] _giveupButtonSize = new float[2];
    Font _giveupFont;
    ButtonCallback _giveupCallback;

    // Back Button
    final String _backText = "Volver";
    final int[] _backButtonPos = new int[2];
    final float[] _backButtonSize = new float[2];
    Font _backFont;
    ButtonCallback _backCallback;

    // Check Button
    final String _checkText = "Comprobar";
    final Image _checkButtonImage = Assets.lens;
    /**
     * CheckButtonIcon's position
     */
    final int[] _checkImagePos = {240, 50};
    /**
     * CheckText's position
     */
    final int[] _checkTextPos = new int[2];
    /**
     * CheckButtonIcon's size
     */
    final float[] _checkImageSize = new float[2];
    /**
     * CheckButtonIcon + text's size
     */
    final float[] _checkButtonSize = new float[2];
    ButtonCallback _checkCallback;

    // Text hints
    String _textHints1 = "";
    String _textHints2 = "";
    Font _fontHint;
    final int[] _hintPos1 = new int[2];
    final int[] _hintPos2 = new int[2];
    PlayingState _playState = PlayingState.Gaming;

    // WIN TEXT
    String _winText1 = "ENHORABUENA!";
    String _winText2 = "Solución original";
    final int[] _winPos1 = new int[2];
    final int[] _winPos2 = new int[2];
    Font _fontWin;

    // Audio

}
