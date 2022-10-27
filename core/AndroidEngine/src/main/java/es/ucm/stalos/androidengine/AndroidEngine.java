package es.ucm.stalos.androidengine;

import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import es.ucm.stalos.engine.AbstractEngine;
import es.ucm.stalos.engine.State;

public class AndroidEngine extends AbstractEngine implements Runnable {
    public AndroidEngine() {

    }

    public boolean init(State initState, int w, int h, AppCompatActivity activity) {
        //STATE
        _currState = initState;

        //GRAPHICS
        _graphics = new AndroidGraphics(w, h, activity.getWindowManager(), activity.getWindow());

        // INPUT
        _input = new AndroidInput(this);

        // AUDIO
        _audio = new AndroidAudio(activity.getApplicationContext().getAssets());

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
        while (_running) {
            // Refresco del deltaTime
            updateDeltaTime();
            // Refresco del estado actual
            _currState.handleInput();
            _currState.update(_deltaTime);

            // Pintado del estado actual
            _graphics.prepareFrame();
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
                } catch (InterruptedException ie) {
                    // Esto no debería ocurrir nunca...
                }
            }
        }
    }

    private Thread _renderThread;
    private boolean _running;
}
