package entity;

import constant.Data;
import core.Board;
import core.SoundPlayer;
import entity.abstracts.MovingObject;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.io.File;


/**
 * klasa odpowiedzialana za glowna kalse ktora steruje gracz
 * posiada atrybuty - flaga ruchu, w ktora strone porusza sie pacman
 *                  - flaga kolizji po wspolrzednej X
 *                  - flaga kolizij po wspolrzednej Y
 *                  - ostatni ruch po X
 *                  - ostatni ruch po Y
 *                  - liczba zyc
 *                  - predkosc
 */
public class Pacman extends MovingObject {

    private static String moveFlag="O";
    private static String currentlyIntersectedX = "O";
    private static String currentlyIntersectedY = "O";
    private static String lastMoveX = "O";
    private static String lastMoveY = "O";
    private static int pacmanLifes = 3;

    private static double velocityX=0;
    private static double velocityY=0;
    public Pacman(int row, int column) {
        super(row, column, Data.PACMAN_TYPE);
        this.positionX=3+ Data.BLOCK_SIZE_PX*column ;
        this.positionY=3+ Data.BLOCK_SIZE_PX*row;
        velocityX=0;
        velocityY=0;
    }


    /**
     * @param time
     * metoda aktualizujaca stan pacmana, pozycje, predkosc, tekstury itd.
     */
    public void update(double time){

        setVelocityByMoveFlag();
        resetCurrentlyIntersected();
        checkTransition();
        checkCollisionWithDoors();
        checkCollisionWithWall();
        checkCollisionWithScore();
        calculateLastMove();
        calculatePosition(time);
        calculateRow();
        calculateColumn();
        prepareTexture();
        calculateTexture();


    }

    /**
     * metoda sprawdzajaca czy pacman przechodzi przez teleport
     * jesli przechodzi odpowiednio go teleportuje
     */
    private void checkTransition(){
        if(this.positionX<0){
            this.positionX=Data.BLOCK_SIZE_PX*(Data.BOARD_ROW_SIZE-1);
        }
        if(this.positionX>Data.BLOCK_SIZE_PX*(Data.BOARD_ROW_SIZE-1)){
            this.positionX=0;
        }
    }

    /**
     * metoda ktora w zaleznosci od flagi MoveFlag ustawia predkosc pacmana
     */
    private void setVelocityByMoveFlag(){
        switch(moveFlag){
            case "R":
                velocityX = Data.PACMAN_SPEED;
                velocityY = 0;
                break;
            case "L":
                velocityX = -Data.PACMAN_SPEED;
                velocityY = 0;
                break;
            case "D":
                velocityY = Data.PACMAN_SPEED;
                velocityX = 0;
                break;
            case "U":
                velocityY = -Data.PACMAN_SPEED;
                velocityX = 0;
                break;
        }
    }
    public void reposition(){
        this.column=1;
        this.row=1;
        velocityX=0;
        velocityY=0;
        this.positionX=3+ Data.BLOCK_SIZE_PX*column ;
        this.positionY=3+ Data.BLOCK_SIZE_PX*row;
    }
    private void resetCurrentlyIntersected(){
        currentlyIntersectedX="O";
        currentlyIntersectedY="O";
    }

