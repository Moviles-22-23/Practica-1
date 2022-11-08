package es.ucm.stalos.logic.states;

import es.ucm.stalos.engine.AbstractState;
import es.ucm.stalos.engine.Audio;
import es.ucm.stalos.engine.Engine;
import es.ucm.stalos.engine.Graphics;
import es.ucm.stalos.engine.State;
import es.ucm.stalos.logic.Assets;

/**
 * This state is created to initialize all the assets of the game before it start
 */
public class LoadState extends AbstractState {
    public LoadState(Engine engine) {
        super(engine);
    }

    @Override
    public boolean init() {
        try {
            Graphics graphics = _engine.getGraphics();
            Audio audio = _engine.getAudio();

            //Sprites
            Assets.playButton = graphics.newImage("playButton.png");
            Assets.greyButton = graphics.newImage("greyButton.png");
            Assets.giveUpButton = graphics.newImage("giveUpButton.png");
            Assets.checkButton = graphics.newImage("checkButton.png");
            Assets.cellHelp = graphics.newImage("cellHelp.png");
            Assets.cellHelp2 = graphics.newImage("cellHelp2.png");
            Assets.cellHelp3 = graphics.newImage("cellHelp3.png");
            Assets.backArrow = graphics.newImage("backArrow.png");
            Assets.lens = graphics.newImage("lents.png");

            //Fonts
            //Assets.mediumJosse = graphics.newFont("JosefinSans-Bold.ttf", 35, true);
            //Assets.mediumMolly = graphics.newFont("Molle-Regular.ttf", 35, true);
            //Assets.littleJosse = graphics.newFont("JosefinSans-Bold.ttf", 20, true);
            //Assets.littleMolly = graphics.newFont("Molle-Regular.ttf", 20, true);


            // Audio
            //Assets.testSound = audio.newSound("testSound.wav");
            Assets.menuTheme = audio.newSound("SomethingBetweenUs.wav");
            Assets.mainTheme = audio.newSound("Jhove–BeenAWhile.wav");
            Assets.clickSound = audio.newSound("tapSound.wav");
            Assets.winSound = audio.newSound("winning-notification.wav");

            // Start MainMenu
            State mainMenu = new MainMenuState(_engine);
            _engine.reqNewState(mainMenu);

        } catch (Exception e) {
            System.err.println(e);
            return false;
        }

        return true;
    }
}
