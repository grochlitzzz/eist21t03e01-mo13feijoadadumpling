package code.controller.keyboardsteering;

import code.Dimension2D;
import code.controller.gamecontroller.GameController;
import code.controller.shot.Shot;
import code.model.PlayerSpaceship;
import code.view.GameView;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyboardSteering {

	private final PlayerSpaceship playerSpaceship;
	private final Dimension2D gameBoardSize;
	private final GameController gameController;

	public KeyboardSteering(GameView gameView, PlayerSpaceship spaceship) {
		this.playerSpaceship = spaceship;
		this.gameController = gameView.getGameController();
		this.gameBoardSize = gameController.getSize();
		gameView.setFocusTraversable(true);
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
		gameController.getShots().add(playerSpaceship.shoot(Shot.UP));
	}

}
