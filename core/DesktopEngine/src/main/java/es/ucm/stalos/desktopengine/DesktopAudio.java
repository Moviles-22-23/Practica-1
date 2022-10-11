package es.ucm.stalos.desktopengine;

import java.io.File;

import es.ucm.stalos.engine.Audio;
import es.ucm.stalos.engine.Sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class DesktopAudio implements Audio {
    @Override
    public Sound newSound(String file) throws Exception {
        DesktopSound sound = new DesktopSound("./assets/" + file);
        if(!sound.init()) throw new Exception();
        return sound;
    }

    @Override
    public Sound getSound(String id) {
        return null;
    }

    @Override
    public void play(Sound sound, boolean loop) {

    }

    @Override
    public void pause(Sound sound) {

    }

    @Override
    public void stop(Sound sound) {

    }

    @Override
    public void resume(Sound sound) {

    }
}
