package es.ucm.stalos.logic.enums;

/**
 * Information about different cell types of the game
 */
public enum CellType {
    GREY(0),
    BLUE(1),
    WHITE(2),
    RED(3);

    CellType(int cellType) { this._cellType = cellType; }

    public int getCellType() { return _cellType; }

    private int _cellType;
}
