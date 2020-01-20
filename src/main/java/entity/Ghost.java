package entity;

import constant.Data;
import core.*;
import core.AI.AStarForwardPacmanGhostAI;
import core.AI.AStarToPacmanGhostAI;
import core.AI.GhostAI;
import entity.abstracts.MovingObject;


/**
 * klasa duszka ktora posiada atrybuty - szybkosc wzrostu predkosci Blinky'ego
 *                                      - rodzaj sztucznej inteligencji duszka
 *                                       - typ duszka
 *                                       - predkosc duszka
 */
public class Ghost extends MovingObject {
    private  static double blinkyVelocityModifier=0;

    public GhostAI ghostAI;
    private int type;
    private double velocityX=0;
    private double velocityY=0;


    static void incrementBlinkyVelocityModifier(){
        blinkyVelocityModifier+=Data.BLINKY_SPEED_MODIFIER;
    }
    public Ghost(int row, int column, int type)
    {
        super(row, column, type);
        this.positionX=(Data.BLOCK_SIZE_PX*column);
        this.positionY=(Data.BLOCK_SIZE_PX*row);

        Node startNode = new Node(row, column);
        if(type==Data.GHOST_ASTAR_TYPE) {
            ghostAI = new AStarToPacmanGhostAI(startNode);
        }
        else if(type == Data.GHOST_ASTAR_FORWARD_TYPE){
            ghostAI = new AStarForwardPacmanGhostAI(startNode);
        }
        this.type = type;

    }

    public void reposition(){
        if(this.type==Data.GHOST_ASTAR_TYPE){
            this.row=11;
            this.column=11;
        }
        else if(this.type==Data.GHOST_ASTAR_FORWARD_TYPE){
            this.row=11;
            this.column=12;
        }
        this.positionX=(Data.BLOCK_SIZE_PX*column);
        this.positionY=(Data.BLOCK_SIZE_PX*row);
    }

    public static void resetBlinkyVelocityModifier(){
        blinkyVelocityModifier = 0;
    }

    /**
     * metoda odpowiedzialna za poruszanie sie duszka
     */
    private void moveToTarget(){

        Node nextNode = ghostAI.getPathToTarget().peek();
        switch(type) {
            case Data.GHOST_ASTAR_TYPE:
            if ((nextNode.getColumn() > Board.getReddy().getColumn() && (nextNode.getRow() == Board.getReddy().getRow()))) {
                Board.moveReddyRight();
            } else if ((nextNode.getColumn() < Board.getReddy().getColumn() && (nextNode.getRow() == Board.getReddy().getRow()))) {
                Board.moveReddyLeft();
            } else if ((nextNode.getColumn() == Board.getReddy().getColumn() && (nextNode.getRow() < Board.getReddy().getRow()))) {
                Board.moveReddyUp();
            } else if ((nextNode.getColumn() == Board.getReddy().getColumn() && (nextNode.getRow() > Board.getReddy().getRow()))) {
                Board.moveReddyDown();
            } else {
                Board.stopReddy();
            }
            break;

            case Data.GHOST_ASTAR_FORWARD_TYPE:
                if(this.getRow()==ghostAI.getPathToTarget().peek().getRow() && this.getColumn()==ghostAI.getPathToTarget().peek().getColumn()){
                    nextNode = ghostAI.getPathToTarget().poll();
                }
                if ((nextNode.getColumn() > Board.getInky().getColumn() && (nextNode.getRow() == Board.getInky().getRow()))) {
                    Board.moveGreenyRight();
                } else if ((nextNode.getColumn() < Board.getInky().getColumn() && (nextNode.getRow() == Board.getInky().getRow()))) {
                    Board.moveGreenyLeft();
                } else if ((nextNode.getColumn() == Board.getInky().getColumn() && (nextNode.getRow() < Board.getInky().getRow()))) {
                    Board.moveGreenyUp();
                } else if ((nextNode.getColumn() == Board.getInky().getColumn() && (nextNode.getRow() > Board.getInky().getRow()))) {
                    Board.moveGreenyDown();
                } else {
                    Board.stopGreeny();
                }
                break;

        }
    }







    public void moveRight(){
        if(velocityY>0 && (Math.abs((positionY/(double)Data.BLOCK_SIZE_PX)-this.row))>0.1){
            return;
        }
        velocityY=0;
        if(this.type==Data.GHOST_ASTAR_TYPE) {
            velocityX = Data.GHOST_SPEED+ blinkyVelocityModifier;
        }
        else{
            velocityX = Data.GHOST_SPEED;
        }

    }
    public void moveLeft(){
        if(velocityY<0 && (Math.abs((positionY/(double)Data.BLOCK_SIZE_PX)-this.row))>0.1){
            return;
        }
        velocityY=0;
        if(this.type==Data.GHOST_ASTAR_TYPE) {
            velocityX = -Data.GHOST_SPEED - blinkyVelocityModifier;
        }
        else{
            velocityX = -Data.GHOST_SPEED;
        }

    }
    public void moveDown(){
        if(velocityX>0 && (Math.abs((positionX/(double)Data.BLOCK_SIZE_PX)-this.column))>0.1){
            return;
        }
        if(this.type==Data.GHOST_ASTAR_TYPE) {
            velocityY = Data.GHOST_SPEED+ blinkyVelocityModifier;
        }
        else{
            velocityY=Data.GHOST_SPEED;
        }
        velocityX=0;

    }
    public void moveUp(){
        if(velocityX<0 && (Math.abs((positionX/(double)Data.BLOCK_SIZE_PX)-this.column))>0.1){
            return;
        }
        if(this.type==Data.GHOST_ASTAR_TYPE) {
            velocityY = -Data.GHOST_SPEED-this.blinkyVelocityModifier;
        }
        else{
            velocityY=-Data.GHOST_SPEED;
        }
        velocityX=0;



    }
    public void stopMove(){
        velocityX=0;
        velocityY=0;
    }


    public void update(double time){

        try {
            moveToTarget();
        }
        catch(NullPointerException npe){
            if(this.type==Data.GHOST_ASTAR_FORWARD_TYPE){
                Board.stopGreeny();
            }
            else if(this.type ==Data.GHOST_ASTAR_TYPE){
                Board.stopReddy();
            }
        }




        positionX += velocityX * time;
        positionY += velocityY * time;



        if(velocityY < 0) {
            double posY = Math.ceil( positionY / (double) Data.BLOCK_SIZE_PX);
            this.row = (int) posY;
        }
        else if(velocityY > 0) {
            double posY = Math.floor(positionY / (double) Data.BLOCK_SIZE_PX);
            this.row = (int) posY;
        }

        if(velocityX < 0) {
            double posX = Math.ceil( positionX / (double) Data.BLOCK_SIZE_PX);
            this.column = (int) posX;
        }
        else if(velocityX > 0){
            double posX = Math.floor( positionX / (double) Data.BLOCK_SIZE_PX);
            this.column = (int) posX;
        }


        this.texture.setX(this.positionX);
        this.texture.setY(this.positionY);



    }






}
