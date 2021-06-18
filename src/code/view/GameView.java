package code.view;

import code.Dimension2D;
import code.GameOutcome;
import code.Point2D;
import code.Policy;
import code.controller.gamecontroller.GameController;
import code.controller.keyboardsteering.KeyboardSteering;
import code.controller.audio.AudioPlayer;
import code.controller.shot.Shot;
import code.model.PlayerSpaceship;
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

public class GameView extends Canvas {

	private static final Color BACKGROUND_COLOR = Color.WHITE;

//	private static final String BACKGROUND_IMAGE = "background.png";

	private static final int UPDATE_PERIOD = 1000 / 25;
	private static final int DEFAULT_WIDTH = 500;
	private static final int DEFAULT_HEIGHT = 300;
	private static final Dimension2D DEFAULT_SIZE = new Dimension2D(DEFAULT_WIDTH, DEFAULT_HEIGHT);

	public static Dimension2D getPreferredSize() {
		return DEFAULT_SIZE;
	}

	private Timer gameTimer;

	private GameController gameController;

	private final GameToolBar gameToolBar;

	private KeyboardSteering keyboardSteering;

	private HashMap<String, Image> imageCache;

	private Policy policy;

	private boolean updated = false;

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

	public void setup() {
		setupGameBoard();
		setupImageCache();
		this.gameToolBar.updateToolBarStatus(false);
		this.gameToolBar.updateModeStatus(false);
		paint();
	}

	private void setupGameBoard() {
		Dimension2D size = getPreferredSize();
		this.gameController = new GameController(size);
		this.gameController.setAudioPlayer(new AudioPlayer());
		this.policy = new Policy(gameController);
		widthProperty().set(size.getWidth());
		heightProperty().set(size.getHeight());
		this.keyboardSteering = new KeyboardSteering(this, (PlayerSpaceship) this.gameController.getPlayerShip());
	}

	private void setupImageCache() {
		this.imageCache = new HashMap<>();
//		this.imageCache.put(BACKGROUND_IMAGE, getImage(BACKGROUND_IMAGE));
		for (Spaceship spaceship : this.gameController.getSpaceships()) {
			String imageLocation = spaceship.getIconLocation();
			this.imageCache.computeIfAbsent(imageLocation, this::getImage);
		}
		String playerImageLocation = this.gameController.getPlayerShip().getIconLocation();
		this.imageCache.put(playerImageLocation, getImage(playerImageLocation));
	}

	// update the Shot image after the game starts running
	public void updateImageCache() {
		if (!this.gameController.getShots().isEmpty()) {
			String imageLocation = gameController.getShots().get(0).getIconLocation();
			this.imageCache.put(imageLocation, getImage(imageLocation));
			updated = true;
		}
	}

	private Image getImage(String carImageFilePath) {
		URL carImageUrl = getClass().getClassLoader().getResource(carImageFilePath);
		if (carImageUrl == null) {
			throw new IllegalArgumentException(
					"Please ensure that your resources folder contains the appropriate files for this exercise.");
		}
		return new Image(carImageUrl.toExternalForm());
	}

	public void startGame() {
		if (!this.gameController.isRunning()) {
			this.gameController.startGame();
			this.gameToolBar.updateToolBarStatus(true);
			this.gameToolBar.updateModeStatus(false);
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
			this.gameController.update();
			if (!updated) {
				updateImageCache();
			}
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

	public void stopGame() {
		if (this.gameController.isRunning()) {
			this.gameController.stopGame();
			this.gameToolBar.updateToolBarStatus(false);
			this.gameTimer.cancel();
		}
	}

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

	private void showAsyncAlert(String message) {
		Platform.runLater(() -> {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(message);
			alert.showAndWait();
			this.setup();
		});
	}

	public Policy getPolicy() {
		return policy;
	}
}
