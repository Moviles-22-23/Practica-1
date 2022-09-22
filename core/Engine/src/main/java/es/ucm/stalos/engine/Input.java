package es.ucm.stalos.engine;

import java.util.List;

public interface Input {
    enum TouchEvent {
        touchDown(0),
        touchUp(1),
        touchDrag(2),
        MAX(3);

        private int _x, _y;
        private final int _value;

        public void setX(int x) { _x = x; }
        public void setY(int y) { _y = y; }

        public int getValue() { return _value; }
        public int getX() { return _x; }
        public int getY() { return _y; }

        TouchEvent(int i) { this._value = i; }

    }

    List<TouchEvent> getTouchEvents();
}
