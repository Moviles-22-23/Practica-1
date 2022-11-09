package es.ucm.stalos.engine;

public interface FileReader {

    /**
     * Create and return a new file
     */
    IFile newFile(String _fileName) throws Exception;
}
