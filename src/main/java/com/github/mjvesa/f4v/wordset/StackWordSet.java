package com.github.mjvesa.f4v.wordset;

import com.github.mjvesa.f4v.BaseWord;
import com.github.mjvesa.f4v.Interpreter;
import com.github.mjvesa.f4v.Word;

public class StackWordSet extends WordSet {

	protected Word[] words = {

	new BaseWord("", "") {

		@Override
		public void execute(Interpreter interpreter) {
		}
	}

	};

	// case DUP: // ( a -- a a )
	// o = dataStack.pop();
	// dataStack.push(o);
	// dataStack.push(o);
	// break;
	// case OVER: // ( a b -- a b a )
	// o1 = dataStack.pop();
	// o2 = dataStack.pop();
	// dataStack.push(o2);
	// dataStack.push(o1);
	// dataStack.push(o2);
	// break;
	// case ROT: // ( a b c -- b c a )
	// o1 = dataStack.pop();
	// o2 = dataStack.pop();
	// o3 = dataStack.pop();
	// dataStack.push(o2);
	// dataStack.push(o1);
	// dataStack.push(o3);
	// break;
	// case MINUSROT: // ( a b c -- c a b )
	// o1 = dataStack.pop();
	// o2 = dataStack.pop();
	// o3 = dataStack.pop();
	// dataStack.push(o1);
	// dataStack.push(o3);
	// dataStack.push(o2);
	// break;
	// case NIP: // ( a b -- b )
	// o1 = dataStack.pop();
	// o2 = dataStack.pop();
	// dataStack.push(o1);
	// break;
	// case TUCK: // ( a b -- b a b )
	// o1 = dataStack.pop();
	// o2 = dataStack.pop();
	// dataStack.push(o1);
	// dataStack.push(o2);
	// dataStack.push(o1);
	// break;
	//
	// case SWAP: // ( a b -- b a )
	// o1 = dataStack.pop();
	// o2 = dataStack.pop();
	// dataStack.push(o1);
	// dataStack.push(o2);
	// break;
	// case TWOSWAP: // ( a b c d -- c d a b)
	// o1 = dataStack.pop();
	// o2 = dataStack.pop();
	// o3 = dataStack.pop();
	// o4 = dataStack.pop();
	// dataStack.push(o2);
	// dataStack.push(o1);
	// dataStack.push(o4);
	// dataStack.push(o3);
	// break;
	// case DROP: // ( n -- )
	// dataStack.pop();
	// break;

}
