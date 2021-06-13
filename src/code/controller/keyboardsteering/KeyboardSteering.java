package code.controller.keyboardsteering;

import code.model.Spaceship;
import code.view.GameView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardSteering implements KeyListener {
	private GameView gameView;
	private Spaceship PlayerSpaceship;
	
	public void moveLeft() {

	}
	
	public void moveRight() {}
	
	public void spaceBar() {}

	//Shoot when space bar is pressed
	@Override
	public void keyTyped(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_SPACE)
			spaceBar();
	}

	//Right and Left movement with Arrow Keys + A/D-Keys
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)
			moveRight();
		if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)
			moveLeft();
	}

	//Nothing happens when a key is released
	@Override
	public void keyReleased(KeyEvent e) {
	}
}
