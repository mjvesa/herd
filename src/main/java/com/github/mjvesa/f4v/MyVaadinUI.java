package com.github.mjvesa.f4v;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class MyVaadinUI extends UI
{

    @Override
    protected void init(VaadinRequest request) {
        ForthIDE ide = new ForthIDE();
        VerticalLayout vl = new VerticalLayout();
        vl.addComponent(ide);
        vl.setSizeFull();
        setContent(vl);
    }

}
