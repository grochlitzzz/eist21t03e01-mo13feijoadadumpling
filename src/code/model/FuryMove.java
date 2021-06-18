package code.model;

import code.Dimension2D;
import code.Point2D;

public class FuryMove implements MoveStrategy {

    // Reference: EiST L02E03 Bumpers
    protected static final int MAX_ANGLE = 360;
    protected static final int HALF_ANGLE = MAX_ANGLE / 2;

    @Override
    public void updatePosition(Dimension2D gameBoardSize, EnemySpaceship spaceship) {
        int direction = spaceship.getDirection();
        double maxX = gameBoardSize.getWidth();
        double maxY = gameBoardSize.getHeight();

        // calculate delta between old coordinates and new ones based on speed and
        double deltaX = 20 * spaceship.getSpeed() * Math.sin(Math.toRadians(direction));
        double deltaY = 20 * spaceship.getSpeed() * Math.cos(Math.toRadians(direction));
        double newX = spaceship.getPosition().getX() + deltaX;
        double newY = spaceship.getPosition().getY() + deltaY;

        // calculate position in case the boarder of the game board has been reached
        if (newX < 0) {
            newX = -newX;
            direction = MAX_ANGLE - direction;
        } else if (newX + spaceship.getSize().getWidth() > maxX) {
            newX = 2 * maxX - newX - 2 * spaceship.getSize().getWidth();
            direction = MAX_ANGLE - direction;
        }

        if (newY < 0) {
            newY = -newY;
            direction = HALF_ANGLE - direction;
            if (direction < 0) {
                direction = MAX_ANGLE + direction;
            }
        } else if (newY + spaceship.getSize().getHeight() > maxY) {
            newY = 2 * maxY - newY - 2 * spaceship.getSize().getHeight();
            direction = HALF_ANGLE - direction;
            if (direction < 0) {
                direction = MAX_ANGLE + direction;
            }
        }
        spaceship.setDirection(direction);
        spaceship.setPosition(gameBoardSize, new Point2D(newX, newY));
    }
}
