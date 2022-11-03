package es.ucm.stalos.engine;

public interface Engine {
    /**
     * Request a change state. It is used to not change immediately, but
     * to do later.
     * @param newState New state to change
     */
    public void reqNewState(State newState);

    /**
     * @return Instance of graphic engine
     */
    Graphics getGraphics();

    /**
     * @return Instance of input manager
     */
    Input getInput();

    /**
     * @return Instace of audio manager
     */
    Audio getAudio();

    /**
     * Create and return a new file
     */
    IFile newFile(String _fileName) throws Exception;
}
