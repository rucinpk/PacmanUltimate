package core;

import constant.Data;
import entity.*;
import entity.abstracts.StaticObject;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.LinkedList;


/**
 * klasa odpowiadajaca za plansze, labirynt naszej aplikacji
 */
public class Board {


    private static Pane root;
    private static Pacman pacman;

    private static LinkedList<Wall> wallList;
    private static LinkedList<Path> pathList;
    private static LinkedList<StaticObject> boardList;
    private static LinkedList<Point> pointList;

    private static GhostDoors ghostDoor;
    private static Ghost blinky;
    private static Ghost inky;


    private static  boolean paused = true;
    private static double startTime;

    private static int pacmanPoints;



    public static SoundPlayer soundPlayer;


    /**
     * konstruktuor ktory inicializuje korzen planszy, odtwarzac muzyki,
     * oraz inicializujacy pozostale obiekty ktore odpwoaidaja za stan palnszy
     */
    public Board(){

        soundPlayer = new SoundPlayer();
        root = new Pane();
        root.setFocusTraversable(true);
        setPaused(true);
        pacmanPoints=0;
        initBoard();
        initKeys();
        initMovingObjects();
        resetScoresList();
        pacman.resetPacmanLifes();

    }

    /**
     * metoda ktore resetuje stan planszy
     */
    public  void reset(){

        root.setFocusTraversable(true);
        pacman.resetPacmanLifes();
        resetStartTime();
        resetPacmanPoints();
        resetScoresList();
        repositionMovingObjects();
        setPaused(true);
        
    }

    /**
     * metoda ktora zamyka wati aby mozna bylo bezpiecznie zamknac aplikacje
     */
    public static void cleanUp(){
        soundPlayer.interrupt();
        blinky.ghostAI.interrupt();
        inky.ghostAI.interrupt();
    }

    /**
     * klasa resetujaca pozycje pacmana i duszkow na planszy
     */
    private static void repositionMovingObjects(){
        blinky.reposition();
        inky.reposition();
        pacman.reposition();
    }
    private double getCurrentTime(){
        return System.nanoTime()/Data.TIME_CONSTANT;
    }

    private void resetStartTime(){
        startTime = System.nanoTime()/Data.TIME_CONSTANT;
    }
    private  void resetPacmanPoints(){
        Board.pacmanPoints = 0;
    }


    private static  void resetPacPosition(){
        pacman = new Pacman(pathList.get(2).getRow(), pathList.get(2).getColumn());

    }
    public static void resetGhostsPosition(){
        blinky = new Ghost(11, 11, Data.GHOST_ASTAR_TYPE);
        inky = new Ghost(11, 12, Data.GHOST_ASTAR_FORWARD_TYPE);
    }

    /**
     * metoda inicializuajca obsluge klawiszy
     */
    private void initKeys(){
        root.setOnKeyPressed(event -> {

            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {

                pacman.setMoveFlag("R");
            }
            else if(event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A){

                pacman.setMoveFlag("L");
            }
            else if(event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S){

                pacman.setMoveFlag("D");
            }
            else if(event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W){

                pacman.setMoveFlag("U");
            }
        });
    }

    private boolean checkIfWallAtIJExists(int i, int j){
        for(int k=0; k<wallList.size(); k++){
            if(wallList.get(k).getColumn()==j && wallList.get(k).getRow()==i){
                return true;
            }
        }
        return false;
    }

    private void initMovingObjects(){
        pacman = new Pacman(pathList.get(2).getRow(), pathList.get(2).getColumn());
        blinky = new Ghost(11, 11, Data.GHOST_ASTAR_TYPE);
        inky = new Ghost(11, 12, Data.GHOST_ASTAR_FORWARD_TYPE);
    }
    private void resetScoresList(){
        pointList = new LinkedList<>();
        for(int i=0; i<pathList.size(); i++){
            if(!(pathList.get(i).getColumn()<14 && pathList.get(i).getColumn()>8 && pathList.get(i).getRow()>8 && pathList.get(i).getRow()<14) )
                if(pathList.get(i).getColumn()!=1 || pathList.get(i).getRow()!=1)
                    pointList.addLast(new Point(pathList.get(i).getRow(), pathList.get(i).getColumn()));

        }
    }

