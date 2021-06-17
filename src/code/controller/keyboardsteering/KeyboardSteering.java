package code.controller.keyboardsteering;

import code.Dimension2D;
import code.controller.shot.Shot;
import code.model.PlayerSpaceship;
import code.model.Spaceship;
import code.view.GameView;
import javafx.event.EventHandler;
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
//		gameView.addEventHandler(KeyEvent.KEY_PRESSED, this::keyPressed);
		gameView.setOnKeyPressed(new EventHandler<KeyEvent>() {
			
            @Override
            public void handle(KeyEvent event) {
            	if (event.getCode().getCode() == 68 || event.getCode().getCode() == 39)
        			moveRight();
            	else if (event.getCode().getCode() == 65 || event.getCode().getCode() == 37)
        			moveLeft();
            	else if(event.getCode().getCode() == 32)
            		spaceBar();
			}
		});
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

//	//Shoot when space bar is pressed
//	public void keyTyped(KeyEvent e) {
//		KeyCode key = e.getCode();
//		if (key == KeyCode.SPACE)
//			spaceBar();
//	}


	//Right and Left movement with Arrow Keys + A/D-Keys
//	public void keyPressed(KeyEvent e) {
//		KeyCode key = e.getCode();
//		if (key == KeyCode.D || key == KeyCode.KP_RIGHT)
//			System.out.println("pressed D");
//			moveRight();
//		if (key == KeyCode.A || key == KeyCode.KP_LEFT)
//			moveLeft();
//		if (key == KeyCode.SPACE)
//			spaceBar();
//	}

	//Nothing happens when a key is released
	public void keyReleased(KeyEvent e) {
	}
}
