package ru.daemon.colorization.game.viewport;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class ZoomViewportByScroll extends InputListener {
    private final Stage stage;
    private final int speed;

    public ZoomViewportByScroll(Stage stage, int speed) {
        this.stage = stage;
        this.speed = speed;
    }

    public ZoomViewportByScroll(Stage stage) {
        this(stage, 30);
    }

    @Override
    public boolean scrolled(InputEvent event, float x, float y, int amount) {
        ZoomViewportAction action = Actions.action(ZoomViewportAction.class);
        action.setDeltaZoom(speed * amount);
        action.setDuration(0.2f);
        action.setViewport((BoundedViewport) stage.getViewport());
        stage.addAction(action);
        return true;
    }
}