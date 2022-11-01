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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

import es.ucm.stalos.engine.Font;
import es.ucm.stalos.engine.Graphics;
import es.ucm.stalos.logic.enums.CellType;

public class Board {
    /**
     * Constructor of the board
     *
     * @param rows Number of rows
     * @param cols Number of columns
     * @param pos  Up-Left position
     * @param size Board size (hints includes)
     */
    public Board(int rows, int cols, int[] pos, float[] size) {
        this._rows = rows;
        this._cols = cols;
        this._sol = new boolean[rows][cols];
        this._boardState = new Cell[rows][cols];
        this._hintRows = new int[rows][(int) Math.ceil(cols / 2.0f)];
        this._hintCols = new int[(int) Math.ceil(rows / 2.0f)][cols];

        this._pos = pos;
        this._size = size;

        // Cell Size must be square so we have to use the min between rows and cols
        float maxRowsSize = size[1] / (rows + (int) Math.ceil(rows / 2.0f));
        float maxColsSize = size[0] / (cols + (int) Math.ceil(cols / 2.0f));
//        _cellSize = Math.min(maxRowsSize, maxColsSize);
        _cellSize = 280 / Math.max(rows, cols);
    }

    //-------------------------------------------INIT-------------------------------------------------//
    public boolean init(Graphics graphics) {
        try {
            _rowFont = graphics.newFont("Molle-Regular.ttf", 50, true);
            _colFont = graphics.newFont("Molle-Regular.ttf", 50, true);

            readSolution();
            loadLevel();
        } catch (Exception e) {
            System.out.println("Error leyendo el mapa");
            return false;
        }

        return true;
    }

