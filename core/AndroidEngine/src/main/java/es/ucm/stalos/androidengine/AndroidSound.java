package es.ucm.stalos.androidengine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;

import es.ucm.stalos.engine.ISound;

public class AndroidSound implements ISound {
    public AndroidSound(String filename, AssetManager assetManager) {
        _filename = filename;
        _assetManager = assetManager;
    }

    public boolean init() {
        try {
            _assetDescriptor = _assetManager.openFd(_filename);

        } catch (Exception e) {
            System.err.println("Couldn't load audio file");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public int getPlayId() {
        return _playId;
    }

    public void setPlayId(int playId) {
        _playId = playId;
    }

    public AssetFileDescriptor getAssetDescriptor() {
        return _assetDescriptor;
    }

    private String _filename;
    private AssetFileDescriptor _assetDescriptor;
    private AssetManager _assetManager;
    private int _id, _playId;
}
