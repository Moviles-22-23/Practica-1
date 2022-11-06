package es.ucm.stalos.desktopengine;

import es.ucm.stalos.engine.Audio;
import es.ucm.stalos.engine.Sound;

import javax.sound.sampled.Clip;

public class DesktopAudio implements Audio {
    public DesktopAudio() {

    }

    @Override
    public Sound newSound(String file) throws Exception {
        DesktopSound sound = new DesktopSound("./assets/sounds/" + file);
        if (!sound.init()) throw new Exception();
        return sound;
    }

    @Override
    public Sound getSound(String id) {
        return null;
    }

    @Override
    public void play(Sound sound, int numberLoop) {
        Clip clip = ((DesktopSound) sound).getClip();
        if (clip.isRunning()) {
            clip.stop();
            clip.setFramePosition(clip.getFramePosition());
        }

        clip.loop(numberLoop);
        clip.start();
    }

    @Override
    public void pause(Sound sound) {
        Clip clip = ((DesktopSound) sound).getClip();
        if (!clip.isRunning())
            return;

        // Calling just this method will paused the sound
        clip.stop();
    }

    @Override
    public void stop(Sound sound) {
        Clip clip = ((DesktopSound) sound).getClip();
        if (!clip.isRunning())
            return;

        // Calling just this method will paused the sound
        clip.stop();
        // We have to call setFramePosition after to restart
        clip.setFramePosition(0);
    }

    @Override
    public void resume(Sound sound) {
        Clip clip = ((DesktopSound) sound).getClip();
        clip.start();
    }
}
