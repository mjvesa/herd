package com.github.mjvesa.f4v;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;

public interface View {

	
	public void print(String msg);

	public UI getUI();

	public void addNewWord(String word);

	public ComponentContainer getMainComponentContainer();

	public void disableContinueButton();

	public void enableContinueButton();

}
