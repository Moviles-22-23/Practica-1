package es.ucm.stalos.logic.enums;

/**
 * Enum which contains the information of the name of
 * every Sound Asset of the game. It is used as for
 * creating new sounds from audio as for playing
 */
public enum SoundName {
    MainTheme("MainTheme"),
    MenuTheme("MenuTheme"),
    ClickSound("ClickSound"),
    WinSound("WinSound");

    SoundName(String name) {
        this._name = name;
    }

    public String getName() {
        return _name;
    }

    private String _name;
}
