package ru.daemon.colorization.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import ru.daemon.colorization.game.actors.ActorTextureFactory;
import ru.daemon.colorization.game.actors.CellActor;
import ru.daemon.colorization.game.actors.MoveCellActorByKeyboard;
import ru.daemon.colorization.game.map.MapGenerator;
import ru.daemon.colorization.game.viewport.BoundedViewport;
import ru.daemon.colorization.game.viewport.FollowingViewportAction;
import ru.daemon.colorization.game.viewport.ZoomViewportByScroll;

public class GameScreen extends ScreenAdapter {
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final BoundedViewport viewport;
    private final Stage worldStage;

    public GameScreen() {
        TiledMap map = MapGenerator.generateTiledMap(16, 16);
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        TiledMapTileLayer terrain = (TiledMapTileLayer) mapRenderer.getMap().getLayers().get(MapGenerator.TERRAIN_LAYER);
        float terrainHeight = terrain.getHeight() * terrain.getTileHeight();
        float terrainWidth = terrain.getWidth() * terrain.getTileWidth();
        float tileSize = (terrain.getTileHeight() + terrain.getTileWidth()) / 2;
        viewport = new BoundedViewport(5 * tileSize, terrainHeight, terrainWidth, -tileSize / 2);

        worldStage = new Stage(viewport);

        CellActor player = new CellActor(terrain, ActorTextureFactory.createPlayerTexture());
        player.setCellX(0);
        player.setCellY(0);
        player.addAction(Actions.forever(new FollowingViewportAction(viewport)));

        worldStage.addActor(player);

        worldStage.addListener(new MoveCellActorByKeyboard(player));
        worldStage.addListener(new ZoomViewportByScroll(worldStage));

        Gdx.input.setInputProcessor(worldStage);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render(float delta) {
        worldStage.act(delta);

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.setView(viewport.getCamera());
        mapRenderer.render();
        worldStage.draw();
    }
}
