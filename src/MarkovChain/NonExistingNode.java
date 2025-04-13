package MarkovChain;

public class NonExistingNode extends RuntimeException{

    public NonExistingNode(String word){
        System.out.println("Error: The word \"" + word + "\" was not found in your text input.");
    }
}
