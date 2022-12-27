package es.ucm.stalos.logic.enums;

/**
 * Information about different grid level types
 */
public enum GridType {
    _4x4(0),
    _5x5(1),
    _10x5(2),
    _8x8(3),
    _10x10(4),
    _15x10(5),
    MAX(6);

    GridType(int gridType) {
        this._gridType = gridType;
        switch (gridType) {
            case 0:
                _rows = 4;
                _cols = 4;
                _text = 4 + "x" + 4;
                break;
            case 1:
                _rows = 5;
                _cols = 5;
                _text = 5 + "x" + 5;
                break;
            case 2:
                _rows = 10;
                _cols = 5;
                _text = 10 + "x" + 5;
                break;
            case 3:
                _rows = 8;
                _cols = 8;
                _text = 8 + "x" + 8;
                break;
            case 4:
                _rows = 10;
                _cols = 10;
                _text = 10 + "x" + 10;
                break;
            case 5:
                _rows = 15;
                _cols = 10;
                _text = 15 + "x" + 10;
                break;
        }
    }

    public int getGridType() {
        return _gridType;
    }


    public String getText() {
        return _text;
    }

    public int getRows() {
        return _rows;
    }

    public int getCols() {
        return _cols;
    }

    private int _gridType;
    private int _rows;
    private int _cols;
    private String _text;
}
