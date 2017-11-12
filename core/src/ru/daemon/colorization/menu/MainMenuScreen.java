package ru.daemon.colorization.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.kotcrab.vis.ui.widget.VisImageTextButton;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.spinner.IntSpinnerModel;
import com.kotcrab.vis.ui.widget.spinner.Spinner;
import ru.daemon.colorization.game.GameScreen;
import ru.daemon.colorization.game.map.MapGenerator;

public class MainMenuScreen extends ScreenAdapter {
    private Stage stage;
    private final IntSpinnerModel worldWidthModel = new IntSpinnerModel(16, 8, 128, 4);
    private final IntSpinnerModel worldHeightModel = new IntSpinnerModel(16, 8, 128, 4);
    private final IntSpinnerModel colorStepsModel = new IntSpinnerModel(4, 2, 10, 1);

    public MainMenuScreen(final Game game) {
        stage = new Stage(new ExtendViewport(600, 600));
        VisTable table = new VisTable();
        table.setPosition(stage.getWidth()/2, stage.getHeight()/2);
        table.add(new VisLabel("Colorization")).pad(10);
        table.row();
        table.add(new Spinner("Color steps", colorStepsModel)).right().pad(5);
        table.row();
        table.add(new Spinner("World width", worldWidthModel)).right().pad(5);
        table.row();
        table.add(new Spinner("World height", worldHeightModel)).right().pad(5);
        table.row();
        VisImageTextButton playButton = new VisImageTextButton("Play", "default");
        table.add(playButton).pad(10);
        playButton.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                int worldWidth = worldWidthModel.getValue();
                int worldHeight = worldHeightModel.getValue();
                int colorSteps = colorStepsModel.getValue();
                TiledMap map = MapGenerator.generateTiledMap(worldWidth, worldHeight, colorSteps);
                game.setScreen(new GameScreen(game, map));
            }
        });
        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
