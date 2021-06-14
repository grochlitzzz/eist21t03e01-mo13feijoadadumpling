package code.controller.gamecontroller;
import java.util.List;

import code.controller.audio.*;
import code.model.Spaceship;
import code.controller.keyboardsteering.*;
import code.controller.shot.*;
import code.view.GameView;


public class GameController {

	private AudioPlayer audioPlayer;
	
	private GameView gameView;
	
	private KeyboardSteering keyboardSteering;
	
	private List<Shot> shots;
	
	private List<Spaceship> spaceships;
	
	public void startGame() {}
	
	public void stopGame() {}
	
	public void playMusic() {}
	
	public void stopMusic() {}

	public void update() {
		moveEverything();
		deleteEverything();
	}

	public void moveEverything() {}

	public void deleteEverything() {}

}
