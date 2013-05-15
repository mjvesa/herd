package com.github.mjvesa.f4v.wordset;

import com.github.mjvesa.f4v.BaseWord;
import com.github.mjvesa.f4v.Interpreter;
import com.github.mjvesa.f4v.Word;

public class StringWordSet extends WordSet {

	protected Word[] words = {

	new BaseWord("", "", Word.POSTPONED) {

		@Override
		public void execute(Interpreter interpreter) {
		}
	},
			// case STRTOINT:
			// str = dataStack.pop().toString();
			// dataStack.push(str.isEmpty() ? 0 : Integer.parseInt(str));
			// break;

			new BaseWord("", "", Word.POSTPONED) {

				@Override
				public void execute(Interpreter interpreter) {
				}
			},
			// case INTTOSTR:
			// a = (Integer) dataStack.pop();
			// dataStack.push(a.toString());
			// break;

			new BaseWord("", "", Word.POSTPONED) {

				@Override
				public void execute(Interpreter interpreter) {
				}
			},

			// case CAT:
			// str = (String) dataStack.pop();
			// str2 = (String) dataStack.pop();
			// dataStack.push(str2 + str);
			// break;
			new BaseWord("", "", Word.POSTPONED) {
				@Override
				public void execute(Interpreter interpreter) {
				}
			}

	};
}
