package es.ucm.stalos.engine;

import java.util.ArrayList;
import java.util.List;

public class AbstractInput implements Input {
    protected AbstractInput(Engine e) {
        _engine = e;
        _events = new ArrayList<>();
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        return null;
    }

    protected Engine _engine;
    protected List<TouchEvent> _events;
}
