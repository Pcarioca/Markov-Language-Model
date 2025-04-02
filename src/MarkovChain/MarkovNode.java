package MarkovChain;

import java.util.TreeSet;

class MarkovNode{
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

    public MarkovNode getNodeWord(String word){// This will return the node containing the word
        //or Null if it was not found.
        for(Connection pointer: this.pointsTo){
            if(pointer.getPointingWord().equals(word)){
                return pointer.getPoints_to();
            }
        }
        return null;
    }
//          to be implemented int the markovChain class
//        public void addPointer(String word, int frequency) {
//            MarkovNode node = this.getNodeWord(word);
//            if(node == null){
//                pointsTo.add(new Connection(1, ));
//            } else {
//
//            }
//        }

    private boolean pointsToWord(String word){
        for(Connection pointer: pointsTo){
            if(pointer.pointsToWord(word)) {
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

    public void updateProbabilities(){
        for(Connection pointer: pointsTo){
            pointer.setProbability((float) pointer.getFrequency() / getCountConnections());
        }
    }

}
