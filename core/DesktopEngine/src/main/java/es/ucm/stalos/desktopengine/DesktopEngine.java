package es.ucm.stalos.desktopengine;

import java.awt.image.BufferStrategy;

import es.ucm.stalos.engine.AbstractEngine;
import es.ucm.stalos.engine.State;

public class DesktopEngine extends AbstractEngine implements Runnable {
    public DesktopEngine() {
    }

    public boolean init(State initState, String nameGame, int w, int h) {
        // STATE
        _currState = initState;
        // INPUT
        _input = new DesktopInput(this);
        // GRAPHICS
        _graphics = new DesktopGraphics(nameGame, this, w, h);
        // AUDIO
        _audio = new DesktopAudio();

        return ((DesktopGraphics) _graphics).init() && ((DesktopInput) _input).init() && _currState.init();
    }

    @Override
    public void run() {
        if (_renderThread != Thread.currentThread()) {
            // Evita que cualquiera que no sea esta clase llame a este Runnable en un Thread
            // Programación defensiva
            throw new RuntimeException("run() should not be called directly");
        }

        // Si el Thread se pone en marcha
        // muy rápido, la vista podría todavía no estar inicializada.
        while (this._running && this._graphics.getWidth() == 0) ;
        // Espera activa. Sería más elegante al menos dormir un poco.

        BufferStrategy strategy = ((DesktopGraphics) _graphics).getStrategy();
        _lastFrameTime = System.nanoTime();

        while (_running) {
            // Refresco del deltaTime
            updateDeltaTime();

            // Refresco del estado actual
            _currState.handleInput();
            _currState.update(_deltaTime);

            // Pintamos el frame con el BufferStrategy
            do {
                do {
                    _graphics.updateGraphics();
                    _graphics.prepareFrame();
                    _graphics.clear(0xFFFFFFFF);
                    try {
                        _currState.render();
                    } finally {
                        _graphics.restore();
                    }
                } while (strategy.contentsRestored());
                strategy.show();
            } while (strategy.contentsLost());

            // Inicializacion del nuevo estado en diferido
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