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

public class FlowControlWordSet extends WordSet {

	@Override
	public Word[] getWords() {
		return new Word[] {

				new BaseWord(
						"do",
						"Beginning of DO loop. (n1 n2 -- ) Expects begin and end counter values to be at TOS and NOS",
						Word.POSTPONED) {
							private static final long serialVersionUID = 725553921951475444L;

					@Override
					public void execute(Interpreter interpreter) {
						interpreter.pushReturn(interpreter.getIp());
					}
				},

				new BaseWord("loop", "", Word.POSTPONED) {
					private static final long serialVersionUID = 1257742702701648281L;
					@Override
					public void execute(Interpreter interpreter) {
						Integer a = (Integer) interpreter.popData();
						a++;
						Integer b = (Integer) interpreter.popData();
						if (a < b) {
							Integer ip = interpreter.popReturn();
							interpreter.pushReturn(ip);
							interpreter.pushData(b);
							interpreter.pushData(a);
						} else {
							interpreter.popReturn();
						}
					}
				},

				new BaseWord("if", "", Word.POSTPONED) {
					private static final long serialVersionUID = 5644346296894935281L;
					@Override
					public void execute(Interpreter interpreter) {
						Boolean bool = (Boolean) interpreter.popData();
						// If false, skip to endif
						if (!bool) {
							interpreter.setIp(interpreter.getIp()
									+ (Integer) interpreter.getCurrentParam());
						}
					}
				},

				new BaseWord("else", "", Word.POSTPONED) {
					private static final long serialVersionUID = -3423294489661624665L;
					@Override
					public void execute(Interpreter interpreter) {
						interpreter.setIp(interpreter.getIp()
								+ (Integer) interpreter.getCurrentParam());
					}
				},

				new BaseWord("endif", "", Word.IMMEDIATE) {
					private static final long serialVersionUID = -2009086460982378869L;

					@Override
					public void execute(Interpreter interpreter) {
						// Find latest IF
						// set current address minus one as parameter
						int i = interpreter.getCurrentDefinitionSize() - 1;
						int jumpDest = i;
						while (!"if".equals(interpreter
								.getFromCurrentDefinition(i).getName())
								&& (i >= 0)) {
							CompiledWord word = interpreter
									.getFromCurrentDefinition(i);
							if ("else".equals(word.getName())) {
								word.setParameter(jumpDest - i);
								jumpDest = i; // IF jumps to word after else
							}
							i--;
						}
						if (i < 0) {
							throw new RuntimeException();
						}
						interpreter.getFromCurrentDefinition(i).setParameter(
								jumpDest - i);
					}
				},

				new BaseWord("begin", "", Word.POSTPONED) {
					private static final long serialVersionUID = -7691980134445081893L;

					@Override
					public void execute(Interpreter interpreter) {
						interpreter.pushReturn(interpreter.getIp());
					}
				},

				new BaseWord("while", "", Word.POSTPONED) {
					private static final long serialVersionUID = -3955102690779725873L;

					@Override
					public void execute(Interpreter interpreter) {
						Boolean bool = (Boolean) interpreter.popData();
						if (!bool) {
							int ip = interpreter.getIp();
							while (!"repeat".equals(interpreter.peekCode(ip).getName())) {
								ip++;
							}
							interpreter.setIp(ip);
							interpreter.popReturn();
						}
					}
				},

				new BaseWord("repeat", "", Word.POSTPONED) {
					private static final long serialVersionUID = -5679551839132929519L;

					@Override
					public void execute(Interpreter interpreter) {
						interpreter.setIp(interpreter.peekReturn());
					}
				},

				new BaseWord("<0", "LESSTHANZERO", Word.POSTPONED) {
					private static final long serialVersionUID = 2150333302093966728L;

					@Override
					public void execute(Interpreter interpreter) {
						interpreter.pushData((Boolean) ((Integer) interpreter
								.popData() < 0));
					}
				},

				new BaseWord("=0", "ZERO", Word.POSTPONED) {
					private static final long serialVersionUID = -2667702245481974238L;
					@Override
					public void execute(Interpreter interpreter) {
						interpreter.pushData((Boolean) ((Integer) interpreter
								.popData() == 0));

					}
				},

				new BaseWord(">0", "GREATERTHANZERO", Word.POSTPONED) {
					private static final long serialVersionUID = 1053257668863792784L;
					@Override
					public void execute(Interpreter interpreter) {
						interpreter.pushData((Boolean) ((Integer) interpreter
								.popData() > 0));
					}
				},

				new BaseWord("<", "LESSTHAN", Word.POSTPONED) {
					private static final long serialVersionUID = 8109822200287698995L;
					@Override
					public void execute(Interpreter interpreter) {
						Integer a = (Integer) interpreter.popData();
						Integer b = (Integer) interpreter.popData();
						interpreter.pushData((Boolean) (b < a));
					}
				},

				new BaseWord("=", "EQUALS", Word.POSTPONED) {

					private static final long serialVersionUID = 4583316207346834874L;

					@Override
					public void execute(Interpreter interpreter) {
						Object a = interpreter.popData();
						Object b = interpreter.popData();
						interpreter.pushData((Boolean) (b.equals(a)));
					}
				},

				new BaseWord(">", "GREATERTHAN", Word.POSTPONED) {

					private static final long serialVersionUID = -1794024576694925712L;

					@Override
					public void execute(Interpreter interpreter) {
						Integer a = (Integer) interpreter.popData();
						Integer b = (Integer) interpreter.popData();
						interpreter.pushData((Boolean) (b > a));
					}
				},

				new BaseWord("not", "NOT", Word.POSTPONED) {

					private static final long serialVersionUID = 7816554427553408531L;

					@Override
					public void execute(Interpreter interpreter) {
						Boolean b = (Boolean) interpreter.popData();
						interpreter.pushData((Boolean) !b);
					}

				}

		};

	}

}
