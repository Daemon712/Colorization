package ru.daemon.colorization.game.actors.player;

import com.badlogic.gdx.math.Vector2;
import ru.daemon.colorization.game.actors.CellActor;
import ru.daemon.colorization.game.turns.TurnHandler;
import ru.daemon.colorization.game.turns.TurnManager;

public class MoveCellActorByRandom implements TurnHandler {
    private TurnManager turnManager;
    private CellActor actor;

    public MoveCellActorByRandom(TurnManager turnManager, CellActor actor) {
        this.turnManager = turnManager;
        this.actor = actor;
    }


    @Override
    public void handleTurn() {
        int x, y;
        do {
            Vector2 delta = generateVector();
            x = actor.getCellX() + (int) delta.x;
            y = actor.getCellY() + (int) delta.y;
        } while (!actor.checkCellPosition(x, y));
        turnManager.addCommand(new MoveByCommand(actor, x, y));
    }

    private Vector2 generateVector(){
        switch ((int) Math.floor(Math.random() * 4)) {
            case 0:
                return new Vector2 (1, 0);
            case 1:
                return new Vector2 (-1, 0);
            case 2:
                return new Vector2 (0, 1);
            case 3:
                return new Vector2 (0, -1);
            default:
                throw new RuntimeException();
        }
    }
}
