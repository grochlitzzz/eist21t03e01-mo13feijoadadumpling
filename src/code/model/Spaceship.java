package code.model;

import code.Dimension2D;
import code.Point2D;
import code.controller.shot.Shot;

public abstract class Spaceship {

	private static final int DEFAULT_SPACESHIP_WIDTH = 25;
	private static final int DEFAULT_SPACESHIP_HEIGHT = 25;

	private double speed;
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
		Point2D mid_point = new Point2D(this.getPosition().getX() + this.getSize().getWidth()/2 - 5, this.getPosition().getY());
		return new Shot(direction, mid_point);
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

	public double getSpeed() {
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
