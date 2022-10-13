package es.ucm.stalos.logic.states;

import java.util.List;

import es.ucm.stalos.engine.AbstractState;
import es.ucm.stalos.engine.Engine;
import es.ucm.stalos.engine.Font;
import es.ucm.stalos.engine.Image;
import es.ucm.stalos.engine.Input;
import es.ucm.stalos.engine.State;
import es.ucm.stalos.logic.Assets;
import es.ucm.stalos.logic.interfaces.ButtonCallback;

public class SelectLevelState extends AbstractState {

    public SelectLevelState(Engine engine) {
        this._engine = engine;
        this._graphics = engine.getGraphics();
    }

    @Override
    public boolean init() {
        try {
            // BACK LEVEL
            _backButtonText = "Volver";
            _backButtonImage = Assets.backArrow;
            _backButtonPos[0] = 0;
            _backButtonPos[1] = 30;
            _backButtonSize[0] = (_graphics.getLogWidth() / 20) * 8;
            _backButtonSize[1] = (_graphics.getLogHeight() / 20) * 2;
            _backCallback = new ButtonCallback() {
                @Override
                public void doSomething() {
                    State mainMenuState = new MainMenuState(_engine);
                    _engine.reqNewState(mainMenuState);
                }
            };

            // SELECT LEVEL TEXT
            _selectLevelTextFont = Assets.littleJosse;
            _selectLevelTextPos[0] = _graphics.getLogWidth() / 8;
            _selectLevelTextPos[1] = _graphics.getLogHeight() / 3;

            // 4 X 4 BUTTON
            _4x4ButtonImage = Assets.cellHelp;
            float aux = Math.min(((_graphics.getLogWidth() / 10) * 2), ((_graphics.getLogHeight() / 10) * 2));
            _4x4ButtonSize[0] = aux;
            _4x4ButtonSize[1] = aux;
            _4x4ButtonPos[0] = (_graphics.getLogWidth() / 7);
            _4x4ButtonPos[1] = (_graphics.getLogHeight() / 10) * 6;
            _4x4Callback = new ButtonCallback() {
                @Override
                public void doSomething() {
                    State gameState = new GameState(_engine, 4, 4);
                    _engine.reqNewState(gameState);
                }
            };

            // 5 X 5 BUTTON
            _5x5ButtonImage = Assets.cellHelp;
            aux = Math.min(((_graphics.getLogWidth() / 10) * 2), ((_graphics.getLogHeight() / 10) * 2));
            _5x5ButtonSize[0] = aux;
            _5x5ButtonSize[1] = aux;
            _5x5ButtonPos[0] = (_graphics.getLogWidth() / 7) * 3;
            _5x5ButtonPos[1] = (_graphics.getLogHeight() / 10) * 6;
            _5x5Callback = new ButtonCallback() {
                @Override
                public void doSomething() {
                    State gameState = new GameState(_engine, 5, 5);
                    _engine.reqNewState(gameState);
                }
            };

            // 5 X 10 BUTTON
            _5x10ButtonImage = Assets.cellHelp;
            aux = Math.min(((_graphics.getLogWidth() / 10) * 2), ((_graphics.getLogHeight() / 10) * 2));
            _5x10ButtonSize[0] = aux;
            _5x10ButtonSize[1] = aux;
            _5x10ButtonPos[0] = (_graphics.getLogWidth() / 7) * 5;
            _5x10ButtonPos[1] = (_graphics.getLogHeight() / 10) * 6;
            _5x10Callback = new ButtonCallback() {
                @Override
                public void doSomething() {
                    State gameState = new GameState(_engine, 5, 10);
                    _engine.reqNewState(gameState);
                }
            };

            // 8 X 8 BUTTON
            _8x8ButtonImage = Assets.cellHelp;
            aux = Math.min(((_graphics.getLogWidth() / 10) * 2), ((_graphics.getLogHeight() / 10) * 2));
            _8x8ButtonSize[0] = aux;
            _8x8ButtonSize[1] = aux;
            _8x8ButtonPos[0] = (_graphics.getLogWidth() / 7);
            _8x8ButtonPos[1] = (_graphics.getLogHeight() / 10) * 8;
            _8x8Callback = new ButtonCallback() {
                @Override
                public void doSomething() {
                    State gameState = new GameState(_engine, 8, 8);
                    _engine.reqNewState(gameState);
                }
            };

            // 5 X 10 BUTTON
            _10x10ButtonImage = Assets.cellHelp;
            aux = Math.min(((_graphics.getLogWidth() / 10) * 2), ((_graphics.getLogHeight() / 10) * 2));
            _10x10ButtonSize[0] = aux;
            _10x10ButtonSize[1] = aux;
            _10x10ButtonPos[0] = (_graphics.getLogWidth() / 7) * 3;
            _10x10ButtonPos[1] = (_graphics.getLogHeight() / 10) * 8;
            _10x10Callback = new ButtonCallback() {
                @Override
                public void doSomething() {
                    State gameState = new GameState(_engine, 10, 10);
                    _engine.reqNewState(gameState);
                }
            };

            // 10 X 15 BUTTON
            _10x15ButtonImage = Assets.cellHelp;
            aux = Math.min(((_graphics.getLogWidth() / 10) * 2), ((_graphics.getLogHeight() / 10) * 2));
            _10x15ButtonSize[0] = aux;
            _10x15ButtonSize[1] = aux;
            _10x15ButtonPos[0] = (_graphics.getLogWidth() / 7) * 5;
            _10x15ButtonPos[1] = (_graphics.getLogHeight() / 10) * 8;
            _10x15Callback = new ButtonCallback() {
                @Override
                public void doSomething() {
                    State gameState = new GameState(_engine, 10, 15);
                    _engine.reqNewState(gameState);
                }
            };
        } catch (Exception e) {
            System.out.println("Error init Select Level");
            System.out.println(e);
            return false;
        }

        return true;
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render() {
        int color = 0x313131FF;
        _graphics.setColor(color);

        _graphics.drawText(_selectLevelTextText, _selectLevelTextPos, _selectLevelTextFont);

        // Back Button
        color = 0XAAAAAAFF;
        _graphics.setColor(color);
        _graphics.fillSquare(_backButtonPos, _backButtonSize);
        color = 0X000000FF;
        _graphics.setColor(color);
        _graphics.drawCenteredString(_backButtonText, _backButtonPos, _backButtonSize, _selectLevelTextFont);
        _graphics.drawImage(_backButtonImage, new int[]{_backButtonPos[0], _backButtonPos[1] + (int) _backButtonSize[1] / 4},
                new float[]{_backButtonSize[0] / 4, _backButtonSize[1] / 4});

        // 4 x 4
        _graphics.drawImage(_4x4ButtonImage, _4x4ButtonPos, _4x4ButtonSize);
        _graphics.drawCenteredString("4 X 4", _4x4ButtonPos, _4x4ButtonSize, _selectLevelTextFont);

        _graphics.drawImage(_5x5ButtonImage, _5x5ButtonPos, _5x5ButtonSize);
        _graphics.drawCenteredString("5 X 5", _5x5ButtonPos, _5x5ButtonSize, _selectLevelTextFont);

        _graphics.drawImage(_5x10ButtonImage, _5x10ButtonPos, _5x10ButtonSize);
        _graphics.drawCenteredString("5 X 10", _5x10ButtonPos, _5x10ButtonSize, _selectLevelTextFont);

        _graphics.drawImage(_8x8ButtonImage, _8x8ButtonPos, _8x8ButtonSize);
        _graphics.drawCenteredString("8 X 8", _8x8ButtonPos, _8x8ButtonSize, _selectLevelTextFont);

        _graphics.drawImage(_10x10ButtonImage, _10x10ButtonPos, _10x10ButtonSize);
        _graphics.drawCenteredString("10 X 10", _10x10ButtonPos, _10x10ButtonSize, _selectLevelTextFont);

        _graphics.drawImage(_10x15ButtonImage, _10x15ButtonPos, _10x15ButtonSize);
        _graphics.drawCenteredString("10 X 15", _10x15ButtonPos, _10x15ButtonSize, _selectLevelTextFont);

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
                else if (clickInsideSquare(clickPos, _4x4ButtonPos, _4x4ButtonSize))
                    _4x4Callback.doSomething();
                else if (clickInsideSquare(clickPos, _5x5ButtonPos, _5x5ButtonSize))
                    _5x5Callback.doSomething();
                else if (clickInsideSquare(clickPos, _5x10ButtonPos, _5x10ButtonSize))
                    _5x10Callback.doSomething();
                else if (clickInsideSquare(clickPos, _8x8ButtonPos, _8x8ButtonSize))
                    _8x8Callback.doSomething();
                else if (clickInsideSquare(clickPos, _10x10ButtonPos, _10x10ButtonSize))
                    _10x10Callback.doSomething();
                else if (clickInsideSquare(clickPos, _10x15ButtonPos, _10x15ButtonSize))
                    _10x15Callback.doSomething();
            }
        }
    }

    // BACK BUTTON
    String _backButtonText;
    Image _backButtonImage;
    int[] _backButtonPos = new int[2];
    float[] _backButtonSize = new float[2];
    ButtonCallback _backCallback;

    // SELECT LEVEL TEXT
    String _selectLevelTextText = "Selecciona el tamaño del puzzle";
    int[] _selectLevelTextPos = new int[2];
    Font _selectLevelTextFont;

    // 4 X 4 BUTTON
    Image _4x4ButtonImage;
    int[] _4x4ButtonPos = new int[2];
    float[] _4x4ButtonSize = new float[2];
    ButtonCallback _4x4Callback;

    // 5 X 5 BUTTON
    Image _5x5ButtonImage;
    int[] _5x5ButtonPos = new int[2];
    float[] _5x5ButtonSize = new float[2];
    ButtonCallback _5x5Callback;

    // 5 X 10 BUTTON
    Image _5x10ButtonImage;
    int[] _5x10ButtonPos = new int[2];
    float[] _5x10ButtonSize = new float[2];
    ButtonCallback _5x10Callback;

    // 8 X 8 BUTTON
    Image _8x8ButtonImage;
    int[] _8x8ButtonPos = new int[2];
    float[] _8x8ButtonSize = new float[2];
    ButtonCallback _8x8Callback;

    // 10 X 10 BUTTON
    Image _10x10ButtonImage;
    int[] _10x10ButtonPos = new int[2];
    float[] _10x10ButtonSize = new float[2];
    ButtonCallback _10x10Callback;

    // 10 X 15 BUTTON
    Image _10x15ButtonImage;
    int[] _10x15ButtonPos = new int[2];
    float[] _10x15ButtonSize = new float[2];
    ButtonCallback _10x15Callback;
}
