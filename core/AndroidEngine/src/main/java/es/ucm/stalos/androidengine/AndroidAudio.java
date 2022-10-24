package es.ucm.stalos.androidengine;

import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

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
        return sound;
    }

    @Override
    public Sound getSound(String id) {
        return null;
    }

    @Override
    public void play(Sound sound, int numberLoops) {
        int id = _soundPool.load(((AndroidSound) sound).getAssetDescriptor(), 1);
        _soundPool.play(id, 100, 100, 1, numberLoops, 1);
    }


    public void playMusic(Sound music, int numberLoops) {

    }

    @Override
    public void pause(Sound sound) {
        int id = _soundPool.load(((AndroidSound) sound).getAssetDescriptor(), 1);
        _soundPool.pause(id);
    }

    @Override
    public void stop(Sound sound) {
        int id = _soundPool.load(((AndroidSound) sound).getAssetDescriptor(), 1);
        _soundPool.stop(id);
    }

    @Override
    public void resume(Sound sound) {
        int id = _soundPool.load(((AndroidSound) sound).getAssetDescriptor(), 1);
        _soundPool.resume(id);
    }

    private AssetManager _assetManager;
    private MediaPlayer _mediaPlayer;
    private SoundPool _soundPool;
}
