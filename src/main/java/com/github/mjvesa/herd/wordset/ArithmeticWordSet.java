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

public class ArithmeticWordSet extends WordSet {

    @Override
    public Word[] getWords() {
        return new Word[] {

                new BaseWord("+", "( n1 n2 -- n1 + n2 ) Adds TOS to NOS",
                        Word.POSTPONED) {

                    /**
							 * 
							 */
                    private static final long serialVersionUID = 7532548718246139262L;

                    @Override
                    public void execute(Interpreter interpreter) {
                        Integer a = (Integer) interpreter.popData();
                        Integer b = (Integer) interpreter.popData();
                        interpreter.pushData(a + b);
                    }
                },

                new BaseWord("-",
                        "( n1 n2 -- n1 - n2 ) Substracts TOS from NOS",
                        Word.POSTPONED) {

                    /**
							 * 
							 */
                    private static final long serialVersionUID = -3716555841893556413L;

                    @Override
                    public void execute(Interpreter interpreter) {
                        Integer a = (Integer) interpreter.popData();
                        Integer b = (Integer) interpreter.popData();
                        interpreter.pushData(b - a);
                    }
                },

                new BaseWord("*",
                        "( n1 n2 -- n1 * n2 ) Multiplies TOS with NOS",
                        Word.POSTPONED) {

                    /**
							 * 
							 */
                    private static final long serialVersionUID = 3043022843877114055L;

                    @Override
                    public void execute(Interpreter interpreter) {
                        Integer a = (Integer) interpreter.popData();
                        Integer b = (Integer) interpreter.popData();
                        interpreter.pushData(a * b);
                    }
                },

                new BaseWord("/", "( n1  n2 -- n1 / n2 ) Divides NOS by TOS",
                        Word.POSTPONED) {

                    /**
							 * 
							 */
                    private static final long serialVersionUID = 3806523703688323839L;

                    @Override
                    public void execute(Interpreter interpreter) {
                        Integer a = (Integer) interpreter.popData();
                        Integer b = (Integer) interpreter.popData();
                        interpreter.pushData(b / a);
                    }
                } };
    }
}
