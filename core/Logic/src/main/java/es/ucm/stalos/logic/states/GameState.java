package es.ucm.stalos.logic.states;

import es.ucm.stalos.engine.Engine;
import es.ucm.stalos.engine.Graphics;
import es.ucm.stalos.engine.Image;
import es.ucm.stalos.engine.State;
import es.ucm.stalos.logic.Assets;
import es.ucm.stalos.logic.objects.Board;

public class GameState implements State {

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

        // GIVE UP
        _checkButtonImage = Assets.checkButton;
        // Size
        _sizeCheckButton[0] = _graphics.getLogWidth() * 0.4f;
        _sizeCheckButton[1] = _graphics.getLogHeight() * 0.1f;
        // Position
        _posCheckButton[0] = 200;
        _posCheckButton[1] = ((int)_sizeCheckButton[1] / 2);
        // CallBack
    }

    public void renderButtons(){
        // Attributes
        int color = 0X313131FF;
        int tamF = 100;
        // Font font = Assets.molle;

        _graphics.setColor(color);

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
    Engine _engine;
    Graphics _graphics;
    int _rows;
    int _cols;

    // Board
    Board _board;
    int[] _posBoard = new int[2];
    float[] _sizeBoard = new float[2];

    // Give Up Button attributes
    Image _giveUpButtonImage;
    int _posGiveUpButton[] = new int[2];
    float _sizeGiveUpButton[] = new float[2];

    // Give Up Button attributes
    Image _checkButtonImage;
    int _posCheckButton[] = new int[2];
    float _sizeCheckButton[] = new float[2];
}
