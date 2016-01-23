package hangman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Jordan on 1/22/2016.
 */
public class EvilHangmanGame implements IEvilHangmanGame {
    HashSet<String> words = new HashSet<>();

    @Override
    public void startGame(File dictionary, int wordLength) {
        readDictionary(dictionary);
        for(String str : words){
            if(str.length() == wordLength){
                //Do something to keep these words.
            }
        }

    }

    private void readDictionary(File dictionary){
        try {
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(dictionary)));

            words.clear(); //Empty dictionary to read in a new one.
            while(scanner.hasNext()){
                words.add(scanner.next()); //Read all words into set.
            }
        }
        catch(Exception e){
            System.out.println("Error with reading dictionary" + e.getMessage());
        }

    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        if(guessMade(guess)) throw new GuessAlreadyMadeException();
        return null;
    }

    private boolean guessMade(char guess){
        return false;
    }
}
