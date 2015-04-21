package com.github.mjvesa.herd.wordset;

import com.github.mjvesa.herd.Interpreter;
import com.github.mjvesa.herd.Word;

/**
 * A set of words that is capable of installing itself.
 * 
 * @author mjvesa
 * 
 */
public class WordSet {

    public void install(Interpreter interpreter) {

        for (Word word : getWords()) {
            interpreter.getDictionary().put(word.getName(), word);
        }
    }

    public Word[] getWords() {
        return null;
    }

}
