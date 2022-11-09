package es.ucm.stalos.engine;

public abstract class AbstractEngine implements Engine {
    public AbstractEngine(){
    }

    @Override
    public void reqNewState(State newState){
        _changeState = true;
        _newState = newState;
    }

    @Override
    public Graphics getGraphics() {
        return _graphics;
    }

    @Override
    public Input getInput() {
        return _input;
    }

    @Override
    public Audio getAudio(){
        return _audio;
    }

    @Override
    public FileReader getFileReader(){
        return _fReader;
    }

    protected void updateDeltaTime() {
        _currentTime = System.nanoTime();
        long nanoElapsedTime = _currentTime - _lastFrameTime;
        _lastFrameTime = _currentTime;
        _deltaTime = (double) nanoElapsedTime / 1.0E9;
    }

    protected boolean _changeState = false;
    protected State _newState;
    protected State _currState;
    protected Graphics _graphics;
    protected Input _input;
    protected Audio _audio;
    protected FileReader _fReader;

    // DELTA TIME
    protected long _lastFrameTime = 0;
    protected long _currentTime = 0;
    protected double _deltaTime = 0;
}
