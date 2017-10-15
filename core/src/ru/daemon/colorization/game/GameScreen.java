package ru.daemon.colorization.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import ru.daemon.colorization.game.actors.player.CollectColor;
import ru.daemon.colorization.game.actors.player.MoveCellActorByKeyboard;
import ru.daemon.colorization.game.actors.player.Player;
import ru.daemon.colorization.game.map.MapGenerator;
import ru.daemon.colorization.game.turns.TurnManager;
import ru.daemon.colorization.game.viewport.BoundedViewport;
import ru.daemon.colorization.game.viewport.FollowingViewportAction;
import ru.daemon.colorization.game.viewport.ZoomViewportByScroll;
import ru.daemon.colorization.menu.MainMenuScreen;

public class GameScreen extends ScreenAdapter {
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Stage worldStage;
    private final Stage uiStage;
    private BoundedViewport viewport;
    private VisLabel turnLabel;
    private VisLabel redLabel;
    private VisLabel greenLabel;
    private VisLabel blueLabel;
    private Player player;
    private final TiledMapTileLayer terrain;

    public GameScreen(final Game game, TiledMap map) {
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        terrain = (TiledMapTileLayer) mapRenderer.getMap().getLayers().get(MapGenerator.TERRAIN_LAYER);
        uiStage = initUI(game);
        worldStage = initWorld();
    }

    private Stage initWorld() {
        viewport = initViewport();
        Stage stage = new Stage(viewport);

        player = new Player((TiledMapTileLayer) mapRenderer.getMap().getLayers().get(MapGenerator.TERRAIN_LAYER));
        player.setCellPosition(0, 0, 0.2f);
        player.addAction(Actions.forever(new FollowingViewportAction(viewport)));

        TurnManager turnManager = initTurnManager();

        stage.addAction(turnManager);
        stage.addActor(player);
        stage.addListener(new MoveCellActorByKeyboard(turnManager, player));
        stage.addListener(new ZoomViewportByScroll(stage));
        return stage;
    }

    private BoundedViewport initViewport() {
        float terrainHeight = terrain.getHeight() * terrain.getTileHeight();
        float terrainWidth = terrain.getWidth() * terrain.getTileWidth();
        float tileSize = (terrain.getTileHeight() + terrain.getTileWidth()) / 2;
        return new BoundedViewport(5 * tileSize, terrainHeight, terrainWidth, -tileSize / 2);
    }

    private TurnManager initTurnManager() {
        TurnManager turnManager = new TurnManager();
        turnManager.addAfterTurnHandler(new CollectColor(player, terrain));
        turnManager.addAfterTurnHandler(() -> redLabel.setText(Integer.toString(player.getRed())));
        turnManager.addAfterTurnHandler(() -> greenLabel.setText(Integer.toString(player.getGreen())));
        turnManager.addAfterTurnHandler(() -> blueLabel.setText(Integer.toString(player.getBlue())));
        turnManager.addBeforeTurnHandler(() -> turnLabel.setText("Turn: " + turnManager.getTurnCount()));
        return turnManager;
    }

    private Stage initUI(Game game) {
        Stage uiStage = new Stage(new ExtendViewport(600, 600));

        VisTable topTable = initTopTable();
        topTable.setWidth(uiStage.getWidth());
        topTable.top();
        topTable.setPosition(0, uiStage.getHeight());
        uiStage.addActor(topTable);

        VisTable bottomTable = initBottomTable();
        bottomTable.setWidth(uiStage.getWidth());
        bottomTable.bottom();
        uiStage.addActor(bottomTable);

        uiStage.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE){
                    game.setScreen(new MainMenuScreen(game));
                    return true;
                }
                return false;
            }
        });
        return uiStage;
    }

    private VisTable initBottomTable() {
        VisTable bottomTable = new VisTable();
        redLabel = new VisLabel("0");
        greenLabel = new VisLabel("0");
        blueLabel = new VisLabel("0");
        redLabel.setColor(Color.RED);
        greenLabel.setColor(Color.GREEN);
        blueLabel.setColor(Color.BLUE);
        bottomTable.add(redLabel).pad(10);
        bottomTable.add(greenLabel).pad(10);
        bottomTable.add(blueLabel).pad(10);
        return bottomTable;
    }

    private VisTable initTopTable() {
        VisTable topTable = new VisTable();
        turnLabel = new VisLabel("Turn: 0");
        topTable.add(turnLabel);
        return topTable;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputMultiplexer(uiStage, worldStage));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        uiStage.getViewport().update(width, height);
    }

    @Override
    public void render(float delta) {
        worldStage.act(delta);
        uiStage.act(delta);

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.setView(viewport.getCamera());
        mapRenderer.render();
        worldStage.draw();
        uiStage.draw();
    }
}
