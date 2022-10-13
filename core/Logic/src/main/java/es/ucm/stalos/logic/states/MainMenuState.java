package es.ucm.stalos.logic.states;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import es.ucm.stalos.engine.AbstractState;
import es.ucm.stalos.engine.Engine;
import es.ucm.stalos.engine.Font;
import es.ucm.stalos.engine.Graphics;
import es.ucm.stalos.engine.Input.TouchEvent;
import es.ucm.stalos.engine.State;
import es.ucm.stalos.logic.Assets;
import es.ucm.stalos.logic.interfaces.ButtonCallback;

public class MainMenuState extends AbstractState {

    public MainMenuState(Engine engine){
        this._engine = engine;
    }

    @Override
    public boolean init() {
        try {

            _graphics = _engine.getGraphics();
            _audio = _engine.getAudio();

            // TODO: Eliminar de aquí la prueba
            _audio.play(Assets.testSound, 0);
            _timer = new Timer();
            _timeDelay = 1000;
            _timerTask = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Audio parado...");
                    _audio.stop(Assets.testSound);
                }
            };
            _timer.schedule(_timerTask, _timeDelay);

            // TITLE
            _titleText = "Nonogramas";
            _titleFont = Assets.bigMolly;
            _titlePos[0] = 0;
            _titlePos[1] = (_graphics.getLogHeight()/15);;
            _titleSize[0] = (_graphics.getLogWidth());
            _titleSize[1] = (_graphics.getLogHeight()/10);

            // PLAYBUTTON
            _playButtonText = "Jugar";
            _playButtonFont = Assets.bigJosse;
            // Size
            _playButtonSize[0] = (_graphics.getLogWidth() / 20) * 8;
            _playButtonSize[1] = (_graphics.getLogHeight() / 20) * 3;
            // Position
            _playButtonPos[0] = (_graphics.getLogWidth() / 2) - ((int) _playButtonSize[0] / 2);
            _playButtonPos[1] = (_graphics.getLogHeight() / 10) * 5;
            // Callback
            _playCallback = new ButtonCallback() {
                @Override
                public void doSomething() {
                    // Levels
                    Assets.testLevel = "testLevel.txt";
                    State selectLevelState = new SelectLevelState(_engine);
                    _engine.reqNewState(selectLevelState);
                    _audio.stop(Assets.testSound);
                }
            };
        }
        catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return false;
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render() {
        Graphics g = _engine.getGraphics();

        // TITLE
        int color = 0XAAAAAAFF;
        g.setColor(color);
        g.fillSquare(_titlePos, _titleSize);
        color = 0X000000FF;
        g.setColor(color);
        g.drawCenteredString(_titleText, _titlePos, _titleSize, _titleFont);

        // Play Button
        color = 0XAAAAAAFF;
        g.setColor(color);
        g.fillSquare(_playButtonPos, _playButtonSize);
        color = 0X000000FF;
        g.setColor(color);
        g.drawCenteredString(_playButtonText, _playButtonPos, _playButtonSize, _playButtonFont);
    }

    @Override
    public void handleInput() {
        List<TouchEvent> events = _engine.getInput().getTouchEvents();
        for (int i = 0; i < events.size(); i++) {
            TouchEvent currEvent = events.get(i);
            if (currEvent == TouchEvent.touchDown) {
                int[] clickPos = {currEvent.getX(), currEvent.getY()};
                if(clickInsideSquare(clickPos, _playButtonPos, _playButtonSize)){
                    _playCallback.doSomething();
                }
            }
        }
    }

    // TITLE
    String _titleText;
    Font _titleFont;
    int[] _titlePos = new int[2];
    float[] _titleSize = new float[2];

    // PLAY_BUTTON
    String _playButtonText;
    Font _playButtonFont;
    int[] _playButtonPos = new int[2];
    float[] _playButtonSize = new float[2];
    ButtonCallback _playCallback;
}
