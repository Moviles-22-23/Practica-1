package es.ucm.stalos.androidengine;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

import es.ucm.stalos.engine.Image;

public class AndroidImage implements Image {
    public AndroidImage(String filename, AssetManager assetManager) {
        _filename = filename;
        _assetManager = assetManager;
    }

    public boolean init() {
        InputStream is = null;
        try {
            is = _assetManager.open(_filename);
            _bitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            System.err.println("Error: --Failed loading the image " + e);
            return false;
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException("InputStream null en la carga del asset: " + _filename);
                }
            }
        }
        return true;
    }

    @Override
    public int getWidth() {
        return _bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return _bitmap.getHeight();
    }

    public Bitmap getBitmap() {
        return _bitmap;
    }

    private Bitmap _bitmap;
    private AssetManager _assetManager;
    private String _filename;
}
