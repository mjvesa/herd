package com.github.mjvesa.herd;

/**
 * BaseWord is an atomic word that is installed into the interpreter state.
 * 
 * @author mjvesa@vaadin.com
 * 
 */
public abstract class BaseWord extends Word {

	/**
	 * 
	 */
	private static final long serialVersionUID = -859780963869165962L;

	public BaseWord(String name, String description, boolean immediate) {
		this.name = name;
		this.description = description;
		this.immediate = immediate;
	}
}
