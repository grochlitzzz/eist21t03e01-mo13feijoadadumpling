package code.controller.keyboardsteering;

import code.Dimension2D;
import code.controller.shot.Shot;
import code.model.PlayerSpaceship;
import code.model.Spaceship;
import code.view.GameView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;

public class KeyboardSteering {

	private final PlayerSpaceship playerSpaceship;
	private final Dimension2D gameBoardSize;

	public KeyboardSteering(GameView gameView, PlayerSpaceship spaceship) {
		this.playerSpaceship = spaceship;
		this.gameBoardSize = gameView.getGameController().getSize();
		gameView.addEventHandler(KeyEvent.KEY_PRESSED, this::keyPressed);
	}
	
	public void moveLeft() {
		playerSpaceship.move(gameBoardSize, PlayerSpaceship.LEFT);
	}
	
	public void moveRight() {
		playerSpaceship.move(gameBoardSize, PlayerSpaceship.RIGHT);
	}
	
	public void spaceBar() {
		playerSpaceship.shoot(Shot.UP);
	}

	//Shoot when space bar is pressed
	public void keyTyped(KeyEvent e) {
		KeyCode key = e.getCode();
		if (key.equals(KeyCode.SPACE))
			spaceBar();
	}

	//Right and Left movement with Arrow Keys + A/D-Keys
	public void keyPressed(KeyEvent e) {
		KeyCode key = e.getCode();
		if (key.equals(KeyCode.D) || key.equals(KeyCode.KP_RIGHT))
			moveRight();
		if (key.equals(KeyCode.A) || key.equals(KeyCode.KP_LEFT))
			moveLeft();
	}

	//Nothing happens when a key is released
	public void keyReleased(KeyEvent e) {
	}
}
