package code.controller.gamecontroller;
import java.util.ArrayList;
import java.util.List;

import code.Dimension2D;
import code.GameOutcome;
import code.Player;
import code.Point2D;
import code.controller.audio.*;
import code.controller.shot.Shot;
import code.model.EnemySpaceship;
import code.model.PlayerSpaceship;
import code.model.Spaceship;

public class GameController {

	private static final int NUMBER_OF_ENEMIES = 6;

	private final List<Spaceship> spaceships = new ArrayList<>();
	private final List<Shot> shots = new ArrayList<>();

	private final Player player;
	private AudioPlayerInterface audioPlayer;
	private final Dimension2D size;
	private boolean running;
	private GameOutcome gameOutcome = GameOutcome.OPEN;
	private boolean isFury = false;

	public GameController(Dimension2D size) {
		this.size = size;
		Spaceship playerSpaceship = new PlayerSpaceship(size,
				new Point2D(size.getWidth()/2 - 25, size.getHeight() - 25));
		this.player = new Player(playerSpaceship);
		this.player.setup(size);
		createEnemies();
	}

	private void createEnemies() {
		double X = size.getWidth()/2 - 150;
		double Y = 25.0;
		for (int i = 0; i < NUMBER_OF_ENEMIES; i++) {
			this.spaceships.add(new EnemySpaceship(this.size, new Point2D(X, Y)));
			X += 50.0;
		}
	}

	public Dimension2D getSize() {
		return size;
	}

	public boolean isRunning() {
		return this.running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public GameOutcome getGameOutcome() {
		return gameOutcome;
	}

	public List<Spaceship> getSpaceships() {
		return this.spaceships;
	}
	public List<Shot> getShots() {
		return this.shots;
	}

	public Spaceship getPlayerShip() {
		return this.player.getSpaceship();
	}

	public AudioPlayerInterface getAudioPlayer() {
		return this.audioPlayer;
	}

	public void setAudioPlayer(AudioPlayerInterface audioPlayer) {
		this.audioPlayer = audioPlayer;
	}

	public void update() {
		moveEverything();
		randomShoot();
	}

	public void startGame() {
		playMusic();
		this.running = true;
	}

	public void stopGame() {
		stopMusic();
		this.running = false;
	}

	public void playMusic() {
		this.audioPlayer.playBackgroundMusic();
	}

	public void stopMusic() {
		this.audioPlayer.stopBackgroundMusic();
	}

	public void moveEverything() {
		for (Spaceship spaceship : this.spaceships) {
			spaceship.move(size);
		}
		this.player.getSpaceship().move(size);
		List<Spaceship> shipToRemove = new ArrayList<>();

		for (Shot shot: shots) {
			shot.travel(getSize());
			if (shot.getDirection() == Shot.UP) {
				for (Spaceship spaceship : spaceships) {
					if (spaceship.isCrunched()) {
						continue;
					}
					if (shot.detectCollision(spaceship)) {
						System.out.println("Enemy shot down!");
						this.audioPlayer.playHitSound();
						spaceship.crunch();
						shipToRemove.add(spaceship);
						shot.remove();
						break;
					}
				}
			} else {
				if (shot.detectCollision(player.getSpaceship())) {
					player.getSpaceship().crunch();
				}
			}

			if (player.getSpaceship().isCrunched()) {
				gameOutcome = GameOutcome.LOST;
				break;
			}

			if (isWinner()) {
				gameOutcome = GameOutcome.WON;
				break;
			}
		}
		// remove crunched spaceships and shots
		spaceships.removeAll(shipToRemove);
		shots.removeIf(Shot::toBeRemoved);
	}

	public void randomShoot() {
		for (Spaceship spaceship: spaceships) {
			Shot shot = spaceship.shoot(Shot.DOWN);
			if (shot != null) {
				shots.add(shot);
			}
		}
	}

	private boolean isWinner() {
		for (Spaceship spaceship : spaceships) {
			if (!spaceship.isCrunched()) {
				return false;
			}
		}
		return true;
	}

	public boolean isFury() {
		return isFury;
	}

	public void setFury(boolean fury) {
		isFury = fury;
	}
}