    private void readSolution() throws FileNotFoundException {
        try {
            String filePath = "./assets/levels/levelPack" + String.valueOf(_rows) + "x" + String.valueOf(_cols) + ".txt";
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            String line;

            line = br.readLine();
            int numLevels = Integer.parseInt(line);
            Random rn = new Random();
            int levelChoosen = Math.abs(rn.nextInt() % numLevels);

            System.out.println("Level: " + levelChoosen);

            // Skip lines to be on the correct level
            for (int i = 0; i < levelChoosen; i++) {
                br.readLine();
                for (int j = 0; j < _rows; j++) {
                    br.readLine();
                }
            }
            // Read lines
            for (int i = 0; i < _rows; i++) {
                line = br.readLine();
                // Process every character as a boolean
                for (int j = 0; j < _cols; j++) {
                    _sol[i][j] = line.charAt(j) != '0';
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading file");
        }
    }

    private void loadLevel() {
        loadHintsRows();
        loadHintsCols();
        loadBoardState();
    }

    /**
     * Is important to read right to left the row to fill hintsRows
     * - 2 1 | 1 1 0 1 0
     */
    private void loadHintsRows() {
        int cont, auxJ;
        // Cada fila de las pistas (Up to Down)
        for (int i = 0; i < _sol.length; i++) {
            // Reset cont and auxJ
            cont = 0;
            auxJ = _hintRows[0].length - 1;

            // Columns (Right to Left)
            for (int j = _sol[0].length - 1; j >= 0; j--) {
                // Plus one to counter
                if (_sol[i][j]) {
                    cont++;
                }
                // Save the counter number as a hint
                else if (cont > 0) {
                    _hintRows[i][auxJ] = cont;
                    cont = 0;
                    auxJ--;
                }
            }
            if (cont > 0) _hintRows[i][auxJ] = cont;
        }
    }

    private void loadHintsCols() {
        int cont, auxI;

        // Cada columna de las pistas (Left to Right)
        for (int j = 0; j < _sol[0].length; j++) {
            // Reset cont and auxJ
            cont = 0;
            auxI = _hintCols.length - 1;

            // Filas (Down to Up)
            for (int i = _sol.length - 1; i >= 0; i--) {
                // Plus one to counter
                if (_sol[i][j]) {
                    cont++;
                }
                // Save the counter number as a hint
                else if (cont > 0) {
                    _hintCols[auxI][j] = cont;
                    cont = 0;
                    auxI--;
                }
            }
            if (cont > 0) _hintCols[auxI][j] = cont;
        }
    }

    private void loadBoardState() {
        int[] pos = new int[2];
        pos[0] = _pos[0] + (int) (_size[0] * _hintRows[0].length / (_hintRows[0].length + _boardState[0].length));
        pos[1] = _pos[1] + (int) (_size[1] * _hintCols.length / (_hintCols.length + _boardState[0].length));

        int aux = pos[0];

        for (int i = 0; i < _rows; i++) {
            pos[0] = aux;
            for (int j = 0; j < _cols; j++) {
                _boardState[i][j] = new Cell(i, j, pos, _cellSize);
                pos[0] += _cellSize;
            }
            pos[1] += _cellSize;
        }
    }

//----------------------------------------MAIN-LOOP-----------------------------------------------//

    public void render(Graphics graphics) {
        graphics.setColor(0x000000FF);
        int s1 = 100, s2 = 280;
        int[] pos = {10, 270};
        float[] size = {s1, s2};
        // Vertical square
        graphics.drawRect(pos, size);
        // Horizontal square
        size[0] = s2;
        size[1] = s1;
        pos[0] += s1;
        pos[1] -= s1;
        graphics.drawRect(pos, size);
        // Grid overlay
        pos[1] += s1;
        size[1] = s2;
        graphics.drawRect(pos, size);

        // Grid squares
        int aux = pos[0];
        for(int i = 0; i < _rows; i++)
        {
            for(int j = 0; j < _cols; j++)
            {
                graphics.drawRect(pos, _cellSize);
                pos[0] += _cellSize;
            }
            pos[0] = aux;
            pos[1] += _cellSize * 2;
        }

//        float maxSide = Math.max(_boardState.length, _boardState[0].length);

        // TOTAL ROWS
//        for(int i = 0; i < (_boardState.length + _hintCols.length); i++){
//            // TOTAL COLS
//            for(int j = 0; j < (_boardState[0].length + _hintRows[0].length); j++){
//                // Carefull, position x is horizontall that corresponse with the j
//                pos[0] = _pos[0] + (int) (j * _cellSize);
//                pos[1] = _pos[1] + (int) (i * _cellSize);
//                size[0] = _cellSize;
//                size[1] = _cellSize;
//
//                String numText;
//                // Range of empty space (BORRAR)
//                if(i < _hintCols.length && j < _hintRows[0].length){
//                    // Empty
//                }
//                // Range of hints rows
//                else if(i >= _hintCols.length && j < _hintRows[0].length){
//                    numText = Integer.toString(_hintRows[i - _hintCols.length][j]);
//                    graphics.drawCenteredString(numText, pos, size, _rowFont);
//                }
//                // Range of hints cols
//                else if(i < _hintCols.length && j >= _hintRows[0].length){
//                    numText = Integer.toString(_hintCols[i][j - _hintRows[0].length]);
//                    graphics.drawCenteredString(numText, pos, size, _colFont);
//                }
//                // Range of board
//                else {
//                    _boardState[i - _hintCols.length][j - _hintRows[0].length].render(graphics);
////                    graphics.drawRect(pos, size[0]);
//                }
//            }
//        }
    }

    public void handleInput(int[] clickPos) {
        for (int i = 0; i < _rows; i++) {
            for (int j = 0; j < _cols; j++) {
                _boardState[i][j].handleInput(clickPos);
            }
        }
    }

    public boolean checkOriginalSolution() {
        boolean possible = true;
        int i = 0, j;
        while (possible && i < _rows) {
            j = 0;
            while (possible && j < _cols) {
                if ((_boardState[i][j].cellType == CellType.BLUE && !_sol[i][j]) ||
                        (_boardState[i][j].cellType != CellType.BLUE && _sol[i][j])) {
                    System.out.println("Error in row: " + i + ", col: " + j);
                    System.out.println("That cell is: " + _boardState[i][j].cellType + ", and should be " + _sol[i][j]);
                    possible = false;
                }
                j++;
            }
            i++;
        }
        return possible;
    }

    public boolean checkAnotherSolution() {
        boolean possible = true;

        // Check Rows
        int i = 0;
        while (possible && i < _rows) {
            possible = checkRow(i);
            if (possible) System.out.println("La fila " + i + " es correcta");
            i++;
        }

        // Check Cols
        int j = 0;
        while (possible && j < _cols) {
            possible = checkCol(j);
            if (possible) System.out.println("La columna " + j + " es correcta");
            j++;
        }

        return possible;
    }

    public boolean checkRow(int row) {
        boolean possible = true;
        boolean hintStart;
        int currCol = _cols - 1;
        int currHint = _hintRows[0].length - 1;
        int hintCounter = 0;

        while (possible && currCol >= 0) {
            // Empieza a comprobar esa pista
            if (_boardState[row][currCol].cellType == CellType.BLUE) {
                hintStart = true;
                hintCounter++;
            } else hintStart = false;

            // Si el contador tiene algun valor acumulado y se ha encontrado una casilla gris o el final del tablero
            if (hintCounter > 0 && (!hintStart || currCol == 0)) {
                if (hintCounter == _hintRows[row][currHint]) {
                    currHint--;
                    hintCounter = 0;
                } else possible = false;
            }

            currCol--;
        }
        // Si no ha contado todas las pistas distintas de 0 tampoco es posible
        // Si currHint es menor de 0 es que ha tenido que comprobar todas las pistas y por tanto esta bien,
        // Si no ha tenido que comprobar todas, hay que ver que la siguiente que le tocase fuera un 0.
        if (possible && currHint >= 0 && _hintRows[row][currHint] != 0) possible = false;

        return possible;
    }

    public boolean checkCol(int col) {
        boolean possible = true;
        boolean hintStart = false;
        int currRow = _rows - 1;
        int currHint = _hintCols.length - 1;
        int hintCounter = 0;

        while (possible && currRow >= 0) {
            // Empieza a comprobar esa pista
            if (_boardState[currRow][col].cellType == CellType.BLUE) {
                hintStart = true;
                hintCounter++;
            } else hintStart = false;
            // Si el contador tiene algun valor acumulado y se ha encontrado una casilla gris o el final del tablero
            if (hintCounter > 0 && (!hintStart || currRow == 0)) {
                if (hintCounter == _hintCols[currHint][col]) {
                    currHint--;
                    hintCounter = 0;
                } else possible = false;
            }
            currRow--;
        }
        // Si no ha contado todas las pistas distintas de 0 tampoco es posible
        // Si currHint es menor de 0 es que ha tenido que comprobar todas las pistas y por tanto esta bien,
        // Si no ha tenido que comprobar todas, hay que ver que la siguiente que le tocase fuera un 0.
        if (possible && currHint >= 0 && _hintCols[currHint][col] != 0) {
            possible = false;
        }
        return possible;
    }

    //-------------------------------------------MISC-------------------------------------------------//
    // Numero de filas y columnas del tablero
    public int getNum_rows() {
        return _rows;
    }

    public int getCols() {
        return _cols;
    }

    //----------------------------------------ATTRIBUTES----------------------------------------------//
    private final int _rows;
    private final int _cols;
    boolean[][] _sol;
    int[][] _hintRows;
    int[][] _hintCols;
    Cell[][] _boardState;

    int[] _pos = {10, 270};
    float[] _size;
    float _cellSize;
    Font _rowFont;
    Font _colFont;
}
