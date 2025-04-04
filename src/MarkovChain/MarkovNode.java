package MarkovChain;

import java.util.Objects;
import java.util.TreeSet;

class MarkovNode implements Comparable<MarkovNode> {
    private String word;
    private TreeSet<Connection> connections; //list of outward arcs
    private int countConnections; //number of outward arcs

    public MarkovNode(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

//    public void setWord(String word) {
//        this.word = word;
//    } Irrelevant: the nodes are unmutable.

    public TreeSet<Connection> getConnections() {
        return connections;
    }

    public void setConnections(TreeSet<Connection> connections) {
        this.connections = connections;
    }

    public MarkovNode getNodeWord(String word) {// This will return the node containing the word
        //or Null if it was not found.
        for (Connection pointer : this.connections) {
            if (pointer.getPointingWord().equals(word)) {
                return pointer.getPoints_to();
            }
        }
        return null;
    }

    private Connection addConection(Connection newCon){ //returns the connection just added.
        Connection to_return = null;
//        if(this.pointsToWord(newCon.getPointingWord())){
            for(Connection c: this.connections){
                if(c.getPointingWord().equals(newCon.getPointingWord())){ //will be equal if it's the same word
                    c.setFrequency(c.getFrequency() + 1);
                    to_return = c;
                    break;
                }
            }
//        } else
        if(to_return == null){ // the connection is completely new.
            to_return = newCon;
        }
        this.connections.add(to_return);

        return to_return;
    }

    public Connection addTransitionToWord(String word){
        Connection newCon = new Connection(1, new MarkovNode(word));
        return addConection(newCon);
    }

//    public MarkovNode addTransitionToNode(MarkovNode node){
//
//    }


    private boolean pointsToWord(String word) {
        for (Connection pointer : connections) {
            if (pointer.pointsToWord(word)) {
                return true;
            }
        }

        return false;
    }

    public int getCountConnections() {
        return countConnections;
    }

    public void setCountConnections(int countConnections) {
        this.countConnections = countConnections;
    }

        /*
            parses all the nodes and sets probabilities accordingly
        */

    public void updateProbabilities() {
        if(this.countConnections == 0) return;
        for (Connection pointer : connections) {
            pointer.setProbability((double) pointer.getFrequency() / this.getCountConnections());
        }
    }

    public int compareTo(MarkovNode other) {
        return Integer.compare(getCountConnections(), other.getCountConnections());
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MarkovNode that)) return false; //creates a new dummy node for the return line
        return Objects.equals(word, that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }

    public String toString() {

        StringBuilder to_ret = new StringBuilder();
        to_ret.append("The word is " + this.getWord() + " and it points to: ");

        int i = 1;
        for (Connection c : this.connections) {
            to_ret.append(i + ": word " + c.getPointingWord() + " with a frequency of " + c.getFrequency() + " And a probability of " + c.getProbability());
            to_ret.append("\n");
            i++;
        }

        return to_ret.toString();
    }

    public void printNode(){
        System.out.println(this.toString());
    }


}
