package es.ucm.stalos.engine;

public interface IState {
    /**
     * Initialize the state
     * @return false if it fails
     * */
    boolean init();
    /**
     * Updates logic
     * */
    void update(double deltaTime);
    /**
     * Updates graphics
     * */
    void render();
    /**
     * Updates input
     * */
    void handleInput();

}