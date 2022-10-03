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
    private float getScaleFactor() {
        float widthScale = getWidth() / _logWidth;
        float heightScale = getHeight() / _logHeight;

        // Nos interesa el tamaño más pequeño
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
        _scaleFactor = getScaleFactor();
        float offsetX = (getWidth() - (_logWidth * _scaleFactor)) / 2.0f;
        float offsetY = (getHeight() - (_logHeight) * _scaleFactor) / 2.0f;

        return new int [] {
                (int) ((x * _scaleFactor) + offsetX),
                (int) ((y * _scaleFactor) + offsetY)
        };
    }

    /**
     * Calculate the physic size applying the scale factor
     *
     * @param w width value
     * @param h height value
     * @return the real size [width, height]
     */
    protected int[] finalSize(float w, float h) {
        _scaleFactor = getScaleFactor();

        return new int[] {
                (int) (w * _scaleFactor),
                (int) (h * _scaleFactor)
        };
    }

    /**
     * Calculate the physic size applying the scale factor
     *
     * @param size size value
     * @return the real size
     */
    protected int finalSize(float size) {
        _scaleFactor = getScaleFactor();
        return (int) (size * _scaleFactor);
    }

    /**
     * Given a position P(x, y), returns a new value into the
     * logic system of coordinates
     * @param x X-axis position
     * @param y Y-axis position
     */
    public int[] logPos(int x, int y) {
        _scaleFactor = getScaleFactor();
        float offsetX = (_logWidth - (getWidth() / _scaleFactor)) / 2;
        float offsetY = (_logHeight - (getHeight() / _scaleFactor)) / 2;

        int newPosX = (int) ((x / _scaleFactor) + offsetX);
        int newPosY = (int) ((y / _scaleFactor) + offsetY);

        int[] newPos = new int[2];
        newPos[0] = newPosX;
        newPos[1] = newPosY;

        return newPos;
    }

    private int[] translateWindow() {
        float offsetX = (getWidth() - (_logWidth * _scaleFactor)) / 2.0f;
        float offsetY = (getHeight() - (_logHeight) * _scaleFactor) / 2.0f;

        int newPosX = (int) ((_logPosX * _scaleFactor) + offsetX);
        int newPosY = (int) ((_logPosY * _scaleFactor) + offsetY);

        int[] newPos = new int[2];
        newPos[0] = newPosX;
        newPos[1] = newPosY;

        return newPos;
    }

    @Override
    public void prepareFrame() {
        _scaleFactor = getScaleFactor();
        int[] newPos = translateWindow();

        translate(newPos[0], newPos[1]);
        scale(_scaleFactor, _scaleFactor);
    }

    @Override
    public int getLogWidth() {
        return (int) _logWidth;
    }

    @Override
    public int getLogHeight() {
        return (int) _logHeight;
    }


    // Logic position
    protected float _logPosX, _logPosY;

    // Logic scale
    protected float _logWidth, _logHeight;

    // Scale factor
    protected float _scaleFactor;
}
