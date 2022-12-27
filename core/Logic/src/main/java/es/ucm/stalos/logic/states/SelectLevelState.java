package es.ucm.stalos.logic.states;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import es.ucm.stalos.engine.AbstractState;
import es.ucm.stalos.engine.IEngine;
import es.ucm.stalos.engine.IState;
import es.ucm.stalos.engine.IInput;
import es.ucm.stalos.logic.enums.FontName;
import es.ucm.stalos.logic.enums.GridType;
import es.ucm.stalos.logic.enums.ImageName;
import es.ucm.stalos.logic.enums.SoundName;
import es.ucm.stalos.logic.interfaces.ButtonCallback;
import es.ucm.stalos.logic.objects.SelectLevelButton;

public class SelectLevelState extends AbstractState {

    public SelectLevelState(IEngine engine, boolean isRandom) {
        super(engine);
        this._isRandom = isRandom;
    }

//-----------------------------------------OVERRIDE-----------------------------------------------//

    @Override
    public boolean init() {
        try {
            // MODE TEXT
            if (_isRandom) _modeText = "JUEGO ALEATORIO";
            else _modeText = "JUEGO CLASICO";
            _modeSize[0] = _graphics.getLogWidth();
            _modeSize[1] = _graphics.getLogHeight() * 0.1f;
            _modePos[0] = (int) (_graphics.getLogWidth() - _modeSize[0]);
            _modePos[1] = (int) ((_graphics.getLogHeight() - _modeSize[1]) * 0.18f);

            // Comment Text
            _commentSize[0] = _graphics.getLogWidth();
            _commentSize[1] = _graphics.getLogHeight() * 0.1f;
            _commentPos[0] = (int) (_graphics.getLogWidth() - _commentSize[0]);
            _commentPos[1] = (int) ((_graphics.getLogHeight() - _commentSize[1]) * 0.28f);

            // Back Button
            // IImage
            _backImageSize[0] = _graphics.getLogWidth() * 0.072f;
            _backImageSize[1] = _graphics.getLogHeight() * 0.04f;
            _backImagePos[0] = 10;
            _backImagePos[1] = 31;

            // Text
            _backTextSize[0] = _graphics.getLogWidth() * 0.2f;
            _backTextSize[1] = _backImageSize[1];
            _backTextPos[0] = (int) (_backImagePos[0] + _backImageSize[0]);
            _backTextPos[1] = _backImagePos[1];

            // Back Button
            _backButtonSize[0] = _backImageSize[0] + _backTextSize[0];
            _backButtonSize[1] = _backImageSize[1];
            _backCallback = new ButtonCallback() {
                @Override
                public void doSomething() {
                    IState mainMenuState = new MainMenuState(_engine);
                    _engine.reqNewState(mainMenuState);
                    _audio.playSound(SoundName.ClickSound.getName(), 0);
                }
            };

            // BUTTONS
            initSelectLevelButtons();

            // AUDIO
            _audio.playMusic(SoundName.MenuTheme.getName());

        } catch (Exception e) {
            System.out.println("Error init Select Level");
            System.out.println(e);
            return false;
        }

        return true;
    }

    @Override
    public void render() {
        // Texts
        _graphics.setColor(_greyColor);
        _graphics.drawCenteredString(_modeText, FontName.SelectStateTitle.getName(),
                _modePos, _modeSize);
        _graphics.drawCenteredString(_commentText, FontName.SelectStateDescription.getName(),
                _commentPos, _commentSize);

        // Back Button
        _graphics.setColor(_blackColor);
        _graphics.drawImage(ImageName.BackArrow.getName(),
                _backImagePos, _backImageSize);
        _graphics.drawCenteredString(_backText, FontName.SelectStateButton.getName(),
                _backTextPos, _backTextSize);

        // SelectLevel buttons
        for (SelectLevelButton button : _selectButtons) {
            button.render(_graphics);
        }
    }

    @Override
    public void handleInput() {
        List<IInput.TouchEvent> events = _engine.getInput().getTouchEvents();
        for (int i = 0; i < events.size(); i++) {
            IInput.TouchEvent currEvent = events.get(i);
            if (currEvent == IInput.TouchEvent.touchDown) {
                int[] clickPos = {currEvent.getX(), currEvent.getY()};

                if (clickInsideSquare(clickPos, _backImagePos, _backButtonSize)) _backCallback.doSomething();
                else {
                    for (SelectLevelButton button : _selectButtons) {
                        int[] pos = button.getPos();
                        float[] size = button.getSize();
                        if (clickInsideSquare(clickPos, pos, size)) {
                            button.doSomething();
                        }
                    }
                }
            }
        }
    }

//-------------------------------------------MISC-------------------------------------------------//

    /**
     * Initialize the buttons to select the levels
     *
     */
    private void initSelectLevelButtons() {
        _selectButtons = new ArrayList<>();

        float min = Math.min((_graphics.getLogWidth() * 0.2f), (_graphics.getLogHeight() * 0.2f));
        float[] size = new float[]{min, min};

        int[] pos = new int[2];

        initGridTypesMap();

        int j = 0;
        for (int i = 0; i < GridType.MAX.getGridType(); i++) {
            pos[0] = (int)(_graphics.getLogWidth() * 0.1f) * (1 + (3 * j));
            pos[1] = (int)(_graphics.getLogHeight() * 0.143f) * (3 + (i / 3) * 2);

            final SelectLevelButton _level = new SelectLevelButton(pos, size,
                    _gridTypes.get(i), FontName.LevelNumber.getName());
            _level.setCallback(new ButtonCallback() {
                @Override
                public void doSomething() {
                    int r = _level.getRows();
                    int c = _level.getCols();
                    IState gameState = new GameState(_engine, r, c, _isRandom);
                    _engine.reqNewState(gameState);
                    _audio.playSound(SoundName.ClickSound.getName(), 0);
                    _audio.stopMusic(SoundName.MenuTheme.getName());
                }
            });
            _selectButtons.add(_level);
            j++;
            if (j == 3) j = 0;
        }
    }

    /**
     * Initializes rows and cols types of the selectButton.
     */
    private void initGridTypesMap() {
        _gridTypes = new HashMap<>();
        _gridTypes.put(0, GridType._4x4);
        _gridTypes.put(1, GridType._5x5);
        _gridTypes.put(2, GridType._10x5);
        _gridTypes.put(3, GridType._8x8);
        _gridTypes.put(4, GridType._10x10);
        _gridTypes.put(5, GridType._15x10);
    }

//----------------------------------------ATTRIBUTES----------------------------------------------//
    // Game Mode
    boolean _isRandom;

    // Mode Text
    private String _modeText = "Juego clasico";
    private int[] _modePos = new int[2];
    private float[] _modeSize = new float[2];

    // Comment Text
    private final String _commentText = "Selecciona el tamaño del puzzle";
    private int[] _commentPos = new int[2];
    private float[] _commentSize = new float[2];

    // Back Button
    private final String _backText = "Volver";
    private int[] _backTextPos = new int[2];
    private float[] _backTextSize = new float[2];

    private int[] _backImagePos = new int[2];
    private float[] _backImageSize = new float[2];

    private float[] _backButtonSize = new float[2];
    private ButtonCallback _backCallback;

    // SELECT LEVEL BUTTONS
    /**
     * List of all select level buttons
     */
    List<SelectLevelButton> _selectButtons;
    /**
     * Dictionary of information about
     * different grid level types
     */
    Map<Integer, GridType> _gridTypes;

    // Colors
    private final int _greyColor = 0x313131FF;
    private final int _blackColor = 0x000000FF;
}
