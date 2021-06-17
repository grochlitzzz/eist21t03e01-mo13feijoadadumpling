package code.model;

import code.Dimension2D;
import code.Point2D;

public class EnemySpaceship extends Spaceship {

    private static final String ENEMY = "enemy.gif";
    private final double speed = 0.5;

    public EnemySpaceship(Dimension2D gameBoardSize, Point2D position) {
        super(gameBoardSize, position);
        setLives(1);
        setIconLocation(ENEMY);
    }

    //TODO: automove and shot
    public void move(Dimension2D gameBoardSize) {
        double maxY = gameBoardSize.getHeight() - 100;
        double deltaY = this.speed;
        double newY = this.getPosition().getY() + deltaY;
        if (newY > maxY) {
            this.setSpeed(0);
            newY = maxY;
        }
        this.setPosition(gameBoardSize, new Point2D(this.getPosition().getX(), newY));
    }
}
