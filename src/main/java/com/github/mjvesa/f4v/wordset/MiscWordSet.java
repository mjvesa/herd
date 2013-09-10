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
package com.github.mjvesa.f4v.wordset;

import com.github.mjvesa.f4v.BaseWord;
import com.github.mjvesa.f4v.Interpreter;
import com.github.mjvesa.f4v.Parser;
import com.github.mjvesa.f4v.Util;
import com.github.mjvesa.f4v.Word;

public class MiscWordSet extends WordSet {

	@Override
	public Word[] getWords() {
		return new Word[] {

				new BaseWord(
						"literal",
						"Literal, which simply pushes its parameter field onto the data stack.",
						Word.POSTPONED) {
					private static final long serialVersionUID = -5770701787042227684L;

					@Override
					public void execute(Interpreter interpreter) {
						interpreter.pushData(interpreter.getCurrentParam());
					}
				},

				new BaseWord("print", "", Word.POSTPONED) {
					private static final long serialVersionUID = 7714891178820050279L;

					@Override
					public void execute(Interpreter interpreter) {
						interpreter.print(interpreter.popData().toString());
					}
				},

				new BaseWord("(", "Begins a comment", Word.IMMEDIATE) {
					private static final long serialVersionUID = 4249876991114154308L;

					@Override
					public void execute(Interpreter interpreter) {
						Parser parser = interpreter.getParser();
						String str = parser.getNextWord();
						while (!")".equals(str)) {
							str = parser.getNextWord();
						}
					}
				},

				new BaseWord("null", "Pushes null onto the stack",
						Word.POSTPONED) {
					private static final long serialVersionUID = 9124998901423079266L;

					@Override
					public void execute(Interpreter interpreter) {
						interpreter.pushData(Util.NULL_OBJECT);
					}
				},

				new BaseWord("equals", "Checks the equality of two objects",
						Word.POSTPONED) {
					private static final long serialVersionUID = -7765035051312744965L;

					@Override
					public void execute(Interpreter interpreter) {
						Object o1 = interpreter.popData();
						Object o2 = interpreter.popData();
						interpreter.pushData(o1.equals(o2));

					}
				},

				new BaseWord("list-terminator", "", Word.POSTPONED) {
					private static final long serialVersionUID = -6618751074624107338L;

					@Override
					public void execute(Interpreter interpreter) {
						interpreter.pushData(Util.LIST_TERMINATOR);
					}
				},

				new BaseWord("print-stack", "", Word.POSTPONED) {
					private static final long serialVersionUID = 1702161052631113819L;

					@Override
					public void execute(Interpreter interpreter) {
						interpreter.printStack();
					}
				}, new BaseWord("log", "", Word.POSTPONED) {
					private static final long serialVersionUID = 5881495156097324769L;

					@Override
					public void execute(Interpreter interpreter) {
						interpreter.print((String) interpreter.popData());
					}
				} };

	}
}
