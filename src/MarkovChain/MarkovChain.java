package MarkovChain;

import java.util.TreeSet;

public class MarkovChain {

    private TreeSet<MarkovNode> nodes;
    private TreeSet<Connection> arches;
    private MarkovNode lastNodeAdded;

    public MarkovChain() {
        lastNodeAdded = null;
        nodes = new TreeSet<>();
        arches = new TreeSet<>();
    }

    String text_trial = "After some time the girl's wedding-day came, and she was decked out," +
                        "and went in a great procession over the fields to the\n" +
                        "place where the church was. All at once she came to a stream";


    private MarkovNode getNodeWord(String word){
        for(MarkovNode node: this.nodes){
            if(word.equals(node.getWord())){
                return node;
            }
        }
        return null;
    }

    public void addWord(String newWord){ //this implements transitions.
        MarkovNode node;
        if(lastNodeAdded != null){ //in the future I want to avoid this annoying condition
                                    //it will be false only once at the beginning.
            MarkovNode alreadyNode = getNodeWord(newWord);
            if(alreadyNode == null){ //first time we add this word
                node = new MarkovNode(newWord);
                this.nodes.add(node); //add new node to total nodes

                Connection newConn = lastNodeAdded.addTransitionToWord(newWord); //add the connection to the last node

                arches.add(newConn); // add new connection to list of total arches

                this.arches.add(newConn); //also add it to the main markov Chain for easy future reference.

            } else { //not the first time we see the word
                // to be continued

            }
        } else {

            node = new MarkovNode(newWord);//first node in the chain
        }

        this.lastNodeAdded = node;
    }


    public static void main(String[] args) {
        System.out.println("Hello world!");
    }





}
