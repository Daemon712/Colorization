package ru.daemon.colorization.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class BoundedViewport extends ExtendViewport {
    private float bottomBound, topBound, leftBound, rightBound;
    private final float boundHeight, boundWidth;
    private float zoom;

    public BoundedViewport(float zoom, float boundHeight, float boundWidth, Camera camera) {
        super(zoom, zoom, camera);
        this.boundHeight = boundHeight;
        this.boundWidth = boundWidth;
        setZoom(zoom);
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
        setMinWorldHeight(zoom);
        setMinWorldWidth(zoom);
        update(getScreenWidth(), getScreenHeight());
        calculateBounds();
    }

    public float getBottomBound() {
        return bottomBound;
    }

    public float getTopBound() {
        return topBound;
    }

    public float getLeftBound() {
        return leftBound;
    }

    public float getRightBound() {
        return rightBound;
    }

    public float getBoundHeight() {
        return boundHeight;
    }

    public float getBoundWidth() {
        return boundWidth;
    }

    @Override
    public void update(int screenWidth, int screenHeight, boolean centerCamera) {
        super.update(screenWidth, screenHeight, centerCamera);
        calculateBounds();
    }

    private void calculateBounds(){
        bottomBound = getWorldHeight() / 2;
        topBound = boundHeight - bottomBound;
        leftBound = getWorldWidth() / 2;
        rightBound = boundWidth - leftBound;
    }
}
