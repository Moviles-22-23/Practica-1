package es.ucm.stalos.logic.enums;

/**
 * Information about different game states on GameState
 */
public enum PlayingState {
    Gaming(0),
    Checking(1),
    Win(2);

    PlayingState(int playingState) { this._playingState = playingState; }

    public int get_playingState() { return _playingState; }

    private int _playingState;
}
