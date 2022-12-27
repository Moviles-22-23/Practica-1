package es.ucm.stalos.logic.enums;

/**
 * Enum which contains the information of the name of
 * every Image Asset of the game. It is used as for
 * creating new images from graphics as for drawing
 */
public enum ImageName {
    BackArrow("BackArrow"),
    Lents("Lents");

    ImageName(String name) {
        this._name = name;
    }

    public String getName() {
        return _name;
    }

    private final String _name;
}
