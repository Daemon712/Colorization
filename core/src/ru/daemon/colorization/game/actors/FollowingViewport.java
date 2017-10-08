package ru.daemon.colorization.game.actors;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Align;
import ru.daemon.colorization.game.BoundedViewport;

public class FollowingViewport extends Action {
    private final BoundedViewport viewport;
    private final Camera camera;

    public FollowingViewport(BoundedViewport viewport) {
        this.viewport = viewport;
        this.camera = viewport.getCamera();
    }

    @Override
    public boolean act(float delta) {
        if (camera.viewportWidth > viewport.getBoundWidth()){
            camera.position.x = viewport.getBoundWidth()/2;
        } else {
            camera.position.x = MathUtils.clamp(target.getX(Align.center), viewport.getLeftBound(), viewport.getRightBound());
        }

        if (camera.viewportHeight > viewport.getBoundHeight()){
            camera.position.y = viewport.getBoundHeight()/2;
        } else {
            camera.position.y = MathUtils.clamp(target.getY(Align.center), viewport.getBottomBound(), viewport.getTopBound());
        }

        camera.update();
        return true;
    }
}
