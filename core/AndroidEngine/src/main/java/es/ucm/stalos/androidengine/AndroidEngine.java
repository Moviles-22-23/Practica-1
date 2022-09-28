package es.ucm.stalos.androidengine;

import android.view.SurfaceView;

import es.ucm.stalos.engine.AbstractEngine;
import es.ucm.stalos.engine.State;

public class AndroidEngine extends AbstractEngine implements Runnable {
    public AndroidEngine() {
        super();
    }

    public boolean init(State initState, int w, int h) {
        //STATE
        _currState = initState;
        //GRAPHICS
        _graphics = new AndroidGraphics(w, h);
        // INPUT

        return ((AndroidGraphics) _graphics).init() && _currState.init();
    }

    @Override
    public void run() {
        if (_renderThread != Thread.currentThread()) {
            // Evita que cualquiera que no sea esta clase llame a este Runnable en un Thread
            // Programación defensiva
            throw new RuntimeException("run() should not be called directly");
        }
        //TODO: Bucle principal en android

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
