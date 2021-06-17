package code.model;

import code.Dimension2D;
import code.Point2D;

public class PlayerSpaceship extends Spaceship {

    public static final int RIGHT = 0;
    public static final int LEFT = 1;

    private static final String PLAYER  = "11.jpg";

    public PlayerSpaceship(Dimension2D gameBoardSize, Point2D position) {
        super(gameBoardSize, position);
        this.setSpeed(10);
        setLives(3);
        setIconLocation(PLAYER);
    }

    public void move(Dimension2D gameBoardSize, int direction) {
        double maxX = gameBoardSize.getWidth() - 25;
        double deltaX = direction == LEFT? -this.getSpeed(): this.getSpeed();
        double currentX = this.getPosition().getX();
        double newX = currentX + deltaX;

        // calculate position in case the boarder of the game board has been reached
        if (newX < 25 || newX > maxX) {
            newX = currentX;
        }
        // set coordinates
        this.setPosition(gameBoardSize, new Point2D(newX, this.getPosition().getY()));
    }
}
