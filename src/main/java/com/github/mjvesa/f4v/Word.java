package com.github.mjvesa.f4v;

/**
 * Interface for all kinds of Words
 * 
 * @author mjvesa@vaadin.com
 * 
 */
public abstract class Word {

	protected String name;

	protected String description;

	private boolean immediate;

	private Object param;

	public abstract void execute(Interpreter interpreter);

	public boolean isImmediate() {
		return immediate;
	}

	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param;
	}

}
