package code.controller.audio;


public interface AudioPlayerInterface {
	
	void playBackgroundMusic();

	void stopBackgroundMusic();

	void playHitSound();
	
	void playShotSound();

	boolean isPlayingBackgroundMusic();

}
