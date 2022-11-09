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
        clip.loop(numberLoop);
        clip.setFramePosition(0);
        clip.start();
    }

    @Override
    public void playMusic(Sound sound) {
        Clip clip = ((DesktopSound) sound).getClip();
        if (!clip.isRunning()) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }
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
    public void pauseMusic(Sound music) {
        pause(music);
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
    public void stopMusic(Sound music) {
        stop(music);
    }

    @Override
    public void resume(Sound sound) {
        Clip clip = ((DesktopSound) sound).getClip();
        if (!clip.isRunning())
            clip.start();
    }

    @Override
    public void resumeMusic(Sound music) {
        resume(music);
    }
}
