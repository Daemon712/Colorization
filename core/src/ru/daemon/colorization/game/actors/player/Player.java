package ru.daemon.colorization.game.actors.player;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.daemon.colorization.game.actors.ActorTextureFactory;
import ru.daemon.colorization.game.actors.CellActor;

public class Player extends CellActor {
    private int red, green, blue;

    public Player(TiledMapTileLayer tileLayer) {
        super(tileLayer, ActorTextureFactory.createPlayerTexture());
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
}
