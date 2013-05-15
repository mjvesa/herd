package com.github.mjvesa.f4v.wordset;

import com.github.mjvesa.f4v.BaseWord;
import com.github.mjvesa.f4v.Interpreter;
import com.github.mjvesa.f4v.Word;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class VaadinWordSet extends WordSet {

	@Override
	public Word[] getWords() {
		return new Word[] {

		new BaseWord("NEWBUTTON", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				Button b = new Button("", interpreter);
				interpreter.pushData(b);
			}
		},

		new BaseWord("SETCLICKLISTENER", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				Object o = interpreter.popData();
				Button b = (Button) interpreter.popData();
				b.setData(o);
				interpreter.pushData(b);
			}
		},

		new BaseWord("NEWHL", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				HorizontalLayout hl = new HorizontalLayout();
				hl.setSpacing(true);
				interpreter.pushData(hl);
			}
		},

		new BaseWord("NEWVL", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				VerticalLayout vl = new VerticalLayout();
				vl.setSpacing(true);
				interpreter.pushData(vl);
			}
		},

		new BaseWord("NEWGL", "( x y - gl )", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				Integer height = (Integer) interpreter.popData();
				Integer width = (Integer) interpreter.popData();
				interpreter.pushData(new GridLayout(width, height));
			}
		},

		new BaseWord("GLNEWLINE", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				((GridLayout) interpreter.peekData()).newLine();
			}
		},

		new BaseWord("NEWWINDOW", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				Window w = new Window();
				((VerticalLayout) w.getContent()).setSpacing(true);
				interpreter.pushData(w);
			}
		},

		new BaseWord("MAINPANEL", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				interpreter.pushData(interpreter.getMainPanel());
			}
		},

		new BaseWord("ADDWINDOW", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				Window w = (Window) interpreter.popData();
				interpreter.getGuiEventListener().getUI().addWindow(w);
			}
		},

		new BaseWord("ADDCOMPONENT", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
				Component comp = (Component) interpreter.popData();
				ComponentContainer cc = (ComponentContainer) interpreter
						.popData();
				cc.addComponent(comp);
				interpreter.pushData(cc);
			}
		},

		new BaseWord("", "", Word.POSTPONED) {

			@Override
			public void execute(Interpreter interpreter) {
			}
		},

				// case SETCAPTION:
				// str = (String) dataStack.pop();
				// comp = (Component) dataStack.pop();
				// comp.setCaption(str);
				// dataStack.push(comp);
				// break;

				new BaseWord("", "", Word.POSTPONED) {

					@Override
					public void execute(Interpreter interpreter) {
					}
				},

				// case SETVALUE:
				// o = dataStack.pop();
				// f = (Field) dataStack.pop();
				// f.setValue(o);
				// dataStack.push(f);
				// break;

				new BaseWord("", "", Word.POSTPONED) {

					@Override
					public void execute(Interpreter interpreter) {
					}
				},

				// case GETVALUE:
				// f = (Field) dataStack.pop();
				// dataStack.push(f);
				// dataStack.push(f.getValue());
				// break;

				new BaseWord("", "", Word.POSTPONED) {

					@Override
					public void execute(Interpreter interpreter) {
					}
				},

				// case SETSIZEFULL:
				// comp = (Component) dataStack.pop();
				// comp.setSizeFull();
				// dataStack.push(comp);
				// break;

				new BaseWord("", "", Word.POSTPONED) {

					@Override
					public void execute(Interpreter interpreter) {
					}
				},

				// case SETSIZEUNDEFINED:
				// comp = (Component) dataStack.pop();
				// comp.setSizeUndefined();
				// dataStack.push(comp);
				// break;

				new BaseWord("", "", Word.POSTPONED) {

					@Override
					public void execute(Interpreter interpreter) {
					}
				},

				// case SETHEIGHT:
				// str = (String) dataStack.pop();
				// comp = (Component) dataStack.pop();
				// comp.setHeight(str);
				// dataStack.push(comp);
				// break;

				new BaseWord("", "", Word.POSTPONED) {

					@Override
					public void execute(Interpreter interpreter) {
					}
				},

				// case SETWIDTH:
				// str = (String) dataStack.pop();
				// comp = (Component) dataStack.pop();
				// comp.setWidth(str);
				// dataStack.push(comp);
				// break;

				new BaseWord("", "", Word.POSTPONED) {

					@Override
					public void execute(Interpreter interpreter) {
					}
				},

				// case CLEARCONTAINER:
				// cc = (ComponentContainer) dataStack.pop();
				// cc.removeAllComponents();
				// break;
				new BaseWord("", "", Word.POSTPONED) {

					@Override
					public void execute(Interpreter interpreter) {
					}
				},

				// case NEWCHECKBOX:
				// dataStack.push(new CheckBox());
				// break;
				new BaseWord("", "", Word.POSTPONED) {

					@Override
					public void execute(Interpreter interpreter) {
					}
				},

				// case NEWDATEFIELD:
				// dataStack.push(new DateField());
				// final String dfCommand = (String) dataStack.pop();
				// DateField df = new DateField();
				// df.setImmediate(true);
				// df.addListener(new ValueChangeListener() {
				// public void valueChange(ValueChangeEvent event) {
				// dataStack.push(event.getProperty().getValue());
				// interpret(dfCommand);
				// }
				// });
				// dataStack.push(df);
				// break;
				new BaseWord("", "", Word.POSTPONED) {

					@Override
					public void execute(Interpreter interpreter) {
					}
				},

				// case NEWLABEL:
				// dataStack.push(new Label());
				// break;
				new BaseWord("", "", Word.POSTPONED) {

					@Override
					public void execute(Interpreter interpreter) {
					}
				},

				// case NEWTEXTFIELD: // ( caption -- textfield)
				// final String tfCommand = getNextNonNopWord();
				// TextField tf = new TextField();
				// tf.setCaption((String) dataStack.pop());
				// tf.setValue("");
				// tf.setImmediate(true);
				// tf.addValueChangeListener(new ValueChangeListener() {
				// /**
				// *
				// */
				// private static final long serialVersionUID =
				// 4325104922208051065L;
				//
				// public void valueChange(ValueChangeEvent event) {
				// dataStack.push(event.getProperty().getValue());
				// interpret(tfCommand);
				// }
				// });
				// dataStack.push(tf);
				// break;
				new BaseWord("", "", Word.POSTPONED) {

					@Override
					public void execute(Interpreter interpreter) {
					}
				},

				// /* Tables */
				// case NEWTABLE:
				// final String tableCommand = getNextNonNopWord();
				// table = new Table();
				// table.setCaption((String) dataStack.pop());
				// table.setImmediate(true);
				// table.setSelectable(true);
				// table.addListener(new ItemClickListener() {
				//
				// /**
				// *
				// */
				// private static final long serialVersionUID =
				// 3585546076571010729L;
				//
				// public void itemClick(ItemClickEvent event) {
				//
				// dataStack.push(event.getItem());
				// executeDefinedWord(dictionary.get(tableCommand));
				// }
				// });
				// dataStack.push(table);
				// break;
				//
				new BaseWord("", "", Word.POSTPONED) {

					@Override
					public void execute(Interpreter interpreter) {
					}
				},

				// case NEWCOMBOBOX:
				// final String newItemCommand = getNextNonNopWord();
				// final String itemSelectedCommand = getNextNonNopWord();
				// final ComboBox cb = new ComboBox();
				// cb.setImmediate(true);
				// str = (String) dataStack.pop();
				// cb.setNullSelectionAllowed(false);
				// cb.setCaption(str);
				// cb.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_ITEM);
				// cb.setNewItemsAllowed(true);
				// cb.setNewItemHandler(new NewItemHandler() {
				//
				// /**
				// *
				// */
				// private static final long serialVersionUID =
				// 3340658590351611289L;
				//
				// public void addNewItem(String newItemCaption) {
				// cb.setImmediate(false);
				// dataStack.push(newItemCaption);
				// interpret(newItemCommand);
				// cb.setImmediate(true);
				// }
				// });
				//
				// cb.addValueChangeListener(new ValueChangeListener() {
				//
				// /**
				// *
				// */
				// private static final long serialVersionUID =
				// 2706579869793251379L;
				//
				// public void valueChange(ValueChangeEvent event) {
				// dataStack.push(cb.getContainerDataSource().getItem(
				// event.getProperty().getValue()));
				// interpret(itemSelectedCommand);
				// }
				// });
				// dataStack.push(cb);
				// break;
				new BaseWord("", "", Word.POSTPONED) {

					@Override
					public void execute(Interpreter interpreter) {
					}
				},

				// case NEWSELECT:
				// final String selCommand = getNextNonNopWord();
				// final Select sel = new Select();
				// sel.setCaption((String) dataStack.pop());
				// sel.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_ITEM);
				// sel.setNullSelectionAllowed(false);
				// sel.setImmediate(true);
				// sel.addValueChangeListener(new ValueChangeListener() {
				// /**
				// *
				// */
				// private static final long serialVersionUID =
				// -7705548618092166199L;
				//
				// public void valueChange(ValueChangeEvent event) {
				// Item item = sel.getContainerDataSource().getItem(
				// event.getProperty().getValue());
				// dataStack.push(item);
				// interpret(selCommand);
				// }
				// });
				// dataStack.push(sel);
				// break;
				new BaseWord("", "", Word.POSTPONED) {
					@Override
					public void execute(Interpreter interpreter) {
					}
				},

				// case NEWLISTSELECT:
				// final String lselCommand = getNextNonNopWord();
				// final ListSelect lsel = new ListSelect();
				// lsel.setCaption((String) dataStack.pop());
				// lsel.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_ITEM);
				// lsel.setNullSelectionAllowed(false);
				// lsel.setImmediate(true);
				// lsel.addValueChangeListener(new ValueChangeListener() {
				// /**
				// *
				// */
				// private static final long serialVersionUID =
				// -5523488417834167806L;
				//
				// public void valueChange(ValueChangeEvent event) {
				// Item item = lsel.getContainerDataSource().getItem(
				// event.getProperty().getValue());
				// dataStack.push(item);
				// interpret(lselCommand);
				// }
				// });
				// dataStack.push(lsel);
				// break;
				new BaseWord("", "", Word.POSTPONED) {
					@Override
					public void execute(Interpreter interpreter) {
					}
				},

				// case SETCONTAINERDATASOURCE:
				// Container cont = (Container) dataStack.pop();
				// AbstractSelect as = (AbstractSelect) dataStack.pop();
				// as.setContainerDataSource(cont);
				// dataStack.push(as);
				// break;
				new BaseWord("", "", Word.POSTPONED) {
					@Override
					public void execute(Interpreter interpreter) {
					}
				},

				// case SETCOLUMHEADERS:
				// table = (Table) dataStack.pop();
				// table.setColumnHeaders((String[]) sql
				// .getArrayFromList(new String[0]));
				// break;
				new BaseWord("", "", Word.POSTPONED) {
					@Override
					public void execute(Interpreter interpreter) {
					}
				}

		// case SETVISIBLECOLUMNS:
		// table = (Table) dataStack.pop();
		// table.setVisibleColumns((String[]) sql
		// .getArrayFromList(new String[0]));
		// break;
		//

		};
	}
}
