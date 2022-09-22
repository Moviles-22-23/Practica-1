package es.ucm.stalos.engine;

public interface Engine {
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
}
