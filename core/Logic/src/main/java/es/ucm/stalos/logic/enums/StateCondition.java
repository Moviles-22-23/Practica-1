package es.ucm.stalos.logic.enums;

public enum StateCondition {
    Playing(0),
    Checking(1),
    Win(2);

    StateCondition(int i) { this.value = i; }

    public int getValue () { return value; }

    private int value;
}
