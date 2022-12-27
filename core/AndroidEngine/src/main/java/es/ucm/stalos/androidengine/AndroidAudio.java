package es.ucm.stalos.androidengine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.io.IOException;

import es.ucm.stalos.engine.AbstractAudio;
import es.ucm.stalos.engine.ISound;

public class AndroidAudio extends AbstractAudio {
    public AndroidAudio(AssetManager assetManager) {
        super();
        _assetManager = assetManager;
        _mediaPlayer = new MediaPlayer();
        _mediaPlayer.reset();
        _soundPool = new SoundPool.Builder().setMaxStreams(10).build();
    }

    @Override
    public void newSound(String name, String fileName) throws Exception {
        AndroidSound sound = new AndroidSound("sounds/" + fileName, _assetManager);
        if (!sound.init())
            throw new Exception();

        _sounds.put(name, sound);
        AssetFileDescriptor afd = sound.getAssetDescriptor();
        sound.setId(_soundPool.load(afd, 1));

    }

    @Override
    public void playSound(String soundName, int numberLoops) {
        ISound so = isContain(soundName);
        if(so == null)
            return;

        int id = ((AndroidSound) so).getId();
        int playId = _soundPool.play(id, 100, 100, 1, numberLoops, 1);
        ((AndroidSound) so).setPlayId(playId);
    }

    @Override
    public void playMusic(String soundName) {
        ISound so = isContain(soundName);
        if(so == null)
            return;


        AssetFileDescriptor afd = ((AndroidSound) so).getAssetDescriptor();
        if (!_mediaPlayer.isPlaying()) {
            try {
                _mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                _mediaPlayer.setLooping(true);
                _mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            _mediaPlayer.start();
        }
    }

    @Override
    public void pause(String soundName) {
        ISound so = isContain(soundName);
        if(so == null)
            return;

        int id = ((AndroidSound) so).getPlayId();
        _soundPool.pause(id);
    }

    public void pause() {
        _mediaPlayer.pause();
    }

    @Override
    public void pauseMusic(String soundName) {
        _mediaPlayer.pause();
    }

    @Override
    public void stop(String soundName) {
        ISound so = isContain(soundName);
        if(so == null)
            return;

        int id = ((AndroidSound) so).getPlayId();
        _soundPool.stop(id);
    }

    @Override
    public void stopMusic(String soundName) {
        _mediaPlayer.stop();
        _mediaPlayer.reset();
    }

    @Override
    public void resume(String soundName) {
        ISound so = isContain(soundName);
        if(so == null)
            return;

        int id = ((AndroidSound) so).getPlayId();
        _soundPool.resume(id);
    }

    @Override
    public void resumeMusic(String soundName) {
        _mediaPlayer.start();
    }

    public void resume() {
        _mediaPlayer.start();
    }

    /**
     * Reference to the AssetManager of Android
     */
    private AssetManager _assetManager;
    /**
     * Reference to the audio library of Android
     */
    private MediaPlayer _mediaPlayer;
    private SoundPool _soundPool;
}
