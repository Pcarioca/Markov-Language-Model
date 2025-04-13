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




    private MarkovNode getNodeWord(String word){
        for(MarkovNode node: this.nodes){
            if(word.equals(node.getWord())){
                return node;
            }
        }
        return null;
    }

    // FILE: MarkovChain.java
// CLASS: MarkovChain
// METHOD: addWord(...)
// Around line 42 (depending on your code editor line numbering),
// replace the entire addWord method with the following:

    public void addWord(String newWord) {
        // Try to find an existing node for this word
        MarkovNode currentNode = getNodeWord(newWord);

        // If no node exists yet, create a new one
        if (currentNode == null) {
            currentNode = new MarkovNode(newWord);
            this.nodes.add(currentNode);
        }

        // If there is a 'lastNodeAdded', we create or update the connection from it to our currentNode
        if (lastNodeAdded != null) {
            // See if the connection from lastNodeAdded to currentNode already exists
            Connection existingConn = lastNodeAdded.getConnectionToWord(currentNode.getWord());
            if (existingConn == null) {
                // No existing connection, so we create a new one
                Connection newConn = new Connection(currentNode);
                lastNodeAdded.addConnection(newConn);
                this.arches.add(newConn);
            } else {
                // If the connection exists, just increase the frequency
                existingConn.increaseFrequency();
            }
        }

        // Update 'lastNodeAdded' so the chain can keep linking forward
        this.lastNodeAdded = currentNode;
    }

    public void addWord_old(String newWord){ //this implements transitions.
        MarkovNode node = new MarkovNode(newWord);
        if(lastNodeAdded != null){ //meaning the last node that has been added is not null
                                    //TODO: assign lastNodeAdded each time you add a node, not at the end.
                                    //in the future I want to avoid this annoying condition
                                    //it will be false only once at the beginning.
            MarkovNode alreadyNode = getNodeWord(newWord);
            if(alreadyNode == null){ //first time we add this word
                node = new MarkovNode(newWord);
                this.nodes.add(node); //add new node to total nodes

                Connection newConn = lastNodeAdded.addTransitionToWord(newWord); //add the connection to the last node

//                node.addConnection(newConn); // add new connection to list of total arches

                this.arches.add(newConn); //also add it to the main markov Chain for easy future reference.

            } else { //not the first time we see the word
                // to be continued
                Connection c = node.getConnectionToWord(newWord);
                if(c!= null){ //The node connects to the word already
                    c.increaseFrequency();
                    arches.add(c);
                } else { //no connection so far
                    //To be continued: search in list nodes and add a new connection to alreadyNode
                    node = new MarkovNode(newWord);
                    this.nodes.add(node); //add new node to total nodes

                    Connection newConn = lastNodeAdded.addTransitionToWord(newWord); //add the connection to the last node

//                node.addConnection(newConn); // add new connection to list of total arches

                    this.arches.add(newConn); //also add it to the main markov Chain for easy future reference.
                }
            }
        } else {

            node = new MarkovNode(newWord);//first node in the chain

            nodes.add(node);
        }

        this.lastNodeAdded = node;
    }

    public void addText(String chunk){
        for(String word: chunk.split(" ")){
             addWord(word);
        }
    }

    private MarkovNode findNodeWord(String word){
        for(MarkovNode n: this.nodes){
            if(n.getWord().equals(word)){
                return n;
            }
        }
        return null;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for(MarkovNode n: this.nodes){
            s.append(n.toString());
            s.append("\n\n");
        }
        return s.toString();
    }

    public void updateProbabilities(){
        for(MarkovNode n: this.nodes){
            n.updateProbabilities();
        }
    }


    public static void main(String[] args) {


        String text_trial = "After some time the girl's wedding-day came, and she was decked out, " +
                "and went in a great procession over the fields to the\n " +
                "place where the church was. All at once she came to a stream After some After some After me";

        MarkovChain m = new MarkovChain();

        m.addText(text_trial);

        m.updateProbabilities();

        System.out.println(m);

    }





}
