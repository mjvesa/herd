package com.github.mjvesa.f4v.wordset;

import com.github.mjvesa.f4v.State;
import com.github.mjvesa.f4v.Word;

/**
 * A set of words that is capable of installing itself.
 * 
 * @author mjvesa
 * 
 */
public class WordSet {

	private Word[] words;

	public void install(State state) {

		for (Word word : words) {
			state.getDictionary().put(word.getName(), word);
		}
	}

}
