package spell_corrector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by Jordan on 1/13/2016.
 */
public class SpellCorrector implements ISpellCorrector{
    Trie dictionary = new Trie();

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader(new File(dictionaryFileName))));

          //TESTING FILE READING
//        for(int i = 0; i < 100; i++){
//            System.out.println(scanner.next());
//        }
        while(scanner.hasNext()){
            dictionary.add(scanner.next());
        }
//        System.out.println("Words: " + dictionary.getWordCount() + "\nNodes: " + dictionary.getNodeCount());
    }

    @Override
    public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException {
        dictionary.findWordsAtOneEditDistance(inputWord);
        HashSet<String> suggestions = dictionary.getPossibleSuggestions();
        System.out.println(dictionary.toString());

        return dictionary.getBestWord();
    }
}
