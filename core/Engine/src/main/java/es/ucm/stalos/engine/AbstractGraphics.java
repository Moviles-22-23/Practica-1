package es.ucm.stalos.engine;

public abstract class AbstractGraphics implements Graphics {

    protected AbstractGraphics(int w, int h) {
        _logWidth = w;
        _logHeight = h;
        _logPosX = 0.0f;
        _logPosY = 0.0f;
    }

    /**
     * Calculate the scale to force a determined aspect ratio
     *
     * @return the scale factor
     */
    private float scale() {
        float widthScale = getWidth() / _logWidth;
        float heightScale = getHeight() / _logHeight;

        return Math.min(widthScale, heightScale);
    }

    /**
     * Calculate the physic position applying the scale factor
     *
     * @param x X-axis position
     * @param y Y-axis position
     * @return the real position [x, y]
     */
    protected int[] finalPosition(float x, float y) {
        _scale = scale();

        int[] finalPos = new int[2];

        finalPos[0] = (int) (x * _scale);
        finalPos[1] = (int) (y * _scale);

        return finalPos;
    }

    /**
     * Calculate the physic size applying the scale factor
     *
     * @param w width value
     * @param h height value
     * @return the real size [width, height]
     */
    protected int[] finalSize(float w, float h) {
        _scale = scale();

        int[] finalSize = new int[2];

        finalSize[0] = (int) (w * _scale);
        finalSize[1] = (int) (h * _scale);

        return finalSize;
    }

    /**
     * Calculate the physic size applying the scale factor
     *
     * @param size size value
     * @return the real size
     */
    protected int finalSize(float size) {
        _scale = scale();
        return (int) (size * _scale);
    }

    // Logic position
    protected float _logPosX, _logPosY;

    // Logic scale
    protected float _logWidth, _logHeight;

    // Scale factor
    protected float _scale;
}
