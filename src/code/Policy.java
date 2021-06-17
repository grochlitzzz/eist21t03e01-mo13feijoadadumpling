package code;

import code.controller.gamecontroller.GameController;
import code.model.EnemySpaceship;
import code.model.FuryMove;
import code.model.LinearMove;
import code.model.Spaceship;

public class Policy {
    private GameController gameController;

    public Policy(GameController gameController) {
        this.gameController = gameController;
    }

    public void configMove() {
        if (gameController.isFury()) {
            for (Spaceship spaceship: gameController.getSpaceships()) {
                ((EnemySpaceship)spaceship).setMoveStrategy(new FuryMove());
            }
        } else {
            for (Spaceship spaceship: gameController.getSpaceships()) {
                ((EnemySpaceship)spaceship).setMoveStrategy(new LinearMove());
            }
        }
    }
}
