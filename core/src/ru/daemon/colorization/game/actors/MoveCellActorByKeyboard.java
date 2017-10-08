package ru.daemon.colorization.game.actors;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class MoveCellActorByKeyboard extends InputListener {
    private CellActor actor;

    public MoveCellActorByKeyboard(CellActor actor) {
        this.actor = actor;
    }

    @Override
    public boolean keyDown(InputEvent event, int keyCode) {
        switch (keyCode){
            case Input.Keys.UP: return actor.moveUp();
            case Input.Keys.DOWN: return actor.moveDown();
            case Input.Keys.LEFT: return actor.moveLeft();
            case Input.Keys.RIGHT: return actor.moveRight();
            default: return false;
        }
    }
}
