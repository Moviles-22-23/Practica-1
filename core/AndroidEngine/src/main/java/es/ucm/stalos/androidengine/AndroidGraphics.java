package es.ucm.stalos.androidengine;

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
            // ADDITIONAL FLAGS
            _window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            _window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            SurfaceView surfaceView = new SurfaceView(activity.getApplicationContext());
            activity.setContentView(surfaceView);
            // INPUT LISTENER
            surfaceView.setOnTouchListener(input);

            // WIN SIZE
            Point winSize = new Point();
            _wManager.getDefaultDisplay().getSize(winSize);
            _winSize = winSize;

            _holder = surfaceView.getHolder();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

//-----------------------------------------------------------------//

    @Override
    public Image newImage(String name) throws Exception {
        AndroidImage img = new AndroidImage("images/" + name, _assetManager);
        if (!img.init()) throw new Exception();

        return img;
    }

    @Override
    public Font newFont(String filename, int size, boolean isBold) throws Exception {
        AndroidFont font = new AndroidFont("fonts/" + filename, size, isBold, _assetManager);
        if (!font.init()) throw new Exception();
        return font;
    }

//-----------------------------------------------------------------//

    @Override
    public void clear(int color) {
        setColor(color);
        _canvas.drawColor(color);
    }

    @Override
    public void setColor(int color) {
        int r = (color & 0xff000000) >> 24;
        int g = (color & 0x00ff0000) >> 16;
        int b = (color & 0x0000ff00) >> 8;
        int a = color & 0x000000ff;

        this._paint.setColor(Color.argb(a, r, g, b));
    }

//-----------------------------------------------------------------//

    @Override
    public void drawImage(Image image, int[] pos, float[] size) {
        Rect source = new Rect(0, 0, image.getWidth(), image.getHeight());
        Rect destiny = new Rect(pos[0], pos[1], (int) (pos[0] + size[0]), (int) (pos[1] + size[1]));
        _canvas.drawBitmap(((AndroidImage) image).getBitmap(), source, destiny, null);
    }

    @Override
    public void drawText(String text, int[] pos, Font font) {
        Typeface currFont = ((AndroidFont) font).getAndroidFont();
        _paint.setTypeface(currFont);
        _paint.setTextSize(font.getSize());
        _paint.setTextAlign(Paint.Align.LEFT);
        _canvas.drawText(text, pos[0], pos[1], _paint);
        _paint.reset();
    }

    @Override
    public void drawCenteredString(String text, int[] pos, float[] size, Font font) {
        Typeface currFont = ((AndroidFont) font).getAndroidFont();
        _paint.setTypeface(currFont);
        _paint.setTextSize(font.getSize());
        _paint.setTextAlign(Paint.Align.LEFT);
        _canvas.drawText(text, pos[0], pos[1], _paint);
        _paint.reset();
    }

    @Override
    public void drawRect(int[] pos, float side) {
        _paint.setStyle(Paint.Style.STROKE);
        float[] s = {side, side};
        paintRect(pos, s);
    }

    @Override
    public void drawRect(int[] pos, float[] size) {
        _paint.setStyle(Paint.Style.STROKE);
        paintRect(pos, size);
    }

    @Override
    public void drawLine(int[] start, int[] end) {
        _canvas.drawLine(start[0], start[1], end[0], end[1], _paint);
    }

    @Override
    public void fillSquare(int[] pos, float side) {
        _paint.setStyle(Paint.Style.FILL);
        float[] s = {side, side};
        paintRect(pos, s);
    }

    @Override
    public void fillSquare(int[] pos, float[] size) {
        _paint.setStyle(Paint.Style.FILL);
        paintRect(pos, size);
    }

    /**
     * Support function to drawRect(...)
     */
    private void paintRect(int[] pos, float[] size){
        _paint.setStrokeWidth(_rectThick);
        _canvas.drawRect(pos[0], pos[1], pos[0] + size[0], pos[1] + size[1], _paint);
        _paint.reset();
    }
//----------------------------------------------------------------//

    @Override
    public int getWidth() {
        return _winSize.x;
    }

    @Override
    public int getHeight() {
        return _winSize.y;
    }

//----------------------------------------------------------------//

    @Override
    public void prepareFrame() {
        while (!_holder.getSurface().isValid())
        {
            System.out.println("PREPARE FRAME: NULL");
        }

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

//----------------------------------------------------------------//

    public Canvas getCanvas() {
        return _canvas;
    }

//----------------------------------------------------------------//
    // VARIABLES
    private final WindowManager _wManager;
    private final Window _window;
    private final Paint _paint;
    private final AssetManager _assetManager;

    private Point _winSize;
    private Canvas _canvas;
    private SurfaceHolder _holder;

    /**
     * Thickness of the rect lines
     */
    private final float _rectThick = 2.5f;
}
