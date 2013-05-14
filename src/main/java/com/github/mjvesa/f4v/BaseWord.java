package com.github.mjvesa.f4v;

/**
 * BaseWord is an atomic word that is installed into the interpreter state.
 * 
 * @author mjvesa@vaadin.com
 * 
 */
public abstract class BaseWord extends Word {

	public BaseWord(String name, String description, boolean immediate) {
		this.name = name;
		this.description = description;
		this.immediate = immediate;
	}
}
