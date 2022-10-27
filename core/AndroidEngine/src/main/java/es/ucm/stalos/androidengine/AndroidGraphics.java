package es.ucm.stalos.androidengine;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import es.ucm.stalos.engine.AbstractGraphics;
import es.ucm.stalos.engine.Engine;
import es.ucm.stalos.engine.Font;
import es.ucm.stalos.engine.Image;

public class AndroidGraphics extends AbstractGraphics {
    protected AndroidGraphics(int w, int h, WindowManager windowManager, Window window) {
        super(w, h);
        _wManager = windowManager;
        _window = window;
        _assetManager = _window.getContext().getAssets();
        _paint = new Paint();
    }

    public boolean init(AndroidInput input, AppCompatActivity activity) {
        try {
            SurfaceView surfaceView = new SurfaceView(activity.getApplicationContext());

            surfaceView.setOnTouchListener(input);
            activity.setContentView(surfaceView);
            Point winSize = new Point();
            _wManager.getDefaultDisplay().getSize(winSize);
            _winSize = winSize;
            _holder = surfaceView.getHolder();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Image newImage(String name) throws Exception {
        AndroidImage img = new AndroidImage(name, _assetManager);
        if (!img.init()) throw new Exception();

        return img;
    }

    @Override
    public Font newFont(String filename, int size, boolean isBold) throws Exception {
        AndroidFont font = new AndroidFont(filename, size, isBold, _assetManager);
        if (!font.init()) throw new Exception();
        return font;
    }

    @Override
    public void clear(int color) {
        setColor(color);
        _canvas.drawRect(0, 0, getWidth(), getHeight(), _paint);
    }

    @Override
    public void drawImage(Image image, int[] pos, float[] size) {
        int[] finalPos = finalPosition(pos[0], pos[1]);
        //int finalSize=finalSize(finalSize())
        _canvas.drawBitmap(((AndroidImage) image).getBitmap(), finalPos[0], finalPos[1], _paint);
    }

    @Override
    public void setColor(int color) {
        int r = (color & 0xff000000) >> 24;
        int g = (color & 0x00ff0000) >> 16;
        int b = (color & 0x0000ff00) >> 8;
        int a = color & 0x000000ff;

        _paint.setColor(Color.argb(a, r, g, b));
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
        Typeface androidFont = ((AndroidFont) font).getAndroidFont();
        int[] finalPos = finalPosition(pos[0], pos[1]);

        _paint.setTextSize(font.getSize());
        _paint.setTypeface(androidFont);
        _canvas.drawText(text, finalPos[0], finalPos[1], _paint);
    }

    @Override
    public void drawCenteredString(String text, int[] pos, float[] size, Font font) {

    }

    @Override
    public int getWidth() {
        return _winSize.x;
    }

    @Override
    public int getHeight() {
        return _winSize.y;
    }

    @Override
    public void prepareFrame() {
        while (!_holder.getSurface().isValid()) ;
        _canvas = _holder.lockCanvas();

        super.prepareFrame();
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
        //_canvas.restore();
        _holder.unlockCanvasAndPost(_canvas);
    }

    public Canvas getCanvas() {
        return _canvas;
    }

    // VARIABLES
    private final WindowManager _wManager;
    private final Window _window;
    private final Paint _paint;
    private final AssetManager _assetManager;

    private Point _winSize;
    private Canvas _canvas;
    private SurfaceHolder _holder;

}
