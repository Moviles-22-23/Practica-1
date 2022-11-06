package es.ucm.stalos.logic.states;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import es.ucm.stalos.engine.AbstractState;
import es.ucm.stalos.engine.Engine;
import es.ucm.stalos.engine.Font;
import es.ucm.stalos.engine.Image;
import es.ucm.stalos.engine.Input;
import es.ucm.stalos.engine.State;
import es.ucm.stalos.logic.Assets;
import es.ucm.stalos.logic.enums.GridType;
import es.ucm.stalos.logic.interfaces.ButtonCallback;
import es.ucm.stalos.logic.objects.SelectLevelButton;

public class SelectLevelState extends AbstractState {

    public SelectLevelState(Engine engine) {
        super(engine);
    }

//-----------------------------------------OVERRIDE-----------------------------------------------//

    @Override
    public boolean init() {
        try {
            // BACK LEVEL
            _backFont = _graphics.newFont("JosefinSans-Bold.ttf", 20, true);
            _backImageSize[0] = (_graphics.getLogWidth() / 14);
            _backImageSize[1] = (_graphics.getLogHeight() / 25);
            _backCallback = new ButtonCallback() {
                @Override
                public void doSomething() {
                    State mainMenuState = new MainMenuState(_engine);
                    _engine.reqNewState(mainMenuState);
                    _audio.play(Assets.clickSound, 1);
                }
            };
            _backTextPos[0] = _backImagePos[0] + (int) (_backImageSize[0] * 1.25) + 3;
            _backTextPos[1] = _backImagePos[1] + (int) (_backImageSize[1] * 2 / 3) + 3;

            _backButtonSize[0] = 100;
            _backButtonSize[1] = _backImageSize[1];

            // SELECT LEVEL TEXT
            _titleFont = _graphics.newFont("JosefinSans-Bold.ttf", 25, true);
            _titlePos[0] = (_graphics.getLogWidth() / 10);
            _titlePos[1] = _graphics.getLogHeight() / 11 * 3;

            // BUTTONS
            initSelectLevelButtons();

            // AUDIO
            _audio.resume(Assets.menuTheme);

        } catch (Exception e) {
            System.out.println("Error init Select Level");
            System.out.println(e);
            return false;
        }

        return true;
    }

    @Override
    public void render() {
        int color = 0x313131FF;
        _graphics.setColor(color);
        _graphics.drawText(_title, _titlePos, _titleFont);

        // Back Button
        color = 0X000000FF;
        _graphics.setColor(color);
        _graphics.drawImage(_backButtonImage, _backImagePos, _backImageSize);
        _graphics.drawText(_backText, _backTextPos, _backFont);

        // SelectLevel buttons
        for (SelectLevelButton button : _selectButtons) {
            button.render(_graphics);
        }
    }

    @Override
    public void handleInput() {
        List<Input.TouchEvent> events = _engine.getInput().getTouchEvents();
        for (int i = 0; i < events.size(); i++) {
            Input.TouchEvent currEvent = events.get(i);
            if (currEvent == Input.TouchEvent.touchDown) {
                int[] clickPos = {currEvent.getX(), currEvent.getY()};

                if (clickInsideSquare(clickPos, _backImagePos, _backButtonSize))
                    _backCallback.doSomething();
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
     * @throws Exception in case of font creation fails
     */
    private void initSelectLevelButtons() throws Exception {
        _selectButtons = new ArrayList<>();

        float min = Math.min(((_graphics.getLogWidth() / 10) * 2), ((_graphics.getLogHeight() / 10) * 2));
        float[] size = new float[]{min, min};

        Font font = _graphics.newFont("Molle-Regular.ttf", 20, true);

        int[] pos = new int[2];

        initGridTypesMap();

        int j = 0;
        for (int i = 0; i < GridType.MAX.getValue(); i++) {
            pos[0] = (_graphics.getLogWidth() / 10) * (1 + (3 * j));
            pos[1] = (_graphics.getLogHeight() / 7) * (3 + (i / 3) * 2);

            final SelectLevelButton _level = new SelectLevelButton(pos, size, _gridTypes.get(i), font);
            _level.setCallback(new ButtonCallback() {
                @Override
                public void doSomething() {
                    int r = _level.getRows();
                    int c = _level.getCols();
                    State gameState = new GameState(_engine, r, c);
                    _engine.reqNewState(gameState);
                    _audio.play(Assets.clickSound, 1);
                    _audio.stop(Assets.menuTheme);
                }
            });
            _selectButtons.add(_level);
            j++;
            if (j == 3)
                j = 0;
        }
    }

    /**
     * Initializes rows and cols types of the selectButton.
     */
    private void initGridTypesMap() {
        _gridTypes = new HashMap<>();
        _gridTypes.put(0, GridType._4x4);
        _gridTypes.put(1, GridType._5x5);
        _gridTypes.put(2, GridType._5x10);
        _gridTypes.put(3, GridType._8x8);
        _gridTypes.put(4, GridType._10x10);
        _gridTypes.put(5, GridType._10x15);
    }

//----------------------------------------ATTRIBUTES----------------------------------------------//

    // BACK BUTTON
    private final String _backText = "Volver";
    private final Image _backButtonImage = Assets.backArrow;
    /**
     * BackButtonIcon's position
     */
    private final int[] _backImagePos = {15, 50};
    /**
     * BackText's position
     */
    private final int[] _backTextPos = new int[2];
    /**
     * BackButtonIcon's size
     */
    private final float[] _backImageSize = new float[2];
    /**
     * BackButtonIcon + text's size
     */
    private final float[] _backButtonSize = new float[2];
    private Font _backFont;
    private ButtonCallback _backCallback;

    // SELECT LEVEL TEXT
    private final String _title = "Selecciona el tamaño del puzzle";
    private final int[] _titlePos = new int[2];
    private Font _titleFont;

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
}
