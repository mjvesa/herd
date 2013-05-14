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
	}

	};

	// case LITERAL:
	// dataStack.push(word.getParam());
	// break;
	// case PRINT:
	// value = dataStack.pop();
	// print(value.toString());
	// break;
	// case BEGINCOMMENT:
	// str = parser.getNextWord();
	// while (!")".equals(str)) {
	// str = parser.getNextWord();
	// }
	// break;
	// case ENDCOMMENT:
	// // Doesn't do squat (yet?)
	// break;
	// case CAT:
	// str = (String) dataStack.pop();
	// str2 = (String) dataStack.pop();
	// dataStack.push(str2 + str);
	// break;
	// case NULL:
	// dataStack.push(Util.NULL_OBJECT);
	// break;
	// case OBJEQUALS:
	// o1 = dataStack.pop();
	// o2 = dataStack.pop();
	// dataStack.push(o1.equals(o2));
	// break;
	//
	// case LIST_TERMINATOR:
	// dataStack.push(Util.LIST_TERMINATOR);
	// break;
	// case EXECBUFFER:
	// str = (String) dataStack.pop();
	// interpret(source.get(str));
	// break;
	//
	// case PRINTSTACK:
	// printStack();
	// break;
	//
	// case LOG:
	// print((String) dataStack.pop());
	// break;

}
