package code.controller.audio;

import java.net.URL;

import code.controller.audio.AudioPlayerInterface;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * Reference: EiST SoSe21 H02E03 Bumpers
 *
 * This class handles the background music played during the game using a JavaFX
 * {@link MediaPlayer}.
 */
public class AudioPlayer implements AudioPlayerInterface {

    private static final String BACKGROUND_MUSIC_FILE = "back.wav";
    private static final String SHOT_SOUND_FILE = "shot.wav";
    private static final String HIT_SOUND_FILE = "hit.wav";

    private static final double SHOT_SOUND_VOLUME = 0.5;
    private static final double HIT_SOUND_VOLUME = 0.5;

    private final MediaPlayer musicPlayer;
    private final AudioClip shotPlayer;
    private final AudioClip hitPlayer;


    /**
     * Constructs a new AudioPlayer by directly loading the background music and
     * crash sound files into a new MediaPlayer / AudioClip.
     */
    public AudioPlayer() {
        this.musicPlayer = new MediaPlayer(loadAudioFile(BACKGROUND_MUSIC_FILE));
        this.shotPlayer = new AudioClip(convertNameToUrl(SHOT_SOUND_FILE));
        this.hitPlayer = new AudioClip(convertNameToUrl(HIT_SOUND_FILE));
    }

    @Override
    public void playBackgroundMusic() {
        if (isPlayingBackgroundMusic()) {
            return;
        }
        // Loop for the main music sound:
        this.musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        this.musicPlayer.play();
    }

    @Override
    public void stopBackgroundMusic() {
        if (isPlayingBackgroundMusic()) {
            this.musicPlayer.stop();
        }
    }

    @Override
    public void playHitSound() {
        hitPlayer.play(HIT_SOUND_VOLUME);
    }

    @Override
    public boolean isPlayingBackgroundMusic() {
        return MediaPlayer.Status.PLAYING.equals(this.musicPlayer.getStatus());
    }

    @Override
    public void playShotSound() {
        shotPlayer.play(SHOT_SOUND_VOLUME);
    }

    private Media loadAudioFile(String fileName) {
        return new Media(convertNameToUrl(fileName));
    }

    private String convertNameToUrl(String fileName) {
        URL musicSourceUrl = getClass().getClassLoader().getResource(fileName);
        if (musicSourceUrl == null) {
            throw new IllegalArgumentException(
                    "Please ensure that your resources folder contains the appropriate files for this exercise.");
        }
        return musicSourceUrl.toExternalForm();
    }

    public static String getBackgroundMusicFile() {
        return BACKGROUND_MUSIC_FILE;
    }

    public static String getCrashSoundFile() {
        return SHOT_SOUND_FILE;
    }
}
