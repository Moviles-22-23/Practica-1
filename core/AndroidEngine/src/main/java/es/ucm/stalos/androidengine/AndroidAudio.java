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

        //_mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
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
        _mediaPlayer.start();
    }

    @Override
    public void pause(Sound sound) {
        int id = ((AndroidSound) sound).getPlayId();
        _soundPool.pause(id);
        //_mediaPlayer.pause();
    }

    @Override
    public void stop(Sound sound) {
        int id = ((AndroidSound) sound).getPlayId();
        _soundPool.stop(id);
        //_mediaPlayer.stop();
    }

    @Override
    public void resume(Sound sound) {
        int id = ((AndroidSound) sound).getPlayId();
        _soundPool.resume(id);
        //_mediaPlayer.start();
    }

    private AssetManager _assetManager;
    private MediaPlayer _mediaPlayer;
    private SoundPool _soundPool;
}
