package es.ucm.stalos.androidengine;

import es.ucm.stalos.engine.AbstractEngine;
import es.ucm.stalos.engine.State;

public class AndroidEngine extends AbstractEngine {
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

    public void run() {
        //TODO: Bucle principal en android
    }
}
