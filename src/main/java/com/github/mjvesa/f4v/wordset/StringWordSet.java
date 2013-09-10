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
import com.github.mjvesa.f4v.Word;

public class StringWordSet extends WordSet {

	@Override
	public Word[] getWords() {
		return new Word[] {

		new BaseWord("str-to-int", "", Word.POSTPONED) {

			private static final long serialVersionUID = -5368861544287003785L;

			@Override
			public void execute(Interpreter interpreter) {
				String str = interpreter.popData().toString();
				interpreter.pushData(str.isEmpty() ? 0 : Integer.parseInt(str));
			}
		},

		new BaseWord("to-str", "", Word.POSTPONED) {

			private static final long serialVersionUID = -2488778642720482057L;

			@Override
			public void execute(Interpreter interpreter) {
				interpreter.pushData(interpreter.popData().toString());
			}
		},

		new BaseWord("cat", "", Word.POSTPONED) {

			private static final long serialVersionUID = 6048687003225519704L;

			@Override
			public void execute(Interpreter interpreter) {
				String str1 = (String) interpreter.popData();
				String str2 = (String) interpreter.popData();
				interpreter.pushData(str2 + str1);
			}
		}

		};
	}
}