    /**
     * metoda ktora w zaleznosci od predkosci pacmana ustawia jego teksture
     */
    private void prepareTexture(){
        if(velocityX > 0){
            this.texture.setScaleX(1);
            this.texture.setRotate(0);
        }
        else if(velocityX < 0){

            this.texture.setScaleX(-1);
            this.texture.setRotate(0);
        }

        if(velocityY > 0){

            this.texture.setScaleX(1);
            this.texture.setRotate(90);
        }
        else if(velocityY < 0){

            this.texture.setScaleX(1);
            this.texture.setRotate(-90);
        }
    }
    private void checkCollisionWithDoors(){
        if(this.intersects(positionX, positionY+3, (double) Data.MOVING_OBJECT_SIZE_PX, (double)Data.MOVING_OBJECT_SIZE_PX, Board.getGhostDoor()) && velocityY > 0){
            velocityY=0;

            if (lastMoveX.equals("R") && !currentlyIntersectedX.equals("R")) {
                velocityX = Data.PACMAN_SPEED;
            } else if (lastMoveX.equals("L") && !currentlyIntersectedX.equals("L")) {
                velocityX = -Data.PACMAN_SPEED;
            }
        }
    }
    private void checkCollisionWithWallByY(){
        for(int k=0; k<Board.getWallList().size(); k++){
            if(this.intersects(positionX, positionY+3, (double) Data.MOVING_OBJECT_SIZE_PX, (double) Data.MOVING_OBJECT_SIZE_PX, Board.getWallList().get(k))){
                currentlyIntersectedY="D";
            }
            else if(this.intersects(positionX, positionY-3, (double) Data.MOVING_OBJECT_SIZE_PX, (double) Data.MOVING_OBJECT_SIZE_PX, Board.getWallList().get(k))){
                currentlyIntersectedY="U";
            }
        }
    }
    private void checkCollisionWithWallByX(){
        for(int k=0; k<Board.getWallList().size(); k++){
            if(this.intersects(positionX+3, positionY, (double) Data.MOVING_OBJECT_SIZE_PX, (double) Data.MOVING_OBJECT_SIZE_PX, Board.getWallList().get(k))){
                currentlyIntersectedX="R";
            }
            else if(this.intersects(positionX-3, positionY, (double) Data.MOVING_OBJECT_SIZE_PX, (double) Data.MOVING_OBJECT_SIZE_PX, Board.getWallList().get(k))){
                currentlyIntersectedX="L";
            }
        }
    }
    private void checkCollisionWithWallByRight(int wallIndex){
        if (this.intersects(positionX + 3, positionY+0, (double) Data.MOVING_OBJECT_SIZE_PX, (double) Data.MOVING_OBJECT_SIZE_PX, Board.getWallList().get(wallIndex)) && velocityX > 0 ) {
            velocityX = 0;

            currentlyIntersectedX="R";

            checkCollisionWithWallByY();

            if (lastMoveY.equals("D") && !currentlyIntersectedY.equals("D")) {
                velocityY = Data.PACMAN_SPEED;
            }
            else if (lastMoveY.equals("U") && !currentlyIntersectedY.equals("U")) {
                velocityY = -Data.PACMAN_SPEED;
            }


        }
    }
    private void checkCollisionWithWallByLeft(int wallIndex){
        if (this.intersects(positionX - 3, positionY, (double) Data.MOVING_OBJECT_SIZE_PX, (double) Data.MOVING_OBJECT_SIZE_PX, Board.getWallList().get(wallIndex)) && velocityX < 0)
        {
            velocityX = 0;

            checkCollisionWithWallByY();
            if (lastMoveY.equals("D") && !currentlyIntersectedY.equals("D")) {
                velocityY = Data.PACMAN_SPEED;
            }
            else if (lastMoveY.equals("U") && !currentlyIntersectedY.equals("U")) {
                velocityY = -Data.PACMAN_SPEED;
            }

        }
    }
    private void checkCollisionWithWallByDown(int wallIndex){
        if (this.intersects(positionX, positionY + 3, (double) Data.MOVING_OBJECT_SIZE_PX, (double) Data.MOVING_OBJECT_SIZE_PX, Board.getWallList().get(wallIndex)) && velocityY > 0 )
        {
            velocityY = 0;


            checkCollisionWithWallByX();
            if (lastMoveX.equals("R") && !currentlyIntersectedX.equals("R")) {
                velocityX = Data.PACMAN_SPEED;
            } else if (lastMoveX.equals("L") && !currentlyIntersectedX.equals("L")) {
                velocityX = -Data.PACMAN_SPEED;
            }

        }
    }
    private void checkCollisionWithWallByUp(int wallIndex){
        if (this.intersects(positionX, positionY - 3, (double) Data.MOVING_OBJECT_SIZE_PX, (double) Data.MOVING_OBJECT_SIZE_PX, Board.getWallList().get(wallIndex)) && velocityY < 0)
        {
            velocityY = 0;
            checkCollisionWithWallByY();
            if (lastMoveX.equals("R") && !currentlyIntersectedX.equals("R")) {
                velocityX = Data.PACMAN_SPEED;
            } else if (lastMoveX.equals("L") && !currentlyIntersectedX.equals("L")) {
                velocityX = -Data.PACMAN_SPEED;
            }
        }
    }
    private void checkCollisionWithScore(){
        for(int l = 0; l<Board.getPointList().size(); l++){
            if(this.intersects(Board.getPointList().get(l))){
                Board.removePointListAtI(l);
                Board.setPacmanPoints(Board.getPacmanPoints()+10);
                Ghost.incrementBlinkyVelocityModifier();
                Board.soundPlayer.playCollectSound();
            }
        }
    }

    private void checkCollisionWithWall(){
        for(int i=0; i < Board.getWallList().size();i++) {
            checkCollisionWithWallByRight(i);
            checkCollisionWithWallByLeft(i);
            checkCollisionWithWallByDown(i);
            checkCollisionWithWallByUp(i);
        }
    }

    private void calculateLastMove(){
        if(velocityX > 0){
            lastMoveX = "R";

        }
        else if(velocityX < 0){
            lastMoveX = "L";

        }

        if(velocityY > 0){
            lastMoveY = "D";

        }
        else if(velocityY < 0){
            lastMoveY = "U";

        }
    }
    private void calculateTexture(){
        this.texture.setX(this.positionX);
        this.texture.setY(this.positionY);
    }
    private void calculateColumn(){
        this.column = (int)positionX/Data.BLOCK_SIZE_PX;
    }
    private void calculateRow(){
        this.row = (int)positionY/Data.BLOCK_SIZE_PX;
    }
    private void calculatePosition(double time){
        positionX += velocityX * time;
        positionY += velocityY * time;
    }

    public int getPacmanLifes() {
        return pacmanLifes;
    }



    public void resetPacmanLifes(){
        pacmanLifes=3;
    }
    public void decrementPacmanLifes(){
        pacmanLifes--;
    }

    public  double getVelocityX() {
        return velocityX;
    }

    public  double getVelocityY() {
        return velocityY;
    }

    public void setMoveFlag(String flag){
        moveFlag=flag;
    }

}
