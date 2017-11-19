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
import ru.daemon.colorization.game.actors.ActorTextureFactory;
import ru.daemon.colorization.game.actors.player.CollectColor;
import ru.daemon.colorization.game.actors.player.ColorActor;
import ru.daemon.colorization.game.actors.player.MoveCellActorByKeyboard;
import ru.daemon.colorization.game.actors.player.MoveCellActorByRandom;
import ru.daemon.colorization.game.logic.ColorHolder;
import ru.daemon.colorization.game.logic.PointHolder;
import ru.daemon.colorization.game.map.MapGenerator;
import ru.daemon.colorization.game.turns.TurnManager;
import ru.daemon.colorization.game.viewport.BoundedViewport;
import ru.daemon.colorization.game.viewport.FollowingViewportAction;
import ru.daemon.colorization.game.viewport.ZoomViewportByScroll;
import ru.daemon.colorization.menu.MainMenuScreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static ru.daemon.colorization.game.logic.ColorHolder.Component;

public class GameScreen extends ScreenAdapter {
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Stage worldStage;
    private final Stage uiStage;
    private BoundedViewport viewport;
    private VisLabel turnLabel;
    private VisLabel redLabel;
    private VisLabel greenLabel;
    private VisLabel blueLabel;
    private ColorActor hero;
    private List<ColorActor> players = new ArrayList<>();
    private final TiledMapTileLayer terrain;
    private TurnManager turnManager;

    public GameScreen(final Game game, TiledMap map) {
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        terrain = (TiledMapTileLayer) mapRenderer.getMap().getLayers().get(MapGenerator.TERRAIN_LAYER);
        uiStage = initUI(game);
        worldStage = initWorld();
    }

    private Stage initWorld() {
        initViewport();
        Stage stage = new Stage(viewport);

        turnManager = new TurnManager();
        initPlayers();
        initGUI();

        stage.addAction(turnManager);
        players.forEach(stage::addActor);
        stage.addListener(new MoveCellActorByKeyboard(turnManager, hero));
        stage.addListener(new ZoomViewportByScroll(stage));
        return stage;
    }

    private void initPlayers() {
        List<Component> components = Arrays.asList(Component.values());
        Collections.shuffle(components);

        for (Component component : components) {
            int max = terrain.getWidth() * terrain.getHeight();
            ColorHolder ch = new ColorHolder(max + 1);
            ch.get(component).set(max);

            Function<PointHolder, Float> func = (point) -> 0.5f + (float) point.get() / max / 2;
            Color color = new Color(func.apply(ch.red), func.apply(ch.green), func.apply(ch.blue), 1);
            ColorActor player = new ColorActor(terrain, ActorTextureFactory.createPlayerTexture(color), ch);

            turnManager.addAfterTurnHandler(new CollectColor(player, component, -1));
            Arrays.stream(Component.values())
                    .filter(c -> c != component)
                    .forEach(c -> turnManager.addAfterTurnHandler(new CollectColor(player, c, 1)));

            players.add(player);
        }

        hero = players.get(0);
        hero.addAction(Actions.forever(new FollowingViewportAction(viewport)));

        players.get(1).setCellPosition(0, terrain.getHeight() - 1);
        turnManager.addBeforeTurnHandler(new MoveCellActorByRandom(turnManager, players.get(1)));
        players.get(2).setCellPosition(terrain.getWidth() - 1, 0);
        turnManager.addBeforeTurnHandler(new MoveCellActorByRandom(turnManager, players.get(2)));
    }

    private void initViewport() {
        float terrainHeight = terrain.getHeight() * terrain.getTileHeight();
        float terrainWidth = terrain.getWidth() * terrain.getTileWidth();
        float tileSize = (terrain.getTileHeight() + terrain.getTileWidth()) / 2;
        viewport = new BoundedViewport(5 * tileSize, terrainHeight, terrainWidth, -tileSize / 2);
    }

    private void initGUI() {
        ColorHolder color = hero.color();
        turnManager.addAfterTurnHandler(() -> redLabel.setText(Integer.toString(color.red.get())));
        turnManager.addAfterTurnHandler(() -> greenLabel.setText(Integer.toString(color.green.get())));
        turnManager.addAfterTurnHandler(() -> blueLabel.setText(Integer.toString(color.blue.get())));
        turnManager.addBeforeTurnHandler(() -> turnLabel.setText("Turn: " + turnManager.getTurnCount()));
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
