package com.github.mjvesa.f4v;

public class CompiledWord extends Word {

	private Word word;
	private Object parameter;

	public CompiledWord() {

	}

	public CompiledWord(Word word, Object parameter) {
		this.word = word;
		this.parameter = parameter;
	}

	public Word getWord() {
		return word;
	}

	public void setWord(Word word) {
		this.word = word;
	}

	public Object getParameter() {
		return parameter;
	}

	public void setParameter(Object parameter) {
		this.parameter = parameter;
	}

	@Override
	public void execute(Interpreter interpreter) {
		// TODO Auto-generated method stub

	}

}
