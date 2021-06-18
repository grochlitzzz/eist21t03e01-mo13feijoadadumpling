package code.model;

import code.Dimension2D;

public interface MoveStrategy {
    void updatePosition(Dimension2D gameBoardSize, EnemySpaceship spaceship);
}
