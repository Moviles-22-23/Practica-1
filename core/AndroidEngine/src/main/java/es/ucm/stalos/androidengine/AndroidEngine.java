package es.ucm.stalos.androidengine;

import android.content.res.AssetManager;

import androidx.appcompat.app.AppCompatActivity;

import es.ucm.stalos.engine.AbstractEngine;
import es.ucm.stalos.engine.IState;

public class AndroidEngine extends AbstractEngine implements Runnable {
    public AndroidEngine() {

    }

    public boolean init(IState initState, int w, int h, AppCompatActivity activity) {
        AssetManager assetsMan = activity.getApplicationContext().getAssets();

        //STATE
        _currState = initState;

        //GRAPHICS
        _graphics = new AndroidGraphics(w, h, activity.getWindowManager(), activity.getWindow());

        // INPUT
        _input = new AndroidInput(this);

        // AUDIO
        _audio = new AndroidAudio(assetsMan);

        // FILE READER
        _fReader = new AndroidFileReader(assetsMan);

        return ((AndroidGraphics) _graphics).init((AndroidInput) _input, activity) && _currState.init();
    }

    @Override
    public void run() {
        if (_renderThread != Thread.currentThread()) {
            // Evita que cualquiera que no sea esta clase llame a este Runnable en un Thread
            // Programación defensiva
            throw new RuntimeException("run() should not be called directly");
        }
        //TODO: Bucle principal en android
        _lastFrameTime = System.nanoTime();
        _running = true;

        while (_running) {
            // Refresco del deltaTime
            updateDeltaTime();

            // Refresco del estado actual
            _currState.handleInput();
            _currState.update(_deltaTime);

            // Pintado del estado actual
            _graphics.prepareFrame();
            _graphics.clear(0xFFFFFFFF);
            _currState.render();
            _graphics.restore();

            // Inicializacion del siguiente estado en diferido
            if (_changeState) {
                _changeState = false;
                _currState = _newState;
                _currState.init();
            }
        }

    }

    public void resume() {
        if (!this._running) {
            this._running = true;

            this._renderThread = new Thread(this);
            this._renderThread.start();

            ((AndroidAudio) _audio).resume();
        }
    }

    public void pause() {
        if (this._running) {
            this._running = false;

            while (true) {
                try {
                    this._renderThread.join();
                    this._renderThread = null;
                    break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            ((AndroidAudio) _audio).pause();
        }
    }

    private Thread _renderThread;
    private boolean _running;
}
