package es.ucm.stalos.desktopengine;

import java.io.BufferedReader;
import java.io.FileReader;

import es.ucm.stalos.engine.IFile;

public class DesktopFile implements IFile {
    public DesktopFile(String fileName)
    {
        _fileName = fileName;
    }

    public boolean init() {
        try {
            FileReader fr = new FileReader(_fileName);
            _br = new BufferedReader(fr);
        } catch (Exception e) {
            System.err.println("Error: --Failed loading the file " + _fileName + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public BufferedReader getBufferReader() {
        return _br;
    }

    private String _fileName;
    private BufferedReader _br;
}
