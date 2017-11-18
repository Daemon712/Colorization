package ru.daemon.colorization.game.actors.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.daemon.colorization.game.actors.CellActor;
import ru.daemon.colorization.game.logic.ColorHolder;

public class ColorActor extends CellActor {
    private final ColorHolder colorHolder;

    public ColorActor(TiledMapTileLayer tileLayer, Texture texture) {
        this(tileLayer, texture, new ColorHolder(Integer.MAX_VALUE));
    }

    public ColorActor(TiledMapTileLayer tileLayer, Texture texture, ColorHolder colorHolder) {
        super(tileLayer, texture);
        this.colorHolder = colorHolder;
    }

    public ColorHolder getColorHolder() {
        return colorHolder;
    }
}
