package es.ucm.stalos.desktopengine;

import es.ucm.stalos.engine.AbstractAudio;
import es.ucm.stalos.engine.ISound;

import javax.sound.sampled.Clip;

public class DesktopAudio extends AbstractAudio {
    public DesktopAudio() {
        super();
    }

    @Override
    public void newSound(String name, String fileName) throws Exception {
        DesktopSound sound = new DesktopSound("./assets/sounds/" + fileName);
        if (!sound.init()) throw new Exception();
        _sounds.put(name, sound);
    }

    @Override
    public void playSound(String soundName, int numberLoops) {
        ISound so = isContain(soundName);
        if(so == null)
            return;

        Clip clip = ((DesktopSound) so).getClip();
        clip.loop(numberLoops);
        clip.setFramePosition(0);
        clip.start();
    }

    @Override
    public void playMusic(String soundName) {
//        ISound so = isContain(soundName);
//        if(so == null)
//            return;
//
//        Clip clip = ((DesktopSound) so).getClip();
//        if (!clip.isRunning()) {
//            clip.loop(Clip.LOOP_CONTINUOUSLY);
//            clip.start();
//        }
    }

    @Override
    public void pause(String soundName) {
        ISound so = isContain(soundName);
        if(so == null)
            return;

        Clip clip = ((DesktopSound) so).getClip();
        if (!clip.isRunning())
            return;

        // Calling just this method will paused the sound
        clip.stop();
    }

    @Override
    public void pauseMusic(String soundName) {
        pause(soundName);
    }

    @Override
    public void stop(String soundName) {
        ISound so = isContain(soundName);
        if(so == null)
            return;

        Clip clip = ((DesktopSound) so).getClip();
        if (!clip.isRunning())
            return;

        // Calling just this method will paused the sound
        clip.stop();
        // We have to call setFramePosition after to restart
        clip.setFramePosition(0);
    }

    @Override
    public void stopMusic(String soundName) {
        stop(soundName);
    }

    @Override
    public void resume(String soundName) {
        ISound so = isContain(soundName);
        if(so == null)
            return;

        Clip clip = ((DesktopSound) so).getClip();
        if (!clip.isRunning())
            clip.start();
    }

    @Override
    public void resumeMusic(String soundName) {
        resume(soundName);
    }
}
