package com.github.mjvesa.f4v;

/**
 * Interface for all kinds of Words
 * 
 * @author mjvesa@vaadin.com
 * 
 */
public abstract class Word {

	private boolean immediate;

	public abstract String getName();

	public abstract String getDescription();

	public abstract void execute(State state);

	public boolean isImmediate() {
		return immediate;
	}

	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
	}

}
