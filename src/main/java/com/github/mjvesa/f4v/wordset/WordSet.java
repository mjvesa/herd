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

	private Word[] words;

	public void install(Interpreter interpreter) {

		for (Word word : words) {
			interpreter.getDictionary().put(word.getName(), word);
		}
	}

}
