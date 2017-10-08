package ru.daemon.colorization.game.viewport;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class BoundedViewport extends ExtendViewport {
    private float bottomBound, topBound, leftBound, rightBound;
    private final float boundHeight, boundWidth;
    private float zoom;
    private final float offset;
    private final float maxZoom, minZoom;

    public BoundedViewport(float zoom, float boundHeight, float boundWidth, float offset){
        super(zoom, zoom);
        this.offset = offset;
        this.boundHeight = boundHeight;
        this.boundWidth = boundWidth;
        setZoom(zoom);
        this.maxZoom = Math.max(boundHeight, boundWidth) - 2 * offset;
        this.minZoom = zoom;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        float clamp = MathUtils.clamp(zoom, minZoom, maxZoom);
        if (clamp == this.zoom) return;

        this.zoom = clamp;
        setMinWorldHeight(this.zoom);
        setMinWorldWidth(this.zoom);
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

    public float getOffset() {
        return offset;
    }

    public OrthographicCamera getCamera() {
        return (OrthographicCamera) super.getCamera();
    }

    @Override
    public void update(int screenWidth, int screenHeight, boolean centerCamera) {
        super.update(screenWidth, screenHeight, centerCamera);
        calculateBounds();
    }

    private void calculateBounds(){
        bottomBound = getWorldHeight() / 2 + offset;
        topBound = boundHeight - bottomBound;
        leftBound = getWorldWidth() / 2 + offset;
        rightBound = boundWidth - leftBound;
    }
}
