package hexagon.example;

import hexagon.core.ApplicationLauncher;
import hexagon.utils.Log;

public class ExampleGame extends ApplicationLauncher {
	
	@Override
	protected void onInit() {
		Log.info("Init!");
	}

	@Override
	protected void onClose() {
		Log.info("Close!");
	}

	public static void main(String[] args) {
		new ExampleGame().init("Example Game", 400, 225).run("/test_state.json");
	}
}
