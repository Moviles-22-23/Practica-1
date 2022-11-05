package es.ucm.stalos.logic.enums;

/**
 * Information about different cell types of the game
 */
public enum CellType {
    GREY(0),
    BLUE(1),
    WHITE(2),
    RED(3),
    MAX(4);

    CellType(int i) { this.value = i; }

    public int getValue () { return value; }

    private int value;
}
