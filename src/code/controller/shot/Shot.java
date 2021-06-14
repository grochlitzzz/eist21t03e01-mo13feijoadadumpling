package code.controller.shot;

import code.Dimension2D;
import code.Point2D;
import code.model.Spaceship;

public class Shot {

	private static final int DEFAULT_SHOT_WIDTH = 50; // YET TO DEFINE
	private static final int DEFAULT_SHOT_HEIGHT = 25; // YET TO DEFINE

	public static final int UP = 1;
	public static final int DOWN = 0;

	private Spaceship spaceship; // DO WE NEED THIS?
	private String iconLocation;
	private Dimension2D size = new Dimension2D(DEFAULT_SHOT_WIDTH, DEFAULT_SHOT_HEIGHT);
	private Point2D position;
	private int speed = 10;
	private int direction;
	private boolean toBeRemoved = false;

	public Shot(int direction) {
		this.direction = direction;
	}

	public void setPosition(Dimension2D gameBoardSize, Point2D position) {
		this.position = position;
	}
	
	public boolean detectCollision(Spaceship spaceship) {
		Point2D p1 = this.getPosition();
		Dimension2D d1 = this.getSize();

		Point2D p2 = spaceship.getPosition();
		Dimension2D d2 = spaceship.getSize();

		boolean above = p1.getY() + d1.getHeight() < p2.getY();
		boolean below = p1.getY() > p2.getY() + d2.getHeight();
		boolean right = p1.getX() + d1.getWidth() < p2.getX();
		boolean left = p1.getX() > p2.getX() + d2.getWidth();

		return !above && !below && !right && !left;
	}

	public Dimension2D getSize() {
		return size;
	}

	public Point2D getPosition() {
		return position;
	}

	public void travel(Dimension2D gameBoardSize) {
		if (direction == UP) {
			speed = Math.abs(speed) * -1;
		} else if (direction == DOWN) {
			speed = Math.abs(speed);
		}
		double maxY = gameBoardSize.getHeight();
		// calculate delta between old coordinates and new ones based on speed and
		// direction
		double deltaY = this.speed;
		double newY = this.position.getY() + deltaY;

		// calculate position in case the boarder of the game board has been reached
		if (newY < 0 || newY + this.size.getHeight() > maxY) {
			toBeRemoved = true;
		}
		// set coordinates
		this.position = new Point2D(this.position.getX(), newY);
	}

	public String getIconLocation() {
		return this.iconLocation;
	}

	public void setIconLocation(String iconLocation) {
		if (iconLocation == null) {
			throw new NullPointerException("The image of a shot cannot be null.");
		}
		this.iconLocation = iconLocation;
	}

	public boolean toBeRemoved() {
		return toBeRemoved;
	}
}
