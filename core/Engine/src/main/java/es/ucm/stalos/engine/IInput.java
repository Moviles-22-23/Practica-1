package es.ucm.stalos.engine;

import java.util.List;

public interface IInput {
    /**
     * Enum to classify every TouchEvent that can happens
     */
    enum TouchEvent {
        touchDown(0),
        touchUp(1),
        touchDrag(2);

        private int _x, _y;
        private final int _touchEvent;

        public void setX(int x) { _x = x; }
        public void setY(int y) { _y = y; }

        public int getValue() { return _touchEvent; }
        public int getX() { return _x; }
        public int getY() { return _y; }

        TouchEvent(int touchEvent) { this._touchEvent = touchEvent; }

    }

    /**
     * @return return the list of all events
     */
    List<TouchEvent> getTouchEvents();
}
