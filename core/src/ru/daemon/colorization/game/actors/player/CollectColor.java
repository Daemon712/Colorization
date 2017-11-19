package ru.daemon.colorization.game.actors.player;

import ru.daemon.colorization.game.logic.ColorHolder;
import ru.daemon.colorization.game.logic.PointHolder;
import ru.daemon.colorization.game.map.ColorCell;
import ru.daemon.colorization.game.turns.TurnHandler;

public class CollectColor implements TurnHandler {
    private ColorActor actor;
    private ColorHolder.Component component;
    private int points;

    public CollectColor(ColorActor actor, ColorHolder.Component component, int points) {
        this.actor = actor;
        this.component = component;
        this.points = points;
    }

    @Override
    public void handleTurn() {
        ColorCell cell = (ColorCell) actor.getCell();

        PointHolder cellPoints = cell.color().get(component);
        PointHolder actorPoints = actor.color().get(component);

        if (points > 0){
            int cellGives = cellPoints.take(points);
            int actorTakes = actorPoints.give(cellGives);
            cellPoints.give(cellGives - actorTakes);
        } else {
            int actorGives = actorPoints.take(-points);
            int cellTakes = cellPoints.give(actorGives);
            actorPoints.give(actorGives - cellTakes);
        }
    }
}
