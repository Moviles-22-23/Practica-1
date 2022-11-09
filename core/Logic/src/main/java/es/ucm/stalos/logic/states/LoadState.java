package es.ucm.stalos.logic.states;

import es.ucm.stalos.engine.AbstractState;
import es.ucm.stalos.engine.Audio;
import es.ucm.stalos.engine.Engine;
import es.ucm.stalos.engine.Graphics;
import es.ucm.stalos.engine.State;
import es.ucm.stalos.logic.Assets;

/**
 * This state is created to initialize all the assets of the game before it starts
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
            Assets.backArrow = graphics.newImage("backArrow.png");
            Assets.lens = graphics.newImage("lents.png");

            // Audio
            Assets.menuTheme = audio.newSound("menuTheme.wav");
            Assets.mainTheme = audio.newSound("mainTheme.wav");
            Assets.clickSound = audio.newSound("clickSound.wav");
            Assets.winSound = audio.newSound("winSound.wav");

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
