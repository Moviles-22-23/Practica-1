package es.ucm.stalos.logic.states;

import java.util.List;

import es.ucm.stalos.engine.AbstractState;
import es.ucm.stalos.engine.Engine;
import es.ucm.stalos.engine.Font;
import es.ucm.stalos.engine.Image;
import es.ucm.stalos.engine.Input;
import es.ucm.stalos.engine.State;
import es.ucm.stalos.logic.Assets;
import es.ucm.stalos.logic.interfaces.ButtonCallback;
import es.ucm.stalos.logic.objects.Board;

public class GameState extends AbstractState {

    public GameState(Engine engine, int rows, int columns){
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

                if(clickInsideSquare(clickPos, _backButtonPos, _backButtonSize)) _backCallback.doSomething();
                else if(clickInsideSquare(clickPos, _posCheckButton, _sizeButtonCheck)) _checkCallback.doSomething();
                else if(clickInsideSquare(clickPos, _posBoard, _sizeBoard)) _board.handleInput(clickPos);
            }
        }
    }

//-------------------------------------------MISC-------------------------------------------------//

    public void initButtons() throws Exception {
        // GIVE UP
        _backFont = _graphics.newFont("JosefinSans-Bold.ttf", 20, true);
        _backButtonSize[0] = (_graphics.getLogWidth() / 14);
        _backButtonSize[1] = (_graphics.getLogHeight() / 25);
        _backCallback = new ButtonCallback() {
            @Override
            public void doSomething() {
                State selectLevelState = new SelectLevelState(_engine);
                _engine.reqNewState(selectLevelState);
            }
        };

        // CHECK
        _sizeButtonCheck[0] = _backButtonSize[0];
        _sizeButtonCheck[1] = _backButtonSize[1];
        _checkCallback = new ButtonCallback() {
            @Override
            public void doSomething() {
                // TODO
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
        // Back Button
        int color = 0X000000FF;
        _graphics.setColor(color);
        _graphics.drawImage(_backButtonImage, _backButtonPos, _backButtonSize);
        int[] pos = {
                _backButtonPos[0] + (int) (_backButtonSize[0] * 1.25) + 3,
                _backButtonPos[1] + (int) (_backButtonSize[1] * 2 / 3) + 3
        };
        _graphics.drawText(_backText, pos, _backFont);

        // Check Button
        _graphics.drawImage(_checkButtonImage, _posCheckButton, _sizeButtonCheck);
        pos[0] += 230;
        _graphics.drawText(_checkText, pos, _backFont);
    }

    public void initBoard(){
        // Create the board
        _posBoard[0] = 20;
        _posBoard[1] = 200;
        _sizeBoard[0] = 380.0f;
        _sizeBoard[1] = 380.0f;
        // TODO
        //System.out.println("Rows: " + _rows + ", cols:" + _cols);
        _board = new Board(_rows, _cols, _posBoard, _sizeBoard);
        _board.init(_graphics);
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
    final String _backText = "Rendirse";
    final Image _backButtonImage = Assets.backArrow;
    final int[] _backButtonPos = {15, 50};
    final float[] _backButtonSize = new float[2];
    Font _backFont;
    ButtonCallback _backCallback;

    // Check Button attributes
    final String _checkText = "Comprobar";
    final Image _checkButtonImage = Assets.lens;
    final int[] _posCheckButton = {240, 50};
    final float[] _sizeButtonCheck = new float[2];
    ButtonCallback _checkCallback;
}
