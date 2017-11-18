package ru.daemon.colorization.game.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import static ru.daemon.colorization.game.map.ColorTileSet.TILE_SIZE;

public class ActorTextureFactory {
    public static Texture createPlayerTexture(Color color){
        Pixmap pix = new Pixmap(TILE_SIZE, TILE_SIZE, Pixmap.Format.RGBA8888);
        int offset = TILE_SIZE/6;
        int size = 2 * TILE_SIZE/3;
        pix.setColor(color);
        pix.fillRectangle(offset, offset, size, size);
        pix.setColor(Color.BLACK);
        pix.drawRectangle(offset, offset, size, size);
        return new Texture(pix);
    }
}
