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

	public CompiledWord(Word word) {
		this.word = word;
		this.parameter = null;
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
		word.execute(interpreter);

	}

	public boolean isImmediate() {
		return word.isImmediate();
	}

	public void setImmediate(boolean immediate) {
		word.setImmediate(immediate);
	}

	public String getName() {
		return word.getName();
	}

	public void setName(String name) {
		word.setName(name);
	}

	public String getDescription() {
		return word.getDescription();
	}

	public void setDescription(String description) {
		word.setDescription(description);
	}

}
