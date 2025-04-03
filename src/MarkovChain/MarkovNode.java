package MarkovChain;

import java.util.Objects;
import java.util.TreeSet;

class MarkovNode implements Comparable<MarkovNode> {
    private String word;
    private TreeSet<Connection> pointsTo;
    private int CountConnections;


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public TreeSet<Connection> getPointsTo() {
        return pointsTo;
    }

    public void setPointsTo(TreeSet<Connection> pointsTo) {
        this.pointsTo = pointsTo;
    }

    public MarkovNode getNodeWord(String word) {// This will return the node containing the word
        //or Null if it was not found.
        for (Connection pointer : this.pointsTo) {
            if (pointer.getPointingWord().equals(word)) {
                return pointer.getPoints_to();
            }
        }
        return null;
    }

    public void addConection(Connection newCon){
        this.pointsTo.add(newCon);
    }


    private boolean pointsToWord(String word) {
        for (Connection pointer : pointsTo) {
            if (pointer.pointsToWord(word)) {
                return true;
            }
        }

        return false;
    }

    public int getCountConnections() {
        return CountConnections;
    }

    public void setCountConnections(int countConnections) {
        CountConnections = countConnections;
    }

        /*
            parses all the nodes and sets probabilities accordingly
        */

    public void updateProbabilities() {
        for (Connection pointer : pointsTo) {
            pointer.setProbability((float) pointer.getFrequency() / getCountConnections());
        }
    }

    public int compareTo(MarkovNode other) {
        return this.getCountConnections() - other.getCountConnections();
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MarkovNode that)) return false;
        return Objects.equals(word, that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }

    public String toString() {

        StringBuilder to_ret = new StringBuilder();
        to_ret.append("The word is " + this.getWord() + "and it points to: ");

        int i = 1;
        for (Connection c : this.pointsTo) {
            to_ret.append(i + ": word " + c.getPointingWord() + " with a frequency of " + c.getFrequency() + " And a probability of " + c.getProbability());
            to_ret.append("\n");
            i++;
        }

        return to_ret.toString();
    }

    public void print_node(){
        System.out.println(this.toString());
    }


}
