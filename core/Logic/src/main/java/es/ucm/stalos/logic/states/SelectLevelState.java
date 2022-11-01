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
import es.ucm.stalos.logic.enums.GridSize;
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
            _backButtonSize[0] = (_graphics.getLogWidth() / 14);
            _backButtonSize[1] = (_graphics.getLogHeight() / 25);
            _backCallback = new ButtonCallback() {
                @Override
                public void doSomething() {
                    State mainMenuState = new MainMenuState(_engine);
                    _engine.reqNewState(mainMenuState);
                }
            };

            // SELECT LEVEL TEXT
            _titleFont = _graphics.newFont("JosefinSans-Bold.ttf", 25, true);
            _titlePos[0] = (_graphics.getLogWidth() / 10);
            _titlePos[1] = _graphics.getLogHeight() / 11 * 3;

            // BUTTONS
            initSelectLevelButtons();

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
        _graphics.drawImage(_backButtonImage, _backButtonPos, _backButtonSize);
        int[] pos = {
                _backButtonPos[0] + (int) (_backButtonSize[0] * 1.25) + 3,
                _backButtonPos[1] + (int) (_backButtonSize[1] * 2 / 3) + 3
        };
        _graphics.drawText(_backText, pos, _backFont);

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

                if (clickInsideSquare(clickPos, _backButtonPos, _backButtonSize))
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
     * @throws Exception in case of font creation fails
     */
    private void initSelectLevelButtons() throws Exception {
        _selectButtons = new ArrayList<>();

        float min = Math.min(((_graphics.getLogWidth() / 10) * 2), ((_graphics.getLogHeight() / 10) * 2));
        float[] size = new float[]{min, min};

        Font font = _graphics.newFont("Molle-Regular.ttf", 20, true);

        int[] pos = new int[2];
        ButtonCallback cb;

        initGridTypesMap();

        int j = 0;
        for (int i = 0; i < GridSize.MAX.getValue(); i++) {
            pos[0] = (_graphics.getLogWidth() / 10) * (1 + (3 * j));
            pos[1] = (_graphics.getLogHeight() / 7) * (3 + (i / 3) * 2);

            final SelectLevelButton _level = new SelectLevelButton(pos, size, _gridMap.get(i), font);
            _level.setCallback(new ButtonCallback() {
                @Override
                public void doSomething() {
                    int r = _level.getRows();
                    int c = _level.getCols();
                    State gameState = new GameState(_engine, r, c);
                    _engine.reqNewState(gameState);
                    playSound();
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
        _gridMap = new HashMap<>();
        _gridMap.put(0, GridSize._4x4);
        _gridMap.put(1, GridSize._5x5);
        _gridMap.put(2, GridSize._5x10);
        _gridMap.put(3, GridSize._8x8);
        _gridMap.put(4, GridSize._10x10);
        _gridMap.put(5, GridSize._10x15);
    }

    // TODO: Esto es una prueba de sonido
    private void playSound(){

        _audio.play(Assets.testSound, 0);
//        _audio.setVolume();
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
    }

//----------------------------------------ATTRIBUTES----------------------------------------------//

    // BACK BUTTON
    final String _backText = "Volver";
    final Image _backButtonImage = Assets.backArrow;
    final int[] _backButtonPos = {15, 50};
    final float[] _backButtonSize = new float[2];
    Font _backFont;
    ButtonCallback _backCallback;

    // SELECT LEVEL TEXT
    final String _title = "Selecciona el tamaño del puzzle";
    int[] _titlePos = new int[2];
    Font _titleFont;

    // SELECT LEVEL BUTTONS
    List<SelectLevelButton> _selectButtons;
    Map<Integer, GridSize> _gridMap;
}
