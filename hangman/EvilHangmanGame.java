package hangman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collector;

/**
 * Created by Jordan on 1/22/2016.
 */
public class EvilHangmanGame implements IEvilHangmanGame {
    private HashSet<String> words = new HashSet<>();
    private TreeSet<String> guessed = new TreeSet<>();
    private HashMap<String, Set<String>> partitions = new HashMap<>();
    private String partialWord;

    //Empty constructor
    public EvilHangmanGame(){
        partialWord = "";
    }

    public String getPartialWord() {
        return partialWord;
    }

    public TreeSet<String> getGuessed() {
        return guessed;
    }

    //Starts the game.
    @Override
    public void startGame(File dictionary, int wordLength) {
        guessed = new TreeSet<>();
        createPartialWord(wordLength);
        readDictionary(dictionary); //Read in word file for use as current dictionary
        HashSet<String> partitionedWords = new HashSet<>(); //Set for only words of length wordLength
        for(String str : words){
            if(str.length() == wordLength){
                partitionedWords.add(str);
            }
        }
        words = partitionedWords;
        System.out.println(partitionedWords.toString());
    }

    private void readDictionary(File dictionary){
        try {
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(dictionary)));

            words.clear(); //Empty dictionary to read in a new one.
            while(scanner.hasNext()){
                String current = scanner.next();
                if(current.matches("[a-zA-Z]+")) {
                    words.add(scanner.next()); //Read all words into set.
                }
            }
        }
        catch(Exception e){
            System.out.println("Error with reading dictionary " + e.getMessage());
        }

    }

    private void createPartialWord(int wordLength){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < wordLength; i++){
            sb.append("-");
        }
        partialWord = sb.toString();
    }

    private String key(String word, char currChar){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < word.length(); i++){
            char ch = word.charAt(i);
            if(ch == currChar) sb.append(currChar);
            else sb.append("-");
        }
//        System.out.println(sb.toString());
        return sb.toString();
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        if(guessMade(guess)) throw new GuessAlreadyMadeException();
        partitions.clear();
        guessed.add(Character.toString(guess));
        for(String currWord : words){
            String currentKey = key(currWord, guess);
            if(partitions.containsKey(currentKey)){
                partitions.get(currentKey).add(currWord);
            } else {
                HashSet<String> newSet = new HashSet<>();
                newSet.add(currWord);
                partitions.put(currentKey, newSet);
            }
        }

        String bestKey = "";
        Set<String> bestSet = new HashSet<>();
        for(String currentKey : partitions.keySet()){
            Set<String> currSet = partitions.get(currentKey);
            if(currSet.size() > bestSet.size()){
                bestSet = currSet;
                bestKey = currentKey;
            }
            else if(currSet.size() == bestSet.size()){
                if(containsNoGuesses(currentKey)) {
                    bestKey = currentKey;
                    bestSet = currSet;
                    break;
                }

                if(getNumberOfGuessedLetters(currentKey) < getNumberOfGuessedLetters(bestKey)){
                    bestKey = currentKey;
                    bestSet = currSet;
                    break;
                }
                if(!containsNoGuesses(bestKey)) {
                    if (rightMostIndexCompare(currentKey, bestKey)) {
                        bestKey = currentKey;
                        bestSet = currSet;
                        break;
                    }
                }

                if(bestKey == ""){
                    System.out.println("No best key found");
                }
            }
        }
        partialWord = unionKeys(bestKey, partialWord);
        words = (HashSet<String>) bestSet;
        return bestSet;
    }

    private String unionKeys(String key1, String key2){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < key1.length(); i++){
            if(key1.charAt(i) == '-') sb.append(key2.charAt(i));
            else sb.append(key1.charAt(i));
        }
        return sb.toString();
    }

    private boolean containsNoGuesses(String word){
        for(int i = 0; i < word.length(); i++){
            if(word.charAt(i) != '-') return false;
        }
        return true;
    }

    private int getNumberOfGuessedLetters(String word){
        int count = 0;
        for(int i = 0; i < word.length(); i++){
            if(word.charAt(i) != '-') count++;
        }
        return count;
    }

    private boolean rightMostIndexCompare(String word1, String word2){
        int index1 = 0;
        int index2 = 0;
        for(int i = 0; i < word1.length(); i++){
            if(word1.charAt(i) != '-') index1 = i;
            if(word2.charAt(i) != '-') index2 = i;
        }
        if(index1 == index2) {
            return rightMostIndexCompare(word1.substring(0, index1), word2.substring(0, index2));
        }
        return index1 < index2;
    }

    private boolean guessMade(char guess){
        if(guessed.contains(Character.toString(guess))) return true;
        else return false;
    }

    public boolean gameWin(){
        for(int i = 0; i < partialWord.length(); i++){
            if(partialWord.charAt(i) == '-') return false;
        }
        return true;
    }

    public String getAWord() throws Exception{
        for(String str : words) {
            return str;
        }
        throw new Exception("Error, word set was empty");
    }
}
