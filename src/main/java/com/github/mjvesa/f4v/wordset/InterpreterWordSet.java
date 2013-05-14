package com.github.mjvesa.f4v.wordset;

import com.github.mjvesa.f4v.BaseWord;
import com.github.mjvesa.f4v.Interpreter;
import com.github.mjvesa.f4v.Word;

public class InterpreterWordSet extends WordSet {

	protected Word[] words = {

	new BaseWord("", "") {

		@Override
		public void execute(Interpreter interpreter) {
		}
	},

			// case BEGININTERPRET:
			// isCompiling = false;
			// break;

			new BaseWord("", "") {

				@Override
				public void execute(Interpreter interpreter) {
				}
			},
			// case ENDINTERPRET:
			// isCompiling = true;
			// break;

			new BaseWord("", "") {

				@Override
				public void execute(Interpreter interpreter) {
				}
			},
			// case STRTOINT:
			// str = dataStack.pop().toString();
			// dataStack.push(str.isEmpty() ? 0 : Integer.parseInt(str));
			// break;

			new BaseWord("", "") {

				@Override
				public void execute(Interpreter interpreter) {
				}
			},
			// case INTTOSTR:
			// a = (Integer) dataStack.pop();
			// dataStack.push(a.toString());
			// break;

			new BaseWord("", "") {

				@Override
				public void execute(Interpreter interpreter) {
				}
			},
			// case TICK: // ( str -- xt )
			// // wrd = dictionary.get(parser.getNextWord());
			// str = (String) dataStack.pop();
			// wrd = dictionary.get(str);
			// dataStack.push(wrd);
			// break;

			new BaseWord("", "") {

				@Override
				public void execute(Interpreter interpreter) {
				}
			},
			// case BRACKETTICK:
			// str = (String) dataStack.pop();
			// wrd = dictionary.get(str);
			// currentDefinitionWords.add(wrd);
			// break;

			new BaseWord("", "") {

				@Override
				public void execute(Interpreter interpreter) {
				}
			},
			// case FIND: // ( str -- xt )
			// str = (String) dataStack.pop();
			// wrd = dictionary.get(str);
			// if (wrd == null) {
			// print("FIND did not find word " + str);
			// }
			// dataStack.push(wrd);
			// break;

			new BaseWord("", "") {

				@Override
				public void execute(Interpreter interpreter) {
				}
			},
			// case EXECUTE:
			// wrd = (DefinedWord) dataStack.pop();
			// returnStack.push(ip);
			// codeStack.push(this.code);
			// execute(wrd);
			// ip = returnStack.pop();
			// this.code = codeStack.pop();
			// break;

			new BaseWord("", "") {

				@Override
				public void execute(Interpreter interpreter) {
				}
			},
			// case WORD:
			// dataStack.push(parser.getNextWord());
			// break;

			new BaseWord("", "") {

				@Override
				public void execute(Interpreter interpreter) {
				}
			},
			// case CREATE:
			// create();
			// break;

			new BaseWord("", "") {

				@Override
				public void execute(Interpreter interpreter) {
				}
			},
			// case STACKCREATE:
			// createFromStack();
			// break;

			new BaseWord("", "") {

				@Override
				public void execute(Interpreter interpreter) {
				}
			},
			// case CREATENOP:
			// DefinedWord nop = new DefinedWord();
			// nop.setType(DefinedWord.Type.NOP);
			// String name = parser.getNextWord();
			// dictionary.put(name, nop);
			// guiEventListener.newWord(name);
			// break;

			new BaseWord("", "") {

				@Override
				public void execute(Interpreter interpreter) {
				}
			},
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

			new BaseWord("", "") {

				@Override
				public void execute(Interpreter interpreter) {
				}
			},
			// case IMMEDIATE:
			// currentDefinition.setImmediate(true);
			// break;

			new BaseWord("", "") {

				@Override
				public void execute(Interpreter interpreter) {
				}
			},
			// case COMPILE:
			// wrd = getNextExecutableWord();
			// currentDefinitionWords.add(wrd);
			// break;

			new BaseWord("", "") {

				@Override
				public void execute(Interpreter interpreter) {
				}
			},
			// case COLONCREATE:
			// create();
			// break;

			new BaseWord("", "") {

				@Override
				public void execute(Interpreter interpreter) {
				}
			},
			// case ANONCREATE:
			// anonCreate();
			// break;

			new BaseWord("", "") {

				@Override
				public void execute(Interpreter interpreter) {
				}
			},
			// case FINISHCOMPILATION:
			// finishCompilation();
			// break;

			new BaseWord("", "") {

				@Override
				public void execute(Interpreter interpreter) {
				}
			},
			// case ISXT: // ( XT? -- Boolean )
			// dataStack.push(dataStack.pop() instanceof DefinedWord);
			// break;

			new BaseWord("", "") {

				@Override
				public void execute(Interpreter interpreter) {
				}
			},
			// case WORDS:
			// printWords();
			// break;

			new BaseWord("", "") {

				@Override
				public void execute(Interpreter interpreter) {
				}
			}
	// case GENLITERAL:
	// generateLiteral(dataStack.pop());
	// break;
	};

}
