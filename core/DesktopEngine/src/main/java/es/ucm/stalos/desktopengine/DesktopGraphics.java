package es.ucm.stalos.desktopengine;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import es.ucm.stalos.engine.AbstractGraphics;
import es.ucm.stalos.engine.IEngine;
import es.ucm.stalos.engine.IFont;
import es.ucm.stalos.engine.IImage;

public class DesktopGraphics extends AbstractGraphics {

    public DesktopGraphics(String title, IEngine engine, int w, int h) {
        super(w, h, 31, 1, 1, 1);
        _mainEngine = engine;
        _title = title;
    }

    public boolean init() {
        // Creación de la ventana
        _screen = new DesktopScreen(_title);
        _screen.addMouseListener((DesktopInput) _mainEngine.getInput());
        _screen.addMouseMotionListener((DesktopInput) _mainEngine.getInput());

        return _screen.init((int) _logWidth, (int) _logHeight);
    }

    public BufferStrategy getStrategy() {
        return _screen.getStrategy();
    }

//------------------------------------------------------------------------------------------------//

    @Override
    public void newImage(String name, String fileName) throws Exception {
        DesktopImage img = new DesktopImage("./assets/images/" + fileName);
        if (!img.init())
            throw new Exception();

        _images.put(name, img);
    }

    @Override
    public void newFont(String name, String fileName, int size, boolean isBold) throws Exception {
        DesktopFont newFont = new DesktopFont("./assets/fonts/" + fileName, size, isBold);
        if (!newFont.init())
            throw new Exception();

        _fonts.put(name, newFont);
    }

    //------------------------------------------------------------------------------------------------//
    @Override
    public void clear(int color) {
        while (getStrategy() == null) {
            System.out.println("NULL");
        }
        _graphics = getStrategy().getDrawGraphics();
        setColor(color);
        _graphics.fillRect(0, 0, getWidth(), getHeight());
        _graphics.setPaintMode();
    }

    @Override
    public void setColor(int color) {
        float r = ((color >> 24) & 0xff) / 255.0f;
        float g = ((color >> 16) & 0xff) / 255.0f;
        float b = ((color >> 8) & 0xff) / 255.0f;
        float a = ((color) & 0xff) / 255.0f;

        Color c = new Color(r, g, b, a);
        _graphics.setColor(c);
    }

//------------------------------------------------------------------------------------------------//

    @Override
    public void drawImage(String imageName, int[] pos, float[] size) {
        if (!_images.containsKey(imageName)) {
            System.err.println("La imagen '" + imageName + "' no existe...");
            return;
        }

        IImage im = _images.get(imageName);
        int[] newPos = transformPosition(pos[0], pos[1]);
        int[] newSize = transformSize(size[0], size[1]);
        _graphics.drawImage(((DesktopImage) im).getImage(), newPos[0], newPos[1],
                newSize[0], newSize[1], null);
        _graphics.setPaintMode();
    }

    @Override
    public void drawRect(int[] pos, float side) {
        int[] newPos = transformPosition(pos[0], pos[1]);
        int newSize = transformSize(side);
        _graphics.drawRect(newPos[0], newPos[1], newSize, newSize);
        _graphics.setPaintMode();
    }

    @Override
    public void drawRect(int[] pos, float[] size) {
        int[] newPos = transformPosition(pos[0], pos[1]);
        int[] newSize = transformSize(size[0], size[1]);
        _graphics.drawRect(newPos[0], newPos[1], newSize[0], newSize[1]);
        _graphics.setPaintMode();
    }

    @Override
    public void drawLine(int[] start, int[] end) {
        int[] point1 = transformPosition(start[0], start[1]);
        int[] point2 = transformPosition(end[0], end[1]);
        _graphics.drawLine(point1[0], point1[1], point2[0], point2[1]);
        _graphics.setPaintMode();
    }

    private java.awt.Font initFont(IFont font) {
        java.awt.Font javaFont = ((DesktopFont) font).getJavaFont();
        float tam = transformSize(font.getSize());
        javaFont = javaFont.deriveFont(tam);
        // Set the font
        _graphics.setFont(javaFont);

        return javaFont;
    }

    @Override
    public void drawText(String text, String fontName, int[] pos) {
        if (!_fonts.containsKey(fontName)) {
            System.err.println("La fuente '" + fontName + "' no existe...");
            return;
        }

        IFont fo = _fonts.get(fontName);

        // Init font
        java.awt.Font javaFont = initFont(fo);

        // Scale
        int[] newPos = transformPosition(pos[0], pos[1]);

        // Drawing
        _graphics.drawString(text, newPos[0], newPos[1]);
        _graphics.setPaintMode();
    }

    @Override
    public void drawCenteredString(String text, String fontName, int[] pos, float[] size) {
        if (!_fonts.containsKey(fontName)) {
            System.err.println("La fuente '" + fontName + "' no existe...");
            return;
        }
        IFont fo = _fonts.get(fontName);

        // Init font
        java.awt.Font javaFont = initFont(fo);

        // Calculates de logic pos and size
        int[] logicPos = transformPosition(pos[0], pos[1]);
        int[] logicSize = transformSize(size[0], size[1]);

        // Get the FontMetrics
        FontMetrics metrics = _graphics.getFontMetrics(javaFont);

        // Determine the X coordinate for the text
        int x = logicPos[0] + (logicSize[0] - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java_2D 0 is top of the screen)
        int y = logicPos[1] + ((logicSize[1] - metrics.getHeight()) / 2) + metrics.getAscent();

        // Drawing
        _graphics.drawString(text, x, y);
        _graphics.setPaintMode();
    }

//------------------------------------------------------------------------------------------------//

    @Override
    public void fillSquare(int[] pos, float side) {
        int[] newPos = transformPosition(pos[0], pos[1]);
        int newSize = transformSize(side);
        _graphics.fillRect(newPos[0], newPos[1], newSize, newSize);
        _graphics.setPaintMode();
    }

    @Override
    public void fillSquare(int[] pos, float[] size) {
        int[] newPos = transformPosition(pos[0], pos[1]);
        int[] newSize = transformSize(size[0], size[1]);
        _graphics.fillRect(newPos[0], newPos[1], newSize[0], newSize[1]);
        _graphics.setPaintMode();
    }

//------------------------------------------------------------------------------------------------//

    @Override
    public int getWidth() {
        return _screen.getWidth();
    }

    @Override
    public int getHeight() {
        return _screen.getHeight();
    }

//------------------------------------------------------------------------------------------------//

    @Override
    public void prepareFrame() {
        while (getStrategy() == null) {
            System.out.println("PREPARE FRAME: NULL");
        }
        _graphics = getStrategy().getDrawGraphics();

        super.prepareFrame();
    }

    @Override
    public void translate(int x, int y) {
        _graphics.translate(x, y);
    }

    @Override
    public void scale(float x, float y) {
        ((Graphics2D) _graphics).scale(x, y);
    }

    @Override
    public void restore() {
        _graphics.dispose();
    }

//------------------------------------------------------------------------------------------------//

    // VARIABLES
    private final IEngine _mainEngine;
    private final String _title;
    private DesktopScreen _screen;
    private java.awt.Graphics _graphics;
}
