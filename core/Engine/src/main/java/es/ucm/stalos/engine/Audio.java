package es.ucm.stalos.engine;

public interface Audio {
    /**
     * Create a new sound from assets
     *
     * @param file filename
     * @return Sound
     */
    Sound newSound(String file) throws Exception;

    /**
     * Get an specified sound by id
     */
    Sound getSound(String id);

    /**
     * Play an specified sound indicating the number of looping
     *
     * @param sound       Sound to be played
     * @param numberLoops Indicate number of extra-repetitions: -1 -> Infinite
     */
    void play(Sound sound, int numberLoops);

    /**
     * Play a looped specified sound
     *
     * @param sound Sound to be played
     */
    void playMusic(Sound sound);

    /**
     * Pause an specified sound. It can be resume.
     *
     * @param sound Sound to be paused
     */
    void pause(Sound sound);

    /**
     * Pause the background music
     *
     * @param music Music to be stopped
     */
    void pauseMusic(Sound music);

    /**
     * Stopped an specified sound. Restart the clip.
     *
     * @param sound Sound to be stopped
     */
    void stop(Sound sound);

    /**
     * Stop the background music
     *
     * @param music Music to be stopped
     */
    void stopMusic(Sound music);

    /**
     * Resume an specified paused sound.
     *
     * @param sound Sound to be resumed.
     */
    void resume(Sound sound);

    /**
     * Resume the background music
     *
     * @param music Music to be resumed
     */
    void resumeMusic(Sound music);
}
