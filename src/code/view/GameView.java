package code.view;

import code.Dimension2D;
import code.Point2D;
import code.controller.gamecontroller.GameController;
import code.controller.keyboardsteering.KeyboardSteering;
import code.controller.audio.AudioPlayer;
import code.controller.shot.Shot;
import code.model.Spaceship;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class implements the user interface for steering the player car. The
 * user interface is implemented as a Thread that is started by clicking the
 * start button on the tool bar and stops by the stop button.
 */
public class GameView extends Canvas {

	private static final Color BACKGROUND_COLOR = Color.WHITE;

//	private static final String BACKGROUND_IMAGE = "background.png";

	/**
	 * The update period of the game in ms, this gives us 25 fps.
	 */
	private static final int UPDATE_PERIOD = 1000 / 25;
	private static final int DEFAULT_WIDTH = 500;
	private static final int DEFAULT_HEIGHT = 300;
	private static final Dimension2D DEFAULT_SIZE = new Dimension2D(DEFAULT_WIDTH, DEFAULT_HEIGHT);

	public static Dimension2D getPreferredSize() {
		return DEFAULT_SIZE;
	}

	/**
	 * Timer responsible for updating the game every frame that runs in a separate
	 * thread.
	 */
	private Timer gameTimer;

	private GameController gameController;

	private final GameToolBar gameToolBar;

	private KeyboardSteering keyboardSteering;

	private HashMap<String, Image> imageCache;

	public GameView(GameToolBar gameToolBar) {
		this.gameToolBar = gameToolBar;
		setup();
	}

	public GameController getGameController() {
		return gameController;
	}

	public KeyboardSteering getKeyboardSteering() {
		return keyboardSteering;
	}

	/**
	 * Removes all existing cars from the game board and re-adds them. Player car is
	 * reset to default starting position. Renders graphics.
	 */
	public void setup() {
		setupGameBoard();
		setupImageCache();
		this.gameToolBar.updateToolBarStatus(false);
		paint();
	}

	private void setupGameBoard() {
		Dimension2D size = getPreferredSize();
		this.gameController = new GameController(size);
		this.gameController.setAudioPlayer(new AudioPlayer());
		widthProperty().set(size.getWidth());
		heightProperty().set(size.getHeight());
		this.keyboardSteering = new KeyboardSteering(this, this.gameController.getPlayerCar());
	}

	private void setupImageCache() {
		this.imageCache = new HashMap<>();
		this.imageCache.put(BACKGROUND_IMAGE, getImage(BACKGROUND_IMAGE));
		for (Car car : this.gameController.getCars()) {
			String imageLocation = car.getIconLocation();
			this.imageCache.computeIfAbsent(imageLocation, this::getImage);
		}
		String playerImageLocation = this.gameController.getPlayerCar().getIconLocation();
		this.imageCache.put(playerImageLocation, getImage(playerImageLocation));
	}

	public void updateImageCache() {
		String playerImageLocation = this.gameController.getPlayerCar().getIconLocation();
		this.imageCache.put(playerImageLocation, getImage(playerImageLocation));
	}

	/**
	 * Sets the car's image.
	 *
	 * @param carImageFilePath an image file path that needs to be available in the
	 *                         resources folder of the project
	 */
	private Image getImage(String carImageFilePath) {
		URL carImageUrl = getClass().getClassLoader().getResource(carImageFilePath);
		if (carImageUrl == null) {
			throw new IllegalArgumentException(
					"Please ensure that your resources folder contains the appropriate files for this exercise.");
		}
		return new Image(carImageUrl.toExternalForm());
	}

	/**
	 * Starts the GameBoardUI Thread, if it wasn't running. Starts the game board,
	 * which causes the cars to change their positions (i.e. move). Renders graphics
	 * and updates tool bar status.
	 */
	public void startGame() {
		if (!this.gameController.isRunning()) {
			this.gameController.startGame();
			this.gameToolBar.updateToolBarStatus(true);
			startTimer();
			paint();
		}
	}

	private void startTimer() {
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				updateGame();
			}
		};
		if (this.gameTimer != null) {
			this.gameTimer.cancel();
		}
		this.gameTimer = new Timer();
		this.gameTimer.scheduleAtFixedRate(timerTask, UPDATE_PERIOD, UPDATE_PERIOD);
	}

	private void updateGame() {
		if (gameController.isRunning()) {
			// updates car positions and re-renders graphics
			this.gameController.update();
			updateImageCache();
			// when this.gameBoard.getOutcome() is OPEN, do nothing
			if (this.gameController.getGameOutcome() == GameOutcome.LOST) {
				showAsyncAlert("Oh.. you lost.");
				this.stopGame();
			} else if (this.gameController.getGameOutcome() == GameOutcome.WON) {
				showAsyncAlert("Congratulations! You won!!");
				this.stopGame();
			}
			paint();
		}
	}

	/**
	 * Stops the game board and set the tool bar to default values.
	 */
	public void stopGame() {
		if (this.gameController.isRunning()) {
			this.gameController.stopGame();
			this.gameToolBar.updateToolBarStatus(false);
			this.gameTimer.cancel();
		}
	}

	/**
	 * Render the graphics of the whole game by iterating through the cars of the
	 * game board at render each of them individually.
	 */
	private void paint() {
		getGraphicsContext2D().setFill(BACKGROUND_COLOR);
		getGraphicsContext2D().fillRect(0, 0, getWidth(), getHeight());
//		getGraphicsContext2D().drawImage(getImage(BACKGROUND_IMAGE), 0, 0, getWidth(), getHeight());

		for (Spaceship ship : this.gameController.getSpaceships()) {
			paintSpaceship(ship);
		}

		for (Shot shot: this.gameController.getShots()) {
			paintShot(shot);
		}
		// render player car
		paintSpaceship(this.gameController.getPlayerShip());
	}

	private void paintSpaceship(Spaceship spaceship) {
		Point2D carPosition = spaceship.getPosition();

		getGraphicsContext2D().drawImage(this.imageCache.get(spaceship.getIconLocation()), carPosition.getX(),
				carPosition.getY(), spaceship.getSize().getWidth(), spaceship.getSize().getHeight());
	}

	private void paintShot(Shot shot) {
		Point2D carPosition = shot.getPosition();

		getGraphicsContext2D().drawImage(this.imageCache.get(shot.getIconLocation()), carPosition.getX(),
				carPosition.getY(), shot.getSize().getWidth(), shot.getSize().getHeight());
	}
	/**
	 * Method used to display alerts in moveCars().
	 *
	 * @param message you want to display as a String
	 */
	private void showAsyncAlert(String message) {
		Platform.runLater(() -> {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(message);
			alert.showAndWait();
			this.setup();
		});
	}
}
