package ru.daemon.colorization.game.map;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class ColorCell extends TiledMapTileLayer.Cell {
    private final PointColor color = new PointColor();

    @Override
    public TiledMapTile getTile() {
        return ColorTileSet.getTileSet().getTile(color.getId());
    }

    public PointColor getColor() {
        return color;
    }
}
