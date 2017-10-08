package ru.daemon.colorization.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import ru.daemon.colorization.game.actors.ActorTextureFactory;
import ru.daemon.colorization.game.actors.CellActor;
import ru.daemon.colorization.game.actors.FollowingViewport;
import ru.daemon.colorization.game.actors.listeners.MoveCellActorByKeyboard;
import ru.daemon.colorization.game.actors.listeners.ZoomViewportByMouseWheel;
import ru.daemon.colorization.game.map.MapGenerator;

public class GameScreen extends ScreenAdapter {
    private final TiledMap map = MapGenerator.generateTiledMap(16, 16);
    private final OrthogonalTiledMapRenderer mapRenderer = new OrthogonalTiledMapRenderer(map);
    private final OrthographicCamera camera;
    private final BoundedViewport viewport;
    private final Stage worldStage;
    private final CellActor player;
    private final float terrainHeight;
    private final float terrainWidth;

    public GameScreen() {
        int zoom = 300;
        TiledMapTileLayer terrain = (TiledMapTileLayer) map.getLayers().get(MapGenerator.TERRAIN_LAYER);
        terrainHeight = terrain.getHeight() * terrain.getTileHeight();
        terrainWidth = terrain.getWidth() * terrain.getTileWidth();
        camera = new OrthographicCamera();
        viewport = new BoundedViewport(zoom, terrainHeight, terrainWidth, -10, camera);
        worldStage = new Stage(viewport);

        player = new CellActor(terrain, ActorTextureFactory.createPlayerTexture());
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

        mapRenderer.setView(camera);
        mapRenderer.render();
        worldStage.draw();
    }
}
