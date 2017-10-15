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

    public void setCellPosition(int cellX, int cellY) {
        setCellPosition(cellX, cellY, 0);
    }

    public void setCellPosition(int cellX, int cellY, float duration){
        if (!checkCellPosition(cellX, cellY))
            throw new IllegalArgumentException();

        this.cellX = cellX;
        this.cellY = cellY;

        float newX = this.cellX * tileLayer.getTileWidth();
        float newY = this.cellY * tileLayer.getTileHeight();

        if (duration <= 0){
            this.setPosition(newX, newY);
        } else {
            this.addAction(Actions.moveTo(newX, newY, duration));
        }
    }

    public boolean checkCellPosition(int cellX, int cellY){
        return cellY >= 0 && cellY < tileLayer.getHeight() &&
                cellX >= 0 && cellX < tileLayer.getWidth();
    }
}
