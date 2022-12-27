package es.ucm.stalos.androidengine;

import android.content.res.AssetManager;

import es.ucm.stalos.engine.IFileReader;
import es.ucm.stalos.engine.IFile;

public class AndroidFileReader implements IFileReader {
    public AndroidFileReader(AssetManager aMan)
    {
        _assetsMan = aMan;
    }

    @Override
    public IFile newFile(String _fileName) throws Exception {
        AndroidFile file = new AndroidFile(_fileName, _assetsMan);
        if (!file.init()) throw new Exception();

        return file;
    }

    AssetManager _assetsMan;
}
