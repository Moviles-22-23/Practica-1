package es.ucm.stalos.desktopengine;

import es.ucm.stalos.engine.IImage;

import java.io.File;

import javax.imageio.ImageIO;

public class DesktopImage implements IImage {
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
        return _image.getWidth(null);
    }

    @Override
    public int getHeight() {
        return _image.getHeight(null);
    }

    public java.awt.Image getImage() {
        return _image;
    }

    private java.awt.Image _image;
    private String _filename;
}
