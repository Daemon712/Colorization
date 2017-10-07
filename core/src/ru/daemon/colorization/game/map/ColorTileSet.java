package ru.daemon.colorization.game.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class ColorTileSet {
    public static final int TILE_SIZE = 32;
    private static TiledMapTileSet tileSet = getTileSet();

    public static TiledMapTileSet getTileSet() {
        if (tileSet != null) return tileSet;

        tileSet = new TiledMapTileSet();
        for (byte r = 0; r < PointColor.MAX; r++) {
            for (byte g = 0; g < PointColor.MAX; g++) {
                for (byte b = 0; b < PointColor.MAX; b++) {
                    PointColor color = new PointColor(r, g, b);
                    tileSet.putTile(color.getId(), generateTile(color.getTrueColor()));
                }
            }
        }
        return tileSet;
    }

    private static TiledMapTile generateTile(Color color) {
        Pixmap pix = new Pixmap(TILE_SIZE, TILE_SIZE, Pixmap.Format.RGBA8888);
        pix.setColor(color);
        pix.fill();
        TextureRegion textureRegion = new TextureRegion(new Texture(pix));
        return new StaticTiledMapTile(textureRegion);
    }
}
