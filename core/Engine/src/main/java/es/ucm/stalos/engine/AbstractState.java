package es.ucm.stalos.engine;

import java.util.Timer;
import java.util.TimerTask;

public abstract class AbstractState implements State {

    protected AbstractState(Engine engine)
    {
        this._engine = engine;
        this._graphics = engine.getGraphics();
        this._audio = _engine.getAudio();
    }

    /**
     * Calculate if the position of the click is inside an square
     * @param clickPos CLick position to be checked
     * @param squarePos Upper-left corner of the square
     * @param squareSize Size of the square
     * @return true if the click is inside the square
     */
    protected boolean clickInsideSquare(int[] clickPos, int[] squarePos, float[] squareSize) {
        return (clickPos[0] > squarePos[0] && clickPos[0] < (squarePos[0] + squareSize[0]) &&
                clickPos[1] > squarePos[1] && clickPos[1] < (squarePos[1] + squareSize[1]));
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

    protected Engine _engine;
    protected Graphics _graphics;
    protected Audio _audio;

    // Attributes to create a timer
    protected Timer _timer;
    protected TimerTask _timerTask;
    protected int _timeDelay;
}
