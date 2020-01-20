package core.AI;


import core.Node;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;


/**
 * autorska implementacja algorytmu A*
 *  implementacja opiera sie na liscie przeszukanych juz pol oraz na kolejce priorytetowej otwartych pol- tzw. fali czolowej
 *  na poczatku inicializujemy pierwsze pole ktora ma zostac przeszukane - jest to pozycja w ktorej znajduje sie Inky lub Blinky
 *  nastepnie dodajemy nasze poczatkowe pole do kolejki otwartych pol
 *  nastepnie w petli az do wyczerpania kolejki lub napotkania docelowego pola przeszukujemy kolejno sasidow obecnie przeszukiwanego pola
 *  nastepnie probujemy odtworzyc nasza sciezke
 */
public class AStarGhostAI extends GhostAI{

    private List<Node> closedSet;
    private Queue<Node> frontier;

     void calculatePath(){


        frontier = new PriorityQueue<>();
        closedSet = new LinkedList<>();


        initializeStartNode();
        frontier.add(startNode);


            while (!frontier.isEmpty()) {

                Node currNode = frontier.poll();

                if (currNode.equals(targetNode)) {
                    closedSet.add(currNode);
                    break;
                }

                arrangeNeighbors(currNode);


            }
            try {
                reconstructPath(closedSet);
            }
            catch(Exception e){
                System.out.println(e.toString());
            }
        }


    /**
     * @param currNode
     * metoda ustalajaca sasiadow danego pola
     * metoda ustala rodzica tych sasiadow jako currNode
     * nastepnie metoda ustala dystans od pierwszego pola
     * nastepnie metoda oblicza dystanas heurestyczny do celu
     * nastepnie ustala dystans glowny jako sume dsyansu i dystansu heurystycznego
     */
    private void arrangeNeighbors(Node currNode){
        List<Node> currNodeNeighbors = getNeighbors(currNode.getRow(), currNode.getColumn());

        for (Node next : currNodeNeighbors) {

            next.setParent(currNode);
            next.setDistanceSoFar(next.getParent().getDistanceSoFar()+1);
            next.setHeuristicDistance(calculateHeurestic(next.getRow(), next.getColumn()));

            next.setDistance();

            int newCost = currNode.getDistanceSoFar()+1;

            if(!nodeInList(next, closedSet) || (newCost < next.getDistanceSoFar() ) ) {
                frontier.add(next);
                closedSet.add(currNode);
            }
        }
    }

    private void initializeStartNode(){
        startNode.setDistanceSoFar(0);
        startNode.setHeuristicDistance(calculateHeurestic(startNode.getRow(), startNode.getColumn()));
        startNode.setDistance();
    }


    public void setTargetNode(){

    }
    int calculateHeurestic(int srcRow, int srcCol, int destRow, int destCol){
        return(Math.abs(srcCol-destCol) + Math.abs(srcRow-destRow));
    }
    private int calculateHeurestic(int srcRow, int srcCol){

        return(Math.abs(srcCol-targetNode.getColumn()) + Math.abs(srcRow-targetNode.getRow()));
    }
}
