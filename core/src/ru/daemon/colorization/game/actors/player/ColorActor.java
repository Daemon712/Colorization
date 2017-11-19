package ru.daemon.colorization.game.actors.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.daemon.colorization.game.actors.CellActor;
import ru.daemon.colorization.game.logic.ColorHolder;
import ru.daemon.colorization.game.logic.Colorful;

public class ColorActor extends CellActor implements Colorful {
    private final ColorHolder colorHolder;

    public ColorActor(TiledMapTileLayer tileLayer, Texture texture, ColorHolder colorHolder) {
        super(tileLayer, texture);
        this.colorHolder = colorHolder;
    }

    @Override
    public ColorHolder color() {
        return colorHolder;
    }
}
