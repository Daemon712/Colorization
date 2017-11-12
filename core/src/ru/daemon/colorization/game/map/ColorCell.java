package ru.daemon.colorization.game.map;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.daemon.colorization.game.logic.ColorHolder;

public class ColorCell extends TiledMapTileLayer.Cell {
    private final ColorHolder color;
    private final ColorTileSet tileSet;

    public ColorCell(ColorHolder color, ColorTileSet tileSet) {
        this.color = color;
        this.tileSet = tileSet;
    }

    @Override
    public TiledMapTile getTile() {
        int r = color.getRed().get();
        int g = color.getGreen().get();
        int b = color.getBlue().get();
        return tileSet.getTile(r, g, b);
    }

    public ColorHolder getColorHolder() {
        return color;
    }
}
