package es.ucm.stalos.logic.objects;

import java.util.HashMap;
import java.util.Map;

import es.ucm.stalos.engine.Graphics;
import es.ucm.stalos.logic.enums.CellType;

public class Cell {
    Cell(int row, int col, int[] pos, float cellSize) {
        this.row = row;
        this.col = col;
        this.x = pos[0];
        this.y = pos[1];
        this.fx = (int)(pos[0] + whiteMargin);
        this.fy = (int)(pos[1] + whiteMargin);
        this.size = cellSize;
        this.fsize = cellSize - whiteMargin * 2;
        this.cellType = CellType.GREY;

        // COLORS - MAP
        _colors = new HashMap<>();
        _colors.put(CellType.BLUE, 0x0000FFFF);
        _colors.put(CellType.GREY, 0xBBBBBBFF);
        _colors.put(CellType.WHITE, 0xFFFFFFFF);
        _colors.put(CellType.RED, 0xFF0000FF);
    }

    public void render(Graphics graphics){
        int[] pos = new int[]{x, y};
        // Cuadrado blanco
        graphics.setColor(0xFFFFFFFF);
        graphics.fillSquare(pos, size);

        int[] fillPos = new int[]{fx, fy};
        graphics.setColor(_colors.get(cellType));
        graphics.fillSquare(fillPos, fsize);
        int[] fillPos2 = new int[]{fx + (int)fsize, fy + (int)fsize};

        if(cellType == CellType.WHITE) {
            graphics.setColor(0x000000FF);
            graphics.drawRect(fillPos, fsize);
            graphics.drawLine(fillPos, fillPos2);
        }
    }

    public void handleInput(int[] clickPos){
        if(clickInside(clickPos)){
            System.out.println("Click:" + row + " , " + col);
            switch(cellType) {
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

    private boolean clickInside(int[] clickPos){
        return (clickPos[0] > x && clickPos[0] < (x + size) &&
                clickPos[1] > y && clickPos[1] < (y + size));
    }

    public CellType cellType;
    public int x, y;
    public int fx, fy;
    public int row, col;
    public float size;
    public float fsize;
    public float whiteMargin = 2;
    private Map<CellType, Integer> _colors;
}
