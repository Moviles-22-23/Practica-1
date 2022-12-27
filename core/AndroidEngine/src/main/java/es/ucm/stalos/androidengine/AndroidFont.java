package es.ucm.stalos.androidengine;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import es.ucm.stalos.engine.IFont;

public class AndroidFont implements IFont {

    public AndroidFont(String fileName, float size, boolean isBold, AssetManager assetManager) {
        _fileName = fileName;
        _size = size;
        _isBold = isBold;
        _assetManager = assetManager;
    }

    public boolean init() {
        _typeFace = Typeface.createFromAsset(_assetManager, _fileName);
        if (_typeFace == null) return false;
        return true;
    }

    @Override
    public void setSize(float newSize) {
        _size = newSize;
    }

    @Override
    public float getSize() {
        return _size;
    }

    public Typeface getAndroidFont() {
        return _typeFace;
    }

    private String _fileName;
    private boolean _isBold;
    private float _size;
    private Typeface _typeFace;
    private AssetManager _assetManager;
}
