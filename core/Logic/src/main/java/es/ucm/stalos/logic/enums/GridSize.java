package es.ucm.stalos.logic.enums;

public enum GridSize {
    _4x4(0),
    _5x5(1),
    _5x10(2),
    _10x10(4),
    _8x8(3),
    _10x15(5),
    MAX(6);

    GridSize(int i) { this.value = i; }

    public int getValue () { return value; }

    private int value;
}
