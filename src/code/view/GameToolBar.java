package code.view;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.util.Optional;

public class GameToolBar extends ToolBar {
	private final Button start;
	private final Button stop;
	private final Button info;
	private final Button casual;
	private final Button fury;

	public GameToolBar() {
		this.start = new Button("Start");
		this.stop = new Button("Stop");
		this.info = new Button("Info");
		this.casual = new Button("Casual");
		this.fury = new Button("Fury");
		updateToolBarStatus(false);
		getItems().addAll(this.start, new Separator(), this.stop,
				new Separator(), this.info, new Separator(), this.casual, this.fury);
	}

	public void initializeActions(GameView gameView) {
		this.start.setOnAction(event -> gameView.startGame());

		this.stop.setOnAction(event -> {
			// stop the game while the alert is shown
			gameView.stopGame();

			Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to stop the game?", ButtonType.YES,
					ButtonType.NO);
			alert.setTitle("Stop Game Confirmation");
			alert.setHeaderText("");

			Optional<ButtonType> result = alert.showAndWait();
			// reference equality check is OK here because the result will return the same
			// instance of the ButtonType
			if (result.isPresent() && result.get() == ButtonType.YES) {
				// reset the game board to prepare the new game
				gameView.setup();
			} else {
				// continue running
				gameView.startGame();
			}
		});

		this.info.setOnAction(event -> {
			Alert alert = new Alert(AlertType.INFORMATION,
					"This describes how to play\n" +
							"Have Fun!",
					ButtonType.OK);
			alert.setHeaderText("How to Play");
			alert.showAndWait();
		});

		this.casual.setOnAction(event -> {
			gameView.getGameController().setFury(false);
			gameView.getPolicy().configMove();
			updateModeStatus(false);
		});

		this.fury.setOnAction(event -> {
			gameView.getGameController().setFury(true);
			gameView.getPolicy().configMove();
			updateModeStatus(true);
		});

		start.setFocusTraversable(false);
		stop.setFocusTraversable(false);
		info.setFocusTraversable(false);
		casual.setFocusTraversable(false);
		fury.setFocusTraversable(false);
	}

	public void updateToolBarStatus(boolean running) {
		this.start.setDisable(running);
		this.stop.setDisable(!running);
	}

	public void updateModeStatus(boolean isFury) {
		this.casual.setDisable(!isFury);
		this.fury.setDisable(isFury);
	}



}
