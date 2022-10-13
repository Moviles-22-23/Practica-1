package es.ucm.stalos.logic.states;

import java.util.List;

import es.ucm.stalos.engine.Audio;
import es.ucm.stalos.engine.Engine;
import es.ucm.stalos.engine.Font;
import es.ucm.stalos.engine.Graphics;
import es.ucm.stalos.engine.Input.TouchEvent;
import es.ucm.stalos.engine.State;
import es.ucm.stalos.logic.Assets;
import es.ucm.stalos.logic.interfaces.ButtonCallback;

public class MainMenuState implements State {

    public MainMenuState(Engine engine){
        this._engine = engine;
    }

    @Override
    public boolean init() {
        try {
            _graphics = _engine.getGraphics();
            _audio = _engine.getAudio();

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
                    // TODO: Eliminar de aquí la prueba
                    _audio.play(Assets.testSound, 0);
                    // Levels
                    Assets.testLevel = "testLevel.txt";
                    State selectLevelState = new SelectLevelState(_engine);
                    _engine.reqNewState(selectLevelState);
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
                if(clickInside(clickPos, _playButtonPos, _playButtonSize)){
                    _playCallback.doSomething();
                }
            }
        }
    }

    public boolean clickInside(int[] clickPos, int[] buttonPos, float[] buttonSize){
        return (clickPos[0] > buttonPos[0] && clickPos[0] < (buttonPos[0] + buttonSize[0]) &&
                clickPos[1] > buttonPos[1] && clickPos[1] < (buttonPos[1] + buttonSize[1]));
    }

    Engine _engine;
    Graphics _graphics;
    Audio _audio;

    // ATTRIBUTES
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
