package es.ucm.stalos.engine;

public interface IEngine {
    /**
     * Request a change state. It is used to not change immediately, but
     * to do later.
     * @param newState New state to change
     */
    public void reqNewState(IState newState);

    /**
     * @return Instance of graphic engine
     */
    IGraphics getGraphics();

    /**
     * @return Instance of input manager
     */
    IInput getInput();

    /**
     * @return Instace of audio manager
     */
    IAudio getAudio();

    /**
     * @return Instance of File Reader
     */
    IFileReader getFileReader();
}
