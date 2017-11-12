package ru.daemon.colorization.game.actors.player;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.daemon.colorization.game.actors.ActorTextureFactory;
import ru.daemon.colorization.game.actors.CellActor;
import ru.daemon.colorization.game.logic.ColorHolder;

public class ColorActor extends CellActor {
    private final ColorHolder colorHolder = new ColorHolder(Integer.MAX_VALUE);

    public ColorActor(TiledMapTileLayer tileLayer) {
        super(tileLayer, ActorTextureFactory.createPlayerTexture());
    }

    public ColorHolder getColorHolder() {
        return colorHolder;
    }
}
