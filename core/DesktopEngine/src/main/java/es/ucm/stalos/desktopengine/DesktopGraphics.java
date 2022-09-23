package es.ucm.stalos.desktopengine;

import java.awt.Color;

import es.ucm.stalos.engine.AbstractGraphics;
import es.ucm.stalos.engine.Font;
import es.ucm.stalos.engine.Image;

public class DesktopGraphics extends AbstractGraphics {

    public DesktopGraphics(String winTitle, int w, int h) {
        super(w, h);
    }

    @Override
    public Image newImage(String name) {
        DesktopImage img = new DesktopImage(name);
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
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    private java.awt.Graphics _graphics;
}
