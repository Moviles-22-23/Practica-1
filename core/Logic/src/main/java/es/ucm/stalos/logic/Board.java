package es.ucm.stalos.logic;

// Borrar luego
//     1   1
//     1 5 1 3 1
//   5 X X X X X
// 1 1 - X - X -
// 1 1 - X - X -
//   1 - X - - -
//   3 X X X - -

public class Board {
    // Numero de filas y columnas del tablero
    public int getNum_rows() {
        return num_rows;
    }

    public void setNum_rows(int num_rows) {
        this.num_rows = num_rows;
    }

    public int getNum_cols() {
        return num_cols;
    }

    public void setNum_cols(int num_cols) {
        this.num_cols = num_cols;
    }

    int num_rows;
    int num_cols;

    boolean[][] sol;
    int[][] hintRows;
    int[][] hintCols;
    CellType[][] boardState;

}
