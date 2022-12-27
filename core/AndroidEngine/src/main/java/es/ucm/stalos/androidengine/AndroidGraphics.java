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

import java.util.HashMap;

import es.ucm.stalos.engine.AbstractGraphics;
import es.ucm.stalos.engine.IFont;
import es.ucm.stalos.engine.IImage;

public class AndroidGraphics extends AbstractGraphics {
    protected AndroidGraphics(int w, int h, WindowManager windowManager, Window window) {
        super(w, h);
        _wManager = windowManager;
        _window = window;
        _assetManager = _window.getContext().getAssets();
        _paint = new Paint();
    }

    public boolean init(AndroidInput input, AppCompatActivity activity, SurfaceView surfaceView) {
        try {
            // ADDITIONAL FLAGS
            _window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            _window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


            // INPUT LISTENER
            _surfaceView = surfaceView;
            _surfaceView.setOnTouchListener(input);

            // WIN SIZE
            Point winSize = new Point();
            _wManager.getDefaultDisplay().getSize(winSize);
            _winSize = winSize;

            _holder = _surfaceView.getHolder();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //-----------------------------------------------------------------//

    /**
     * Creates and stores a new image ready to be used
     *
     * @param name     Image's name-key to store
     * @param fileName File name of the image with extension
     * @throws Exception if the creation fails
     */
    @Override
    public void newImage(String name, String fileName) throws Exception {
        AndroidImage img = new AndroidImage("images/" + fileName, _assetManager);
        if (!img.init())
            throw new Exception();

        _images.put(name, img);
    }

    /**
     * Creates and stores a new font ready to be used
     *
     * @param name     Font's name-key to store
     * @param fileName File name of the font with extension
     * @param size     Size of the font
     * @param isBold   Determines if the font will be bold
     * @throws Exception if the creation fails
     */
    @Override
    public void newFont(String name, String fileName, int size, boolean isBold) throws Exception {
        AndroidFont font = new AndroidFont("fonts/" + fileName, size, isBold, _assetManager);
        if (!font.init())
            throw new Exception();

        _fonts.put(name, font);
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
    public void drawImage(String imageName, int[] pos, float[] size) {
        if (!_images.containsKey(imageName)) {
            System.err.println("La imagen '" + imageName + "' no existe...");
            return;
        }
        IImage im = _images.get(imageName);

        Rect source = new Rect(0, 0, im.getWidth(), im.getHeight());
        Rect destiny = new Rect(pos[0], pos[1], (int) (pos[0] + size[0]), (int) (pos[1] + size[1]));
        _canvas.drawBitmap(((AndroidImage) im).getBitmap(), source, destiny, null);
    }

    @Override
    public void drawText(String text, String fontName, int[] pos) {
        if (!_fonts.containsKey(fontName)) {
            System.err.println("La fuente '" + fontName + "' no existe...");
            return;
        }

        IFont fo = _fonts.get(fontName);
        Typeface currFont = ((AndroidFont) fo).getAndroidFont();
        _paint.setTypeface(currFont);
        _paint.setTextSize(fo.getSize());
        _paint.setTextAlign(Paint.Align.LEFT);
        _canvas.drawText(text, pos[0], pos[1], _paint);
        _paint.reset();
    }

    @Override
    public void drawCenteredString(String text, String fontName, int[] pos, float[] size) {
        if (!_fonts.containsKey(fontName)) {
            System.err.println("La fuente '" + fontName + "' no existe...");
            return;
        }

        IFont fo = _fonts.get(fontName);
        Typeface currFont = ((AndroidFont) fo).getAndroidFont();
        _paint.setTypeface(currFont);
        _paint.setTextSize(fo.getSize());
        _paint.setTextAlign(Paint.Align.CENTER);

        // La posicion en x va a ser siempre la misma gracias al Aling.CENTER
        final int xPos = (int) (pos[0] + size[0] / 2);
        // yPos se modifica con los saltos del linea
        int numLines = text.split("\n").length;

        // Primera linea
        int yPos = (int) ((pos[1] + size[1] / 2) - ((_paint.descent() + _paint.ascent()) / 2) - ((_paint.descent() - _paint.ascent()) / 2) * (numLines - 1));
        for (String line : text.split("\n")) {

            _canvas.drawText(line, xPos, yPos, _paint);
            // Va aumentando la diferencia entre lineas
            yPos += _paint.descent() - _paint.ascent();
        }
//        _canvas.restore();
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
    private void paintRect(int[] pos, float[] size) {
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
        while (!_holder.getSurface().isValid()) {
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
    public void restore() {
        _holder.unlockCanvasAndPost(_canvas);
    }

//----------------------------------------------------------------//

    //----------------------------------------------------------------//
    // VARIABLES
    private final WindowManager _wManager;
    private final Window _window;
    private final Paint _paint;
    private final AssetManager _assetManager;

    private Point _winSize;
    private Canvas _canvas;
    private SurfaceView _surfaceView;
    private SurfaceHolder _holder;

    /**
     * Thickness of the rect lines
     */
    private final float _rectThick = 2.5f;
}
