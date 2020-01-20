package core.AI;

import constant.Data;
import core.Board;
import core.Node;

import java.util.*;


/**
 * klasa sztucznej inteligencji, ktora ma za zadanie obsluzyc poruszanie sie duszka
 *
 */
public abstract class GhostAI extends Thread{

    Node startNode;
    Node targetNode;

    private Queue<Node> pathToTarget;
    private Queue<Node> tmpPathToTarget;

    private Node iterator;
    private int counter = 0;


    /**
     * @param closedSet
     * metoda rekonstruujaca sciezke na podstaiwe close set oraz rodzicow w tym zbiorze
     */
    void reconstructPath(List<Node> closedSet){

        tmpPathToTarget = new PriorityQueue<>();

        if(!initializeTmpPath(closedSet)){ return;
        }

        iterator = tmpPathToTarget.peek().getParent();
        counter = closedSet.size()-1;


        arrangeTmpPath(tmpPathToTarget);

        tmpPathToTarget.poll();

        if(!tmpPathToTarget.isEmpty()){
            pathToTarget=tmpPathToTarget;
        }

    }
    private boolean initializeTmpPath(List<Node> closedSet){
        for(Node next : closedSet){
            if(next.equals(targetNode)){
                next.setDistance(closedSet.size());
                tmpPathToTarget.add(next);
                return true;
            }
        }
        return false;
    }
    private void arrangeTmpPath(Queue<Node> tmpPathToTarget){
        while(iterator != null){
            iterator.setDistance(counter);
            tmpPathToTarget.add(iterator);
            iterator=iterator.getParent();
            counter--;
        }
    }


    List<Node> getNeighbors(int row, int col){
        List<Node> nodeList = new ArrayList<>();

        if(!isWall(row, col+1)){
            Node node = new Node();
            node.setColumn(col+1);
            node.setRow(row);
            nodeList.add(node);
        }
        if(!isWall(row, col-1)){
            Node node = new Node();
            node.setColumn(col-1);
            node.setRow(row);
            nodeList.add(node);
        }
        if(!isWall(row+1, col)){
            Node node = new Node();
            node.setColumn(col);
            node.setRow(row+1);
            nodeList.add(node);
        }
        if(!isWall(row-1, col)){
            Node node = new Node();
            node.setColumn(col);
            node.setRow(row-1);
            nodeList.add(node);
        }

        return nodeList;
    }
    boolean nodeInList(Node node, List<Node> nodes){
        for(int i=0; i<nodes.size(); i++){
            if(node.equals(nodes.get(i))){
                return true;
            }
        }
        return false;
    }

     boolean isWall(int row, int column){

        if(Board.getTypeOfBoardAtIJ(row, column)== Data.WALL_TYPE){
            return true;
        }
        if(Board.getTypeOfBoardAtIJ(row, column) != Data.PATH_TYPE){
            return true;
        }
        return false;
    }


    public Queue<Node> getPathToTarget() {
        return pathToTarget;
    }
}
