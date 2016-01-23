package spell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by Jk on 1/13/2016.
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

    private int wordCount;
    private int nodeCount;
    Node rootNode = new Node();

    private HashSet <String> acceptedWords = new HashSet<>();
    private HashSet <String> rejectedWords = new HashSet<>();
    private HashSet <String> dictionaryWords = new HashSet<>();

    public Trie(){
        wordCount = 0;
        nodeCount = 1;
    }

    public String toString(){
        ArrayList<String> sortedDictionary = new ArrayList<>(dictionaryWords);
        Collections.sort(sortedDictionary);
        StringBuilder sb = new StringBuilder();
        for(Object i : sortedDictionary){
            sb.append(i).append("\n");
        }
        return sb.toString();
    }
    
    public void clear(){
    	dictionaryWords.clear();
    }

    @Override
    public void add(String word) {
        String wordLower = word.toLowerCase();
        Node currentNode = rootNode;
        dictionaryWords.add(wordLower);
        for(int i = 0; i < wordLower.length(); i++){
            int numValOfChar = wordLower.charAt(i) - 'a';
//            System.out.println(wordLower.charAt(i));
            if(currentNode.children[numValOfChar] == null){
//                System.out.println("Adding char " + wordLower.charAt(i));
                currentNode.children[numValOfChar] = new Node();
                nodeCount++;
            }
            currentNode = currentNode.children[numValOfChar];
            if(i == word.length() - 1){
                if(currentNode.frequency == 0){
                    wordCount++;
                }
                currentNode.frequency++;
//                System.out.println("  Incrementing " + wordLower + " to " + currentNode.frequency);
//                System.out.println("  Incrementing " + wordLower.charAt(i) + " " + i);
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
//        System.out.println(wordLower.charAt(wordLower.length() - 1) + " has the frequency of " + currentNode.frequency);
        if(currentNode.getValue() == 0) return null;
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

    public String getBestWord(){
        return bestWord;
    }

    private void deletionChecker(String wordIn) {
        String word = wordIn.toLowerCase();
//        StringBuilder sbOriginal = new StringBuilder(word);
        StringBuilder sbCopy = new StringBuilder(word);
        for(int i = 0; i < word.length(); i++) {
            sbCopy.deleteCharAt(i); //Delete each character.
            INode n = find(sbCopy.toString());
            if(n != null){
                if(n.getValue() != 0) {
//                    System.out.println("Added:");
//                    System.out.println(sbCopy.toString());
//                    System.out.println("Frequency: " + n.getValue());
                    acceptedWords.add(sbCopy.toString());//Add to set of accepted one-edit distance words
                }
            }
            rejectedWords.add(sbCopy.toString());
            sbCopy = new StringBuilder(word);
        }
//        System.out.println(acceptedWords.toString());
    }

    private void transpositionChecker(String wordIn) {
        String word = wordIn.toLowerCase();
        StringBuilder sbCopy = new StringBuilder(word.toLowerCase());
        for(int i = 0; (i + 1) < word.length(); i++){
            StringBuilder transposed = new StringBuilder();
            transposed.append(sbCopy.substring(0, i));
            transposed.append(sbCopy.charAt(i+1));
            transposed.append(sbCopy.charAt(i));
            transposed.append(sbCopy.substring(i+2, sbCopy.length()));
//            System.out.println(transposed.toString());
            INode n = find(transposed.toString());
            if(n != null){
                if(n.getValue() != 0) {
//                    System.out.println("Added:");
//                    System.out.println(transposed.toString());
//                    System.out.println("Frequency: " + n.getValue());
                    acceptedWords.add(transposed.toString());//Add to set of accepted one-edit distance words
                }
            }
            rejectedWords.add(sbCopy.toString());
        }
    }

    private void alterationChecker(String wordIn) {
        String word = wordIn.toLowerCase();
        StringBuilder sbCopy = new StringBuilder(word);
        for(int i = 0; i < word.length(); i++){
            for(int j = 0; j < NUMBER_OF_CHILDREN; j++){
                sbCopy.deleteCharAt(i);
                char charToInsert = (char) (j + 'a');
                sbCopy.insert(i, charToInsert);
//                System.out.println(sbCopy.toString());
                INode n = find(sbCopy.toString());
                if(n != null){
                    if(n.getValue() != 0) {
//                        System.out.println("Added:");
//                        System.out.println(sbCopy.toString());
//                        System.out.println("Frequency: " + n.getValue());
                        acceptedWords.add(sbCopy.toString());//Add to set of accepted one-edit distance words
                    }
                }
                rejectedWords.add(sbCopy.toString());
                sbCopy = new StringBuilder(word);
            }
        }
    }

    private void insertionChecker(String wordIn) {
        String word = wordIn.toLowerCase();
        StringBuilder sbCopy = new StringBuilder(word);
        for(int i = 0; i <= word.length(); i++) {
            for (int j = 0; j < NUMBER_OF_CHILDREN; j++) {
                char charToInsert = (char) (j + 'a');
                sbCopy.insert(i, charToInsert);
                INode n = find(sbCopy.toString());
                if(n != null){
                    if(n.getValue() != 0) {
//                        System.out.println("Added:");
//                        System.out.println(sbCopy.toString());
//                        System.out.println("Frequency: " + n.getValue());
                        acceptedWords.add(sbCopy.toString());//Add to set of accepted one-edit distance words
                    }
                }
                rejectedWords.add(sbCopy.toString());
                sbCopy = new StringBuilder(word);
            }
        }
    }

    private void assignBestWord(){
        if(acceptedWords.isEmpty()) return; //Leave bestword as null to signify no suggestion
        for(String bestCandidate : acceptedWords){
            if (bestWord == null) bestWord = bestCandidate; //Assign the first word to be our current best
            else {
                INode bestNode = find(bestWord);
                INode candidateNode = find(bestCandidate);
                if (bestNode.getValue() < candidateNode.getValue() ||
                        (bestNode.getValue() == candidateNode.getValue() && bestWord.compareTo(bestCandidate) > 0)) {
                    bestWord = bestCandidate; //Assign the current candidate to be the new best suggestion.
                }
            }
        }
    }

    public void findWordsAtOneEditDistance(String word){
    	
    	acceptedWords.clear();
    	rejectedWords.clear();
    	
    	INode wordNode = find(word);
        if(wordNode != null){
        	if(wordNode.getValue() != 0){
//	        	System.out.println("Word is in dictionary with frequency " + wordNode.getValue());
	            bestWord = word;
	            return;
        	}
        }
        deletionChecker(word);
        transpositionChecker(word);
        insertionChecker(word);
        alterationChecker(word);

        assignBestWord();
        
        if(bestWord == null){
        	HashSet<String> prevAccepted = new HashSet<>(rejectedWords);
        	acceptedWords.clear();
        	for(String str : prevAccepted){
                deletionChecker(str);
                transpositionChecker(str);
                insertionChecker(str);
                alterationChecker(str);        		
        	}
        	assignBestWord();
        }
    }

     @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        Trie ob = (Trie) o;
        if (ob.getNodeCount() != this.nodeCount || ob.getWordCount() != this.getWordCount()) {
            return false;
        }
        if (!this.rootNode.equals(ob.rootNode)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = wordCount;
        result = 31 * result + nodeCount;
        return result;
    }

    public class Node implements INode{
        Node[] children = new Node[NUMBER_OF_CHILDREN];
        int frequency;
        
        public Node(){
            frequency = 0;
        }

        @Override
        public int getValue() {
            return frequency;
        }
        
         @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            final Node other = (Node) obj;
            if (this.frequency != other.frequency) {
                return false;
            }
            return Arrays.deepEquals(this.children, other.children);
        }
    }
}
