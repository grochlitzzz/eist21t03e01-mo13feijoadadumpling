package code.model;

import code.Dimension2D;
import code.Point2D;
import code.controller.shot.Shot;

import java.util.concurrent.ThreadLocalRandom;

public class EnemySpaceship extends Spaceship {

    private static final String ENEMY = "EnemySpaceship.png";
    private final double speed = 0.1;

    public EnemySpaceship(Dimension2D gameBoardSize, Point2D position) {
        super(gameBoardSize, position);
        setLives(1);
        setIconLocation(ENEMY);
    }

    public void move(Dimension2D gameBoardSize) {
        double maxY = gameBoardSize.getHeight() - 100;
        double newY = this.getPosition().getY() + speed;
        if (newY > maxY) {
            this.setSpeed(0);
            newY = maxY;
        }
        this.setPosition(gameBoardSize, new Point2D(this.getPosition().getX(), newY));
    }

    @Override
    public Shot shoot(int direction) {
        int shot = ThreadLocalRandom.current().nextInt(0, 150);
        return shot == 99? new Shot(direction, this.getPosition()): null;
    }
}
