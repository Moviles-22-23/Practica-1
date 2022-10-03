package es.ucm.stalos.logic.states;

import java.util.List;

import es.ucm.stalos.engine.Engine;
import es.ucm.stalos.engine.Font;
import es.ucm.stalos.engine.Graphics;
import es.ucm.stalos.engine.Image;
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
            Graphics g = _engine.getGraphics();

            // TITLE
            _titlePos[0] = (g.getLogWidth() / 7);
            _titlePos[1] = (g.getLogHeight() / 6);
            _titleFont = Assets.molle;

            // PLAYBUTTON
            _playButtonImage = Assets.playButton;
            // Size
            _playButtonSize[0] = (g.getLogWidth() / 13) * 3;
            _playButtonSize[1] = (g.getLogWidth() / 13) * 3;
            // Position
            _playButtonPos[0] = (g.getLogWidth() / 2) - ((int) _playButtonSize[0] / 2);
            _playButtonPos[1] = (g.getLogHeight() / 10) * 5;
            // Callback
            _play = new ButtonCallback() {
                @Override
                public void doSomething() {
                    // Levels
                    Assets.testLevel = "testLevel.txt";

                    // Estado GameState
                    State playState = new GameState(_engine, 5, 5);
                    _engine.reqNewState(playState);
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
        int color = 0X313131FF;
        g.setColor(color);
        g.drawText("Nonogramas", _titlePos, _titleFont);

        // Play Button
        g.drawImage(_playButtonImage, _playButtonPos, _playButtonSize);
    }

    @Override
    public void handleInput() {
        List<TouchEvent> events = _engine.getInput().getTouchEvents();
        for (int i = 0; i < events.size(); i++) {
            TouchEvent currEvent = events.get(i);
            if (currEvent == TouchEvent.touchDown) {
                if (currEvent.getX() > _playButtonPos[0] &&
                        currEvent.getX() < _playButtonPos[0] + _playButtonSize[0] &&
                        currEvent.getY() > _playButtonPos[1] &&
                        currEvent.getY() < _playButtonPos[1] + _playButtonSize[1]) {
                    _play.doSomething();
                }
            }
        }
    }

    Engine _engine;

    // ATTRIBUTES
    // TITLE
    int[] _titlePos = new int[2];
    Font _titleFont;
    
    // PLAY_BUTTON
    Image _playButtonImage;
    int[] _playButtonPos = new int[2];
    float[] _playButtonSize = new float[2];
    ButtonCallback _play;
}
