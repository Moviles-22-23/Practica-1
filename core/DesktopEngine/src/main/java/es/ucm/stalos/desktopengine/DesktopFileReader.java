package es.ucm.stalos.desktopengine;

import es.ucm.stalos.engine.FileReader;
import es.ucm.stalos.engine.IFile;

public class DesktopFileReader implements FileReader {
    public DesktopFileReader() {
    }

    @Override
    public IFile newFile(String _fileName) throws Exception {
        DesktopFile file = new DesktopFile("./assets/" + _fileName);
        if (!file.init()) throw new Exception();

        return file;
    }
}
