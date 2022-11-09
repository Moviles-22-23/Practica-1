package es.ucm.stalos.androidengine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.io.IOException;

import es.ucm.stalos.engine.Audio;
import es.ucm.stalos.engine.Sound;

public class AndroidAudio implements Audio {
    public AndroidAudio(AssetManager assetManager) {
        _assetManager = assetManager;
        _mediaPlayer = new MediaPlayer();
        _mediaPlayer.reset();
        _soundPool = new SoundPool.Builder().setMaxStreams(10).build();
    }

    @Override
    public Sound newSound(String file) throws Exception {
        AndroidSound sound = new AndroidSound("sounds/" + file, _assetManager);
        if (!sound.init()) throw new Exception();

        AssetFileDescriptor afd = sound.getAssetDescriptor();
        sound.setId(_soundPool.load(afd, 1));

        return sound;
    }

    @Override
    public Sound getSound(String id) {
        return null;
    }

    @Override
    public void play(Sound sound, int numberLoops) {
        int id = ((AndroidSound) sound).getId();
        int playId = _soundPool.play(id, 100, 100, 1, numberLoops, 1);
        ((AndroidSound) sound).setPlayId(playId);
    }

    @Override
    public void playMusic(Sound sound) {
        AssetFileDescriptor afd = ((AndroidSound) sound).getAssetDescriptor();
        if (!_mediaPlayer.isPlaying()) {
            try {
                _mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                _mediaPlayer.setLooping(true);
                _mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            _mediaPlayer.start();
            //System.out.println("playmusic");
        }
    }

    @Override
    public void pause(Sound sound) {
        int id = ((AndroidSound) sound).getPlayId();
        _soundPool.pause(id);
    }

    public void pause() {
        _mediaPlayer.pause();
    }

    @Override
    public void pauseMusic(Sound music) {
        _mediaPlayer.pause();
    }

    @Override
    public void stop(Sound sound) {
        int id = ((AndroidSound) sound).getPlayId();
        _soundPool.stop(id);
    }

    @Override
    public void stopMusic(Sound music) {
        _mediaPlayer.stop();
        _mediaPlayer.reset();
    }

    @Override
    public void resume(Sound sound) {
        int id = ((AndroidSound) sound).getPlayId();
        _soundPool.resume(id);
    }

    @Override
    public void resumeMusic(Sound music) {
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
