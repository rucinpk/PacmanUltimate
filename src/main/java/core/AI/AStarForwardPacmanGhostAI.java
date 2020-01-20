package core.AI;


import constant.Data;
import core.Board;
import core.Node;


/**
 * klasa algorymtu sztucznej inteligencji, ktora wyznacza droge do miejsca przed pacmanem
 */
public class AStarForwardPacmanGhostAI extends AStarGhostAI {


    private int currTargetCol;
    private int currTargetRow;
    private int counter = 0;

    public AStarForwardPacmanGhostAI(Node startNode){
        this.startNode = startNode;
        start();
    }


    public  void run(){
        while(true) {
            setTargetNode();
            setSrcNode();
            try {
                calculatePath();
            }
            catch (Exception e){
                Board.stopGreeny();
                System.out.println(e.toString() + "Inky");
            }
        }
    }


    /**
     * @return zwraca pozycje do ktorej ma podazac duszek
     * metoda oblicza pozycje do ktorej ma udac sie duszek
     * zaleznie w ktora strone porusza sie pacman tam obliczona bedzie pozycja docelowa dla algorytmu
     * przykladowo jesli duszek porusza sie w prawo to algorytm zwroci pozycje na 5 pol po prawej przed pacmanem
     * jesli wczesniej jest sciana to wtedy zostanie wybrany gora lub dol
     */
    private Node getTargetNode(){
        int pacRow = Board.getPacman().getRow();
        int pacCol = Board.getPacman().getColumn();

        double pacVelocityX = Board.getPacman().getVelocityX();
        double pacVelocityY = Board.getPacman().getVelocityY();


        currTargetCol = pacCol;
        currTargetRow = pacRow;
        counter = 0;


        if(pacVelocityX>0){
            getTargetIfPacmanMovesRight();
        }
        else if(pacVelocityX<0){
            getTargetIfPacmanMovesLeft();
        }
        if(pacVelocityY>0){
            getTargetIfPacmanMovesDown();
        }
        else if(pacVelocityY<0){
            getTargetIFPacmanMovesUp();
        }

        if(calculateHeurestic(Board.getInky().getRow(), Board.getInky().getColumn(), Board.getPacman().getRow(), Board.getPacman().getColumn())<Data.ASTAR_ASTARFORWARD_SWITCHER){
            return new Node(Board.getPacman().getRow(), Board.getPacman().getColumn());
        }
        return new Node(currTargetRow, currTargetCol);
    }


    /**
     * zwraca maksymalnego sasiada MAX_ASTAR_FORWARD_DISTANCE od lewej strony lub do momentu napotkania sciany
     */
    private void getMaxLeftNeighbor(){
        while(getLeftNeighbor(currTargetRow, currTargetCol)!=null && counter < Data.MAX_ASTAR_FORWARD_DISTANCE){
            currTargetCol--;
            counter++;
        }
    }
    private void getMaxRightNeighbor(){
        while(getRightNeighbor(currTargetRow, currTargetCol)!=null && counter < Data.MAX_ASTAR_FORWARD_DISTANCE){
            currTargetCol++;
            counter++;
        }
    }
    private void getMaxUpperNeighbor(){
        while(getUpperNeighbor(currTargetRow, currTargetCol)!=null && counter < Data.MAX_ASTAR_FORWARD_DISTANCE){
            currTargetRow--;
            counter++;
        }
    }
    private void getMaxDownNeighbor(){
        while(getDownNeighbor(currTargetRow, currTargetCol)!=null && counter < Data.MAX_ASTAR_FORWARD_DISTANCE){
            currTargetRow++;
            counter++;
        }
    }


    /**
     * @param row
     * @param col
     * @return zwraca pole po prawej stronie od danego wiersza lub kolumny
     */
    private Node getRightNeighbor(int row, int col){
        if(!isWall(row, col+1)){
            Node node = new Node();
            node.setColumn(col+1);
            node.setRow(row);
            return node;
        }
        return null;
    }
    private Node getLeftNeighbor(int row, int col){
        if(!isWall(row, col-1)){
            Node node = new Node();
            node.setColumn(col-1);
            node.setRow(row);
            return node;
        }
        return null;
    }
    private Node getUpperNeighbor(int row, int col){
        if(!isWall(row-1, col)){
            Node node = new Node();
            node.setColumn(col);
            node.setRow(row-1);
            return node;
        }
        return null;
    }
    private Node getDownNeighbor(int row, int col){
        if(!isWall(row+1, col)){
            Node node = new Node();
            node.setColumn(col);
            node.setRow(row+1);
            return node;
        }
        return null;
    }


    @Override
    public void setTargetNode(){
        this.targetNode=getTargetNode();
    }
    public void setSrcNode(){
        startNode = new Node(Board.getInky().getRow(), Board.getInky().getColumn());
    }


    /**
     * wyszukuje cel jesli pacman porusza sie w prawo
     */
    private void getTargetIfPacmanMovesRight(){
        getMaxRightNeighbor();
        if(getUpperNeighbor(currTargetRow, currTargetCol)!=null && counter < Data.MAX_ASTAR_FORWARD_DISTANCE){
            getMaxUpperNeighbor();
        }
        else if(getDownNeighbor(currTargetRow, currTargetCol)!=null && counter < Data.MAX_ASTAR_FORWARD_DISTANCE){
            getMaxDownNeighbor();
        }
    }
    private void getTargetIfPacmanMovesLeft(){
        getMaxLeftNeighbor();
        if(getDownNeighbor(currTargetRow, currTargetCol)!=null && counter < Data.MAX_ASTAR_FORWARD_DISTANCE){
            getMaxDownNeighbor();
        }
        else if(getUpperNeighbor(currTargetRow, currTargetCol)!=null && counter < Data.MAX_ASTAR_FORWARD_DISTANCE){
            getMaxUpperNeighbor();
        }
    }
    private void getTargetIfPacmanMovesDown(){
        getMaxDownNeighbor();
        if(getRightNeighbor(currTargetRow, currTargetCol)!=null && counter < Data.MAX_ASTAR_FORWARD_DISTANCE){
            getMaxRightNeighbor();
        }
        else if(getLeftNeighbor(currTargetRow, currTargetCol)!=null && counter < Data.MAX_ASTAR_FORWARD_DISTANCE){
            getMaxLeftNeighbor();
        }
    }
    private void getTargetIFPacmanMovesUp(){
        getMaxUpperNeighbor();
        if(getLeftNeighbor(currTargetRow, currTargetCol)!=null && counter < Data.MAX_ASTAR_FORWARD_DISTANCE){
            getMaxLeftNeighbor();
        }
        else if(getRightNeighbor(currTargetRow, currTargetCol)!=null && counter < Data.MAX_ASTAR_FORWARD_DISTANCE){
            getMaxRightNeighbor();
        }
    }




}
