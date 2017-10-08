package ru.daemon.colorization.game.viewport;

import com.badlogic.gdx.scenes.scene2d.actions.RelativeTemporalAction;

public class ZoomViewportAction extends RelativeTemporalAction {
    private BoundedViewport viewport;
    private float deltaZoom;

    public void setViewport(BoundedViewport viewport) {
        this.viewport = viewport;
    }

    public void setDeltaZoom(float deltaZoom) {
        this.deltaZoom = deltaZoom;
    }

    @Override
    protected void updateRelative(float percentDelta) {
        viewport.setZoom(viewport.getZoom() + deltaZoom * percentDelta);
    }
}
