package es.ucm.stalos.logic.objects;

// Example
//         1   1
//         1 2 1   1
//         1 2 1 2 1
//        __________
//     5 | X X X X X
//   1 1 | - X - X -
// 1 1 1 | X - X - X
//     1 | - X - - -
//     3 | X X X - -

import es.ucm.stalos.engine.Graphics;
import es.ucm.stalos.logic.Assets;
import es.ucm.stalos.logic.enums.CellType;

public class Board {
    /**
     *
     * @param rows Number of rows
     * @param cols Number of columns
     * @param pos Up-Left position
     * @param size Board size (hints includes)
     */
    public Board(int rows, int cols, int[] pos, float[] size){
        this._rows = rows;
        this._cols = cols;
        this._sol = new boolean[rows][cols];
        this._boardState = new CellType[rows][cols];
        this._hintRows = new int[rows][(int)Math.ceil(cols / 2.0f)];
        this._hintCols = new int[(int)Math.ceil(rows / 2.0f)][cols];
        System.out.println("hintsRows(" + rows + ", " + (int)Math.ceil(cols / 2.0f) + ")");
        System.out.println("hintsCols(" + (int)Math.ceil(rows / 2.0f) + ", " + cols + ")");

        this._pos = pos;

        // Cell Size must be square so we have to use the min between rows and cols
        float maxRowsSize = size[1] / (rows + (int)Math.ceil(rows / 2.0f));
        float maxColsSize = size[0] / (cols + (int)Math.ceil(cols / 2.0f));
        _cellSize = Math.min(maxRowsSize,maxColsSize);
        System.out.println("CellSize: " + _cellSize);

    }

    public void render(Graphics graphics){
        System.out.println("RenderBoard with rows:" + (_boardState.length + _hintRows[0].length) + " cols:" + (_boardState[0].length + _hintCols.length));

        // TOTAL ROWS
        for(int i = 0; i < (_boardState.length + _hintCols.length); i++){
            // TOTAL COLS
            for(int j = 0; j < (_boardState[0].length + _hintRows[0].length); j++){
                int[] pos = new int[2];
                pos[0] = _pos[0] + (int) (i * _cellSize);
                pos[1] = _pos[1] + (int) (j * _cellSize);
                float[] size = new float[2];
                size[0] = _cellSize;
                size[1] = _cellSize;

                // Range of empty space (BORRAR)
                if(i < _hintCols.length && j < _hintRows[0].length){
                    // Empty
                }
                // Range of hints cols
                else if(i >= _hintCols.length && j < _hintRows[0].length){
                    graphics.drawImage(Assets.cellHelp, pos, size);
                }
                // Range of hints cols
                else if(i < _hintCols.length && j >= _hintRows[0].length){
                    graphics.drawImage(Assets.cellHelp2, pos, size);
                }
                // Range of board
                else {
                    graphics.drawImage(Assets.cellHelp3, pos, size);
                }
            }
        }


    }

    // Numero de filas y columnas del tablero
    public int getNum_rows() {
        return _rows;
    }

    public int getCols() {
        return _cols;
    }

    // Attributes
    private int _rows;
    private int _cols;
    boolean[][] _sol;
    int[][] _hintRows;
    int[][] _hintCols;
    CellType[][] _boardState;

    int[] _pos = new int[2];
    float _cellSize;
}
