package es.ucm.stalos.desktopengine;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;

import es.ucm.stalos.engine.AbstractGraphics;
import es.ucm.stalos.engine.Engine;
import es.ucm.stalos.engine.Font;
import es.ucm.stalos.engine.Image;

public class DesktopGraphics extends AbstractGraphics {

    public DesktopGraphics(String title, Engine engine, int w, int h) {
        super(w, h);
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

    public DesktopScreen getScreen() {
        return _screen;
    }

    public BufferStrategy getStrategy() {
        return _screen.getStrategy();
    }

    public java.awt.Graphics getJavaGraphics() {
        return _graphics;
    }

//------------------------------------------------------------------------------------------------//

    @Override
    public Image newImage(String name) throws Exception {
        DesktopImage img = new DesktopImage("./assets/images/" + name);
        if (!img.init()) throw new Exception();
        return img;
    }

    @Override
    public Font newFont(String filename, int size, boolean isBold) throws Exception {
        DesktopFont newFont = new DesktopFont("./assets/fonts/" + filename, size, isBold);
        if (!newFont.init())
            throw new Exception();
        return newFont;
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
    public void drawImage(Image image, int[] pos, float[] size) {
        int[] newPos = finalPosition(pos[0], pos[1]);
        int[] newSize = finalSize(size[0], size[1]);
        _graphics.drawImage(((DesktopImage) image).getImage(), newPos[0], newPos[1],
                newSize[0], newSize[1], null);
        _graphics.setPaintMode();
    }

    @Override
    public void drawRect(int[] pos, float side) {
        int[] newPos = finalPosition(pos[0], pos[1]);
        int newSize = finalSize(side);
        _graphics.drawRect(newPos[0], newPos[1], newSize, newSize);
        _graphics.setPaintMode();
    }

    @Override
    public void drawRect(int[] pos, float[] size) {
        int[] newPos = finalPosition(pos[0], pos[1]);
        int[] newSize = finalSize(size[0], size[1]);
        _graphics.drawRect(newPos[0], newPos[1], newSize[0], newSize[1]);
        _graphics.setPaintMode();
    }

    @Override
    public void drawLine(int[] start, int[] end) {
        int[] point1 = finalPosition(start[0], start[1]);
        int[] point2 = finalPosition(end[0], end[1]);
        _graphics.drawLine(point1[0], point1[1], point2[0], point2[1]);
        _graphics.setPaintMode();
    }

    @Override
    public void drawText(String text, int[] pos, Font font) {
        // Init font
        java.awt.Font javaFont = initFont(font);

        // Scale
        int[] newPos = finalPosition(pos[0], pos[1]);

        // Drawing
        _graphics.drawString(text, newPos[0], newPos[1]);
        _graphics.setPaintMode();
    }

    private java.awt.Font initFont(Font font)
    {
        java.awt.Font javaFont = ((DesktopFont) font).getJavaFont();
        float tam = finalSize(font.getSize());
        javaFont = javaFont.deriveFont(tam);
        // Set the font
        _graphics.setFont(javaFont);

        return javaFont;
    }

    @Override
    public void drawCenteredString(String text, int[] pos, float[] size, Font font) {
        java.awt.Font javaFont = initFont(font);

        // Scale
        // Get the FontMetrics
        FontMetrics metrics = _graphics.getFontMetrics(javaFont);
        // Determine the X coordinate for the text
        int x = pos[0] + ((int) size[0] - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java_2D 0 is top of the screen)
        int y = pos[1] + (((int) size[1] - metrics.getHeight()) / 2) + metrics.getAscent();
        // Draw the String
        int[] newPos = finalPosition(x, y);

        // Drawing
        _graphics.drawString(text, newPos[0], newPos[1]);
        _graphics.setPaintMode();
    }

//------------------------------------------------------------------------------------------------//

    @Override
    public void fillSquare(int[] pos, float side) {
        int[] newPos = finalPosition(pos[0], pos[1]);
        int newSize = finalSize(side);
        _graphics.fillRect(newPos[0], newPos[1], newSize, newSize);
        _graphics.setPaintMode();
    }

    @Override
    public void fillSquare(int[] pos, float[] size) {
        int[] newPos = finalPosition(pos[0], pos[1]);
        int[] newSize = finalSize(size[0], size[1]);
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
        ((Graphics2D) _graphics).translate(x, y);
    }

    @Override
    public void scale(float x, float y) {
        ((Graphics2D) _graphics).scale(x, y);
    }

    @Override
    public void save() {

    }

    @Override
    public void restore() {
        _graphics.dispose();
    }

//------------------------------------------------------------------------------------------------//

    // VARIABLES
    private final Engine _mainEngine;
    private final String _title;
    private DesktopScreen _screen;
    private java.awt.Graphics _graphics;
}
