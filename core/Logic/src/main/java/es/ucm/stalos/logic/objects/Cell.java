package es.ucm.stalos.logic.objects;

import java.util.HashMap;
import java.util.Map;

import es.ucm.stalos.engine.IGraphics;
import es.ucm.stalos.logic.enums.CellType;

public class Cell {
    Cell(int row, int col, int[] pos, float cellSize) {
        // INDEX
        this.row = row;
        this.col = col;
        // POSITION
        this.x = pos[0];
        this.y = pos[1];
        // FILLED SQUARE POSITION
        this.fx = (int) (pos[0] + whiteMargin);
        this.fy = (int) (pos[1] + whiteMargin);
        // CELL SIZE
        this.size = cellSize;
        // FILLED SQUARE SIZE
        this.fsize = cellSize - whiteMargin * 2;
        // TYPE
        this.cellType = CellType.GREY;

        // COLORS - MAP
        _colors = new HashMap<>();
        _colors.put(CellType.BLUE, 0x0000FFFF);
        _colors.put(CellType.GREY, 0xBBBBBBFF);
        _colors.put(CellType.WHITE, 0xFFFFFFFF);
        _colors.put(CellType.RED, 0xFF0000FF);
    }

    public void render(IGraphics graphics) {
        // Filled Square
        int[] fillPos = new int[]{fx, fy};
        graphics.setColor(_colors.get(cellType));
        graphics.fillSquare(fillPos, fsize);
        int[] fillPos2 = new int[]{fx + (int) fsize, fy + (int) fsize};

        if (cellType == CellType.WHITE) {
            graphics.setColor(0x000000FF);
            graphics.drawRect(fillPos, fsize);
            graphics.drawLine(fillPos, fillPos2);
        }
    }

    public void handleInput(int[] clickPos) {
        if (clickInside(clickPos)) {
            switch (cellType) {
                case GREY:
                    cellType = CellType.BLUE;
                    break;
                case BLUE:
                    cellType = CellType.WHITE;
                    break;
                case WHITE:
                    cellType = CellType.GREY;
                    break;
            }
        }
    }

    /**
     * @param clickPos Mouse position
     * @return if the mouse has clicked inside the cell
     */
    private boolean clickInside(int[] clickPos) {
        return (clickPos[0] > x && clickPos[0] < (x + size) &&
                clickPos[1] > y && clickPos[1] < (y + size));
    }

    /**
     * Cell or color type
     */
    public CellType cellType;
    /**
     * Entire logic cell position X
     */
    public int x;
    /**
     * Entire logic cell position Y
     */
    public int y;
    /**
     * Logic filled square position X
     */
    public int fx;
    /**
     * Logic filled square position Y
     */
    public int fy;
    /**
     * Row index of the cell
     * in the board
     */
    public int row;
    /**
     * Column index of the cell
     * in the board
     */
    public int col;
    /**
     * Entire cell size
     */
    public float size;
    /**
     * Filled square size
     */
    public float fsize;
    /**
     * Offset to draw the filled square
     */
    public float whiteMargin = 2;
    /**
     * Different colors of the cel
     */
    private Map<CellType, Integer> _colors;
}
