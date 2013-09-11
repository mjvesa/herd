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
package com.github.mjvesa.herd;

import java.io.Serializable;

/*
 * Parser that reads input word or character at a time. Has special handling
 * for strings and for definitions.
 * 
 */
public class  Parser implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5089380317630429856L;
	private String string;
    private int pos;
    private int line;
    private int col;

    public void setString(String s) {
        string = s;
        pos = 0;
        line = 1;
        col = 1;
    }

    /**
     * Get the next word. Can be either a string of characters that contains no
     * whitespace or a string contained within double quotes.
     * 
     * @return The next word
     */
    public String getNextWord() {

        if (!(pos < string.length())) {
            return "";
        }

        StringBuilder word = new StringBuilder();

        // This is the only place there should be newlines
        while (pos < string.length()
                && Character.isWhitespace(string.charAt(pos))) {
            if (string.charAt(pos) == '\n') {
                line++;
                col = 1;
            }
            pos++;
            col++;
        }

        if (!(pos < string.length())) {
            return "";
        }

        if (string.charAt(pos) == '"') {
            do {
                word.append(string.charAt(pos));
                pos++;
            } while (pos < string.length() && string.charAt(pos) != '"');
            word.append('"');
            pos++;
            col++;
        } else {
            while (pos < string.length()
                    && !Character.isWhitespace(string.charAt(pos))) {
                word.append(string.charAt(pos));
                pos++;
                col++;
            }
        }

        return word.toString();
    }

    public String getString() {
        return string;
    }

    public int getLine() {
        return line;
    }

    public int getCol() {
        return line;
    }

    public String getPosition() {
        return "line " + line + " column " + col;
    }
}
