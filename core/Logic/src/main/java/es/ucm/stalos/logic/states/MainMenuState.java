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
            _graphics = _engine.getGraphics();

            // TITLE
            _titlePos[0] = (_graphics.getLogWidth() / 7);
            _titlePos[1] = (_graphics.getLogHeight() / 6);
            _titleFont = Assets.molle;

            // PLAYBUTTON
            _playButtonImage = Assets.playButton;
            // Size
            _playButtonSize[0] = (_graphics.getLogWidth() / 13) * 3;
            _playButtonSize[1] = (_graphics.getLogWidth() / 13) * 3;
            // Position
            _playButtonPos[0] = (_graphics.getLogWidth() / 2) - ((int) _playButtonSize[0] / 2);
            _playButtonPos[1] = (_graphics.getLogHeight() / 10) * 5;
            // Callback
            _playCallback = new ButtonCallback() {
                @Override
                public void doSomething() {
                    // Levels
                    Assets.testLevel = "testLevel.txt";

                    // Estado GameState
//                    State playState = new GameState(_engine, 5, 5);
//                    _engine.reqNewState(playState);
                    // A select level
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

    // ATTRIBUTES
    // TITLE
    int[] _titlePos = new int[2];
    Font _titleFont;
    
    // PLAY_BUTTON
    Image _playButtonImage;
    int[] _playButtonPos = new int[2];
    float[] _playButtonSize = new float[2];
    ButtonCallback _playCallback;
}
