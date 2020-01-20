package UI;

import core.Board;
import javafx.scene.layout.HBox;

/**
 * klasa odpowiedzialan za wyswietlanie punktow pacmana
 */
public class ScoreBoard {
    public static ScoreboardDigit[] scoreboard;
    public static HBox scoreBoardTable;





    public HBox getScoreBoardTable() {
        return scoreBoardTable;
    }


    public ScoreBoard(){
        scoreBoardTable = new HBox();
        scoreboard = new ScoreboardDigit[4];

        for(int i=0; i<4; i++){
            scoreboard[i] = new ScoreboardDigit(0);
            scoreBoardTable.getChildren().add(scoreboard[i].getTexture());
        }


    }

    public static void render(){
        int pacPoints = Board.getPacmanPoints();

        String stringPacPoints = String.valueOf(pacPoints);

        int stringSize = stringPacPoints.length();



        switch(stringSize){
            case 0:
                scoreboard[0] = new ScoreboardDigit(0);
                scoreboard[1] = new ScoreboardDigit(0);
                scoreboard[2] = new ScoreboardDigit(0);
                scoreboard[3] = new ScoreboardDigit(0);
                break;
            case 1:
                scoreboard[0] = new ScoreboardDigit(0);
                scoreboard[1] = new ScoreboardDigit(0);
                scoreboard[2] = new ScoreboardDigit(0);
                scoreboard[3] = new ScoreboardDigit(Character.getNumericValue(stringPacPoints.charAt(0)));
                break;
            case 2:
                scoreboard[0] = new ScoreboardDigit(0);
                scoreboard[1] = new ScoreboardDigit(0);
                scoreboard[2] = new ScoreboardDigit(Character.getNumericValue(stringPacPoints.charAt(0)));
                scoreboard[3] = new ScoreboardDigit(Character.getNumericValue(stringPacPoints.charAt(1)));
                break;
            case 3:
                scoreboard[0] = new ScoreboardDigit(0);
                scoreboard[1] = new ScoreboardDigit(Character.getNumericValue(stringPacPoints.charAt(0)));
                scoreboard[2] = new ScoreboardDigit(Character.getNumericValue(stringPacPoints.charAt(1)));
                scoreboard[3] = new ScoreboardDigit(Character.getNumericValue(stringPacPoints.charAt(2)));
                break;
            case 4:
                scoreboard[0] = new ScoreboardDigit(Character.getNumericValue(stringPacPoints.charAt(0)));
                scoreboard[1] = new ScoreboardDigit(Character.getNumericValue(stringPacPoints.charAt(1)));
                scoreboard[2] = new ScoreboardDigit(Character.getNumericValue(stringPacPoints.charAt(2)));
                scoreboard[3] = new ScoreboardDigit(Character.getNumericValue(stringPacPoints.charAt(3)));
                break;
        }

        scoreBoardTable.getChildren().clear();
        scoreBoardTable.getChildren().removeAll();

        for(int i=0; i<4; i++){
            scoreBoardTable.getChildren().add(scoreboard[i].getTexture());
        }
    }
}
