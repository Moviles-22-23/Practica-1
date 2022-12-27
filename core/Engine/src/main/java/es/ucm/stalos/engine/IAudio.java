package es.ucm.stalos.engine;

public interface IAudio {
    /**
     * Creates and stores a new sound ready to be used
     *
     * @param name     Sound's name-key to store
     * @param fileName File name of the sound with extension
     * @throws Exception if the creation fails
     */
    void newSound(String name, String fileName) throws Exception;

    /**
     * Play an specified sound indicating the number of looping
     *
     * @param soundName   Name-Key of the sound to be played
     * @param numberLoops Indicate number of extra-repetitions: -1 -> Infinite
     */
    void playSound(String soundName, int numberLoops);

    /**
     * Play a looped specified sound
     *
     * @param soundName Sound to be played
     */
    void playMusic(String soundName);

    /**
     * Pause an specified sound. It can be resume.
     *
     * @param soundName Sound to be played
     */
    void pause(String soundName);

    /**
     * Pause the background music
     *
     * @param soundName Sound to be played
     */
    void pauseMusic(String soundName);

    /**
     * Stopped an specified sound. Restart the clip.
     *
     * @param soundName Sound to be played
     */
    void stop(String soundName);

    /**
     * Stop the background music
     *
     * @param soundName Sound to be played
     */
    void stopMusic(String soundName);

    /**
     * Resume an specified paused sound.
     *
     * @param soundName Sound to be played
     */
    void resume(String soundName);

    /**
     * Resume the background music
     *
     * @param soundName Sound to be played
     */
    void resumeMusic(String soundName);
}
