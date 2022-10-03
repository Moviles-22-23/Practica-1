package es.ucm.stalos.engine;

import java.awt.FontMetrics;

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
    Font newFont(String filename, int size, boolean isBold) throws Exception;

//-----------------------------------------------------------------//
    /**
     * Clear the screen with a specified color
     * @param color color to fill screen
     */
    void clear(int color);

    /**
     * Set color to use in next drawn operations
     * @param color color to use
     */
    void setColor(int color);
//-----------------------------------------------------------------//
    /**
     * Show an image in the screen
     * @param image Image to show
     * @param pos Upper-Left position
     * @param size Size of the image
     */
    void drawImage(Image image, int[] pos, float[]size);

    /**
     * Draw a text from Upper-Left position
     */
    void drawText(String text, int[] pos, Font font);

    /**
     * Draw a String centered in the middle of a Rectangle.
     *
     * @param text The String to draw.
     * @param pos The Up-Left corner
     * @param size The Down-Righ corner
     */
    public void drawCenteredString(String text, int[] pos, float[] size, Font font);

    /**
     * Draw an empty square
     * @param pos Position to draw
     * @param side Side Length
     */
    void drawRect(int[] pos, float side);

    /**
     * Draw a line
     */
    void drawLine(int[] start, int[] end);

    /**
     * Draw a filled square
     * @param pos Position to draw
     * @param side Side Length
     */
    void fillSquare(int[] pos, float side);

//----------------------------------------------------------------//
    /**
     * @return window width
     */
    int getWidth();

    /**
     * @return window height
     */
    int getHeight();
    /**
     * Devuelve el ancho de la ventana lógica
     * */
    int getLogWidth();
    /**
     * Devuelve el alto de la ventana lógica
     * */
    int getLogHeight();

//----------------------------------------------------------------//
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
