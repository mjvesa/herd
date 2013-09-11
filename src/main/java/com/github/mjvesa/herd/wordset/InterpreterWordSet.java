/*
 *  Copyright 2012 Matti Vesa
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you
 *  may not use this file except in compliance with the License. You may
 *  obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package com.github.mjvesa.herd.wordset;

import com.github.mjvesa.herd.BaseWord;
import com.github.mjvesa.herd.CompiledWord;
import com.github.mjvesa.herd.Interpreter;
import com.github.mjvesa.herd.Word;

public class InterpreterWordSet extends WordSet {

	@Override
	public Word[] getWords() {
		return new Word[] {

				new BaseWord("[", "BEGININTERPRET", Word.IMMEDIATE) {

					private static final long serialVersionUID = 1272939771583867641L;

					@Override
					public void execute(Interpreter interpreter) {
						interpreter.setCompiling(false);
					}
				},

				new BaseWord("]", "ENDINTERPRET", Word.IMMEDIATE) {

					private static final long serialVersionUID = -8143466942808300858L;

					@Override
					public void execute(Interpreter interpreter) {
						interpreter.setCompiling(true);
					}
				},

				new BaseWord(
						"'",
						"Resolves the next word in the stream to a word in the dictionary",
						Word.POSTPONED) {

					private static final long serialVersionUID = -7445999245783149399L;

					@Override
					public void execute(Interpreter interpreter) {
						interpreter.pushData(interpreter.getNextWord());
					}
				},

				new BaseWord(
						"[']",
						"Resolves the next word in the stream to a word in the dictionary. Immediate version of ' (TICK)",
						Word.IMMEDIATE) {

					private static final long serialVersionUID = 1419283126594784727L;

					@Override
					public void execute(Interpreter interpreter) {
						interpreter.pushData(interpreter.getNextWord());
					}
				},

				new BaseWord("find",
						"Resolves the word defined by the string at TOS",
						Word.POSTPONED) {

					private static final long serialVersionUID = 3375284831658980419L;

					@Override
					public void execute(Interpreter interpreter) {
						String s = (String) interpreter.popData();
						Word word = interpreter.getDictionary().get(s);
						interpreter.pushData(word);
					}
				},

				new BaseWord("execute", "Executes the word at TOS",
						Word.POSTPONED) {

					private static final long serialVersionUID = 2273176615613845378L;

					@Override
					public void execute(Interpreter interpreter) {
						Word word = (Word) interpreter.popData();
						interpreter.execute(word);
					}
				},

				new BaseWord("word", "Parses the next word in the stream",
						Word.POSTPONED) {

					private static final long serialVersionUID = -8167133157280043136L;

					@Override
					public void execute(Interpreter interpreter) {
						String s = interpreter.getParser().getNextWord();
						interpreter.pushData(s);
					}
				},

				new BaseWord("create", "", Word.POSTPONED) {

					private static final long serialVersionUID = 5815660181444927938L;

					@Override
					public void execute(Interpreter interpreter) {
						interpreter.create();
					}
				},

				new BaseWord("stack-create", "", Word.POSTPONED) {

					private static final long serialVersionUID = -5766347393995029396L;

					@Override
					public void execute(Interpreter interpreter) {
						interpreter.createFromStack();
					}
				},

				new BaseWord("does>", "", Word.POSTPONED) {

					private static final long serialVersionUID = 4642119217442404327L;

					@Override
					public void execute(Interpreter interpreter) {
						// Find where DOES> is
						int codeLength = interpreter.getCodeLength();
						int i = codeLength - 1;
						while (!"does>".equals(interpreter.peekCode(i).getName())) {
							i--;
						}
						i++; // We don't want to copy DOES> now do we
						// Copy words over
						for (; i < codeLength; i++) {
							interpreter.addToCurrentDefinition(interpreter.peekCode(i));
						}
						interpreter.finishCompilation();
						interpreter.setIp(codeLength); // Don't execute stuff
														// after
														// DOES>
					}
				},

				new BaseWord("immediate",
						"Marks the current definition as immediate",
						Word.POSTPONED) {

					private static final long serialVersionUID = -6550448846189084427L;

					@Override
					public void execute(Interpreter interpreter) {
						interpreter.getCurrentDefinition().setImmediate(true);
					}
				},

				new BaseWord(
						"compile",
						"When executed, adds the compiled word at TOS to the current definition",
						Word.POSTPONED) {

					private static final long serialVersionUID = -5521906716269728024L;

					@Override
					public void execute(Interpreter interpreter) {
						interpreter
								.addToCurrentDefinition((CompiledWord) interpreter
										.popData());
					}
				},

				new BaseWord(":", "Creates a new definition", Word.IMMEDIATE) {

					private static final long serialVersionUID = -6005418447489241645L;

					@Override
					public void execute(Interpreter interpreter) {
						interpreter.create();
					}
				},

				new BaseWord(
						"anon-create",
						"Creates a definition without a name, an anonymous definition",
						Word.POSTPONED) {
					private static final long serialVersionUID = -7773886489827608666L;

					@Override
					public void execute(Interpreter interpreter) {
						interpreter.anonCreate();
					}
				},

				new BaseWord(";", "Finish compilation", Word.IMMEDIATE) {

					private static final long serialVersionUID = 5489762761234465242L;

					@Override
					public void execute(Interpreter interpreter) {
						interpreter.finishCompilation();
					}
				},

				new BaseWord("xt?", "", Word.POSTPONED) {

					private static final long serialVersionUID = 1570459301027566588L;

					@Override
					public void execute(Interpreter interpreter) {
						interpreter
								.pushData(interpreter.popData() instanceof Word);
					}
				},

				new BaseWord(
						",",
						"Generates a literal word into the current definition from a value at TOS",
						Word.POSTPONED) {

					private static final long serialVersionUID = 4971321412813826616L;

					@Override
					public void execute(Interpreter interpreter) {
						interpreter.generateLiteral(interpreter.popData());
					}
				},
				
				new BaseWord(
						"require",
						"Executes the file defined by the next word in the stream",
						Word.POSTPONED) {

					private static final long serialVersionUID = 5575355458227239352L;

					@Override
					public void execute(Interpreter interpreter) {
						String fileName = interpreter.getParser().getNextWord();
						String source = interpreter.getSource(fileName);
						interpreter.interpret(source);
					}
				} };

	}
}
