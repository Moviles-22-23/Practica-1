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

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

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

        readSolution();
        loadLevel();
    }

    private void readSolution(){
//        File f = new File("./assets/testLevel.txt");
//        FileReader fr = new FileReader(f);
//         1   1
//         1 2 1   1
//         1 2 1 2 1
//        __________
//     5 | X X X X X
//   1 1 | - X - X -
// 1 1 1 | X - X - X
//     1 | - X - - -
//     3 | X X X - -
        // Estas lineas hay que obtenerlas leyendo un .txt pero ahora mismo me da error
        String[] a = {"11111", "01010", "10101", "01000", "11100"};

        String line;
        for(int i = 0; i < _rows; i++){
            line = a[i];
            for(int j = 0; j < _cols; j++){
                if(a[i].charAt(j) == '0') _sol[i][j] = false;
                else _sol[i][j] = true;
            }
        }
    }

    private void loadLevel(){
        loadHintsRows();
        loadHintsCols();
    }

    /**
     * Is important to read right to left the row to fill hintsRows
     * - 2 1 | 1 1 0 1 0
     */
    private void loadHintsRows(){
        int cont, auxJ;

        System.out.println(_sol.length + " " + (_sol[0].length - 1));
        // Cada fila de las pistas (Up to Down)
        for(int i = 0; i < _sol.length; i++){
            // Reset cont and auxJ
            cont = 0;
            auxJ = _hintRows[0].length - 1;

            // Columns (Right to Left)
            for(int j = _sol[0].length - 1; j >= 0; j--){
                System.out.println("Find a :" + _sol[i][j] +" in i: " + i + ", j:" + j);
                // Plus one to counter
                if(_sol[i][j]){
                    cont++;
                }
                // Save the counter number as a hint
                else if(cont > 0) {
                    _hintRows[i][auxJ] = cont;
                    cont = 0;
                    auxJ--;
                }
            }
            if(cont > 0) _hintRows[i][auxJ] = cont;
        }
        // Borrar es la prueba
        String line = "";
        for(int i = 0; i < _hintRows.length; i++){
            line = "";
            for(int j = 0; j < _hintRows[0].length; j++){
                line += _hintRows[i][j];
            }
            System.out.println(line);
        }
    }

    private void loadHintsCols(){
        int cont, auxI;

        // Cada columna de las pistas (Left to Right)
        for(int j = 0; j < _sol[0].length; j++){
            // Reset cont and auxJ
            cont = 0;
            auxI = _hintCols.length - 1;

            // Filas (Down to Up)
            for(int i = _sol.length - 1; i >= 0; i--){
                // Plus one to counter
                if(_sol[i][j]){
                    cont++;
                }
                // Save the counter number as a hint
                else if(cont > 0) {
                    _hintCols[auxI][j] = cont;
                    cont = 0;
                    auxI--;
                }
            }
            if(cont > 0) _hintCols[auxI][j] = cont;
        }
        // Borrar es la prueba
        String line = "";
        for(int i = 0; i < _hintCols.length; i++){
            line = "";
            for(int j = 0; j < _hintCols[0].length; j++){
                line += _hintCols[i][j];
            }
            System.out.println(line);
        }
    }

    public void render(Graphics graphics){
//        System.out.println("RenderBoard with rows:" + (_boardState.length + _hintRows[0].length) + " cols:" + (_boardState[0].length + _hintCols.length));
        // Number Colors
        graphics.setColor(0x000000FF);

        // TOTAL ROWS
        for(int i = 0; i < (_boardState.length + _hintCols.length); i++){
            // TOTAL COLS
            for(int j = 0; j < (_boardState[0].length + _hintRows[0].length); j++){
                int[] pos = new int[2];
                // Carefull, position x is horizontall that corresponse with the j
                pos[0] = _pos[0] + (int) (j * _cellSize);
                pos[1] = _pos[1] + (int) (i * _cellSize);
                float[] size = new float[2];
                size[0] = _cellSize;
                size[1] = _cellSize;

                String numText;
                // Range of empty space (BORRAR)
                if(i < _hintCols.length && j < _hintRows[0].length){
                    // Empty
                }
                // Range of hints rows
                else if(i >= _hintCols.length && j < _hintRows[0].length){
                    graphics.drawImage(Assets.cellHelp, pos, size);
                    numText = Integer.toString(_hintRows[i - _hintCols.length][j]);
//                    graphics.drawText(numText, pos, Assets.jose);
                    graphics.drawCenteredString(numText, pos, size, Assets.jose);
                }
                // Range of hints cols
                else if(i < _hintCols.length && j >= _hintRows[0].length){
                    graphics.drawImage(Assets.cellHelp2, pos, size);
                    numText = Integer.toString(_hintCols[i][j - _hintRows[0].length]);
//                    graphics.drawText(numText, pos, Assets.jose);
                    graphics.drawCenteredString(numText, pos, size, Assets.jose);

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
