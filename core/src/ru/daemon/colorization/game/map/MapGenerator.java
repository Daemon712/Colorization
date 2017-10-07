package ru.daemon.colorization.game.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class MapGenerator {
    public static final String TERRAIN_LAYER = "Terrain";

    public static TiledMap generateTiledMap(int width, int height) {
        TiledMap tiledMap = new TiledMap();
        tiledMap.getTileSets().addTileSet(ColorTileSet.getTileSet());
        TiledMapTileLayer layer = new TiledMapTileLayer(width, height, ColorTileSet.TILE_SIZE, ColorTileSet.TILE_SIZE);
        layer.setName(TERRAIN_LAYER);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                ColorCell cell = new ColorCell();
                cell.getColor().setRed((int) (Math.random() * PointColor.MAX));
                cell.getColor().setGreen((int) (Math.random() * PointColor.MAX));
                cell.getColor().setBlue((int) (Math.random() * PointColor.MAX));
                layer.setCell(i, j, cell);
            }
        }
        tiledMap.getLayers().add(layer);
        return tiledMap;
    }
}
