package model.spaceship;

import controller.shot.Shot;

public abstract class Spaceship {

	protected static final int DEFAULT_SPACESHIP_WIDTH = 50; // YET TO DEFINE
	protected static final int DEFAULT_SPACESHIP_HEIGHT = 25; // YET TO DEFINE

	private int minSpeed;
	private int maxSpeed;
	private int speed;
	private boolean crunched;

	private Point2D position;

	private String iconLocation;
	private Dimension2D size = new Dimension2D(DEFAULT_SPACESHIP_WIDTH, DEFAULT_SPACESHIP_HEIGHT);

	protected Spaceship(Dimension2D gameBoardSize, Point2D position) {
		setPosition(gameBoardSize, position);
	}

	protected void setPosition(Dimension2D gameBoardSize, Point2D position) {
		this.position = position;
	}
	
	public void shoot() {
		Shot shot = new Shot();
	}
	
	public abstract void move();
	

}
