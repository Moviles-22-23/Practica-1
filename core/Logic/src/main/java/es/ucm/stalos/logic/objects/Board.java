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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import es.ucm.stalos.engine.Engine;
import es.ucm.stalos.engine.Font;
import es.ucm.stalos.engine.Graphics;
import es.ucm.stalos.engine.IFile;
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
    public Board(int rows, int cols, int[] pos, float[] size, boolean isRandom) {
        this._rows = rows;
        this._cols = cols;
        this._sol = new boolean[rows][cols];
        this._boardState = new Cell[rows][cols];
        this._hintRows = new int[rows][(int) Math.ceil(cols / 2.0f)];
        this._hintCols = new int[(int) Math.ceil(rows / 2.0f)][cols];
        this._pos = pos;
        this._size = size;
        this._isRandom = isRandom;

        // Cell Size must be square so we have to use the min between rows and cols
        float maxRowsSize = size[1] * 2 / (rows * 2 + (int) Math.ceil(rows / 2.0f));
        float maxColsSize = size[0] * 2 / (cols * 2 + (int) Math.ceil(cols / 2.0f));
        _cellSize = Math.min(maxRowsSize, maxColsSize);
        _hintSize = _cellSize / 2;
    }

    //-------------------------------------------INIT-------------------------------------------------//
    public boolean init(Engine engine) {
        try {
            _engine = engine;
            System.out.println("Aux: " + _hintSize);
            _fontSize = (int) (_hintSize * 0.9f);
            _hintFont = engine.getGraphics().newFont("JosefinSans-Bold.ttf", _fontSize, true);

            if(_isRandom) createRandomSolution();
            else readSolution();

            loadLevel();

        } catch (Exception e) {
            System.out.println("Error leyendo el mapa");
            return false;
        }
        return true;
    }

    private void readSolution() throws FileNotFoundException {
        try {
            // LevelPack Name
            String name = "levels/levelPack" + String.valueOf(_rows) + "x" + String.valueOf(_cols) + ".txt";
            // IFile from the current platform
            IFile file = _engine.newFile(name);
            BufferedReader br = file.getBufferReader();
            //
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
            System.out.println("ERROR ANDROID: " + e.getMessage());
            System.out.println("Error reading file");
        }
    }

    private void createRandomSolution(){
        Random rn = new Random();
        for (int i = 0; i < _rows; i++) {
            for (int j = 0; j < _cols; j++) {
                boolean aux = rn.nextBoolean();
                System.out.println("Aux: " + aux);
                _sol[i][j] = aux;
            }
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
        int[] cellsPos = new int[2];
        cellsPos[0] = _pos[0] + (int) (_hintSize * _hintRows[0].length);
        cellsPos[1] = _pos[1] + (int) (_hintSize * _hintCols.length);

        int aux = cellsPos[0];

        int[] pos = new int[2];
        for (int i = 0; i < _rows; i++) {
            pos[1] = cellsPos[1] + (int) (i * _cellSize);
            for (int j = 0; j < _cols; j++) {
                pos[0] = cellsPos[0] + (int) (j * _cellSize);
                _boardState[i][j] = new Cell(i, j, pos, _cellSize);
            }
        }
    }

//----------------------------------------MAIN-LOOP-----------------------------------------------//
    public void render(Graphics graphics) {
        if(!_isWin) {
            // Variable auiliares para pintar
            int[] pos = new int[2];
            float[] size = new float[2];
            String numText;

            // Number Colors
            graphics.setColor(0x000000FF);

            // TOTAL ROWS
            for (int i = 0; i < (_boardState.length + _hintCols.length); i++) {
                // TOTAL COLS
                for (int j = 0; j < (_boardState[0].length + _hintRows[0].length); j++) {
                    // Range of empty space (BORRAR)
                    if (i < _hintCols.length && j < _hintRows[0].length) {
                        // Empty o poner aqui el dibujo peque o algo
                    }
                    // Range of hints rows
                    else if (i >= _hintCols.length && j < _hintRows[0].length) {
                        // Los 0 no hace falta ponerlos
                        if(_hintRows[i - _hintCols.length][j] != 0) {
                            graphics.setColor(0x000000FF);
                            pos[0] = _pos[0] + (int) (j * _hintSize);
                            pos[1] = _pos[1] + (int) ((_hintCols.length * _hintSize) + ((i - _hintCols.length) * _cellSize));
                            size[0] = _hintSize;
                            size[1] = _cellSize;
                            numText = Integer.toString(_hintRows[i - _hintCols.length][j]);
                            graphics.drawCenteredString(numText, pos, size, _hintFont);
                        }
                    }
                    // Range of hints cols
                    else if (i < _hintCols.length && j >= _hintRows[0].length) {
                        if(_hintCols[i][j - _hintRows[0].length] != 0) {
                            graphics.setColor(0x000000FF);
                            pos[0] = _pos[0] + (int) ((_hintRows[0].length * _hintSize) + ((j - _hintRows[0].length) * _cellSize));
                            pos[1] = _pos[1] + (int) (i * _hintSize);
                            size[0] = _cellSize;
                            size[1] = _hintSize;
                            numText = Integer.toString(_hintCols[i][j - _hintRows[0].length]);
                            graphics.drawCenteredString(numText, pos, size, _hintFont);
                        }
                    }
                    // Range of board
                    else {
                        _boardState[i - _hintCols.length][j - _hintRows[0].length].render(graphics);
                    }
                }
            }

            graphics.setColor(0x000000FF);

            // HintsRows Rect
            pos[0] = _pos[0];
            pos[1] = _pos[1] + (int) (_hintSize * _hintCols.length);
            size[0] = _hintRows[0].length * _hintSize;
            size[1] = _hintRows.length * _cellSize;
            graphics.drawRect(pos, size);

            // HintCols Rect
            pos[0] = _pos[0] + (int) (_hintSize * _hintRows[0].length);
            pos[1] = _pos[1];
            size[0] = _hintCols[0].length * _cellSize;
            size[1] = _hintCols.length * _hintSize;
            graphics.drawRect(pos, size);
        }
        else{
            int oneRowSize = (int)(_size[0] / _rows);
            int oneColSize = (int)(_size[1] / _cols);
            int rowMargin = (int)(((_size[0] / _rows) - _cellSize) * 0.5f);
            int colMargin = (int)(((_size[1] / _cols) - _cellSize) * 0.5f);

            int size = Math.min(oneRowSize, oneColSize);
            int margin = Math.min(rowMargin, colMargin);

            // Aqui dibuja solo la solucion cuando hemos ganado
            for (int i = 0; i < _rows; i++) {
                for (int j = 0; j < _cols; j++) {
                    graphics.setColor(0x0000FFFF);

                    int[] solPos = {_pos[0] + size * j + margin, _pos[1] + size * i + margin };

                    // Utiliza el estado del tablero por si se ha resuelto con otra solucion
                    if(_boardState[i][j].cellType == CellType.BLUE) graphics.fillSquare(solPos, _cellSize);
                }
            }
        }
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

    /**
     * Count mistakes from the board and return them as an array
     *
     * @return Array of mistakes: [0] = left cells, [1] = wrong cells
     */
    public int[] countMistakes() {
        int[] mistakes = {0, 0};
        _wrongCells = new ArrayList<>();

        for (int i = 0; i < _rows; i++) {
            for (int j = 0; j < _cols; j++) {
                Cell c = _boardState[i][j];
                if (c.cellType == CellType.GREY && _sol[i][j] || c.cellType == CellType.WHITE && _sol[i][j]) {
                    mistakes[0]++;
                } else if (c.cellType == CellType.BLUE && !_sol[i][j]) {
                    mistakes[1]++;
                    c.cellType = CellType.RED;
                    _wrongCells.add(new int[]{i, j});
                }
            }
        }

        return mistakes;
    }

    public void resetWrongCells()
    {
        for (int [] index : _wrongCells)
        {
            int i = index[0];
            int j = index[1];
            _boardState[i][j].cellType = CellType.BLUE;
        }
    }

    //-------------------------------------------MISC-------------------------------------------------//
    // Numero de filas y columnas del tablero
    public int getNum_rows() {
        return _rows;
    }

    public int getCols() {
        return _cols;
    }

    public void setPos(int[] newPos) {
        _pos = newPos;
    }

    public void setSize(float[] newSize) {
        _size = newSize;
    }

    public void setWin(boolean state) { _isWin = state; }

    //----------------------------------------ATTRIBUTES----------------------------------------------//
    private Engine _engine;

    private final int _rows;
    private final int _cols;
    boolean[][] _sol;
    int[][] _hintRows;
    int[][] _hintCols;
    Cell[][] _boardState;
    List<int[]> _wrongCells;
    boolean _isWin = false;
    boolean _isRandom;

    int[] _pos;
    float[] _size;
    float _cellSize;
    float _hintSize;
    Font _hintFont;
    int _fontSize;
}
