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
import com.github.mjvesa.herd.Interpreter;
import com.github.mjvesa.herd.Word;

public class StackWordSet extends WordSet {

	@Override
	public Word[] getWords() {
		return new Word[] {

		new BaseWord("dup", "( a -- a a )", Word.POSTPONED) {
			private static final long serialVersionUID = 7581220925840087455L;

			@Override
			public void execute(Interpreter interpreter) {
				Object o = interpreter.peekData();
				interpreter.pushData(o);
			}
		},

		new BaseWord("over", "( a b -- a b a )", Word.POSTPONED) {
			private static final long serialVersionUID = 3356922048167743848L;

			@Override
			public void execute(Interpreter interpreter) {
				Object o1 = interpreter.popData();
				Object o2 = interpreter.popData();
				interpreter.pushData(o2);
				interpreter.pushData(o1);
				interpreter.pushData(o2);
			}
		},

		new BaseWord("rot", "( a b c -- b c a )", Word.POSTPONED) {
			private static final long serialVersionUID = -5794391128365800716L;

			@Override
			public void execute(Interpreter interpreter) {
				Object o1 = interpreter.popData();
				Object o2 = interpreter.popData();
				Object o3 = interpreter.popData();
				interpreter.pushData(o2);
				interpreter.pushData(o1);
				interpreter.pushData(o3);
			}
		},

		new BaseWord("-rot", "( a b c -- c a b )", Word.POSTPONED) {
			private static final long serialVersionUID = 1977717043072701579L;

			@Override
			public void execute(Interpreter interpreter) {
				Object o1 = interpreter.popData();
				Object o2 = interpreter.popData();
				Object o3 = interpreter.popData();
				interpreter.pushData(o1);
				interpreter.pushData(o3);
				interpreter.pushData(o2);
			}
		},

		new BaseWord("nip", "( a b -- b )", Word.POSTPONED) {
			private static final long serialVersionUID = -4453916323211210875L;

			@Override
			public void execute(Interpreter interpreter) {
				Object o = interpreter.popData();
				interpreter.popData();
				interpreter.pushData(o);
			}
		},

		new BaseWord("tuck", "( a b -- b a b )", Word.POSTPONED) {
			private static final long serialVersionUID = -8145371490716158988L;

			@Override
			public void execute(Interpreter interpreter) {
				Object o1 = interpreter.popData();
				Object o2 = interpreter.popData();
				interpreter.pushData(o1);
				interpreter.pushData(o2);
				interpreter.pushData(o1);

			}
		},

		new BaseWord("swap", "( a b -- b a )", Word.POSTPONED) {
			private static final long serialVersionUID = 2863339348428400228L;

			@Override
			public void execute(Interpreter interpreter) {
				Object o1 = interpreter.popData();
				Object o2 = interpreter.popData();
				interpreter.pushData(o1);
				interpreter.pushData(o2);
			}
		},

		new BaseWord("2swap", "( a b c d -- c d a b)", Word.POSTPONED) {
			private static final long serialVersionUID = -7371046091113465540L;

			@Override
			public void execute(Interpreter interpreter) {
				Object o1 = interpreter.popData();
				Object o2 = interpreter.popData();
				Object o3 = interpreter.popData();
				Object o4 = interpreter.popData();
				interpreter.pushData(o2);
				interpreter.pushData(o1);
				interpreter.pushData(o4);
				interpreter.pushData(o3);

			}
		},

		new BaseWord("drop", "", Word.POSTPONED) {
			private static final long serialVersionUID = 1207531900617799757L;

			@Override
			public void execute(Interpreter interpreter) {
				interpreter.popData();
			}
		}

		};

	}

}
