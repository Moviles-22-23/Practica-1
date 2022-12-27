package es.ucm.stalos.logic.states;

import java.util.List;
import es.ucm.stalos.engine.AbstractState;
import es.ucm.stalos.engine.IEngine;
import es.ucm.stalos.engine.IState;
import es.ucm.stalos.engine.IInput.TouchEvent;
import es.ucm.stalos.logic.enums.FontName;
import es.ucm.stalos.logic.enums.SoundName;
import es.ucm.stalos.logic.interfaces.ButtonCallback;

public class MainMenuState extends AbstractState {

    public MainMenuState(IEngine engine) {
        super(engine);
    }

    //-----------------------------------------OVERRIDE-----------------------------------------------//
    @Override
    public boolean init() {
        try {
            // TITLE
            _titleSize[0] = _graphics.getLogWidth() * 0.7f;
            _titleSize[1] = _graphics.getLogHeight() * 0.1f;
            _titlePos[0] = (int) ((_graphics.getLogWidth() - _titleSize[0]) * 0.5f);
            _titlePos[1] = (int) ((_graphics.getLogHeight() - _titleSize[1]) * 0.1f);

            // BUTTONS
            _playCallback = new ButtonCallback() {
                @Override
                public void doSomething() {
                    IState selectLevelState = new SelectLevelState(_engine, _isRandom);
                    _engine.reqNewState(selectLevelState);
                    _audio.playSound(SoundName.ClickSound.getName(), 0);
                }
            };

            // PLAY BUTTON
            _playButtonSize[0] = _graphics.getLogWidth() * 0.3f;
            _playButtonSize[1] = _graphics.getLogHeight() * 0.1f;
            _playButtonPos[0] = (int) ((_graphics.getLogWidth() - _playButtonSize[0]) * 0.5f);
            _playButtonPos[1] = (int) ((_graphics.getLogHeight() - _playButtonSize[1]) * 0.40f);

            // PLAY RANDOM BUTTON
            _playRandomButtonSize[0] = _graphics.getLogWidth() * 0.8f;
            _playRandomButtonSize[1] = _graphics.getLogHeight() * 0.1f;
            _playRandomButtonPos[0] = (int) ((_graphics.getLogWidth() - _playRandomButtonSize[0]) * 0.5f);
            _playRandomButtonPos[1] = (int) ((_graphics.getLogHeight() - _playRandomButtonSize[1]) * 0.60f);

            // IAudio
            _audio.playMusic(SoundName.MenuTheme.getName());
        } catch (Exception e) {
            System.out.println("Error init Main Menu");
            System.out.println(e);
            return false;
        }
        return true;
    }

    @Override
    public void render() {
        _graphics.setColor(_blackColor);

        // Title
        _graphics.drawCenteredString(_titleText, FontName.TitleMainMenu.getName(),
                _titlePos, _titleSize);
        // Play Button
        _graphics.drawCenteredString(_playButtonText, FontName.ButtonMainMenu.getName(),
                _playButtonPos, _playButtonSize);
        // Play Random Button
        _graphics.drawCenteredString(_playRandomButtonText, FontName.ButtonMainMenu.getName(),
                _playRandomButtonPos, _playRandomButtonSize);
    }

    @Override
    public void handleInput() {
        List<TouchEvent> events = _engine.getInput().getTouchEvents();
        for (int i = 0; i < events.size(); i++) {
            TouchEvent currEvent = events.get(i);
            if (currEvent == TouchEvent.touchDown) {
                int[] clickPos = {currEvent.getX(), currEvent.getY()};

                // Play Button
                if (clickInsideSquare(clickPos, _playButtonPos, _playButtonSize)) {
                    _isRandom = false;
                    _playCallback.doSomething();
                }
                // Play Random Button
                else if (clickInsideSquare(clickPos, _playRandomButtonPos, _playRandomButtonSize)) {
                    _isRandom = true;
                    _playCallback.doSomething();
                }
            }
        }
    }

    //----------------------------------------ATTRIBUTES----------------------------------------------//
    // Title
    private final String _titleText = "Nonogramas";
    private int[] _titlePos = new int[2];
    private float[] _titleSize = new float[2];

    // Buttons
    private boolean _isRandom = false;
    private ButtonCallback _playCallback;

    // Play Button
    private final String _playButtonText = "Jugar";
    private int[] _playButtonPos = new int[2];
    private float[] _playButtonSize = new float[2];

    // Play Random Button
    private final String _playRandomButtonText = "Jugar Aleatorio";
    private int[] _playRandomButtonPos = new int[2];
    private float[] _playRandomButtonSize = new float[2];

    // Colors
    private final int _blackColor = 0x000000FF;
}
