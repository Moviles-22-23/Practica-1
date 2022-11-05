package es.ucm.stalos.logic.enums;

/**
 * Information about different game states on GameState
 */
public enum PlayingState {
    Gaming(0),
    Checking(1),
    Win(2);

    PlayingState(int i) { this.value = i; }

    public int getValue () { return value; }

    private int value;
}
