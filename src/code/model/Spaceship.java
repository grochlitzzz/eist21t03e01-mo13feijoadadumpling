package code.model;

import code.Dimension2D;
import code.Point2D;
import code.controller.shot.Shot;

public abstract class Spaceship {

	private static final int DEFAULT_SPACESHIP_WIDTH = 50; // YET TO DEFINE
	private static final int DEFAULT_SPACESHIP_HEIGHT = 25; // YET TO DEFINE

	private int speed;
	private boolean crunched;
	private int lives;
	private String iconLocation;
	private Point2D position;
	private Dimension2D size = new Dimension2D(DEFAULT_SPACESHIP_WIDTH, DEFAULT_SPACESHIP_HEIGHT);

	public Spaceship(Dimension2D gameBoardSize, Point2D position) {
		setPosition(gameBoardSize, position);
	}

	public void setPosition(Dimension2D gameBoardSize, Point2D position) {
		this.position = position;
	}
	
	public Shot shoot(int direction) {
		return new Shot(direction, position);
	}
	
	public void move(Dimension2D gameBoardSize) {}
	
	public void setLives(int lives) {
		this.lives = lives;
	}

	public int getLives() {
		return lives;
	}

	public Point2D getPosition() {
		return position;
	}

	public Dimension2D getSize() {
		return size;
	}

	public String getIconLocation() {
		return this.iconLocation;
	}

	public void setIconLocation(String iconLocation) {
		if (iconLocation == null) {
			throw new NullPointerException("The image of a spaceship cannot be null.");
		}
		this.iconLocation = iconLocation;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void crunch() {
		this.crunched = true;
		this.speed = 0;
	}

	public boolean isCrunched() {
		return this.crunched;
	}
}
