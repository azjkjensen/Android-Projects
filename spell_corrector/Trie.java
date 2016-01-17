package spell_corrector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by Jordan on 1/13/2016.
 *
 * You are required to implement your dictionary as a Trie (pronounced “try”). A Trie is a
// tree­based data structure designed to store items that are sequences of characters from an
// alphabet. Each Trie­Node stores a count and a sequence of Nodes, one for each element in
// the alphabet. Each Trie­Node has a single parent except for the root of the Trie which does
// not have a parent. A sequence of characters from the alphabet is stored in the Trie as a path
// in the Trie from the root to the last character of the sequence.
// For our Trie we will be storing words (a word is a sequence of alphabetic characters) so the
// length of the sequence of Nodes in every Node will be 26, one for each letter of the alphabet.
// For any node a we will represent the count in a as a.count. For the array of Nodes in a we will
// use a.nodes. For the node in a’s sequence of Nodes associated with the character c we will
// use a.nodes[c]. For instance, a.nodes[‘b’] represents the node in a’s array of Nodes
// corresponding to the character ‘b’.
// Each node in the root’s array of Nodes represents the first letter of a word stored in the Trie.
// Each of those Nodes has an array of Nodes for the second letter of the word, and so on. For
// example, the word “kick” would be stored as follows:
// root.nodes[‘k’].nodes[‘i’].nodes[‘c’].nodes[‘k’]
// The count in a node represents the number of times a word represented by the path from the
// root to that node appeared in the text file from which the dictionary was created. Thus, if the
// word “kick” appeared twice, root.nodes[‘k’].nodes[‘i’].nodes[‘c’].nodes[‘k’].count = 2.
// If the word “kicks” appears at least once in the text file then it would be stored as
// root.nodes[‘k’].nodes[‘i’].nodes[‘c’].nodes[‘k’].nodes[‘s’]
// and root.nodes[‘k’].nodes[‘i’].nodes[‘c’].nodes[‘k’].nodes[‘s’].count would be greater than or
// equal to one.
// If the the count value of any node, n, is zero then the word represented by the path from the
// root to n did not appear in the original text file. For example, if root.nodes[‘k’].nodes[‘i’].count
// = 0 then the word “ki” does not appear in the original text file. A node may have descendant
// nodes even if its count is zero. Using the example above, some of the nodes representing
// “kick” and “kicks” would have counts of 0 (e.g root.nodes[‘k’], root.nodes[‘k’].nodes[‘i’], and
// root.nodes[‘k’].nodes[‘i’].nodes[‘c’]) but root.nodes[‘k’].node[‘i’].nodes[‘c’].nodes[‘k’] and
// root.nodes[‘k’].node[‘i’].nodes[‘c’].nodes[‘k’].nodes[‘s’] would have counts greater than 0.

 */
public class Trie implements ITrie {
    private static final int NUMBER_OF_CHILDREN = 26;

    String bestWord = null;
    int bestFrequency;

    private int wordCount;
    private int nodeCount;
    Node rootNode = new Node();

    private HashSet <INode> acceptedWords = new HashSet<>();
    private HashSet <String> dictionaryWords = new HashSet<>();
//    private HashSet <INode> rejectedWords = new HashSet<INode>();

    public Trie(){
        wordCount = 0;
        nodeCount = 1;
    }

    public String toString(){
        ArrayList sortedDictionary = new ArrayList(dictionaryWords);
        Collections.sort(sortedDictionary);
        StringBuilder sb = new StringBuilder();
        for(Object i : sortedDictionary){
            sb.append(i).append("\n");
        }
        return sb.toString();
    }

    @Override
    public void add(String word) {
//        System.out.println("Adding " + word);
        String wordLower = word.toLowerCase();
        Node currentNode = rootNode;
        dictionaryWords.add(word);
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
        String wordLower = word.toLowerCase();
        Node currentNode = rootNode;
        for(int i = 0; i < wordLower.length(); i++){
            if(currentNode.children[wordLower.charAt(i) - 'a'] == null){
                return null;
            } else{
                currentNode = currentNode.children[wordLower.charAt(i) - 'a'];
            }
        }
        return currentNode;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    public HashSet<INode> getPossibleSuggestions(){
        return acceptedWords;
    }

    public String getBestWord(){
        return bestWord;
    }

    public void deletionChecker(String word) {
//        StringBuilder sbOriginal = new StringBuilder(word);
        StringBuilder sbCopy = new StringBuilder(word);
        for(int i = 0; i < word.length(); i++) {
            sbCopy.deleteCharAt(i); //Delete each character.
            INode n = find(sbCopy.toString());
            if(n == null) {
                System.out.println("Rejected:");
                System.out.println(sbCopy.toString());
//                rejectedWords.add(n);//Add to set of rejected one-edit distance words
            } else if (n.getValue() == 0) {
                System.out.println("Rejected:");
                System.out.println(sbCopy.toString());
//                rejectedWords.add(n);//Add to set of rejected one-edit distance words
            } else {
                    System.out.println("Added:");
                    System.out.println(sbCopy.toString());
                    System.out.println("Frequency: " + n.getValue());
                    acceptedWords.add(n);//Add to set of accepted one-edit distance words
                    if(bestWord == null) bestWord = sbCopy.toString();
                    else{
                        INode bestNode = find(bestWord);
                        if(bestNode.getValue() < n.getValue() ||
                                (bestNode.getValue() == n.getValue() && bestWord.compareTo(word) > 0)){
//                            System.out.println("Changing bestword to " + sbCopy.toString() + " with frequency " + n.getValue());
                            bestWord = sbCopy.toString();
                        }
                    }
            }
//            System.out.println(acceptedWords.toString());
            sbCopy = new StringBuilder(word);
        }
    }

    public void findWordsAtOneEditDistance(String word){
        if(find(word) != null){
            bestWord = word;
            return;
        }
        deletionChecker(word);
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
