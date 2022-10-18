package es.ucm.stalos.androidengine;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import es.ucm.stalos.engine.AbstractGraphics;
import es.ucm.stalos.engine.Engine;
import es.ucm.stalos.engine.Font;
import es.ucm.stalos.engine.Image;

public class AndroidGraphics extends AbstractGraphics {
    protected AndroidGraphics(int w, int h, Canvas canvas, AssetManager assetManager) {
        super(w, h);
        _canvas = canvas;
        _assetManager = assetManager;
    }

    public boolean init() {
        _paint = new Paint();
        return _paint != null;
    }

    @Override
    public Image newImage(String name) throws Exception {
        AndroidImage img = new AndroidImage(name, _assetManager);
        if (!img.init()) throw new Exception();

        return img;
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
        _canvas.drawBitmap(((AndroidImage) image).getBitmap(), pos[0], pos[1], _paint);
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
    public void fillSquare(int[] pos, float[] size) {
        int[] newPos = finalPosition(pos[0], pos[1]);
        int[] newSize = finalSize(size[0], size[1]);

        _canvas.drawRect(newPos[0], newPos[1], newSize[0], newSize[1], _paint);
    }

    @Override
    public void drawRect(int[] pos, float side) {
        int[] finalPos = finalPosition(pos[0], pos[1]);
        int finalSize = finalSize(side);

        _canvas.drawRect(finalPos[0], finalPos[1], finalSize, finalSize, _paint);
    }

    @Override
    public void drawRect(int[] pos, float[] size) {

    }

    @Override
    public void drawLine(int[] start, int[] end) {
        int[] finalStart = finalPosition(start[0], start[1]);
        int[] finalEnd = finalPosition(start[0], start[1]);

        _canvas.drawLine(finalStart[0], finalStart[1], finalEnd[0], finalEnd[1], _paint);
    }

    @Override
    public void drawText(String text, int[] pos, Font font) {

    }

    @Override
    public void drawCenteredString(String text, int[] pos, float[] size, Font font) {

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

    public Canvas getCanvas() {
        return _canvas;
    }

    public void setCanvas(Canvas c) {
        _canvas = c;
    }

    // VARIABLES
    private Canvas _canvas;
    private Paint _paint;
    private AssetManager _assetManager;
}
