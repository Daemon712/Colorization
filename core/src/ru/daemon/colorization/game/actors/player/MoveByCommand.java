package ru.daemon.colorization.game.actors.player;

import ru.daemon.colorization.game.actors.CellActor;
import ru.daemon.colorization.game.turns.TurnCommand;
import ru.daemon.colorization.game.turns.TurnManager;

public class MoveByCommand implements TurnCommand {
    private CellActor actor;
    private int cellX, cellY;

    public MoveByCommand(CellActor actor, int cellX, int cellY) {
        this.actor = actor;
        this.cellX = cellX;
        this.cellY = cellY;
    }

    @Override
    public void execute() {
        actor.setCellPosition(cellX, cellY, TurnManager.TURN_DURATION * 0.8f);
    }
}
