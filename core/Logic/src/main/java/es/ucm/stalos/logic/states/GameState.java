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
            System.out.println("Error init Game State");
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
                else if (_playState == PlayingState.Win && clickInsideSquare(clickPos, _backImagePos, _backButtonSize))
                    _backCallback.doSomething();
            }
        }
    }

//-------------------------------------------MISC-------------------------------------------------//

    private void initButtons() throws Exception {
        _fontButtons = _graphics.newFont("JosefinSans-Bold.ttf", 20, true);

        // Give Up
        _giveupImageSize[0] = _graphics.getLogWidth() * 0.071f;
        _giveupImageSize[1] = _graphics.getLogHeight() * 0.04f;
        _giveupImagePos[0] = 10;
        _giveupImagePos[1] = 31;

        _giveupTextSize[0] = _graphics.getLogWidth() * 0.2f;
        _giveupTextSize[1] = _giveupImageSize[1];
        _giveupTextPos[0] = (int) (_giveupImagePos[0] + _giveupImageSize[0]);
        _giveupTextPos[1] = _giveupImagePos[1];

        _giveupButtonSize[0] = _giveupImageSize[0] + _giveupTextSize[0];
        _giveupButtonSize[1] = _giveupImageSize[1];
        _giveupCallback = new ButtonCallback() {
            @Override
            public void doSomething() {
                State selectLevelState = new SelectLevelState(_engine, _isRandom);
                _engine.reqNewState(selectLevelState);
                _audio.stopMusic(Assets.mainTheme);
                _audio.play(Assets.clickSound, 1);
            }
        };

        // Check
        _checkTextSize[0] = _graphics.getLogWidth() * 0.3f;
        _checkTextSize[1] = _giveupImageSize[1];
        _checkTextPos[0] = (int) (_graphics.getLogWidth() - _checkTextSize[0]);
        _checkTextPos[1] = _giveupImagePos[1];

        _checkImageSize[0] = _giveupImageSize[0];
        _checkImageSize[1] = _giveupImageSize[1];
        _checkImagePos[0] = (int) (_graphics.getLogWidth() - _checkTextSize[0] - _checkImageSize[0]);
        _checkImagePos[1] = _giveupImagePos[1];

        _checkButtonSize[0] = _checkImageSize[0] + _checkTextSize[0];
        _checkButtonSize[1] = _checkImageSize[1];
        _checkCallback = new ButtonCallback() {
            @Override
            public void doSomething() {
                // At first it checks the original solution
                if (_board.checkOriginalSolution()) {
                    _playState = PlayingState.Win;
                    _board.setPos(new int[]{_posBoard[0], _posBoard[1] - 50});
                    _board.setWin(true);
                    _audio.play(Assets.winSound, 1);
                }
                // Then check for another one
                else if (_board.checkAnotherSolution()) {
                    _playState = PlayingState.Win;
                    _winText2 = "Otra solución";
                    _board.setPos(new int[]{_posBoard[0], _posBoard[1] - 50});
                    _board.setWin(true);
                    _audio.play(Assets.winSound, 1);
                } else {
                    _playState = PlayingState.Checking;
                    showText();
                }
                _audio.play(Assets.clickSound, 1);
            }
        };

        // Back
        _backImageSize[0] = _giveupImageSize[1];
        _backImageSize[1] = _giveupImageSize[1];
        _backTextSize[0] = _graphics.getLogWidth() * 0.2f;
        _backTextSize[1] = _giveupImageSize[1];

        _backImagePos[0] = (int) ((_graphics.getLogWidth() - (_backTextSize[0] + _backImageSize[0])) * 0.5f);
        _backImagePos[1] = (int) (_graphics.getLogHeight() * 0.9f);
        _backTextPos[0] = (int) (_backImagePos[0] + _backImageSize[0]);
        _backTextPos[1] = _backImagePos[1];

        _backButtonSize[0] = _backImageSize[0] + _backTextSize[0];
        _backButtonSize[1] = _backImageSize[1];
        _backCallback = new ButtonCallback() {
            @Override
            public void doSomething() {
                State selectLevel = new SelectLevelState(_engine, _isRandom);
                _engine.reqNewState(selectLevel);
                _audio.stopMusic(Assets.mainTheme);
                _audio.play(Assets.clickSound, 1);
            }
        };

    }

    private void initTexts() throws Exception {
        _fontText = _graphics.newFont("JosefinSans-Bold.ttf", 30, true);

        // TEXT HINTS
        _hintSize1[0] = _graphics.getLogWidth();
        _hintSize1[1] = _graphics.getLogHeight() * 0.08f;
        _hintPos1[0] = 0;
        _hintPos1[1] = (int) (_graphics.getLogHeight() * 0.1f);

        _hintSize2[0] = _graphics.getLogWidth();
        _hintSize2[1] = _graphics.getLogHeight() * 0.08f;
        _hintPos2[0] = 0;
        _hintPos2[1] = (int) (_graphics.getLogHeight() * 0.18f);

        // WIN TEXT
        _winSize1[0] = _graphics.getLogWidth();
        _winSize1[1] = _graphics.getLogHeight() * 0.08f;
        _winPos1[0] = 0;
        _winPos1[1] = (int) (_graphics.getLogHeight() * 0.1f);

        _winSize2[0] = _graphics.getLogWidth();
        _winSize2[1] = _graphics.getLogHeight() * 0.08f;
        _winPos2[0] = 0;
        _winPos2[1] = (int) (_graphics.getLogHeight() * 0.18f);
    }

    public void renderButtons() {
        _graphics.setColor(_blackColor);
        switch (_playState) {
            case Gaming:
                // GiveUp Button
                _graphics.drawImage(_giveupImage, _giveupImagePos, _giveupImageSize);
                _graphics.drawCenteredString(_giveupText, _giveupTextPos, _giveupTextSize, _fontButtons);
                // Check Button
                _graphics.drawImage(_checkImage, _checkImagePos, _checkImageSize);
                _graphics.drawCenteredString(_checkText, _checkTextPos, _checkTextSize, _fontButtons);
                break;
            case Win: {
                // Back Button
                _graphics.drawImage(_backImage, _backImagePos, _backImageSize);
                _graphics.drawCenteredString(_backText, _backTextPos, _backTextSize, _fontButtons);
                break;
            }
        }
    }

    public void renderText() {
        switch (_playState) {
            case Checking:
                // TEXT HINTS
                System.out.println(_hintsText1 + " en " + _hintPos1[0] + ", " + _hintPos1[1]);
                System.out.println(_hintsText2);

                _graphics.setColor(_redColor);
                _graphics.drawCenteredString(_hintsText1, _hintPos1, _hintSize1, _fontText);
                _graphics.setColor(_redColor);
                _graphics.drawCenteredString(_hintsText2, _hintPos2, _hintSize2, _fontText);
                break;
            case Win:
                // TEXT WIN
                _graphics.setColor(_blackColor);
                _graphics.drawCenteredString(_winText1, _winPos1, _winSize1, _fontText);
                _graphics.drawCenteredString(_winText2, _winPos2, _winSize2, _fontText);
                break;
            default:
                break;
        }
    }

    public void initBoard() throws Exception {
        // Create the board
        _posBoard[0] = 20;
        _posBoard[1] = 200;
        _sizeBoard[0] = 360.0f;
        _sizeBoard[1] = 360.0f;

        _board = new Board(_rows, _cols, _posBoard, _sizeBoard, _isRandom);
        if(!_board.init(_engine)) throw new Exception("Error al crear el board");
    }

    private void showText() {
        _playState = PlayingState.Checking;
        // ATTRIBUTES
        int[] mistakes = _board.countMistakes();

        if(mistakes[0] == 0) _hintsText1 = "No te faltan casillas";
        else if (mistakes[0] == 1) _hintsText1 = "Te falta " + mistakes[0] + " casilla";
        else _hintsText1 = "Te faltan " + mistakes[0] + " casillas";

        if(mistakes[1] == 0) _hintsText2 = "No tienes casillas mal";
        else if (mistakes[1] == 1) _hintsText2 = "Tienes mal " + mistakes[1] + " casilla";
        else _hintsText2 = "Tienes mal " + mistakes[1] + " casillas";

        // TODO borrar
//        int digits = String.valueOf(mistakes[0]).length();
//        if (digits == 1)
//            _hintPos1[0] = (int) (_graphics.getLogWidth() * 0.35);
//        else if (digits == 2)
//            _hintPos1[0] = (int) (_graphics.getLogWidth() * 0.35);
//
//        digits = String.valueOf(mistakes[1]).length();
//        if (digits == 1)
//            _hintPos2[0] = (int) (_graphics.getLogWidth() * 0.33);
//        else if (digits == 2)
//            _hintPos2[0] = (int) (_graphics.getLogWidth() * 0.33);

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
    private PlayingState _playState = PlayingState.Gaming;
    private boolean _isRandom;

    // Atributos del estado
    private int _rows;
    private int _cols;

    // Board
    private Board _board;
    private int[] _posBoard = new int[2];
    private float[] _sizeBoard = new float[2];

    // Texts
    private Font _fontText;

    private String _hintsText1 = "Te faltan x casillas";
    private int[] _hintPos1 = new int[2];
    private float[] _hintSize1 = new float[2];

    private String _hintsText2 = "Tienes mal x casillas";
    private int[] _hintPos2 = new int[2];
    private float[] _hintSize2 = new float[2];

    private final String _winText1 = "ENHORABUENA!";
    private int[] _winPos1 = new int[2];
    private float[] _winSize1 = new float[2];

    private String _winText2 = "Solución original";
    private int[] _winPos2 = new int[2];
    private float[] _winSize2 = new float[2];

    // Buttons
    private Font _fontButtons;

    // Give Up Button
    private final String _giveupText = "Rendirse";
    private int[] _giveupTextPos = new int[2];
    private float[] _giveupTextSize = new float[2];

    private final Image _giveupImage = Assets.backArrow;
    private int[] _giveupImagePos = new int[2];
    private float[] _giveupImageSize = new float[2];

    private float[] _giveupButtonSize = new float[2];
    private ButtonCallback _giveupCallback;

    // Check Button
    private final String _checkText = "Comprobar";
    private int[] _checkTextPos = new int[2];
    private float[] _checkTextSize = new float[2];

    private final Image _checkImage = Assets.lens;
    private int[] _checkImagePos = new int[2];
    private float[] _checkImageSize = new float[2];

    private float[] _checkButtonSize = new float[2];
    private ButtonCallback _checkCallback;

    // Back Button
    private final String _backText = "Volver";
    private int[] _backTextPos = new int[2];
    private float[] _backTextSize = new float[2];

    private final Image _backImage = Assets.backArrow;
    private int[] _backImagePos = new int[2];
    private float[] _backImageSize = new float[2];

    private float[] _backButtonSize = new float[2];
    private ButtonCallback _backCallback;

    // Audio

    // TODO ¿Mover?
    // Colors
    private final int _greyColor = 0x313131FF;
    private final int _blackColor = 0x000000FF;
    private final int _redColor = 0xFF0000FF;

}
