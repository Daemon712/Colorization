package ru.daemon.colorization.game.actors.player;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.daemon.colorization.game.logic.ColorHolder;
import ru.daemon.colorization.game.logic.PointHolder;
import ru.daemon.colorization.game.map.ColorCell;
import ru.daemon.colorization.game.turns.TurnHandler;

public class CollectColor implements TurnHandler {
    private TiledMapTileLayer tiles;
    private ColorActor actor;
    private ColorHolder.Component component;
    private int points;

    public CollectColor(TiledMapTileLayer tiles, ColorActor actor, ColorHolder.Component component, int points) {
        this.tiles = tiles;
        this.actor = actor;
        this.component = component;
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public ColorHolder.Component getComponent() {
        return component;
    }

    public void setComponent(ColorHolder.Component component) {
        this.component = component;
    }

    @Override
    public void handleTurn() {
        int x = actor.getCellX();
        int y = actor.getCellY();
        ColorCell cell = (ColorCell) tiles.getCell(x, y);

        PointHolder cellPoints = cell.getColorHolder().get(component);
        PointHolder actorPoints = actor.getColorHolder().get(component);

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
