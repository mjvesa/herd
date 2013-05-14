package com.github.mjvesa.f4v;

/**
 * BaseWord is an atomic word that is installed into the interpreter state. 
 * 
 * @author mjvesa@vaadin.com
 *
 */
public interface BaseWord extends Word {
	
	public abstract void execute(State state);

}
