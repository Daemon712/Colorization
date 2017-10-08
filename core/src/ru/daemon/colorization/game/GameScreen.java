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
import ru.daemon.colorization.game.viewport.FollowingViewport;
import ru.daemon.colorization.game.actors.MoveCellActorByKeyboard;
import ru.daemon.colorization.game.viewport.ZoomViewportByMouseWheel;
import ru.daemon.colorization.game.map.MapGenerator;
import ru.daemon.colorization.game.viewport.BoundedViewport;

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
        viewport = new BoundedViewport(300, terrainHeight, terrainWidth, -10);

        worldStage = new Stage(viewport);

        CellActor player = new CellActor(terrain, ActorTextureFactory.createPlayerTexture());
        player.setCellX(0);
        player.setCellY(0);
        player.addAction(Actions.forever(new FollowingViewport(viewport)));

        worldStage.addActor(player);

        worldStage.addListener(new MoveCellActorByKeyboard(player));
        worldStage.addListener(new ZoomViewportByMouseWheel(viewport));

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
