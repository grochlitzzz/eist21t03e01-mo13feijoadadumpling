package code.model;

import code.Dimension2D;
import code.Point2D;
import code.controller.shot.Shot;

public abstract class Spaceship {

	private static final int DEFAULT_SPACESHIP_WIDTH = 50; // YET TO DEFINE
	private static final int DEFAULT_SPACESHIP_HEIGHT = 25; // YET TO DEFINE

	private int minSpeed;
	private int maxSpeed;
	private int speed;
	private boolean crunched;
	private int lives;
	private boolean firstRow;
	private String iconLocation;
	private Point2D position;
	private Dimension2D size = new Dimension2D(DEFAULT_SPACESHIP_WIDTH, DEFAULT_SPACESHIP_HEIGHT);

	public Spaceship(Dimension2D gameBoardSize, Point2D position) {
		setPosition(gameBoardSize, position);
	}

	public void setPosition(Dimension2D gameBoardSize, Point2D position) {
		this.position = position;
	}
	
	public void shoot(int direction) {
		Shot shot = new Shot(direction);
	}
	
	public void move(Dimension2D gameBoardSize) {
		double maxY = gameBoardSize.getHeight();
		// calculate delta between old coordinates and new ones based on speed and
		// direction
		double deltaY = this.speed;
		double newY = this.position.getY() + deltaY;

		// calculate position in case the boarder of the game board has been reached
		if (newY < 0 || newY + this.size.getHeight() > maxY) {
			// delete shot
		}
		// set coordinates
		this.position = new Point2D(this.position.getX(), newY);
	}
	
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
