package ru.daemon.colorization.game.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;

import java.util.Random;

public class MapGenerator {
    public static final String TERRAIN_LAYER = "Terrain";

    public static TiledMap generateTiledMap(int width, int height) {
        TiledMap tiledMap = new TiledMap();
        tiledMap.getTileSets().addTileSet(ColorTileSet.getTileSet());
        TiledMapTileLayer layer = new TiledMapTileLayer(width, height, ColorTileSet.TILE_SIZE, ColorTileSet.TILE_SIZE);
        layer.setName(TERRAIN_LAYER);
        Random random = new Random();
        ScaledPerlinNoise2D redNoise = new ScaledPerlinNoise2D(random.nextLong(), width, height, 0.2f);
        ScaledPerlinNoise2D greenNoise = new ScaledPerlinNoise2D(random.nextLong(), width, height, 0.2f);
        ScaledPerlinNoise2D blueNoise = new ScaledPerlinNoise2D(random.nextLong(), width, height, 0.2f);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                ColorCell cell = new ColorCell();
                cell.getColor().setRed(perlinToColor(redNoise.perlin(i, j)));
                cell.getColor().setGreen(perlinToColor(greenNoise.perlin(i, j)));
                cell.getColor().setBlue(perlinToColor(blueNoise.perlin(i, j)));
                layer.setCell(i, j, cell);
            }
        }
        tiledMap.getLayers().add(layer);
        return tiledMap;
    }

    private static int perlinToColor(float x){
        return (int) (MathUtils.clamp(0.5f + x, 0, 1) * (PointColor.COLORS_NUMBER - 1));
    }
}
