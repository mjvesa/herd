package com.github.mjvesa.f4v;

import java.io.Serializable;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;

public interface View extends Serializable{

	
	public void print(String msg);

	public UI getUI();

	public void addNewWord(String word);

	public ComponentContainer getMainComponentContainer();
}
