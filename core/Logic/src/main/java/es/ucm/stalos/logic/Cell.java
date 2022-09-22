package es.ucm.stalos.logic;

public class Cell {
    Cell() { this.cellType = CellType.GREY; }

    public CellType getCellType () { return cellType; }

    private CellType cellType;
}
