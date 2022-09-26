package es.ucm.stalos.androidengine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import es.ucm.stalos.engine.AbstractGraphics;
import es.ucm.stalos.engine.Font;
import es.ucm.stalos.engine.Image;

public class AndroidGraphics extends AbstractGraphics {
    protected AndroidGraphics(int w, int h) {
        super(w, h);
    }

    public boolean init(){
        return true;
    }

    @Override
    public Image newImage(String name) {
        return null;
    }

    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        return null;
    }

    @Override
    public void clear(int color) {
        setColor(color);
        _canvas.drawRect(0, 0, getWidth(), getHeight(), _paint);
    }

    @Override
    public void drawImage(Image image, int[] pos, float[] size) {

    }

    @Override
    public void setColor(int color) {
        _paint.setColor(color);
    }

    @Override
    public void fillSquare(int[] pos, float side) {
        int[] finalPos = finalPosition(pos[0], pos[1]);
        int finalSize = finalSize(side);

        _canvas.drawRect(finalPos[0], finalPos[1], finalSize, finalSize, _paint);
    }

    @Override
    public void drawSquare(int[] pos, float side) {
        int[] finalPos = finalPosition(pos[0], pos[1]);
        int finalSize = finalSize(side);

        _canvas.drawRect(finalPos[0], finalPos[1], finalSize, finalSize, _paint);
    }

    @Override
    public void drawLine(int[] start, int[] end) {
        int[] finalStart = finalPosition(start[0], start[1]);
        int[] finalEnd = finalPosition(start[0], start[1]);

        _canvas.drawLine(finalStart[0], finalStart[1], finalEnd[0], finalEnd[1], _paint);
    }

    @Override
    public void drawText(String text, int[] pos) {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void updateGraphics() {

    }

    @Override
    public void translate(int x, int y) {
        _canvas.translate(x, y);
    }

    @Override
    public void scale(float x, float y) {
        _canvas.scale(x, y);
    }

    @Override
    public void save() {
        _canvas.save();
    }

    @Override
    public void restore() {
        _canvas.restore();
    }

    private Canvas _canvas;
    private Paint _paint;
}
