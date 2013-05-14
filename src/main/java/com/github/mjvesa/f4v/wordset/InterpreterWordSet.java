package com.github.mjvesa.f4v.wordset;

import com.github.mjvesa.f4v.BaseWord;
import com.github.mjvesa.f4v.Interpreter;
import com.github.mjvesa.f4v.Word;

public class InterpreterWordSet extends WordSet {

	protected Word[] words = {

	new BaseWord("", "") {

		@Override
		public void execute(Interpreter interpreter) {
			// TODO Auto-generated method stub

		}
	}

	};

	// case BEGININTERPRET:
	// isCompiling = false;
	// break;
	// case ENDINTERPRET:
	// isCompiling = true;
	// break;
	// case STRTOINT:
	// str = dataStack.pop().toString();
	// dataStack.push(str.isEmpty() ? 0 : Integer.parseInt(str));
	// break;
	// case INTTOSTR:
	// a = (Integer) dataStack.pop();
	// dataStack.push(a.toString());
	// break;
	// case TICK: // ( str -- xt )
	// // wrd = dictionary.get(parser.getNextWord());
	// str = (String) dataStack.pop();
	// wrd = dictionary.get(str);
	// dataStack.push(wrd);
	// break;
	// case BRACKETTICK:
	// str = (String) dataStack.pop();
	// wrd = dictionary.get(str);
	// currentDefinitionWords.add(wrd);
	// break;
	// case FIND: // ( str -- xt )
	// str = (String) dataStack.pop();
	// wrd = dictionary.get(str);
	// if (wrd == null) {
	// print("FIND did not find word " + str);
	// }
	// dataStack.push(wrd);
	// break;
	// case EXECUTE:
	// wrd = (DefinedWord) dataStack.pop();
	// returnStack.push(ip);
	// codeStack.push(this.code);
	// execute(wrd);
	// ip = returnStack.pop();
	// this.code = codeStack.pop();
	// break;
	// case WORD:
	// dataStack.push(parser.getNextWord());
	// break;

	// case CREATE:
	// create();
	// break;
	// case STACKCREATE:
	// createFromStack();
	// break;
	// case CREATENOP:
	// DefinedWord nop = new DefinedWord();
	// nop.setType(DefinedWord.Type.NOP);
	// String name = parser.getNextWord();
	// dictionary.put(name, nop);
	// guiEventListener.newWord(name);
	// break;
	// case DOES:
	// code = this.code;
	//
	// // Find where DOES> is
	// int i = code.length - 1;
	// while (code[i].getBaseWord() != BaseWord.DOES) {
	// i--;
	// }
	// i++; // We don't want to copy DOES> now do we
	// // Copy words over TODO use array stuff for this?
	// for (; i < code.length; i++) {
	// currentDefinitionWords.add(code[i]);
	// }
	// finishCompilation();
	// ip = code.length; // Don't execute stuff after DOES>
	// break;
	// case IMMEDIATE:
	// currentDefinition.setImmediate(true);
	// break;
	// case COMPILE:
	// wrd = getNextExecutableWord();
	// currentDefinitionWords.add(wrd);
	// break;
	// case COLONCREATE:
	// create();
	// break;
	// case ANONCREATE:
	// anonCreate();
	// break;
	// case FINISHCOMPILATION:
	// finishCompilation();
	// break;
	// case ISXT: // ( XT? -- Boolean )
	// dataStack.push(dataStack.pop() instanceof DefinedWord);
	// break;
	// case WORDS:
	// printWords();
	// break;

	// case GENLITERAL:
	// generateLiteral(dataStack.pop());
	// break;

}
