package com.github.mjvesa.f4v.wordset;

import java.util.ArrayList;

import com.github.mjvesa.f4v.BaseWord;
import com.github.mjvesa.f4v.CompiledWord;
import com.github.mjvesa.f4v.DefinedWord;
import com.github.mjvesa.f4v.Interpreter;
import com.github.mjvesa.f4v.Util;
import com.github.mjvesa.f4v.Word;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractSelect.NewItemHandler;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class VaadinWordSet extends WordSet {

	@Override
	public Word[] getWords() {
		return new Word[] {

		new BaseWord("new-button", "", Word.POSTPONED) {

			@Override
			public void execute(final Interpreter interpreter) {
				
				Button b = new Button("", new Button.ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						Button b = event.getButton();
						DefinedWord command = (DefinedWord) b.getData();
						if (command != null) {
							interpreter.execute(command);
						}
					}
				});
				interpreter.pushData(b);
			}
		},

		new BaseWord("set-click-listener", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				Object o = interpreter.popData();
				Button b = (Button) interpreter.popData();
				b.setData(o);
				interpreter.pushData(b);
			}
		},

		new BaseWord("new-hl", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				HorizontalLayout hl = new HorizontalLayout();
				hl.setSpacing(true);
				interpreter.pushData(hl);
			}
		},

		new BaseWord("new-vl", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				VerticalLayout vl = new VerticalLayout();
				vl.setSpacing(true);
				interpreter.pushData(vl);
			}
		},

		new BaseWord("new-gl", "( x y - gl )", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				Integer height = (Integer) interpreter.popData();
				Integer width = (Integer) interpreter.popData();
				interpreter.pushData(new GridLayout(width, height));
			}
		},

		new BaseWord("gl-new-line", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				((GridLayout) interpreter.peekData()).newLine();
			}
		},

		new BaseWord("new-window", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				Window w = new Window();
				((VerticalLayout) w.getContent()).setSpacing(true);
				interpreter.pushData(w);
			}
		},

		new BaseWord("main-panel", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				interpreter.pushData(interpreter.getMainPanel());
			}
		},

		new BaseWord("add-window", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				Window w = (Window) interpreter.popData();
				interpreter.getView().getUI().addWindow(w);
			}
		},

		new BaseWord("add-component", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				Component comp = (Component) interpreter.popData();
				ComponentContainer cc = (ComponentContainer) interpreter
						.popData();
				cc.addComponent(comp);
				interpreter.pushData(cc);
			}
		},

		new BaseWord("set-caption", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				String s = (String) interpreter.popData();
				Component c = (Component) interpreter.popData();
				c.setCaption(s);
				interpreter.pushData(c);
			}
		},

		new BaseWord("set-value", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				Object o = interpreter.popData();
				Field f = (Field) interpreter.popData();
				f.setValue(o);
				interpreter.pushData(f);
			}
		},

		new BaseWord("get-value", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				Field f = (Field) interpreter.popData();
				interpreter.pushData(f);
				interpreter.pushData(f.getValue());

			}
		},

		new BaseWord("set-size-full", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				Component comp = (Component) interpreter.popData();
				comp.setSizeFull();
				interpreter.pushData(comp);
			}
		},

		new BaseWord("set-size-undefined", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				Component comp = (Component) interpreter.popData();
				comp.setSizeUndefined();
				interpreter.pushData(comp);
			}
		},

		new BaseWord("set-height", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				String str = (String) interpreter.popData();
				Component comp = (Component) interpreter.popData();
				comp.setHeight(str);
				interpreter.pushData(comp);
			}
		},

		new BaseWord("set-width", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				String str = (String) interpreter.popData();
				Component comp = (Component) interpreter.popData();
				comp.setWidth(str);
				interpreter.pushData(comp);
			}
		},

		new BaseWord("clear-container", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				ComponentContainer cc = (ComponentContainer) interpreter
						.popData();
				cc.removeAllComponents();
			}
		},

		new BaseWord("new-check-box", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				interpreter.pushData(new CheckBox());
			}
		},

		new BaseWord("new-date-field", "", Word.POSTPONED) {

			@Override
			public void execute(final Interpreter interpreter) {
				interpreter.pushData(new DateField());
				final String dfCommand = (String) interpreter.popData();
				DateField df = new DateField();
				df.setImmediate(true);
				df.addValueChangeListener(new ValueChangeListener() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1472139878970514093L;

					public void valueChange(ValueChangeEvent event) {
						interpreter.pushData(event.getProperty().getValue());
						interpreter.interpret(dfCommand);
					}

				});
				interpreter.pushData(df);
			}
		},

		new BaseWord("new-label", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				interpreter.pushData(new Label());
			}
		},

		new BaseWord("new-text-field", "", Word.POSTPONED) {

			@Override
			public void execute(final Interpreter interpreter) {
				final String tfCommand = interpreter.getNextNonNopWord();
				TextField tf = new TextField();
				tf.setCaption((String) interpreter.popData());
				tf.setValue("");
				tf.setImmediate(true);
				tf.addValueChangeListener(new ValueChangeListener() {
					/**
				 *
				 */
					private static final long serialVersionUID = 4325104922208051065L;

					public void valueChange(ValueChangeEvent event) {
						interpreter.pushData(event.getProperty().getValue());
						interpreter.interpret(tfCommand);
					}
				});
				interpreter.pushData(tf);
			}
		},

		new BaseWord("new-table", "", Word.POSTPONED) {

			@Override
			public void execute(final Interpreter interpreter) {
				final String tableCommand = interpreter.getParser()
						.getNextWord();
				Table table = new Table();
				table.setCaption((String) interpreter.popData());
				table.setImmediate(true);
				table.setSelectable(true);
				table.addItemClickListener(new ItemClickListener() {

					/**
				 *
				 */
					private static final long serialVersionUID = 3585546076571010729L;

					public void itemClick(ItemClickEvent event) {

						interpreter.pushData(event.getItem());
						interpreter.execute(interpreter.getDictionary().get(
								tableCommand));
					}
				});
				interpreter.pushData(table);
			}
		},

		new BaseWord("new-combo-box", "", Word.POSTPONED) {

			@Override
			public void execute(final Interpreter interpreter) {
				final String newItemCommand = interpreter.getParser()
						.getNextWord();
				final String itemSelectedCommand = interpreter.getParser()
						.getNextWord();
				final ComboBox cb = new ComboBox();
				cb.setImmediate(true);
				String str = (String) interpreter.popData();
				cb.setNullSelectionAllowed(false);
				cb.setCaption(str);
				cb.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_ITEM);
				cb.setNewItemsAllowed(true);
				cb.setNewItemHandler(new NewItemHandler() {

					/**
				 *
				 */
					private static final long serialVersionUID = 3340658590351611289L;

					public void addNewItem(String newItemCaption) {
						cb.setImmediate(false);
						interpreter.pushData(newItemCaption);
						interpreter.interpret(newItemCommand);
						cb.setImmediate(true);
					}
				});

				cb.addValueChangeListener(new ValueChangeListener() {

					/**
				 *
				 */
					private static final long serialVersionUID = 2706579869793251379L;

					public void valueChange(ValueChangeEvent event) {
						interpreter.pushData(cb.getContainerDataSource()
								.getItem(event.getProperty().getValue()));
						interpreter.interpret(itemSelectedCommand);
					}
				});
				interpreter.pushData(cb);
			}
		},

		new BaseWord("new-select", "", Word.POSTPONED) {

			@Override
			public void execute(final Interpreter interpreter) {
				final String selCommand = interpreter.getParser().getNextWord();
				final Select sel = new Select();
				sel.setCaption((String) interpreter.popData());
				sel.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_ITEM);
				sel.setNullSelectionAllowed(false);
				sel.setImmediate(true);
				sel.addValueChangeListener(new ValueChangeListener() {
					/**
				 *
				 */
					private static final long serialVersionUID = -7705548618092166199L;

					public void valueChange(ValueChangeEvent event) {
						Item item = sel.getContainerDataSource().getItem(
								event.getProperty().getValue());
						interpreter.pushData(item);
						interpreter.interpret(selCommand);
					}
				});
				interpreter.pushData(sel);
			}
		},

		new BaseWord("new-list-select", "", Word.POSTPONED) {
			@Override
			public void execute(final Interpreter interpreter) {
				final String lselCommand = interpreter.getParser()
						.getNextWord();
				final ListSelect lsel = new ListSelect();
				lsel.setCaption((String) interpreter.popData());
				lsel.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_ITEM);
				lsel.setNullSelectionAllowed(false);
				lsel.setImmediate(true);
				lsel.addValueChangeListener(new ValueChangeListener() {
					/**
				 *
				 */
					private static final long serialVersionUID = -5523488417834167806L;

					public void valueChange(ValueChangeEvent event) {
						Item item = lsel.getContainerDataSource().getItem(
								event.getProperty().getValue());
						interpreter.pushData(item);
						interpreter.interpret(lselCommand);
					}
				});
				interpreter.pushData(lsel);
			}
		},

		new BaseWord("set-container-data-source", "", Word.POSTPONED) {
			@Override
			public void execute(Interpreter interpreter) {
				Container cont = (Container) interpreter.popData();
				AbstractSelect as = (AbstractSelect) interpreter.popData();
				as.setContainerDataSource(cont);
				interpreter.pushData(as);
			}
		},

		new BaseWord("set-column-headers", "", Word.POSTPONED) {
			@Override
			public void execute(Interpreter interpreter) {
				Table table = (Table) interpreter.popData();
				table.setColumnHeaders((String[]) 
						getArrayFromList(interpreter, new String[0]));
			}
		},

		new BaseWord("set-visible-columns", "", Word.POSTPONED) {
			@Override
			public void execute(Interpreter interpreter) {
				Table table = (Table) interpreter.popData();
				table.setVisibleColumns((String[]) 
						getArrayFromList(interpreter, new String[0]));
			}
		}

		};
	}
	
	/**
	 * Constructs an array from a list on the stack.
	 * 
	 * @param array
	 *            Array whose type will be nicked.
	 * 
	 * @return
	 */
	public <T> T[] getArrayFromList(Interpreter interpreter, T[] array) {

		ArrayList<Object> list = new ArrayList<Object>();

		// The first word of 'list[' is a literal with the address of our
		// list as parameter
		Word[] code = ((DefinedWord) interpreter.getDictionary().get("list[")).getCode();
		int addr = (Integer) ((CompiledWord) code[0]).getParameter();

		while (interpreter.getHeap()[addr] != Util.LIST_TERMINATOR) {
			list.add(interpreter.getHeap()[addr]);
			addr++;
		}
		return list.toArray(array);
	}


}
