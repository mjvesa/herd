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
package com.github.mjvesa.f4v;

/*
 * Forth Words. These can be built in atomic instructions or complex defined
 * words.
 */
public class DefinedWord extends Word {

	private String name;
	private Word[] code;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Word[] getCode() {
		return code;
	}

	public void setCode(Word[] code) {
		this.code = code;
	}

	public String toString() {
		return name;
	}

	@Override
	public void execute(Interpreter interpreter) {
		// TODO Auto-generated method stub

	}

}
