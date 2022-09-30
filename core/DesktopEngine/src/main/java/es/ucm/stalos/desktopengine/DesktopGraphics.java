package es.ucm.stalos.desktopengine;

import java.awt.Color;
import java.awt.Graphics2D;
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

    //---------------------------------------//
    @Override
    public Image newImage(String name) {
        DesktopImage img = new DesktopImage("./assets/" + name);
        if (img.init()) return img;
        return null;
    }

    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        return null;
    }

    @Override
    public void clear(int color) {
        setColor(color);
        _graphics.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void drawImage(Image image, int[] pos, float[] size) {
//        int[] newPos = realPos(x, y);
//        int[] newSize = realSize(w, h);
        _graphics.drawImage(((DesktopImage) image).getImage(), pos[0], pos[1],
                (int)size[0], (int)size[1], null);
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

    @Override
    public void fillSquare(int[] pos, float side) {
        int[] finalPos = finalPosition(pos[0], pos[1]);
        int finalSize = finalSize(side);
        _graphics.fillRect(finalPos[0], finalPos[1], finalSize, finalSize);
    }

    @Override
    public void drawSquare(int[] pos, float side) {
        int[] finalPos = finalPosition(pos[0], pos[1]);
        int finalSize = finalSize(side);
        _graphics.drawRect(finalPos[0], finalPos[1], finalSize, finalSize);
    }

    @Override
    public void drawLine(int[] start, int[] end) {
        int[] finalStart = finalPosition(start[0], start[1]);
        int[] finalEnd = finalPosition(end[0], end[1]);
        _graphics.drawLine(finalStart[0], finalStart[1], finalEnd[0], finalEnd[1]);
    }

    @Override
    public void drawText(String text, int[] pos) {

    }

    @Override
    public int getWidth() {
        return _screen.getWidth();
        //return 0;
    }

    @Override
    public int getHeight() {
        return _screen.getHeight();
        //return 0;
    }

    @Override
    public void updateGraphics() {
        while (getStrategy() == null) {
            System.out.println("NULL");
        }
        _graphics = getStrategy().getDrawGraphics();
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

    // VARIABLES
    private final Engine _mainEngine;
    private final String _title;
    private DesktopScreen _screen;
    private java.awt.Graphics _graphics;
}
