package com.github.mjvesa.f4v.wordset;

import com.github.mjvesa.f4v.BaseWord;
import com.github.mjvesa.f4v.Interpreter;
import com.github.mjvesa.f4v.Word;

public class FlowControlWordSet extends WordSet {

	protected Word[] words = {

	new BaseWord("", "") {

		@Override
		public void execute(Interpreter interpreter) {
		}
	},

	// case DO:
	// returnStack.push(ip);
	// break;

	// case LOOP:
	// a = (Integer) dataStack.pop();
	// a++;
	// b = (Integer) dataStack.pop();
	// if (a < b) {
	// ip = returnStack.pop();
	// returnStack.push(ip);
	// dataStack.push(b);
	// dataStack.push(a);
	// } else {
	// returnStack.pop();
	// }
	// break;

	// case IF:
	// bool = (Boolean) dataStack.pop();
	// // If false, skip to endif
	// if (!bool) {
	// ip += (Integer) word.getParam();
	// System.out.println("IF Jumping to: "
	// + this.code[ip + 1].toString());
	// }
	// break;

	// case ELSE:
	// ip += (Integer) word.getParam();
	// System.out.println("ELSE Jumping to what is after: "
	// + this.code[ip].toString());
	// break;

	// case ENDIF:
	// // Find latest IF
	// // set current address minus one as parameter
	// i = currentDefinitionWords.size() - 1;
	// int jumpDest = i;
	// while (currentDefinitionWords.get(i).getBaseWord() != BaseWord.IF) {
	// if (currentDefinitionWords.get(i).getBaseWord() == BaseWord.ELSE) {
	// currentDefinitionWords.get(i).setParam(jumpDest - i);
	// jumpDest = i; // IF jumps to word after else
	// }
	// i--;
	//
	// // TODO should we check if we are stepping out of array?
	// // Nah.
	// }
	// currentDefinitionWords.get(i).setParam(jumpDest - i);
	// break;
	// case BEGIN:
	// returnStack.push(ip);
	// break;

	// case WHILE:
	// bool = (Boolean) dataStack.pop();
	// if (!bool) {
	// code = this.code;
	// while (code[ip].getBaseWord() != BaseWord.REPEAT) {
	// ip++;
	// }
	// returnStack.pop();
	// }
	// break;
	// case REPEAT:
	// ip = returnStack.pop();
	// returnStack.push(ip); // TODO might be off by one
	// break;
	// case LESSTHANZERO:
	// a = (Integer) dataStack.pop();
	// dataStack.push((Boolean) (a < 0));
	// break;
	// case ZERO:
	// a = (Integer) dataStack.pop();
	// dataStack.push((Boolean) (a == 0));
	// break;
	// case GREATERTHANZERO:
	// a = (Integer) dataStack.pop();
	// dataStack.push((Boolean) (a > 0));
	// break;
	// case LESSTHAN:
	// a = (Integer) dataStack.pop();
	// b = (Integer) dataStack.pop();
	// dataStack.push((Boolean) (b < a));
	// break;
	// case EQUALS:
	// a = (Integer) dataStack.pop();
	// b = (Integer) dataStack.pop();
	// dataStack.push((Boolean) (b.intValue() == a.intValue()));
	// break;
	// case GREATERTHAN: // (a b -- a > b )
	// b = (Integer) dataStack.pop();
	// a = (Integer) dataStack.pop();
	// dataStack.push((Boolean) (a > b));
	// break;
	//

	// case NOT:
	// bool = (Boolean) dataStack.pop();
	// dataStack.push(!bool);
	// break;

	};

}
