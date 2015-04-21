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

public class HeapWordSet extends WordSet {

    @Override
    public Word[] getWords() {
        return new Word[] {

        new BaseWord("!", "STORE", Word.POSTPONED) {
            private static final long serialVersionUID = 862230778092694578L;

            @Override
            public void execute(Interpreter interpreter) {
                Integer address = (Integer) interpreter.popData();
                Object value = interpreter.popData();
                interpreter.poke(address, value);
            }
        },

        new BaseWord("@", "LOAD", Word.POSTPONED) {
            private static final long serialVersionUID = -3546157881639146300L;

            @Override
            public void execute(Interpreter interpreter) {
                Integer address = (Integer) interpreter.popData();
                interpreter.pushData(interpreter.peek(address));
            }
        }

        };
    }
}
