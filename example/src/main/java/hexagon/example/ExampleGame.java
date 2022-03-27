package hexagon.example;

import hexagon.core.ApplicationLauncher;

public class ExampleGame extends ApplicationLauncher {
	
	public static void main(String[] args) {
		new ExampleGame().init("Example Game", 400, 225).run("/test_state.json");
	}
}
