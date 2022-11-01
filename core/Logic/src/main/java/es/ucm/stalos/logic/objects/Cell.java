package es.ucm.stalos.logic.objects;

import java.util.HashMap;
import java.util.Map;

import es.ucm.stalos.engine.Graphics;
import es.ucm.stalos.logic.enums.CellType;

public class Cell {
    Cell(int row, int col, int[] pos, float cellSize) {
        this.row = row;
        this.col = col;
        x = pos[0];
        y = pos[1];
        this.size = cellSize;
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
        graphics.setColor(_colors.get(cellType));
        graphics.fillSquare(pos, size);
        graphics.setColor(0x000000FF);
        graphics.drawRect(pos, size);
        if(cellType == CellType.WHITE) graphics.drawLine(pos, new int[]{pos[0] + (int)size, pos[1] + (int)size});
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
    public int row, col;
    public float size;
    private Map<CellType, Integer> _colors;
}
