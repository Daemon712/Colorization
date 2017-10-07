package ru.daemon.colorization.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FillViewport;
import ru.daemon.colorization.game.map.MapGenerator;

public class GameScreen extends ScreenAdapter {
    private final OrthogonalTiledMapRenderer mapRenderer = new OrthogonalTiledMapRenderer(MapGenerator.generateTiledMap(200, 200));
    private final OrthographicCamera camera = new OrthographicCamera();
    private final FillViewport viewport = new FillViewport(500, 500, camera);

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.rotate(5 * delta);
        camera.translate(delta * 10, delta * 10);
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();
    }
}
