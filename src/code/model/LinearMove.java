package code.model;

import code.Dimension2D;
import code.Point2D;

/**
 * Simply slowly move downwards
 */

public class LinearMove implements MoveStrategy {
    @Override
    public void updatePosition(Dimension2D gameBoardSize, EnemySpaceship spaceship) {
        double maxY = gameBoardSize.getHeight() - 100;
        double newY = spaceship.getPosition().getY() + spaceship.getSpeed();
        if (newY > maxY) {
            newY = maxY;
        }
        spaceship.setPosition(gameBoardSize, new Point2D(spaceship.getPosition().getX(), newY));
    }
}
