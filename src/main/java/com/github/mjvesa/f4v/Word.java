package com.github.mjvesa.f4v;

import java.io.Serializable;

/**
 * Interface for all kinds of Words
 * 
 * @author mjvesa@vaadin.com
 * 
 */
public abstract class Word implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8847395418769164593L;
	public static final boolean IMMEDIATE = true;
	public static final boolean POSTPONED = false;

	protected String name;

	protected String description;

	protected boolean immediate;

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
}
