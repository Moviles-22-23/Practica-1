package es.ucm.stalos.desktopengine;

import es.ucm.stalos.engine.IFileReader;
import es.ucm.stalos.engine.IFile;

public class DesktopFileReader implements IFileReader {
    public DesktopFileReader() {
    }

    @Override
    public IFile newFile(String _fileName) throws Exception {
        DesktopFile file = new DesktopFile("./assets/" + _fileName);
        if (!file.init()) throw new Exception();

        return file;
    }
}
