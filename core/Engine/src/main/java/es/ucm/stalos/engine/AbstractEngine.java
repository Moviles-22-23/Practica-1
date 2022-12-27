package es.ucm.stalos.engine;

public abstract class AbstractEngine implements IEngine {
    public AbstractEngine(){
    }

    @Override
    public void reqNewState(IState newState){
        _changeState = true;
        _newState = newState;
    }

    @Override
    public IGraphics getGraphics() {
        return _graphics;
    }

    @Override
    public IInput getInput() {
        return _input;
    }

    @Override
    public IAudio getAudio(){
        return _audio;
    }

    @Override
    public IFileReader getFileReader(){
        return _fReader;
    }

    protected void updateDeltaTime() {
        _currentTime = System.nanoTime();
        long nanoElapsedTime = _currentTime - _lastFrameTime;
        _lastFrameTime = _currentTime;
        _deltaTime = (double) nanoElapsedTime / 1.0E9;
    }

    protected boolean _changeState = false;
    protected IState _newState;
    protected IState _currState;
    protected IGraphics _graphics;
    protected IInput _input;
    protected IAudio _audio;
    protected IFileReader _fReader;

    // DELTA TIME
    protected long _lastFrameTime = 0;
    protected long _currentTime = 0;
    protected double _deltaTime = 0;
}
