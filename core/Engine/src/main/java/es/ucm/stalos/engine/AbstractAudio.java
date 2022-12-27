package es.ucm.stalos.engine;

import java.util.HashMap;

public abstract class AbstractAudio implements IAudio {
    protected AbstractAudio() {
        _sounds = new HashMap<>();
    }

    protected ISound isContain(String soundName)
    {
        if (!_sounds.containsKey(soundName)) {
            System.err.println("La música '" + soundName + "' no existe...");
            return null;
        }

        return _sounds.get(soundName);
    }

    /**
     * Dictionary which contains the fonts
     */
    protected HashMap<String, ISound> _sounds;
}
