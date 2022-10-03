package es.ucm.stalos.desktopengine;

import java.io.FileInputStream;
import java.io.InputStream;

import es.ucm.stalos.engine.Font;

public class DesktopFont implements Font {
    public DesktopFont(String fileName, float size, boolean isBold) {
        _fileName = fileName;
        _size = size;
        _isBold = isBold;
    }

    public boolean init() {
        try (InputStream is = new FileInputStream(_fileName)) {
            _javaFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, is);
            setSize(_size);
        }
        catch (Exception e) {
            // Ouch. No está.
            System.err.println("Error: " + e);
            return false;
        }
        return true;
    }

    @Override
    public void setSize(float newSize) {
        _javaFont = _javaFont.deriveFont(newSize);
    }

    @Override
    public float getSize() {
        return _size;
    }


    public java.awt.Font getJavaFont() {
        return _javaFont;
    }

    private java.awt.Font _javaFont;
    private String _fileName;
    private float _size;
    private boolean _isBold;
}
