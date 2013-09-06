package com.github.mjvesa.f4v.wordset;

import com.github.mjvesa.f4v.BaseWord;
import com.github.mjvesa.f4v.Interpreter;
import com.github.mjvesa.f4v.Word;

public class HeapWordSet extends WordSet {

	@Override
	public Word[] getWords() {
		return new Word[] {

		new BaseWord("!", "STORE", Word.POSTPONED) {
			@Override
			public void execute(Interpreter interpreter) {
				Integer address = (Integer) interpreter.popData();
				Object value = interpreter.popData();
				interpreter.poke(address, value);
			}
		},

		new BaseWord("@", "LOAD", Word.POSTPONED) {
			@Override
			public void execute(Interpreter interpreter) {
				Integer address = (Integer) interpreter.popData();
				interpreter.pushData(interpreter.peek(address));
			}
		}

		};
	}
}
