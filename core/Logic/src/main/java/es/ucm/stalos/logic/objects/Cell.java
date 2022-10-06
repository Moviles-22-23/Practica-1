package es.ucm.stalos.logic.objects;

import es.ucm.stalos.engine.Graphics;
import es.ucm.stalos.logic.enums.CellType;

public class Cell {
    Cell(int row, int col, int[] pos, float cellSize) {
        this.row = row;
        this.col = col;
        x = pos[0];
        y = pos[1];
        this.color = 0xBBBBBBFF;
        this.size = cellSize;
        this.cellType = CellType.GREY;
    }

    public void render(Graphics graphics){
        graphics.setColor(color);
        graphics.fillSquare(new int[]{x,y}, size);
    }

    public void handleInput(int[] clickPos){
        if(clickInside(clickPos)){
            System.out.println("Click:" + row + " , " + col);
            switch(cellType) {
                case GREY:
                    color = 0x0000FFFF;
                    cellType = CellType.BLUE;
                    break;
                case BLUE:
                    color = 0x00FF00FF;
                    cellType = CellType.WHITE;
                    break;
                case WHITE:
                    color = 0xBBBBBBFF;
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
    private int color;
}
