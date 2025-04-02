package MarkovChain;

import java.util.Objects;

class Connection implements Comparable<Connection>{
    private int frequency;
    private float probability;
    private MarkovNode points_to;

    public Connection(int frequency, MarkovNode points_to){
        this.frequency = frequency;
        this.points_to = points_to;
    }

    public MarkovNode getPoints_to(){
        return this.points_to;
    }

    public String getPointingWord(){
        return points_to.getWord();
    }

        /*will be used to update the probability in the node class after
            adding new conntections
        */

    public void update_probability(float probability){
        this.probability = probability;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public float getProbability() {
        return probability;
    }

    public void setProbability(float probability) {
        this.probability = probability;
    }

        /*
        .   the connections will be put from most frequent to least frequent in each node.
         */

    public int compareTo(Connection other){
        return (this.getFrequency() - other.getFrequency());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Connection that)) return false;
        return frequency == that.frequency && Objects.equals(points_to, that.points_to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(frequency, points_to);
    }

    public boolean pointsToWord(String word){
        return this.points_to.getWord().equals(word);
    }


}
