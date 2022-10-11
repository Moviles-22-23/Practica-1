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
            Assets.greyButton = graphics.newImage("greyButton.png");
            Assets.giveUpButton = graphics.newImage("giveUpButton.png");
            Assets.checkButton = graphics.newImage("checkButton.png");
            Assets.cellHelp = graphics.newImage("cellHelp.png");
            Assets.cellHelp2 = graphics.newImage("cellHelp2.png");
            Assets.cellHelp3 = graphics.newImage("cellHelp3.png");
            Assets.backArrow = graphics.newImage("backArrow.png");
            Assets.lens = graphics.newImage("lupaEnIngles.png");

            //Fonts
            Assets.bigJosse = graphics.newFont("JosefinSans-Bold.ttf", 50, true);
            Assets.bigMolly = graphics.newFont("Molle-Regular.ttf", 50, true);
            Assets.mediumJosse = graphics.newFont("JosefinSans-Bold.ttf", 35, true);
            Assets.mediumMolly = graphics.newFont("Molle-Regular.ttf", 35, true);
            Assets.littleJosse = graphics.newFont("JosefinSans-Bold.ttf", 20, true);
            Assets.littleMolly = graphics.newFont("Molle-Regular.ttf", 20, true);

            // Start MainMenu
            State mainMenu = new MainMenuState(_engine);
            _engine.reqNewState(mainMenu);

//TODO: Borrar esto
//---------------------------TESTEO-----------------------------------//
//            // Levels
//            Assets.testLevel = "testLevel.txt";
//
//            // Estado GameState
//            State playState = new GameState(_engine, 5, 5);
//            _engine.reqNewState(playState);
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
