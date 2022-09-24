package es.ucm.stalos.logic.states;

import es.ucm.stalos.engine.Engine;
import es.ucm.stalos.engine.State;

public class LoadState implements State {
    public LoadState(Engine engine) {
        _engine = engine;
    }

    @Override
    public boolean init() {
        return false;
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render() {

    }

    @Override
    public void handleInput() {

    }

    Engine _engine;
}