    /**
     * metoda inicializujaca sciezki, sciany oraz cala plansze
     */
    private void initBoard(){

        pathList = new LinkedList<>();
        wallList = new LinkedList<>();
        boardList = new LinkedList<>();


        for(int j=0; j<Data.BOARD_ROW_SIZE; j++){
            for(int i=0; i<Data.BOARD_COLUMN_SIZE; i++){
                if((i==0 || j ==0 || i==Data.BOARD_COLUMN_SIZE-1 || Data.BOARD_ROW_SIZE-1==j) && (i!=(Data.BOARD_ROW_SIZE/2)) && (i!=(Data.BOARD_ROW_SIZE/2)+1) ) {
                    wallList.addLast(new Wall(i,j));
                }
            }
        }

            wallList.addLast(new Wall(1, 11));

            for (int j = 2; j < 19; j++) {

                if (j == 2 || j == 3 || j == 18) {
                    wallList.addLast(new Wall(j, 2));
                    wallList.addLast(new Wall(j, 3));
                    wallList.addLast(new Wall(j, 4));

                    wallList.addLast(new Wall(j, 6));
                    wallList.addLast(new Wall(j, 7));
                    wallList.addLast(new Wall(j, 8));
                    wallList.addLast(new Wall(j, 9));

                    wallList.addLast(new Wall(j, 11));

                    wallList.addLast(new Wall(j, 13));
                    wallList.addLast(new Wall(j, 14));
                    wallList.addLast(new Wall(j, 15));
                    wallList.addLast(new Wall(j, 16));

                    wallList.addLast(new Wall(j, 18));
                    wallList.addLast(new Wall(j, 19));
                    wallList.addLast(new Wall(j, 20));
                }
            }

            wallList.addLast(new Wall(5, 2));
            wallList.addLast(new Wall(5, 3));
            wallList.addLast(new Wall(5, 4));

            wallList.addLast(new Wall(5, 6));

            wallList.addLast(new Wall(5, 8));
            wallList.addLast(new Wall(5, 9));
            wallList.addLast(new Wall(5, 10));
            wallList.addLast(new Wall(5, 11));
            wallList.addLast(new Wall(5, 12));
            wallList.addLast(new Wall(5, 13));
            wallList.addLast(new Wall(5, 14));

            wallList.addLast(new Wall(5, 16));

            wallList.addLast(new Wall(5, 18));
            wallList.addLast(new Wall(5, 19));
            wallList.addLast(new Wall(5, 20));


            wallList.addLast(new Wall(6, 6));
            wallList.addLast(new Wall(6, 11));
            wallList.addLast(new Wall(6, 16));

            for (int i = 7; i < 17; i++) {
                if (i == 11) continue;
                if (i == 12) continue;

                wallList.addLast(new Wall(i, 1));
                wallList.addLast(new Wall(i, 2));
                wallList.addLast(new Wall(i, 3));
                wallList.addLast(new Wall(i, 4));

                wallList.addLast(new Wall(i, 6));


                wallList.addLast(new Wall(i, 16));

                wallList.addLast(new Wall(i, 18));
                wallList.addLast(new Wall(i, 19));
                wallList.addLast(new Wall(i, 20));
                wallList.addLast(new Wall(i, 21));

            }

            wallList.addLast(new Wall(7, 11));

            wallList.addLast(new Wall(7, 7));
            wallList.addLast(new Wall(7, 8));
            wallList.addLast(new Wall(7, 9));

            wallList.addLast(new Wall(7, 15));
            wallList.addLast(new Wall(7, 14));
            wallList.addLast(new Wall(7, 13));




            wallList.addLast(new Wall(9, 8));
            wallList.addLast(new Wall(9, 9));
            wallList.addLast(new Wall(9, 10));

            wallList.addLast(new Wall(9, 12));
            wallList.addLast(new Wall(9, 13));
            wallList.addLast(new Wall(9, 14));


            wallList.addLast(new Wall(10, 8));
            wallList.addLast(new Wall(11, 8));
            wallList.addLast(new Wall(12, 8));
            wallList.addLast(new Wall(13, 8));

            wallList.addLast(new Wall(10, 14));
            wallList.addLast(new Wall(11, 14));
            wallList.addLast(new Wall(12, 14));
            wallList.addLast(new Wall(13, 14));

            wallList.addLast(new Wall(14, 8));
            wallList.addLast(new Wall(14, 9));
            wallList.addLast(new Wall(14, 10));
            wallList.addLast(new Wall(14, 11));
            wallList.addLast(new Wall(14, 12));
            wallList.addLast(new Wall(14, 13));
            wallList.addLast(new Wall(14, 14));

            wallList.addLast(new Wall(16, 8));
            wallList.addLast(new Wall(16, 9));
            wallList.addLast(new Wall(16, 10));
            wallList.addLast(new Wall(16, 11));
            wallList.addLast(new Wall(16, 12));
            wallList.addLast(new Wall(16, 13));
            wallList.addLast(new Wall(16, 14));

            wallList.addLast(new Wall(17, 11));


            wallList.addLast(new Wall(19, 4));
            wallList.addLast(new Wall(19, 18));

            wallList.addLast(new Wall(20, 1));
            wallList.addLast(new Wall(20, 2));

            wallList.addLast(new Wall(20, 4));

            wallList.addLast(new Wall(20, 6));
            wallList.addLast(new Wall(20, 7));

            wallList.addLast(new Wall(20, 9));
            wallList.addLast(new Wall(20, 10));
            wallList.addLast(new Wall(20, 11));
            wallList.addLast(new Wall(20, 12));
            wallList.addLast(new Wall(20, 13));

            wallList.addLast(new Wall(20, 15));
            wallList.addLast(new Wall(20, 16));

            wallList.addLast(new Wall(20, 18));

            wallList.addLast(new Wall(20, 20));
            wallList.addLast(new Wall(20, 21));

            wallList.addLast(new Wall(21, 6));
            wallList.addLast(new Wall(21, 7));

            wallList.addLast(new Wall(21, 11));

            wallList.addLast(new Wall(21, 16));
            wallList.addLast(new Wall(21, 15));

            for (int i = 2; i < 10; i++) {
                wallList.addLast(new Wall(22, i));
                wallList.addLast(new Wall(23, i));
            }

            for (int i = 13; i < 21; i++) {
                wallList.addLast(new Wall(22, i));
                wallList.addLast(new Wall(23, i));
            }


            wallList.addLast(new Wall(22, 11));
            wallList.addLast(new Wall(23, 11));



        for(int j=0; j<Data.BOARD_ROW_SIZE; j++){
            for(int i=0; i<Data.BOARD_COLUMN_SIZE; i++){
                if(!checkIfWallAtIJExists(i,j)){
                    pathList.addLast(new Path(i, j));
                }
            }
        }


        for(int i=0; i<wallList.size(); i++){
            boardList.addLast(wallList.get(i));
        }
        for(int i=0; i<pathList.size(); i++){
            boardList.addLast(pathList.get(i));
        }



        ghostDoor = new GhostDoors(9, 11);


    }


