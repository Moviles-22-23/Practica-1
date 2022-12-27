package es.ucm.stalos.logic.states;

import es.ucm.stalos.engine.AbstractState;
import es.ucm.stalos.engine.IAudio;
import es.ucm.stalos.engine.IEngine;
import es.ucm.stalos.engine.IGraphics;
import es.ucm.stalos.engine.IState;
import es.ucm.stalos.logic.Assets;
import es.ucm.stalos.logic.enums.FontName;
import es.ucm.stalos.logic.enums.ImageName;
import es.ucm.stalos.logic.enums.SoundName;

/**
 * This state is created to initialize all the assets of the game before it starts
 */
public class LoadState extends AbstractState {
    public LoadState(IEngine engine) {
        super(engine);
    }

    @Override
    public boolean init() {
        try {
            _graphics = _engine.getGraphics();
            _audio = _engine.getAudio();

            //Sprites
            _graphics.newImage(ImageName.BackArrow.getName(),
                    Assets.backArrowPath);
            _graphics.newImage(ImageName.Lents.getName(),
                    Assets.lentsPath);

            // Font
            _graphics.newFont(FontName.TitleMainMenu.getName(),
                    Assets.mollePath,
                    50, true);
            _graphics.newFont(FontName.ButtonMainMenu.getName(),
                    Assets.josePath,
                    35, true);
            // Select State
            _graphics.newFont(FontName.SelectStateTitle.getName(),
                    Assets.josePath,
                    36, true);
            _graphics.newFont(FontName.SelectStateDescription.getName(),
                    Assets.josePath,
                    26, true);
            _graphics.newFont(FontName.SelectStateButton.getName(),
                    Assets.josePath,
                    26, true);
            _graphics.newFont(FontName.LevelNumber.getName(),
                    Assets.mollePath,
                    20, true);
            _graphics.newFont(FontName.DefaultFont.getName(),
                    Assets.josePath,
                    32, true);
            _graphics.newFont(FontName.RowColNumber.getName(),
                    Assets.mollePath,
                    20, true);
            _graphics.newFont(FontName.GameStateButton.getName(),
                    Assets.josePath,
                    30, true);
            _graphics.newFont(FontName.FigureName.getName(),
                    Assets.josePath,
                    50, true);
            _graphics.newFont(FontName.GameStateText.getName(),
                    Assets.josePath,
                    30, true);

            // IAudio
            _audio.newSound(SoundName.MainTheme.getName(),
                    Assets.mainThemePath);
            _audio.newSound(SoundName.MenuTheme.getName(),
                    Assets.menuThemePath);
            _audio.newSound(SoundName.ClickSound.getName(),
                    Assets.clickSoundPath);
            _audio.newSound(SoundName.WinSound.getName(),
                    Assets.winSoundPath);

            // Start MainMenu
            IState mainMenu = new MainMenuState(_engine);
            _engine.reqNewState(mainMenu);

        } catch (Exception e) {
            System.err.println(e);
            return false;
        }

        return true;
    }
}
