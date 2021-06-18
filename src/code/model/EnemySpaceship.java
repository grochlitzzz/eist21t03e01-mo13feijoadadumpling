package code.model;

import code.Dimension2D;
import code.Point2D;
import code.controller.shot.Shot;

import java.util.concurrent.ThreadLocalRandom;

public class EnemySpaceship extends Spaceship {

    private static final String ENEMY = "EnemySpaceship.png";
    private final double speed = 0.1;
    private MoveStrategy moveStrategy;
    private int direction;

    public EnemySpaceship(Dimension2D gameBoardSize, Point2D position) {
        super(gameBoardSize, position);
        setLives(1);
        setIconLocation(ENEMY);
        this.moveStrategy = new LinearMove();
        this.setDirection(ThreadLocalRandom.current().nextInt(0, 360));
    }

    public void move(Dimension2D gameBoardSize) {
        moveStrategy.updatePosition(gameBoardSize, this);
    }

    @Override
    public Shot shoot(int direction) {
        int shot = ThreadLocalRandom.current().nextInt(0, 150);
        if (shot == 1) {
            Point2D mid_point = new Point2D(this.getPosition().getX() + this.getSize().getWidth()/2 - 5, this.getPosition().getY() + 10);
            return new Shot(direction, mid_point);
        }
        return null;
    }

    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }

    public void setMoveStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
