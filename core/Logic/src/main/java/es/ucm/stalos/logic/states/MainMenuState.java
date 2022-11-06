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

    public MainMenuState(Engine engine) {
        super(engine);
    }

    //-----------------------------------------OVERRIDE-----------------------------------------------//
    @Override
    public boolean init() {
        try {
            // TITLE
            _titleText = "Nonogramas";
            _titleFont = _graphics.newFont("Molle-Regular.ttf", 50, true);
            _titleSize[0] = (_graphics.getLogWidth() / 10) * 7;
            _titleSize[1] = _graphics.getLogHeight() / 10;

            _titlePos[0] = (_graphics.getLogWidth() / 2) - (int) (_titleSize[0] / 2);
            _titlePos[1] = (_graphics.getLogHeight() / 10) + (int) _titleSize[1];

            // PLAYBUTTON
            _playButtonText = "Jugar";
            _playButtonFont = _graphics.newFont("JosefinSans-Bold.ttf", 50, true);
            // Size
            _playButtonSize[0] = (_graphics.getLogWidth() / 7) * 2;
            _playButtonSize[1] = (_graphics.getLogHeight() / 10);
            // Position
            _playButtonPos[0] = (_graphics.getLogWidth() / 2) - ((int) _playButtonSize[0] / 2);
            _playButtonPos[1] = (_graphics.getLogHeight() / 9) * 4;

            // Callback
            _playCallback = new ButtonCallback() {
                @Override
                public void doSomething() {
                    // Levels
                    Assets.testLevel = "testLevel.txt";
                    State selectLevelState = new SelectLevelState(_engine);
                    _engine.reqNewState(selectLevelState);
                    _audio.play(Assets.clickSound, 1);
                    _audio.pause(Assets.menuTheme);
                }
            };

            // Audio
            _audio.playMusic(Assets.menuTheme);

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return false;
    }

    @Override
    public void render() {
        Graphics g = _engine.getGraphics();

        // TITLE
        int color = 0X000000FF;
        g.setColor(color);
        g.drawText(_titleText, _titlePos, _titleFont);

        // Play Button
        g.setColor(color);
        int[] pos = {_playButtonPos[0], _playButtonPos[1] + (int) _playButtonSize[1]};
        g.drawText(_playButtonText, pos, _playButtonFont);
    }

    @Override
    public void handleInput() {
        List<TouchEvent> events = _engine.getInput().getTouchEvents();
        for (int i = 0; i < events.size(); i++) {
            TouchEvent currEvent = events.get(i);
            if (currEvent == TouchEvent.touchDown) {
                int[] clickPos = {currEvent.getX(), currEvent.getY()};
                if (clickInsideSquare(clickPos, _playButtonPos, _playButtonSize)) {
                    _playCallback.doSomething();
                }
            }
        }
    }

    //----------------------------------------ATTRIBUTES----------------------------------------------//
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
