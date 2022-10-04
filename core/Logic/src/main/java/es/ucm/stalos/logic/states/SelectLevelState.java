package es.ucm.stalos.logic.states;

import java.util.List;

import es.ucm.stalos.engine.Engine;
import es.ucm.stalos.engine.Font;
import es.ucm.stalos.engine.Graphics;
import es.ucm.stalos.engine.Image;
import es.ucm.stalos.engine.Input;
import es.ucm.stalos.engine.State;
import es.ucm.stalos.logic.Assets;
import es.ucm.stalos.logic.interfaces.ButtonCallback;

public class SelectLevelState implements State {

    public SelectLevelState(Engine engine) {
        this._engine = engine;
        this._graphics = engine.getGraphics();
    }

    @Override
    public boolean init() {
       try {
           // BACK LEVEL
           _backButtonImage = Assets.giveUpButton;
           _backButtonSize[0] = (_graphics.getLogWidth() / 13) * 3;
           _backButtonSize[1] = (_graphics.getLogHeight() / 13) * 3;
           _backButtonPos[0] = 0;
           _backButtonPos[1] = 0;
           _backCallback = new ButtonCallback() {
               @Override
               public void doSomething() {
                   State mainMenuState = new MainMenuState(_engine);
                   _engine.reqNewState(mainMenuState);
               }
           };

           // SELECT LEVEL TEXT
           _selectLevelTextFont = Assets.jose;
           _selectLevelTextPos[0] = _graphics.getLogWidth() / 8;
           _selectLevelTextPos[1] = _graphics.getLogHeight() / 4;

       } catch (Exception e){
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

        _graphics.drawImage(_backButtonImage, _backButtonPos, _backButtonSize);
    }

    @Override
    public void handleInput() {
        List<Input.TouchEvent> events = _engine.getInput().getTouchEvents();
        for (int i = 0; i < events.size(); i++) {
            Input.TouchEvent currEvent = events.get(i);
            if (currEvent == Input.TouchEvent.touchDown) {
                int[] clickPos = {currEvent.getX(), currEvent.getY()};

                if(clickInside(clickPos, _backButtonPos, _backButtonSize)){
                    _backCallback.doSomething();
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

    // BACK BUTTON
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
    ButtonCallback _4x4;

    // 5 X 5 BUTTON
    Image _5x5ButtonImage;
    int[] _5x5ButtonPos = new int[2];
    float[] _5x5ButtonSize = new float[2];
    ButtonCallback _5x5;

    // 5 X 10 BUTTON
    Image _5x10ButtonImage;
    int[] _5x10ButtonPos = new int[2];
    float[] _5x10ButtonSize = new float[2];
    ButtonCallback _5x10;

    // 8 X 8 BUTTON
    Image _8x8ButtonImage;
    int[] _8x8ButtonPos = new int[2];
    float[] _8x8ButtonSize = new float[2];
    ButtonCallback _8x8;

    // 10 X 10 BUTTON
    Image _10x10ButtonImage;
    int[] _10x10ButtonPos = new int[2];
    float[] _10x10ButtonSize = new float[2];
    ButtonCallback _10x10;

    // 10 X 15 BUTTON
    Image _10x15ButtonImage;
    int[] _10x15ButtonPos = new int[2];
    float[] _10x15ButtonSize = new float[2];
    ButtonCallback _10x15;
}
