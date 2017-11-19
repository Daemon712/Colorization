package ru.daemon.colorization.game.map;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.daemon.colorization.game.logic.ColorHolder;
import ru.daemon.colorization.game.logic.Colorful;

public class ColorCell extends TiledMapTileLayer.Cell implements Colorful {
    private final ColorHolder colorHolder;
    private final ColorTileSet tileSet;

    public ColorCell(ColorHolder colorHolder, ColorTileSet tileSet) {
        this.colorHolder = colorHolder;
        this.tileSet = tileSet;
    }

    @Override
    public TiledMapTile getTile() {
        int r = colorHolder.red.get();
        int g = colorHolder.green.get();
        int b = colorHolder.blue.get();
        return tileSet.getTile(r, g, b);
    }

    @Override
    public ColorHolder color() {
        return colorHolder;
    }
}
