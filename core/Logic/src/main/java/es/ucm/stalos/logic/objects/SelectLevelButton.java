package es.ucm.stalos.logic.objects;

import es.ucm.stalos.engine.IFont;
import es.ucm.stalos.engine.IGraphics;
import es.ucm.stalos.logic.enums.GridType;
import es.ucm.stalos.logic.interfaces.ButtonCallback;

public class SelectLevelButton {
    public SelectLevelButton(int[] pos, float[] size, GridType gridType, String fontName) {
        _buttonPos[0] = pos[0];
        _buttonPos[1] = pos[1];

        _buttonSize[0] = size[0];
        _buttonSize[1] = size[1];

        _gridType = gridType;
        _fontName = fontName;
    }

    public void render(IGraphics gr) {
        gr.drawRect(_buttonPos, _buttonSize);
        gr.drawCenteredString(_gridType.getText(), _fontName, _buttonPos, _buttonSize);
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
        return _gridType.getRows();
    }

    public int getCols() {
        return _gridType.getCols();
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
     * Name-Key of the font to be used
     */
    private String _fontName;
    private GridType _gridType;
    /**
     * Callback of the button
     */
    private ButtonCallback _cb;
}
