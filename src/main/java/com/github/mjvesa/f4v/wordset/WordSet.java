package com.github.mjvesa.f4v.wordset;

import com.github.mjvesa.f4v.Interpreter;
import com.github.mjvesa.f4v.Word;

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
