package spell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Jk on 1/13/2016.
 * SpellCorrector class.
 *
 */
public class SpellCorrector implements ISpellCorrector{
	
    Trie dictionary = new Trie();

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
    	try{
	        Scanner scanner = new Scanner(new BufferedReader(new FileReader(new File(dictionaryFileName))));
	
	        dictionary.clear();
	        while(scanner.hasNext()){
	            dictionary.add(scanner.next());
	        }
//	        System.out.println("Words: " + dictionary.getWordCount() + "\nNodes: " + dictionary.getNodeCount());
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }

    @Override
    public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException {
        dictionary.findWordsAtOneEditDistance(inputWord);
//        System.out.println(dictionary.toString());
//        System.out.println(dictionary.equals(dictionary));
        if (dictionary.getBestWord() == null) throw new NoSimilarWordFoundException();
        return dictionary.getBestWord();
    }
}
