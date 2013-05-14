package com.github.mjvesa.f4v.wordset;

import com.github.mjvesa.f4v.BaseWord;
import com.github.mjvesa.f4v.Interpreter;
import com.github.mjvesa.f4v.Word;

public class HeapWordSet extends WordSet {

	protected Word[] words = {

	new BaseWord("", "") {
		@Override
		public void execute(Interpreter interpreter) {
		}
	},

			// case STORE:
			// o1 = dataStack.pop();
			// address = (Integer) o1;
			// value = dataStack.pop();
			// heap[address] = value;
			// break;

			new BaseWord("", "") {
				@Override
				public void execute(Interpreter interpreter) {
				}
			}
	// case LOAD:
	// address = (Integer) dataStack.pop();
	// dataStack.push(heap[address]);
	// break;

	};

}
