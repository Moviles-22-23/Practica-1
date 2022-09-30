package es.ucm.stalos.logic.states;

import es.ucm.stalos.engine.Engine;
import es.ucm.stalos.engine.Graphics;
import es.ucm.stalos.engine.State;
import es.ucm.stalos.logic.Assets;

public class LoadState implements State {
    public LoadState(Engine engine) {
        _engine = engine;
    }

    @Override
    public boolean init() {
        try {
            Graphics graphics = _engine.getGraphics();

            //Sprites
            Assets.playButton = graphics.newImage("playButton.png");

            //Fuentes
//            Assets.jose = graphics.newFont("JosefinSans-Bold.ttf", 1, true);

            // Estado MainMenu
            State mainMenu = new MainMenuState(_engine);
            _engine.reqNewState(mainMenu);
        }
        catch (Exception e) {
            System.err.println(e);
            return false;
        }

        return true;
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render() {

    }

    @Override
    public void handleInput() {

    }

    Engine _engine;
}
