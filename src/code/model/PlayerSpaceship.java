package code.model;

import code.Dimension2D;
import code.Point2D;

public class PlayerSpaceship extends Spaceship {

    public static final int RIGHT = 0;
    public static final int LEFT = 1;

    public PlayerSpaceship(Dimension2D gameBoardSize, Point2D position) {
        super(gameBoardSize, position);
        setLives(3);
    }

    public void move(Dimension2D gameBoardSize, int direction) {
        if (direction == LEFT) {
            setSpeed(Math.abs(getSpeed()) * -1);
        } else if (direction == RIGHT) {
            setSpeed(Math.abs(getSpeed()));
        }
        double maxX = gameBoardSize.getWidth();
        // calculate delta between old coordinates and new ones based on speed and
        // direction
        double deltaX = this.getSpeed();
        double newX = this.getPosition().getX() + deltaX;

        // calculate position in case the boarder of the game board has been reached
        if (newX < 0 || newX + this.getSize().getWidth() > newX) {
            // delete shot
        }
        // set coordinates
        this.setPosition(gameBoardSize, new Point2D(newX, this.getPosition().getX()));
    }
}
