package es.ucm.stalos.logic.states;

import java.util.List;

import es.ucm.stalos.engine.AbstractState;
import es.ucm.stalos.engine.Engine;
import es.ucm.stalos.engine.Image;
import es.ucm.stalos.engine.Input;
import es.ucm.stalos.engine.State;
import es.ucm.stalos.logic.Assets;
import es.ucm.stalos.logic.interfaces.ButtonCallback;
import es.ucm.stalos.logic.objects.Board;

public class GameState extends AbstractState {

    GameState(Engine engine, int rows, int columns){
        this._engine = engine;
        this._graphics = engine.getGraphics();
        this._rows = rows;
        this._cols = columns;
    }

    @Override
    public boolean init() {
        try {
            // Board
            initBoard();

            // Buttons
            initButtons();

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
    }

    @Override
    public void handleInput() {
        List<Input.TouchEvent> events = _engine.getInput().getTouchEvents();
        for (int i = 0; i < events.size(); i++) {
            Input.TouchEvent currEvent = events.get(i);
            if (currEvent == Input.TouchEvent.touchDown) {
                int[] clickPos = {currEvent.getX(), currEvent.getY()};

                if(clickInsideSquare(clickPos, _posGiveUpButton, _sizeGiveUpButton)) _giveUpCallback.doSomething();
                else if(clickInsideSquare(clickPos, _posCheckButton, _sizeCheckButton)) _checkCallback.doSomething();
                else if(clickInsideSquare(clickPos, _posBoard, _sizeBoard)) _board.handleInput(clickPos);
            }
        }
    }

    public void initButtons(){
        // GIVE UP
        _giveUpButtonImage = Assets.giveUpButton;
        // Size
        _sizeGiveUpButton[0] = _graphics.getLogWidth() * 0.4f;
        _sizeGiveUpButton[1] = _graphics.getLogHeight() * 0.1f;
        // Position
        _posGiveUpButton[0] = 0;
        _posGiveUpButton[1] = ((int)_sizeGiveUpButton[1] / 2);
        // Callback
        _giveUpCallback = new ButtonCallback() {
            @Override
            public void doSomething() {
                State selectLevelState = new SelectLevelState(_engine);
                _engine.reqNewState(selectLevelState);
            }
        };

        // CHECK
        _checkButtonImage = Assets.checkButton;
        // Size
        _sizeCheckButton[0] = _graphics.getLogWidth() * 0.4f;
        _sizeCheckButton[1] = _graphics.getLogHeight() * 0.1f;
        // Position
        _posCheckButton[0] = 200;
        _posCheckButton[1] = ((int)_sizeCheckButton[1] / 2);
        // CallBack
        _checkCallback = new ButtonCallback() {
            @Override
            public void doSomething() {
                System.out.println("Check Callback");
                // At first it checks the original solution
                if(_board.checkOriginalSolution()){
                    System.out.println("Correct with original solution");
                }
                // Then check for another one
                else if(_board.checkAnotherSolution()){
                    System.out.println("Correct with another solution");
                }
                else System.out.println("Not correct");
            }
        };
    }

    public void renderButtons(){
        // Give Up Button
        _graphics.drawImage(_giveUpButtonImage, _posGiveUpButton, _sizeGiveUpButton);
        // Check Button
        _graphics.drawImage(_checkButtonImage, _posCheckButton, _sizeCheckButton);
    }

    public void initBoard(){
        // Create the board
        _posBoard[0] = 50;
        _posBoard[1] = 200;
        _sizeBoard[0] = 300.0f;
        _sizeBoard[1] = 300.0f;
        System.out.println("Rows: " + _rows + ", cols:" + _cols);
        _board = new Board(_rows, _cols, _posBoard, _sizeBoard);

    }

    // Atributos del estado
    int _rows;
    int _cols;

    // Board
    Board _board;
    int[] _posBoard = new int[2];
    float[] _sizeBoard = new float[2];

    // Give Up Button attributes
    Image _giveUpButtonImage;
    int[] _posGiveUpButton = new int[2];
    float[] _sizeGiveUpButton = new float[2];
    ButtonCallback _giveUpCallback;

    // Check Button attributes
    Image _checkButtonImage;
    int[] _posCheckButton = new int[2];
    float[] _sizeCheckButton = new float[2];
    ButtonCallback _checkCallback;
}
