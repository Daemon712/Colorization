package ru.daemon.colorization.game.actors.player;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import ru.daemon.colorization.game.actors.CellActor;
import ru.daemon.colorization.game.turns.TurnManager;

public class MoveCellActorByKeyboard extends InputListener {
    private TurnManager turnManager;
    private CellActor actor;
    private int deltaX;
    private int deltaY;

    public MoveCellActorByKeyboard(TurnManager turnManager, CellActor actor) {
        this.turnManager = turnManager;
        this.actor = actor;

        this.turnManager.addAfterTurnHandler(this::makeMove);
    }

    @Override
    public boolean keyDown(InputEvent event, int keyCode) {
        return handleInput(keyCode, true) && makeMove();
    }

    @Override
    public boolean keyUp(InputEvent event, int keyCode) {
        return handleInput(keyCode, false) && makeMove();
    }

    private boolean makeMove() {
        if (Math.abs(deltaX + deltaY) != 1) return false;

        int x = actor.getCellX() + deltaX;
        int y = actor.getCellY() + deltaY;

        if (actor.checkCellPosition(x, y)) {
            turnManager.addCommand(new MoveByCommand(actor, x, y));
            turnManager.turn();
            return true;
        } else {
            return false;
        }
    }

    private boolean handleInput(int keyCode, boolean keyDown) {
        switch (keyCode) {
            case Input.Keys.LEFT:
                deltaX += keyDown ? -1 : +1;
                return true;
            case Input.Keys.RIGHT:
                deltaX += keyDown ? +1 : -1;
                return true;
            case Input.Keys.DOWN:
                deltaY += keyDown ? -1 : +1;
                return true;
            case Input.Keys.UP:
                deltaY += keyDown ? +1 : -1;
                return true;
            default:
                return false;
        }
    }
}
