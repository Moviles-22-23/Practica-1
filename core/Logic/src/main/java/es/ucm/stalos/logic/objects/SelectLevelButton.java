package es.ucm.stalos.logic.objects;

import es.ucm.stalos.engine.Font;
import es.ucm.stalos.engine.Graphics;
import es.ucm.stalos.logic.enums.GridType;
import es.ucm.stalos.logic.interfaces.ButtonCallback;

public class SelectLevelButton {
    public SelectLevelButton(int[] pos, float[] size, GridType gridType, Font font) {
        _buttonPos[0] = pos[0];
        _buttonPos[1] = pos[1];

        _buttonSize[0] = size[0];
        _buttonSize[1] = size[1];

        initType(gridType);

        _font = font;
    }

    /**
     * Initialize the buttonType
     */
    private void initType(GridType gridType) {
        switch (gridType) {
            case _4x4:
                _rows = 4;
                _cols = 4;
                _text = 4 + "x" + 4;
                break;
            case _5x5:
                _rows = 5;
                _cols = 5;
                _text = 5 + "x" + 5;
                break;
            case _5x10:
                _rows = 5;
                _cols = 10;
                _text = 5 + "x" + 10;
                break;
            case _8x8:
                _rows = 8;
                _cols = 8;
                _text = 8 + "x" + 8;
                break;
            case _10x10:
                _rows = 10;
                _cols = 10;
                _text = 10 + "x" + 10;
                break;
            case _10x15:
                _rows = 10;
                _cols = 15;
                _text = 10 + "x" + 15;
                break;
        }
    }

    public void render(Graphics gr) {
        gr.drawRect(_buttonPos, _buttonSize);
        gr.drawCenteredString(_text, _buttonPos, _buttonSize, _font);
    }

    public void setCallback(ButtonCallback cb) {
        _cb = cb;
    }

    /**
     * Callback function
     */
    public void doSomething() {
        _cb.doSomething();
    }

    public int[] getPos() {
        return _buttonPos;
    }

    public float[] getSize() {
        return _buttonSize;
    }

    public int getRows() {
        return _rows;
    }

    public int getCols() {
        return _cols;
    }

    /**
     * Logic position
     */
    private final int[] _buttonPos = new int[2];
    /**
     * Button size
     */
    private final float[] _buttonSize = new float[2];
    /**
     * Font of the text
     */
    private final Font _font;
    /**
     * Grid's row number to
     * create with the button
     */
    private int _rows;
    /**
     * Grid's column number
     * to create with the button
     */
    private int _cols;
    /**
     * Text to show indise the button
     */
    private String _text;
    /**
     * Callback of the button
     */
    private ButtonCallback _cb;
}
