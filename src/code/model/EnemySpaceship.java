package code.model;

import code.Dimension2D;
import code.Point2D;

public class EnemySpaceship extends Spaceship {

    protected EnemySpaceship(Dimension2D gameBoardSize, Point2D position) {
        super(gameBoardSize, position);
        setLives(1);
    }

}
