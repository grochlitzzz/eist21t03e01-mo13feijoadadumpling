package code;

import code.model.Spaceship;

/**
 * This class defines the player. Each player has its own car.
 */
public class Player {

	private Spaceship spaceship;

	public Player(Spaceship spaceship) {
		this.spaceship = spaceship;
	}

	public void setSpaceship(Spaceship spaceship) {
		this.spaceship = spaceship;
	}

	public Spaceship getSpaceship() {
		return this.spaceship;
	}

	public void setup(Dimension2D gameBoardSize) {
		spaceship.setPosition(gameBoardSize, spaceship.getPosition());
	}
}
