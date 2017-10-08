package ru.daemon.colorization.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class CellActor extends Image {
    private TiledMapTileLayer tileLayer;
    private int cellX;
    private int cellY;

    public CellActor(TiledMapTileLayer tileLayer, Texture texture) {
        super(texture);
        this.tileLayer = tileLayer;
    }

    public int getCellX() {
        return cellX;
    }

    public int getCellY() {
        return cellY;
    }

    public void setCellX(int cellX) {
        if (cellX < 0 || cellX >= tileLayer.getWidth())
            throw new IllegalArgumentException();

        this.cellX = cellX;
        updatePosition();
    }

    public void setCellY(int cellY) {
        if (cellY < 0 || cellY >= tileLayer.getHeight())
            throw new IllegalArgumentException();

        this.cellY = cellY;
        updatePosition();
    }

    public boolean moveUp() {
        if (cellY == tileLayer.getHeight() - 1)
            return false;

        this.cellY++;
        updatePosition();
        return true;
    }
    public boolean moveDown() {
        if (cellY == 0)
            return false;

        this.cellY--;
        updatePosition();
        return true;
    }
    public boolean moveLeft() {
        if (cellX == 0)
            return false;

        this.cellX--;
        updatePosition();
        return true;
    }
    public boolean moveRight() {
        if (cellX == tileLayer.getWidth() - 1)
            return false;

        this.cellX++;
        updatePosition();
        return true;
    }

    private void updatePosition(){
        float newX = this.cellX * tileLayer.getTileWidth();
        float newY = this.cellY * tileLayer.getTileHeight();
        this.addAction(Actions.moveTo(newX, newY, 0.2f));
    }
}
