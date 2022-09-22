package es.ucm.stalos.engine;

public interface Audio {
    /**
     * Create a new sound from assets
     * @param file filename
     * @return Sound
     */
    Sound newSound(String file);

    /**
     * Play an specified sound by id
     */
    Sound playSound(String id);
}
