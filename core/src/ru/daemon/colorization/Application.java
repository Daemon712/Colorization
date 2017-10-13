package ru.daemon.colorization;

import com.badlogic.gdx.Game;
import com.kotcrab.vis.ui.VisUI;
import ru.daemon.colorization.menu.MainMenuScreen;

public class Application extends Game {
	@Override
	public void create () {
		VisUI.load(VisUI.SkinScale.X2);
		setScreen(new MainMenuScreen(this));
	}
}
