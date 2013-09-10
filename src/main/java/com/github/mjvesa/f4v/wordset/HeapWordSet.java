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

public class HeapWordSet extends WordSet {

	@Override
	public Word[] getWords() {
		return new Word[] {

		new BaseWord("!", "STORE", Word.POSTPONED) {
			@Override
			public void execute(Interpreter interpreter) {
				Integer address = (Integer) interpreter.popData();
				Object value = interpreter.popData();
				interpreter.poke(address, value);
			}
		},

		new BaseWord("@", "LOAD", Word.POSTPONED) {
			@Override
			public void execute(Interpreter interpreter) {
				Integer address = (Integer) interpreter.popData();
				interpreter.pushData(interpreter.peek(address));
			}
		}

		};
	}
}
