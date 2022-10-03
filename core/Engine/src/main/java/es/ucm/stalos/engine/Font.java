package es.ucm.stalos.engine;

public interface Font {
    /**
     * Apply a new size to the font
     */
    void setSize(float newSize);

    /**
     * @return current font's size
     */
    float getSize();
}
