package MarkovChain;

import java.util.Objects;
import java.util.TreeSet;

class MarkovNode implements Comparable<MarkovNode> {
    private String word;
    private TreeSet<Connection> connections = new TreeSet<>(); //list of outward arcs
//    int visited; //package protected
//    private int countConnections; //number of outward arcs

    public MarkovNode(String word) {
        this.word = word;
//        this.visited = 0;
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

    public Connection addConnection(Connection newCon){ //returns the connection just added.
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
        Connection newCon = new Connection(new MarkovNode(word));
        return addConnection(newCon);
    }


    public Boolean connectedWith(String Word){
        for(Connection c: connections){
            if(c.getPointingWord().equals(word)){
                return true;
            }
        }
        return false;
    }

    public Connection getConnectionToWord(String searchWord){
        for(Connection c: connections){
            if(c.getPointingWord().equals(searchWord)){
                return c;
            }
        }
        return null;
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

//    public int getCountConnections() {
//        return countConnections;
//    }

//    public void setCountConnections(int countConnections) {
//        this.countConnections = countConnections;
//    }

        /*
            parses all the nodes and sets probabilities accordingly
        */

//    public void updateProbabilities_old() {
//        if(this.countConnections == 0) return;
//        for (Connection pointer : connections) {
//            pointer.setProbability((double) pointer.getFrequency() / this.getCountConnections());
//        }
//    }

    public void updateProbabilities() {
        // First compute the total frequency from all connections
        int totalFrequency = 0;
        for (Connection pointer : connections) {
            totalFrequency += pointer.getFrequency();
        }

        // Now compute each connection's probability = freq / totalFrequency
        for (Connection pointer : connections) {
            double p = (double) pointer.getFrequency() / totalFrequency;
            pointer.setProbability(p);
        }
    }

    public int compareTo(MarkovNode other) {
        // Compare by word, which ensures TreeSet ordering is consistent with equals().
        // That way, each distinct word becomes its own distinct node in the TreeSet.
        return this.word.compareTo(other.word);
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
        to_ret.append("The word is \"" + this.getWord() + "\" and it points to: \n");

        int i = 1;
        for (Connection c : this.connections) {
            to_ret.append(i +  ": word \"" + c.getPointingWord() + "\" with a frequency of " + c.getFrequency() + " And a probability of :" + c.getProbability());
            to_ret.append("\n");
            i++;
        }

        return to_ret.toString();
    }

    public void printNode(){
        System.out.println(this.toString());
    }


    ////Generating part

    public MarkovNode getNextNodeByProbability() {
        if (connections.isEmpty()) {
            return null;
        }

        // updateProbabilities() has been called already,

        double randVal = Math.random();
        double cumulative = 0.0;

        for (Connection c : connections) {
            cumulative += c.getProbability();
            if (randVal <= cumulative) {
//                c.getPoints_to().visited += 1;
                return c.getPoints_to();
            }
        }
        // Fallback (should rarely happen if probabilities sum to 1.0):
        return connections.last().getPoints_to();
    }


}
