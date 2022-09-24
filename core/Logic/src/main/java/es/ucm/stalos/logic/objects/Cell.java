package es.ucm.stalos.logic.objects;

import es.ucm.stalos.logic.enums.CellType;

public class Cell {
    Cell() { this.cellType = CellType.GREY; }

    public CellType getCellType () { return cellType; }

    private CellType cellType;
}
