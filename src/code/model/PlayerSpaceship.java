package code.model;

import code.Dimension2D;
import code.Point2D;

public class PlayerSpaceship extends Spaceship {

    public PlayerSpaceship(Dimension2D gameBoardSize, Point2D position) {
        super(gameBoardSize, position);
        setLives(3);
    }

    public static void main(String []args) {
        PlayerSpaceship playerSpaceship = new PlayerSpaceship(new Dimension2D(10, 10), new Point2D(0, 0));
        System.out.println(playerSpaceship.getLives());
    }
}
