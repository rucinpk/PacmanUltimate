package core.AI;


import core.Board;
import core.Node;


/**
 * klasa wyszukujaca sciezke do celu
 */
public class AStarToPacmanGhostAI extends AStarGhostAI {



    public AStarToPacmanGhostAI(Node startNode)
    {
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
                    Board.stopReddy();
                    System.out.println(e.toString() + "Blinky");
                }
        }
    }
    @Override
    public void setTargetNode(){
        this.targetNode=new Node(Board.getPacman().getRow(), Board.getPacman().getColumn());
    }
    public void setSrcNode(){
        startNode = new Node(Board.getReddy().getRow(), Board.getReddy().getColumn());
    }



}
