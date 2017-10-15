package ru.daemon.colorization.game.actors.player;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.daemon.colorization.game.map.ColorCell;
import ru.daemon.colorization.game.map.PointColor;
import ru.daemon.colorization.game.turns.TurnHandler;

public class CollectColor implements TurnHandler {
    private Player player;
    private TiledMapTileLayer tiles;

    public CollectColor(Player player, TiledMapTileLayer tiles) {
        this.player = player;
        this.tiles = tiles;
    }

    @Override
    public void handleTurn() {
        int x = player.getCellX();
        int y = player.getCellY();
        ColorCell cell = (ColorCell) tiles.getCell(x, y);
        PointColor color = cell.getColor();
        if (color.getRed() > 0){
            color.setRed(color.getRed() - 1);
            player.setRed(player.getRed() + 1);
        }
        if (color.getGreen() > 0){
            color.setGreen(color.getGreen() - 1);
            player.setGreen(player.getGreen() + 1);
        }
        if (color.getBlue() > 0){
            color.setBlue(color.getBlue() - 1);
            player.setBlue(player.getBlue() + 1);
        }

    }
}
