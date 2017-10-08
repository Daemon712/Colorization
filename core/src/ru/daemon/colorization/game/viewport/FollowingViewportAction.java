package ru.daemon.colorization.game.viewport;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Align;

public class FollowingViewportAction extends Action {
    private final BoundedViewport viewport;
    private final Camera camera;

    public FollowingViewportAction(BoundedViewport viewport) {
        this.viewport = viewport;
        this.camera = viewport.getCamera();
    }

    @Override
    public boolean act(float delta) {
        if (camera.viewportWidth > viewport.getBoundWidth() - 2 * viewport.getOffset()) {
            camera.position.x = viewport.getBoundWidth() / 2;
        } else {
            camera.position.x = MathUtils.clamp(target.getX(Align.center), viewport.getLeftBound(), viewport.getRightBound());
        }

        if (camera.viewportHeight > viewport.getBoundHeight() - 2 * viewport.getOffset()) {
            camera.position.y = viewport.getBoundHeight() / 2;
        } else {
            camera.position.y = MathUtils.clamp(target.getY(Align.center), viewport.getBottomBound(), viewport.getTopBound());
        }

        camera.update();
        return true;
    }
}
