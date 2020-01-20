package core;

import constant.Data;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * klasa odpoiwedzialna za odtwarzanie wszystkich dzwiekow w grze
 */
public class SoundPlayer extends Thread {

    private static boolean playCollectSoundFlag = false;
    private static boolean playWinGameSoundFlag = false;
    private static boolean playLoseLifeSoundFlag = false;
    private static boolean playLoseGameSoundFlag = false;
    private static boolean playMainMenuSoundFlag = false;
    private static boolean playMazeSoundtrackFlag = false;
    private static boolean playButtonSoundFlag = false;


    private static Media loseLifeSound  = new Media(new File(Data.LOSE_LIFE_SOUND_FILE).toURI().toString());
    private static Media winGameSound = new Media(new File(Data.WIN_SOUND_FILE).toURI().toString());
    private static Media collectSound = new Media(new File(Data.COLLECT_POINT_SOUND_FILE).toURI().toString());
    private static Media loseGameSound = new Media(new File(Data.LOSE_GAME_SOUND_FILE).toURI().toString());
    private static Media buttonSound = new Media(new File(Data.PRESS_BUTTON_SOUND_FILE).toURI().toString());

    private static MediaPlayer collectSoundPlayer = new MediaPlayer(collectSound);
    private static MediaPlayer loseLifeSoundPlayer = new MediaPlayer(loseLifeSound);
    private static MediaPlayer winGameSoundPlayer = new MediaPlayer(winGameSound);
    private static MediaPlayer loseGameSoundPlayer = new MediaPlayer(loseGameSound);
    private static MediaPlayer buttonSoundPlayer = new MediaPlayer(buttonSound);


    private static Media mainMenuSoundtrack = new Media(new File(Data.START_MENU_MUSIC).toURI().toString());
    private static MediaPlayer mainMenuSoundtrackPlayer = new MediaPlayer(mainMenuSoundtrack);


    private static Media mazeSoundtrack = new Media(new File(Data.GAME_MUSIC).toURI().toString());
    private static MediaPlayer mazeSoundtrackPlayer = new MediaPlayer(mazeSoundtrack);

    public SoundPlayer(){
        collectSoundPlayer.setVolume(0.3);
        loseLifeSoundPlayer.setVolume(0.8);
        loseGameSoundPlayer.setVolume(0.8);
        buttonSoundPlayer.setVolume(0.45);

        start();
    }

    public void playCollectSound(){
        playCollectSoundFlag=true;
    }
    public void playLoseLifeSound(){playLoseLifeSoundFlag=true;}
    public void playLoseGameSound(){playLoseGameSoundFlag = true;}
    public void playWinGameSound(){playWinGameSoundFlag=true;}
    public void playMazeSoundtrack(){playMazeSoundtrackFlag=true;}
    public void playMainMenuSoundtrack(){playMainMenuSoundFlag=true;}
    public void playButtonSound(){playButtonSoundFlag=true;}

    public  void run(){
        while(true) {
            try {
                Thread.sleep(10);
            }
            catch(Exception e){

            }
            if(playButtonSoundFlag){
                buttonSoundPlayer.stop();
                buttonSoundPlayer.play();
                playButtonSoundFlag=false;
            }
            if(playCollectSoundFlag) {
                collectSoundPlayer.stop();
                collectSoundPlayer.play();
                playCollectSoundFlag=false;
            }
            if(playWinGameSoundFlag){
                winGameSoundPlayer.stop();
                winGameSoundPlayer.play();
                playWinGameSoundFlag=false;
            }
            if(playLoseLifeSoundFlag){
                loseLifeSoundPlayer.stop();
                loseLifeSoundPlayer.play();
                playLoseLifeSoundFlag=false;
            }
            if(playLoseGameSoundFlag){
                loseGameSoundPlayer.stop();
                loseGameSoundPlayer.play();
                playLoseGameSoundFlag=false;
            }
            if(playMazeSoundtrackFlag){

                    mainMenuSoundtrackPlayer.stop();
                    mazeSoundtrackPlayer.play();
                    playMazeSoundtrackFlag = false;
            }
            if(playMainMenuSoundFlag){
                    mazeSoundtrackPlayer.stop();
                    mainMenuSoundtrackPlayer.play();
                    playMainMenuSoundFlag = false;

            }
        }
    }
}
