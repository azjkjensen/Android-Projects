package hangman;

import java.io.File;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Jordan on 1/22/2016.
 */
public class Main {

    public static void main(String[] args) {
    	Scanner inputReader;
        try{
            File dictionary = new File(args[0]); //File for words in dictionary
            int wordLength = Integer.parseInt(args[1]); //Length of word to be guessed
            int numberOfGuesses = Integer.parseInt(args[2]); //Number of guesses that the user gets
            
            EvilHangmanGame hangman = new EvilHangmanGame();

            hangman.startGame(dictionary, wordLength);

            inputReader = new Scanner(System.in);
            for(int i = numberOfGuesses;i > 0; i--){
                System.out.println("You have " + i + " guesses left");
                String read;
                while(true) {
                    System.out.print("Enter guess: ");
                    read = inputReader.next();
                    if(read.matches("[a-zA-Z]")) break;
                }
                char guessedChar = read.toLowerCase().charAt(0);
                try {
                    Set<String> guessSet = hangman.makeGuess(guessedChar);
                    System.out.println(guessSet);
                    TreeSet<String> guessedChars = hangman.getGuessed();
                    System.out.print("Used letters: ");
                    for(String str : guessedChars){
                        System.out.print(str + " ");
                    }
                    System.out.println();
                } catch (IEvilHangmanGame.GuessAlreadyMadeException ex){
                    System.out.println("Sorry, you already guessed that!");
                    i++;
                }
                System.out.println("Word: " + hangman.getPartialWord());
                if(hangman.gameWin()) break;
            }
            if(hangman.gameWin()){
                System.out.println("Congratulations! The word was " + hangman.getPartialWord());
            } else {
                System.out.println("GAME OVER");
                System.out.println("Sorry the word was " + hangman.getAWord());
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
