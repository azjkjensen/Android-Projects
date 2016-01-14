package spell_corrector;

/**
 * Created by Jordan on 1/13/2016.
 */
public class Trie implements ITrie {
    private static final int NUMBER_OF_CHILDREN = 26;

    private int wordCount;
    private int nodeCount;
    Node rootNode = new Node();

    public Trie(){
        wordCount = 0;
        nodeCount = 0;
    }

//    public String toString(){
//        StringBuilder output = new StringBuilder("TESTING, TESTING");
//        output.append("\r\n");
//
//
//        return output.toString();
//    }

    @Override
    public void add(String word) {
//        System.out.println("Adding " + word);
        String wordLower = word.toLowerCase();
        Node currentNode = rootNode;
        for(int i = 0; i < wordLower.length(); i++){
            int numValOfChar = wordLower.charAt(i) - 'a';
//            System.out.println(wordLower.charAt(i));
            if(currentNode.children[numValOfChar] == null){
//                System.out.println("Adding char " + wordLower.charAt(i));
                currentNode.children[numValOfChar] = new Node();
                nodeCount++;
            }
            currentNode = currentNode.children[numValOfChar];
            if(i == wordLower.length() - 1){
                if(currentNode.frequency == 0){
                    wordCount++;
                }
                currentNode.frequency++;
//                System.out.println("  Incrementing " + wordLower + " to " + currentNode.frequency);
            }
        }
    }

    @Override
    public INode find(String word) {
        return null;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    public class Node implements INode{
        Node[] children = new Node[NUMBER_OF_CHILDREN];
        int frequency = 0;

        public Node[] getChildren() {
            return children;
        }

        @Override
        public int getValue() {
            return frequency;
        }
    }
}
