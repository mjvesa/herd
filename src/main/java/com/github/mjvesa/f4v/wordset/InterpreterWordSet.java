package com.github.mjvesa.f4v.wordset;

import com.github.mjvesa.f4v.BaseWord;
import com.github.mjvesa.f4v.CompiledWord;
import com.github.mjvesa.f4v.DefinedWord;
import com.github.mjvesa.f4v.Interpreter;
import com.github.mjvesa.f4v.Word;

public class InterpreterWordSet extends WordSet {

	protected Word[] words = {

			new BaseWord("[", "BEGININTERPRET", Word.POSTPONED) {

				@Override
				public void execute(Interpreter interpreter) {
					interpreter.setCompiling(false);
				}
			},

			new BaseWord("]", "ENDINTERPRET", Word.POSTPONED) {

				@Override
				public void execute(Interpreter interpreter) {
					interpreter.setCompiling(true);
				}
			},

			new BaseWord(
					"'",
					"Resolves the next word in the stream to a word in the dictionary",
					Word.POSTPONED) {

				@Override
				public void execute(Interpreter interpreter) {
					String s = interpreter.getParser().getNextWord();
					Word word = interpreter.getDictionary().get(s);
					interpreter.pushData(word);
				}
			},

			new BaseWord(
					"[']",
					"Resolves the next word in the stream to a word in the dictionary. Immediate version of ' (TICK)",
					Word.IMMEDIATE) {

				@Override
				public void execute(Interpreter interpreter) {
					String s = interpreter.getParser().getNextWord();
					Word word = interpreter.getDictionary().get(s);
					interpreter.pushData(word);
				}
			},

			new BaseWord("FIND",
					"Resolves the word defined by the string at TOS",
					Word.POSTPONED) {

				@Override
				public void execute(Interpreter interpreter) {
					String s = (String) interpreter.popData();
					Word word = interpreter.getDictionary().get(s);
					interpreter.pushData(word);
				}
			},

			new BaseWord("EXECUTE", "Executes the word at TOS", Word.POSTPONED) {

				@Override
				public void execute(Interpreter interpreter) {
					Word word = (Word) interpreter.popData();
					interpreter.execute(word);
				}
			},

			new BaseWord("WORD", "Parses the next word in the stream",
					Word.POSTPONED) {

				@Override
				public void execute(Interpreter interpreter) {
					String s = interpreter.getParser().getNextWord();
					interpreter.pushData(s);
				}
			},

			new BaseWord("CREATE", "", Word.POSTPONED) {

				@Override
				public void execute(Interpreter interpreter) {
					interpreter.create();
				}
			},

			new BaseWord("STACKCREATE", "", Word.POSTPONED) {

				@Override
				public void execute(Interpreter interpreter) {
					interpreter.createFromStack();
				}
			},

			new BaseWord("DOES>", "", Word.POSTPONED) {

				@Override
				public void execute(Interpreter interpreter) {
					CompiledWord[] code = interpreter.getCode();

					// Find where DOES> is
					int i = code.length - 1;
					while (!"DOES>".equals(code[i].getName())) {
						i--;
					}
					i++; // We don't want to copy DOES> now do we
					// Copy words over TODO use array stuff for this?
					for (; i < code.length; i++) {
						interpreter.addToCurrentDefinition(code[i]);
					}
					interpreter.finishCompilation();
					interpreter.setIp(code.length); // Don't execute stuff after
													// DOES>
				}
			},

			new BaseWord("IMMEDIATE",
					"Marks the current definition as immediate", Word.POSTPONED) {

				@Override
				public void execute(Interpreter interpreter) {
					interpreter.getCurrentDefinition().setImmediate(true);
				}
			},

			new BaseWord(
					"COMPILE",
					"When executed, adds the compiled word at TOS to the current definition",
					Word.POSTPONED) {

				@Override
				public void execute(Interpreter interpreter) {
					interpreter
							.addToCurrentDefinition((CompiledWord) interpreter
									.popData());
				}
			},

			new BaseWord(":", "Creates a new definition", Word.POSTPONED) {

				@Override
				public void execute(Interpreter interpreter) {
					interpreter.create();
				}
			},

			new BaseWord(
					"ANONCREATE",
					"Creates a definition without a name, an anonymous definition",
					Word.POSTPONED) {
				@Override
				public void execute(Interpreter interpreter) {
					interpreter.anonCreate();
				}
			},

			new BaseWord(";", "Finished compilation", Word.POSTPONED) {

				@Override
				public void execute(Interpreter interpreter) {
					interpreter.finishCompilation();
				}
			},

			new BaseWord("ISXT", "", Word.POSTPONED) {

				@Override
				public void execute(Interpreter interpreter) {
					interpreter
							.pushData(interpreter.popData() instanceof DefinedWord);
				}
			},

			new BaseWord(
					",",
					"Generates a literal word into the current definition from a value at TOS",
					Word.POSTPONED) {

				@Override
				public void execute(Interpreter interpreter) {
					interpreter.generateLiteral(interpreter.popData());
				}
			} };

}