    public static int getTypeOfBoardAtIJ(int row, int col){

        for(int i=0; i<boardList.size(); i++){
            if(boardList.get(i).getColumn()==col && boardList.get(i).getRow() == row){
                return boardList.get(i).getStaticObjectType();
            }
        }
        return -1;
    }




    public static Pacman getPacman() {
        return pacman;
    }
    private void resetRoot(){
        root.getChildren().removeAll();
        root.getChildren().clear();
        root.setFocusTraversable(true);
    }

    /**
     * @param elapsedTime
     * metoda odswiezaajca pozycje obiektow na ekranie
     */
    public void renderBoard(double elapsedTime){
        double currentTime = getCurrentTime();



        if(pacman.intersects(blinky) || pacman.intersects(inky)){
            this.pacmanDies();
        }

        if(pointList.isEmpty()){
            this.reset();
            this.finishGame(true);
        }

        if(currentTime-startTime > 3){
            paused=false;
        }
        else{
            paused = true;
        }


        resetRoot();

        if(!paused) {
            pacman.update(elapsedTime);
            blinky.update(elapsedTime);
            inky.update(elapsedTime);
        }

        addBoardElementsToRoot();



    }

    private void addBoardElementsToRoot(){
        for(int i=0; i<pathList.size(); i++){
            root.getChildren().add(pathList.get(i).getTexture());
        }

        for(int i=0; i<wallList.size(); i++){
            root.getChildren().add(wallList.get(i).getTexture());
        }


        root.getChildren().add(ghostDoor.getTexture());

        for(int i=0; i<pointList.size(); i++){
            root.getChildren().add(pointList.get(i).getTexture());
        }
        root.getChildren().add(blinky.getTexture());
        root.getChildren().add(inky.getTexture());
        root.getChildren().add(pacman.getTexture());
    }

    private void finishGame(boolean isWin){

        if(isWin) {
            soundPlayer.playWinGameSound();
        }
        else{
            soundPlayer.playLoseGameSound();
        }
        reset();
    }

    private  void pacmanDies(){
        blinky.reposition();
        inky.reposition();


        pacman.decrementPacmanLifes();


        if(pacman.getPacmanLifes()>0) {
            soundPlayer.playLoseLifeSound();
        }

        Ghost.resetBlinkyVelocityModifier();

        if(pacman.getPacmanLifes()<1){
            finishGame(false);
        }

        resetPacPosition();

        resetStartTime();
        setPaused(true);


    }
    public static GhostDoors getGhostDoor(){
        return ghostDoor;
    }
    public static void stopReddy(){
        blinky.stopMove();
    }



    public static LinkedList<Wall> getWallList() {
        return wallList;
    }



    public static LinkedList<Point> getPointList() {
        return pointList;
    }



    public static Ghost getReddy() {
        return blinky;
    }


    public static Ghost getInky() {
        return inky;
    }

    public static void stopGreeny(){
        inky.stopMove();
    }

    public static void removePointListAtI(int index){
            pointList.remove(index);
    }
    public static void setPaused(boolean paused) {
        Board.paused = paused;
    }

    public static void interruptAllThreads(){
        inky.ghostAI.interrupt();
        blinky.ghostAI.interrupt();
    }

    public static void moveReddyLeft(){
        blinky.moveLeft();
    }
    public static void moveReddyRight(){
        blinky.moveRight();
    }
    public static void moveReddyUp(){
        blinky.moveUp();
    }
    public static void moveReddyDown(){
        blinky.moveDown();
    }
    public static void moveGreenyLeft(){
        inky.moveLeft();
    }
    public static void moveGreenyRight(){
        inky.moveRight();
    }
    public static void moveGreenyUp(){
        inky.moveUp();
    }
    public static void moveGreenyDown(){
        inky.moveDown();
    }


    public static int getPacmanPoints() {
        return pacmanPoints;
    }

    public static void setPacmanPoints(int pacmanPoints) {
        Board.pacmanPoints = pacmanPoints;
    }


    public Pane getRoot() {
        return root;
    }
}
