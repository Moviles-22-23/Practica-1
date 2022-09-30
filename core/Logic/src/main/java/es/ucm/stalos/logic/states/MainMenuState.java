package es.ucm.stalos.logic.states;

import es.ucm.stalos.engine.Engine;
import es.ucm.stalos.engine.Font;
import es.ucm.stalos.engine.Graphics;
import es.ucm.stalos.engine.Image;
import es.ucm.stalos.engine.State;
import es.ucm.stalos.logic.Assets;

public class MainMenuState implements State {

    public MainMenuState(Engine engine){
        this._engine = engine;
    }

    @Override
    public boolean init() {
        try {
            Graphics g = _engine.getGraphics();

            // PLAYBUTTON
            _playButtonImage = Assets.playButton;
            // Size
            _sizePlayButton[0] = (g.getLogWidth() / 10) * 7;
            _sizePlayButton[1] = (g.getLogHeight() / 6);
            // Position
            _posPlayButton[0] = (g.getLogWidth() / 2) - ((int)_sizePlayButton[0] / 2);
            _posPlayButton[1] = (g.getLogHeight() / 10) * 3;

            // TODO callback

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

        // Attributes
        int color = 0X313131FF;
//        Font font = Assets.molle;
        int tamF = 100;

        g.setColor(color);

        // Play Button
        g.drawImage(_playButtonImage, _posPlayButton, _sizePlayButton);
    }

    @Override
    public void handleInput() {

    }

    Engine _engine;

    // Play Button attributes
    Image _playButtonImage;
    int _posPlayButton[] = new int[2];
    float _sizePlayButton[] = new float[2];
}
