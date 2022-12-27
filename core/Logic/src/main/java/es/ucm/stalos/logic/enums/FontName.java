package es.ucm.stalos.logic.enums;

/**
 * Enum which contains the information of the name of
 * every Font Asset of the game. It is used as for
 * creating new fonts from graphics as for drawing
 */
public enum FontName {
    DefaultFont("defaultFont"),
    TitleMainMenu("titleMainMenu"),
    ButtonMainMenu("buttonMainMenu"),
    RowColNumber("rowColNumber"),
    LevelNumber("levelNumber"),
    GameStateButton("gameStateButton"),
    GameStateText("gameStateText"),
    HintFont("hintFont"),
    FigureName("figureName"),
    SelectStateButton("selectStateButton"),
    SelectStateTitle("selectStateTitle"),
    SelectStateDescription("selectStateDescription"),
    ;

    FontName(String name) {
        this._name = name;
    }

    public String getName() {
        return _name;
    }

    private String _name;
}
