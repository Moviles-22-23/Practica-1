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

        _textPos[0] = _buttonPos[0];
        _textPos[1] = _buttonPos[1] + (int)_buttonSize[1] / 5 * 3;
        initType(gridType);

        _font = font;
    }

    private void initType(GridType gridType)
    {
        switch (gridType) {
            case _4x4:
                _rows = 4;
                _cols = 4;
                _textPos[0] += (int)_buttonSize[0] / 13 * 3;
                _text = 4 + "x" + 4;
                break;
            case _5x5:
                _rows = 5;
                _cols = 5;
                _textPos[0] += (int)_buttonSize[0] / 15 * 4;
                _text = 5 + "x" + 5;
                break;
            case _5x10:
                _rows = 5;
                _cols = 10;
                _textPos[0] += (int)_buttonSize[0] / 5;
                _text = 5 + "x" + 10;
                break;
            case _8x8:
                _rows = 8;
                _cols = 8;
                _textPos[0] += (int)_buttonSize[0] / 15 * 4;
                _text = 8 + "x" + 8;
                break;
            case _10x10:
                _rows = 10;
                _cols = 10;
                _textPos[0] += (int)_buttonSize[0] / 17 * 3;
                _text = 10 + "x" + 10;
                break;
            case _10x15:
                _rows = 10;
                _cols = 15;
                _textPos[0] += (int)_buttonSize[0] / 17 * 3;
                _text = 10 + "x" + 15;
                break;
        }
    }

    public void render(Graphics gr) {
        gr.drawRect(_buttonPos, _buttonSize);
        gr.drawCenteredString(_text, _buttonPos, _buttonSize, _font);
    }

    public void setCallback(ButtonCallback cb)
    {
        _cb = cb;
    }

    public void doSomething() {
        _cb.doSomething();
    }

    public int[] getPos() {
        return _buttonPos;
    }

    public float[] getSize() {
        return _buttonSize;
    }

    public int getRows() {return _rows;}
    public int getCols() {return _cols;}

    private final int[] _buttonPos = new int[2];
    private final int[] _textPos = new int[2];
    private final float[] _buttonSize = new float[2];
    private final Font _font;
    private int _rows;
    private int _cols;
    private String _text;
    private ButtonCallback _cb;
}
