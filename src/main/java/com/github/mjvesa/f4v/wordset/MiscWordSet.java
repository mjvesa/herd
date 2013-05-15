package com.github.mjvesa.f4v.wordset;

import com.github.mjvesa.f4v.BaseWord;
import com.github.mjvesa.f4v.Interpreter;
import com.github.mjvesa.f4v.Word;

public class MiscWordSet extends WordSet {

	protected Word[] words = {

	new BaseWord("", "") {
		@Override
		public void execute(Interpreter interpreter) {
		}
	},

			// case LITERAL:
			// dataStack.push(word.getParam());
			// break;
			new BaseWord("", "") {
				@Override
				public void execute(Interpreter interpreter) {
				}
			},

			// case PRINT:
			// value = dataStack.pop();
			// print(value.toString());
			// break;
			new BaseWord("", "") {
				@Override
				public void execute(Interpreter interpreter) {
				}
			},

			// case BEGINCOMMENT:
			// str = parser.getNextWord();
			// while (!")".equals(str)) {
			// str = parser.getNextWord();
			// }
			// break;
			new BaseWord("", "") {
				@Override
				public void execute(Interpreter interpreter) {
				}
			},

			// case ENDCOMMENT:
			// // Doesn't do squat (yet?)
			// break;
			new BaseWord("", "") {
				@Override
				public void execute(Interpreter interpreter) {
				}
			},

			// case NULL:
			// dataStack.push(Util.NULL_OBJECT);
			// break;
			new BaseWord("", "") {
				@Override
				public void execute(Interpreter interpreter) {
				}
			},

			// case OBJEQUALS:
			// o1 = dataStack.pop();
			// o2 = dataStack.pop();
			// dataStack.push(o1.equals(o2));
			// break;
			new BaseWord("", "") {
				@Override
				public void execute(Interpreter interpreter) {
				}
			},

			//
			// case LIST_TERMINATOR:
			// dataStack.push(Util.LIST_TERMINATOR);
			// break;
			new BaseWord("", "") {
				@Override
				public void execute(Interpreter interpreter) {
				}
			},

			// case EXECBUFFER:
			// str = (String) dataStack.pop();
			// interpret(source.get(str));
			// break;
			new BaseWord("", "") {
				@Override
				public void execute(Interpreter interpreter) {
				}
			},
			// case PRINTSTACK:
			// printStack();
			// break;
			new BaseWord("", "") {
				@Override
				public void execute(Interpreter interpreter) {
				}
			},

	// case LOG:
	// print((String) dataStack.pop());
	// break;
	};

}
