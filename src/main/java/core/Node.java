package core;

/**
 * klasa odpowiedzialne za pole planszy
 */
public class Node implements Comparable<Node>{
    private int column;
    private int row;
    private int distance;
    private int heuristicDistance;
    private int distanceSoFar;
    private Node parent;




    public Node() {
    }

    public Node(int row, int column){
        this.row = row;
        this.column = column;
    }


    public void setDistance(){
        this.distance = this.heuristicDistance + this.distanceSoFar;
    }


    public void setHeuristicDistance(int heuristicDistance) {
        this.heuristicDistance = heuristicDistance;
    }

    public int getDistanceSoFar() {
        return distanceSoFar;
    }

    public void setDistanceSoFar(int distanceSoFar) {
        this.distanceSoFar = distanceSoFar;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(Node o) {
        if(this.getDistance() > o.getDistance() ){
            return 1;
        }
        else if(this.getDistance() < o.getDistance()){
            return -1;
        }
        else{
            return 0;
        }
    }

    public void setParent(Node parent){
        this.parent=parent;
    }
    public Node getParent(){
        return this.parent;
    }
    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }



    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Node)) {
            return false;
        }
        Node node = (Node) object;
        if (this.column == node.getColumn() && this.row == node.getRow()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.column;
        hash = 47 * hash + this.column;
        hash = 47 * hash + this.parent.getColumn();
        hash = 47 * hash + this.parent.getRow();
        hash = 47 * hash + this.distance;
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Row=").append(getRow());
        sb.append(";").append("Column=").append(getColumn());
        return sb.toString();
    }
}