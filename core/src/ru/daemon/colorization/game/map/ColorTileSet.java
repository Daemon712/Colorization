package ru.daemon.colorization.game.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class ColorTileSet extends TiledMapTileSet {
    public static final int TILE_SIZE = 32;
    private final int colors;

    public ColorTileSet(int colors) {
        this.colors = colors;
        if (colors < 2 || colors > Byte.MAX_VALUE) {
            throw new IllegalArgumentException("Colors should be between 2 and " + Byte.MAX_VALUE);
        }
        for (byte r = 0; r < colors; r++) {
            for (byte g = 0; g < colors; g++) {
                for (byte b = 0; b < colors; b++) {
                    int id = generateId(r, g, b);
                    Color color = generateColor(r, g, b);
                    TiledMapTile tile = generateTile(color);
                    putTile(id, tile);
                }
            }
        }
    }

    public TiledMapTile getTile(int r, int g, int b) {
        int id = generateId(r, g, b);
        return getTile(id);
    }

    private Color generateColor(int r, int g, int b) {
        return new Color(toFloat(r), toFloat(g), toFloat(b), 1f);
    }

    private float toFloat(int color) {
        return 0.3f + 0.6f * color / (colors - 1);
    }

    private int generateId(int r, int g, int b) {
        return r << 2 * Byte.SIZE | g << Byte.SIZE | b;
    }

    private static TiledMapTile generateTile(Color color) {
        Pixmap pix = new Pixmap(TILE_SIZE, TILE_SIZE, Pixmap.Format.RGBA8888);
        pix.setColor(color);
        pix.fill();
        TextureRegion textureRegion = new TextureRegion(new Texture(pix));
        return new StaticTiledMapTile(textureRegion);
    }
}
