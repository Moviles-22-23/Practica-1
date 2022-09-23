package es.ucm.stalos.desktopengine;

import es.ucm.stalos.engine.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class DesktopImage implements Image {
    public DesktopImage(String filename) {
        _filename = filename;
    }

    public boolean init() {
        try {
            _image = ImageIO.read(new File(_filename));
        } catch (Exception e) {
            System.err.println("Error: --Failed loading the image " + e);
            return false;
        }

        return true;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    public java.awt.Image getImage() {
        return _image;
    }

    private java.awt.Image _image;
    private String _filename;
}
