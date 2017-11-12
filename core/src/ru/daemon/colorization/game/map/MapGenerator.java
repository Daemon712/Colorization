package ru.daemon.colorization.game.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import ru.daemon.colorization.game.logic.ColorHolder;

import java.util.Random;

public class MapGenerator {
    public static final String TERRAIN_LAYER = "Terrain";
    public static final float PERLIN_SCALE = 0.2f;

    public static TiledMap generateTiledMap(int width, int height, int colors) {
        TiledMap tiledMap = new TiledMap();
        ColorTileSet tileSet = new ColorTileSet(colors);
        tiledMap.getTileSets().addTileSet(tileSet);

        TiledMapTileLayer layer = new TiledMapTileLayer(width, height, ColorTileSet.TILE_SIZE, ColorTileSet.TILE_SIZE);
        layer.setName(TERRAIN_LAYER);
        Random random = new Random();
        ScaledPerlinNoise2D redNoise = new ScaledPerlinNoise2D(random.nextLong(), width, height, PERLIN_SCALE);
        ScaledPerlinNoise2D greenNoise = new ScaledPerlinNoise2D(random.nextLong(), width, height, PERLIN_SCALE);
        ScaledPerlinNoise2D blueNoise = new ScaledPerlinNoise2D(random.nextLong(), width, height, PERLIN_SCALE);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int red = normalize(redNoise.perlin(i, j), colors);
                int green = normalize(greenNoise.perlin(i, j), colors);
                int blue = normalize(blueNoise.perlin(i, j), colors);
                ColorHolder color = new ColorHolder(colors, red, green, blue);
                layer.setCell(i, j, new ColorCell(color, tileSet));
            }
        }
        tiledMap.getLayers().add(layer);
        return tiledMap;
    }

    private static int normalize(float x, int max){
        return Math.round(MathUtils.clamp(0.5f + x, 0, 1) * (max - 1));
    }
}
