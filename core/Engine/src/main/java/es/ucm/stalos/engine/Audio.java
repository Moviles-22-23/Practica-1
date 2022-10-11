package es.ucm.stalos.engine;

public interface Audio {
    /**
     * Create a new sound from assets
     * @param file filename
     * @return Sound
     */
    Sound newSound(String file) throws Exception;

    /**
     * Get an specified sound by id
     */
    Sound getSound(String id);

    void play(Sound sound, boolean loop);

    void pause(Sound sound);

    void stop(Sound sound);

    void resume(Sound sound);
}
