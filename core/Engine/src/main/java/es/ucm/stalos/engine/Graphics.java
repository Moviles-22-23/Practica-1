package es.ucm.stalos.engine;

public interface Graphics {

    /**
     * Load an image from the assets folder
     * @param name name of te image
     * @return the loaded image
     */
    Image newImage(String name);

    /**
     * Create a new font with a specified size from a .ttf.
     * @param filename name of the font
     * @param size size of the font
     * @param isBold true for bold letters
     * @return
     */
    Font newFont(String filename, int size, boolean isBold);

    /**
     * Clear the screen with a specified color
     * @param color color to fill screen
     */
    void clear(int color);

    /**
     * Show an image in the screen
     * @param image Image to show
     * @param pos Upper-Left position
     * @param size Size of the image
     */
    void drawImage(Image image, int[] pos, float[]size);

    /**
     * Set color to use in next drawn operations
     * @param color color to use
     */
    void setColor(int color);

    /**
     * Draw a filled square
     * @param pos Position to draw
     * @param side Side Length
     */
    void fillSquare(int[] pos, float side);

    /**
     * Draw an empty square
     * @param pos Position to draw
     * @param side Side Length
     */
    void drawSquare(int[] pos, float side);

    /**
     * Draw a line
     */
    void drawLine(int[] start, int[] end);

    /**
     * Draw a text from Upper-Left position
     */
    void drawText(String text, int[] pos);

    /**
     * @return window width
     */
    int getWidth();

    /**
     * @return window height
     */
    int getHeight();

    // TODO
    /**
     * Updates the buffer
     * */
    void updateGraphics();

    /**
     * Prepare the next frame
     */
    void prepareFrame();

    void translate(int x, int y);

    void scale(float x, float y);

    void save();

    void restore();
}