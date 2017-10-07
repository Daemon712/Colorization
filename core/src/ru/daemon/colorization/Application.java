package ru.daemon.colorization;

import com.badlogic.gdx.Game;
import ru.daemon.colorization.game.GameScreen;

public class Application extends Game {
	@Override
	public void create () {
		setScreen(new GameScreen());
	}
}
