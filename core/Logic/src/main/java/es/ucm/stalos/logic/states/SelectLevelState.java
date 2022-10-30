package es.ucm.stalos.logic.states;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public boolean init() {
        try {
            // BACK LEVEL
            _backText = "Volver";
            _backFont = _graphics.newFont("JosefinSans-Bold.ttf", 20, true);
            _backButtonImage = Assets.backArrow;
            _backButtonPos[0] = 15;
            _backButtonPos[1] = 50;
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
            _selectButtons = new ArrayList<>();
            float aux = Math.min(((_graphics.getLogWidth() / 10) * 2), ((_graphics.getLogHeight() / 10) * 2));
            float[] size = new float[]{aux, aux};
            Font font = _graphics.newFont("Molle-Regular.ttf", 20, true);

            int[] pos = new int[2];
            ButtonCallback cb;

            int j = 0;
            for (int i = 0; i < GridSize.MAX.getValue(); i++) {
                pos[0] = (_graphics.getLogWidth() / 10) * (1 + (3 * j));
                pos[1] = (_graphics.getLogHeight() / 7) * (3 + (i / 3) * 2);

                initRowCol(i);

                cb = new ButtonCallback() {
                    @Override
                    public void doSomething() {
                        State gameState = new GameState(_engine, _rows, _cols);
                        _engine.reqNewState(gameState);
                        playSound();
                    }
                };
                SelectLevelButton _level = new SelectLevelButton(pos, size, _gridSize, font, cb);
                _selectButtons.add(_level);
                j++;
                if (j == 3)
                    j = 0;
            }
        } catch (Exception e) {
            System.out.println("Error init Select Level");
            System.out.println(e);
            return false;
        }

        return true;
    }

    /**
     * Initializes rows and cols of the selectButton.
     * @param i Index that indicate the gridSize
     */
    private void initRowCol(int i) {
        switch (i) {
            case 0:
                _rows = 4;
                _cols = 4;
                _gridSize = GridSize._4x4;
                break;
            case 1:
                _rows = 5;
                _cols = 5;
                _gridSize = GridSize._5x5;
                break;
            case 2:
                _rows = 5;
                _cols = 10;
                _gridSize = GridSize._5x10;
                break;
            case 3:
                _rows = 8;
                _cols = 8;
                _gridSize = GridSize._8x8;
                break;
            case 4:
                _rows = 10;
                _cols = 10;
                _gridSize = GridSize._10x10;
                break;
            case 5:
                _rows = 10;
                _cols = 15;
                _gridSize = GridSize._10x15;
                break;
        }
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
                _backButtonPos[0] + (int) (_backButtonSize[0] * 1.25),
                _backButtonPos[1] + (int) (_backButtonSize[1] * 2 / 3)
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

    // BACK BUTTON
    String _backText;
    Image _backButtonImage;
    int[] _backButtonPos = new int[2];
    float[] _backButtonSize = new float[2];
    ButtonCallback _backCallback;
    Font _backFont;

    // SELECT LEVEL TEXT
    String _title = "Selecciona el tamaño del puzzle";
    int[] _titlePos = new int[2];
    Font _titleFont;

    List<SelectLevelButton> _selectButtons;
    int _rows, _cols;
    GridSize _gridSize;
}
