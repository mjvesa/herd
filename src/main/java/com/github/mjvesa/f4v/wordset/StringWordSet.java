package com.github.mjvesa.f4v.wordset;

import com.github.mjvesa.f4v.BaseWord;
import com.github.mjvesa.f4v.Interpreter;
import com.github.mjvesa.f4v.Word;

public class StringWordSet extends WordSet {

	protected Word[] words = {

	new BaseWord("STRTOINT", "", Word.POSTPONED) {

		@Override
		public void execute(Interpreter interpreter) {
			String str = interpreter.popData().toString();
			interpreter.pushData(str.isEmpty() ? 0 : Integer.parseInt(str));
		}
	},

	new BaseWord("TOSTR", "", Word.POSTPONED) {

		@Override
		public void execute(Interpreter interpreter) {
			interpreter.pushData(interpreter.popData().toString());
		}
	},

	new BaseWord("CAT", "", Word.POSTPONED) {

		@Override
		public void execute(Interpreter interpreter) {
			String str1 = (String) interpreter.popData();
			String str2 = (String) interpreter.popData();
			interpreter.pushData(str2 + str1);
		}
	}

	};
}
