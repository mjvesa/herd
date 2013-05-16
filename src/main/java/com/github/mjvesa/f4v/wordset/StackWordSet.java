package com.github.mjvesa.f4v.wordset;

import com.github.mjvesa.f4v.BaseWord;
import com.github.mjvesa.f4v.Interpreter;
import com.github.mjvesa.f4v.Word;

public class StackWordSet extends WordSet {

	@Override
	public Word[] getWords() {
		return new Word[] {

		new BaseWord("dup", "( a -- a a )", Word.POSTPONED) {
			@Override
			public void execute(Interpreter interpreter) {
				Object o = interpreter.peekData();
				interpreter.pushData(o);
			}
		},

		new BaseWord("over", "( a b -- a b a )", Word.POSTPONED) {
			@Override
			public void execute(Interpreter interpreter) {
				Object o1 = interpreter.popData();
				Object o2 = interpreter.popData();
				interpreter.pushData(o2);
				interpreter.pushData(o1);
				interpreter.pushData(o2);
			}
		},

		new BaseWord("rot", "( a b c -- b c a )", Word.POSTPONED) {
			@Override
			public void execute(Interpreter interpreter) {
				Object o1 = interpreter.popData();
				Object o2 = interpreter.popData();
				Object o3 = interpreter.popData();
				interpreter.pushData(o2);
				interpreter.pushData(o1);
				interpreter.pushData(o3);
			}
		},

		new BaseWord("-rot", "( a b c -- c a b )", Word.POSTPONED) {
			@Override
			public void execute(Interpreter interpreter) {
				Object o1 = interpreter.popData();
				Object o2 = interpreter.popData();
				Object o3 = interpreter.popData();
				interpreter.pushData(o1);
				interpreter.pushData(o3);
				interpreter.pushData(o2);
			}
		},

		new BaseWord("nip", "( a b -- b )", Word.POSTPONED) {
			@Override
			public void execute(Interpreter interpreter) {
				Object o = interpreter.popData();
				interpreter.popData();
				interpreter.pushData(o);
			}
		},

		new BaseWord("tuck", "( a b -- b a b )", Word.POSTPONED) {
			@Override
			public void execute(Interpreter interpreter) {
				Object o1 = interpreter.popData();
				Object o2 = interpreter.popData();
				interpreter.pushData(o1);
				interpreter.pushData(o2);
				interpreter.pushData(o1);

			}
		},

		new BaseWord("swap", "( a b -- b a )", Word.POSTPONED) {
			@Override
			public void execute(Interpreter interpreter) {
				Object o1 = interpreter.popData();
				Object o2 = interpreter.popData();
				interpreter.pushData(o1);
				interpreter.pushData(o2);
			}
		},

		new BaseWord("2swap", "( a b c d -- c d a b)", Word.POSTPONED) {
			@Override
			public void execute(Interpreter interpreter) {
				Object o1 = interpreter.popData();
				Object o2 = interpreter.popData();
				Object o3 = interpreter.popData();
				Object o4 = interpreter.popData();
				interpreter.pushData(o2);
				interpreter.pushData(o1);
				interpreter.pushData(o4);
				interpreter.pushData(o3);

			}
		},

		new BaseWord("drop", "", Word.POSTPONED) {
			@Override
			public void execute(Interpreter interpreter) {
				interpreter.popData();
			}
		}

		};

	}

}
