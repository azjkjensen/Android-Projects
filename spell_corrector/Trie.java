package spell_corrector;

/**
 * Created by Jordan on 1/13/2016.
 */
public class Trie {
    private static final int NUMBER_OF_CHILDREN = 26;

    private Node[] children = new Node[NUMBER_OF_CHILDREN];

    public Trie(){

    }

    public Node[] getChildren() {
        return children;
    }


    public class Node {
        public Node(){

        }
    }
}
