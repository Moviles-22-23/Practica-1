package es.ucm.stalos.desktopengine;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import es.ucm.stalos.engine.ISound;

public class DesktopSound implements ISound {
    public DesktopSound(String filename) {
        _filename = filename;
    }

    public boolean init() {
        try {
            File audioFile = new File(_filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            _clip = AudioSystem.getClip();
            _clip.open(audioStream);
        } catch (Exception e) {
            System.err.println("Couldn't load audio file");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Clip getClip() {
        return _clip;
    }

    public void setVolume(float newVolume)
    {
        FloatControl gainControl = (FloatControl) _clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-10.0f);
    }

    private Clip _clip;
    private String _filename;
}
