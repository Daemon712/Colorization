package ru.daemon.colorization.game.viewport;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import ru.daemon.colorization.game.viewport.BoundedViewport;

public class ZoomViewportByMouseWheel extends InputListener {
    private final BoundedViewport viewport;
    private final int min;
    private final int max;
    private final int speed;

    public ZoomViewportByMouseWheel(BoundedViewport viewport, int min, int max, int speed) {
        this.viewport = viewport;
        this.min = min;
        this.max = max;
        this.speed = speed;
    }

    public ZoomViewportByMouseWheel(BoundedViewport viewport) {
        this(viewport, 100, 800, 20);
    }

    @Override
    public boolean scrolled(InputEvent event, float x, float y, int amount) {
        float zoom = MathUtils.clamp(viewport.getZoom() + speed * amount, min, max);
        viewport.setZoom(zoom);
        return false;
    }
}
