package ru.daemon.colorization.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import ru.daemon.colorization.game.actors.CellActor;
import ru.daemon.colorization.game.actors.ActorTextureFactory;
import ru.daemon.colorization.game.actors.listeners.MoveCellActorByKeyboard;
import ru.daemon.colorization.game.map.MapGenerator;

public class GameScreen extends ScreenAdapter {
    private final TiledMap map = MapGenerator.generateTiledMap(16, 16);
    private final TiledMapTileLayer terrain = (TiledMapTileLayer) map.getLayers().get(MapGenerator.TERRAIN_LAYER);
    private final float terrainHeight = terrain.getHeight() * terrain.getTileHeight();
    private final float terrainWidth = terrain.getWidth() * terrain.getTileWidth();
    private float zoom = 0.5f;

    private final OrthogonalTiledMapRenderer mapRenderer = new OrthogonalTiledMapRenderer(map);
    private final OrthographicCamera camera = new OrthographicCamera();
    private final ExtendViewport viewport = new ExtendViewport(zoom * terrainWidth, zoom * terrainHeight, camera);
    private final Stage worldStage = new Stage(viewport);

    private float bottomBound, topBound, leftBound, rightBound;

    private final CellActor player = new CellActor(terrain, ActorTextureFactory.createPlayerTexture());

    public GameScreen() {
        calculateBounds();

        player.setCellX(0);
        player.setCellY(0);
        worldStage.addActor(player);
        worldStage.addListener(new MoveCellActorByKeyboard(player));

        Gdx.input.setInputProcessor(worldStage);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        calculateBounds();
    }

    private void calculateBounds() {
        bottomBound = viewport.getWorldHeight() / 2;
        topBound = terrainHeight - bottomBound;
        leftBound = viewport.getWorldWidth() / 2;
        rightBound = terrainWidth - leftBound;
    }

    @Override
    public void render(float delta) {
        worldStage.act(delta);

        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.x = MathUtils.clamp(player.getX(), leftBound, rightBound);
        camera.position.y = MathUtils.clamp(player.getY(), bottomBound, topBound);
//        camera.zoom -= 0.01f * delta;
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();
        worldStage.draw();
    }
}
