package es.ucm.stalos.logic.objects;

import javax.security.auth.callback.Callback;

import es.ucm.stalos.engine.Font;
import es.ucm.stalos.engine.Graphics;
import es.ucm.stalos.engine.State;
import es.ucm.stalos.logic.enums.GridSize;
import es.ucm.stalos.logic.interfaces.ButtonCallback;
import es.ucm.stalos.logic.states.GameState;

public class SelectLevelButton {
    public SelectLevelButton(int[] pos, float[] size, GridSize gridType, Font font, ButtonCallback cb) {
        _buttonPos[0] = pos[0];
        _buttonPos[1] = pos[1];

        _buttonSize[0] = size[0];
        _buttonSize[1] = size[1];

        _textPos[0] = _buttonPos[0];
        _textPos[1] = _buttonPos[1] + (int)_buttonSize[1] / 5 * 3;
        switch (gridType) {
            case _4x4:
                _textPos[0] += (int)_buttonSize[0] / 13 * 3;
                _text = 4 + "x" + 4;
                break;
            case _5x5:
                _textPos[0] += (int)_buttonSize[0] / 15 * 4;
                _text = 5 + "x" + 5;
                break;
            case _5x10:
                _textPos[0] += (int)_buttonSize[0] / 5;
                _text = 5 + "x" + 10;
                break;
            case _8x8:
                _textPos[0] += (int)_buttonSize[0] / 15 * 4;
                _text = 8 + "x" + 8;
                break;
            case _10x10:
                _textPos[0] += (int)_buttonSize[0] / 17 * 3;
                _text = 10 + "x" + 10;
                break;
            case _10x15:
                _textPos[0] += (int)_buttonSize[0] / 17 * 3;
                _text = 10 + "x" + 15;
                break;
        }

        _font = font;

        _cb = cb;
    }

    public void render(Graphics gr) {
        gr.drawRect(_buttonPos, _buttonSize);

        gr.drawText(_text, _textPos, _font);
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

    private final int[] _buttonPos = new int[2];
    private final int[] _textPos = new int[2];
    private final float[] _buttonSize = new float[2];
    private final Font _font;
    private String _text;
    private final ButtonCallback _cb;
}
