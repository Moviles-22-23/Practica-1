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
        if (!_events.isEmpty()) {
            List<TouchEvent> touchEvents = new ArrayList<>(_events);
            _events.clear();
            return touchEvents;
        } else return _events;
    }

    /**
     * Procesa las coordenadas del input entrante para transformar
     * las posiciones x e y en posiciones lógicas.
     * Añade el evento a la lista
     *
     * @param x Posición X física donde se recibió el input
     * @param y Posición Y física donde se recibió el input
     */
//    protected void onTouchDownEvent(int x, int y) {
//        AbstractGraphics g = (AbstractGraphics) _engine.getGraphics();
//
//        TouchEvent currEvent = TouchEvent.touchDown;
//        int[] eventPos = g.logPos(x, y);
//        currEvent.setX(eventPos[0]);
//        currEvent.setY(eventPos[1]);
//
//        events.add(currEvent);
//    }

    protected Engine _engine;
    protected List<TouchEvent> _events;
}
