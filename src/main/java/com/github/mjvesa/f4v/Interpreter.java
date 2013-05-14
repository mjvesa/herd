/*
 *  Copyright 2012 Matti Vesa
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you
 *  may not use this file except in compliance with the License. You may
 *  obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package com.github.mjvesa.f4v;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Set;
import java.util.Stack;

import com.github.mjvesa.f4v.DefinedWord.BaseWord;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractSelect.NewItemHandler;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
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
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * 
 * The Interpreter has two roles: it executes code and also maintains global
 * interpreter state.
 * 
 * @author mjvesa@vaadin.com
 * 
 */
public class Interpreter implements ClickListener {

	/**
     * 
     */
	private static final long serialVersionUID = -8695295335619892044L;

	private HashMap<String, Word> dictionary;
	private HashMap<String, String> source;
	private Stack<Object> dataStack;
	private Stack<Integer> returnStack;
	private Stack<Word[]> codeStack;
	private Object[] heap;
	private DefinedWord currentDefinition;
	private ArrayList<Word> currentDefinitionWords;
	private boolean isCompiling;
	private int ip;
	private Word[] code;
	private Parser parser;
	private ComponentContainer mainComponentContainer;
	private Blocks blocks;
	private boolean logNewWords;
	private boolean logExecutedWords;

	private GuiEventListener guiEventListener;
	private SQL sql;

	/**
	 * Default constructor
	 */
	public Interpreter() {

		// logNewWords = false;
		// logExecutedWords = false;

		dataStack = new Stack<Object>();
		returnStack = new Stack<Integer>();
		codeStack = new Stack<Word[]>();
		heap = new Object[2000];
		dictionary = new HashMap<String, Word>();

	}

	/**
	 * Does actual setting up of the interpreters.
	 */
	public void setup() {
		fillDictionary();
		loadBuffers();
		print("<b>Executing core buffer...</b>");
		executeCore();

		// TODO this should go to the SQL wordset when installing
		sql = new SQL();
		sql.setDictionary(state.getDictionary());
		sql.setHeap(state.getHeap());

	}

	public void setGuiEventListener(GuiEventListener listener) {
		guiEventListener = listener;
		state.setMainComponentContainer(listener.getMainComponentContainer());
	}

	private void loadBuffers() {
		blocks = new Blocks();
		source = blocks.loadBuffers();
	}

	public Set<String> getWordList() {
		return dictionary.keySet();
	}

	public Set<String> getBufferList() {
		return source.keySet();
	}

	// TODO to be replaced by "require Core"

	private void executeCore() {
		runBuffer(source.get("Core"));
	}

	/**
	 * Pulls basic words from the list and
	 * 
	 * TODO this will replaced by installing wordsets instead
	 */
	private void fillDictionary() {
		for (BaseWord bw : BaseWord.values()) {
			DefinedWord word = new DefinedWord();
			word.setType(DefinedWord.Type.BASE);
			word.setBaseWord(bw);
			word.setName(bw.getString());
			guiEventListener.newWord(bw.getString());
			dictionary.put(bw.getString(), word);
		}

		// Set some words to be executed immediately when compiling
		dictionary.get("ENDIF").setImmediate(true);
		dictionary.get("(").setImmediate(true);
		dictionary.get(")").setImmediate(true);
		dictionary.get(":").setImmediate(true);
		dictionary.get(";").setImmediate(true);
		dictionary.get("[").setImmediate(true);
		dictionary.get("]").setImmediate(true);
	}

	public void runBuffer(String command) {
		try {
			dataStack.clear();
			returnStack.clear();
			interpret(command);
			print("OK");
		} catch (Exception e) {
			// print("ERROR: " + e.getClass().toString() + " -- Current word: "
			// + code[ip].getName());
			e.printStackTrace();
		}

	}

	/**
	 * Outer interpreter.
	 * 
	 * @param str
	 */
	public void interpret(String str) {

		state.setCompiling(false);
		Parser parser = new Parser();
		parser.setString(str);
		state.setParser(parser);

		String word = parser.getNextWord();

		while (!word.isEmpty()) {

			if (state.isCompiling()) {
				compileWord(word);
			} else {

				if (state.getDictionary().containsKey(word)) {
					Word w = state.getDictionary().get(word);
					execute(w);
				} else {
					if (word.charAt(0) == '"') {
						state.getDataStack().push(
								word.substring(1, word.length() - 1));
					} else if (Character.isDigit(word.charAt(0))) {
						state.getDataStack().push(Integer.valueOf(word));
					} else {
						print("ERROR: didn't quite get this: " + word);
					}
				}
			}
			word = parser.getNextWord();
		}
	}

	/**
	 * Takes a word and finds its definition from the dictionary and compiles a
	 * reference to it into the current definition.
	 * 
	 * @param word
	 */
	private void compileWord(String word) {
		if (state.getDictionary().containsKey(word)) {
			Word w = state.getDictionary().get(word);
			if (w.isImmediate()) {
				execute(w);
			} else {
				state.getCurrentDefinitionWords().add(w);
			}
		} else {
			if (word.charAt(0) == '"') {
				generateLiteral(word.substring(1, word.length() - 1));
			} else if (Character.isDigit(word.charAt(0))) {
				generateLiteral(Integer.valueOf(word));
			} else {
				print("COMPILER ERROR: didn't quite get this: " + word
						+ state.getParser().getPosition());
			}
		}
	}

	/**
	 * 
	 * Inner interpreter
	 * 
	 * Execution:
	 * 
	 * Base words are atomic, so just fetch and execute. Defined words consist
	 * of other words. Obtain list of words and execute in series. Store
	 * previous point in return stack so there will be no problems with nested
	 * calls.
	 */
	public void execute(Word word) {

		if (word == null) {
			guiEventListener.getUI().showNotification(
					"Attempted to excute undefined word!");
			return;
			// TODO return to main loop or stop interpreting when this happens.
		}

		if (state.isLogExecutedWords()) {
			print("Executing: " + word.getName());
		}

		word.execute(state);
	}

	private void executeBaseWord(DefinedWord word) {

		BaseWord baseWord = word.getBaseWord();
		Integer address;
		Integer a, b, c;
		Object o1, o2, o3, o4;
		Object value, o;
		String str, str2;
		Boolean bool;

		ComponentContainer cc;
		Window w;
		Button btn;
		DefinedWord wrd;
		DefinedWord[] code;
		Table table;
		Field f;

		final Item item;

		if (logExecutedWords) {
			print("Executing base word: " + word.getName());
		}

		try {

			switch (baseWord) {

			case LITERAL:
				dataStack.push(word.getParam());
				break;
			case GENLITERAL:
				generateLiteral(dataStack.pop());
				break;
			case STORE:
				o1 = dataStack.pop();
				address = (Integer) o1;
				value = dataStack.pop();
				heap[address] = value;
				break;
			case LOAD:
				address = (Integer) dataStack.pop();
				dataStack.push(heap[address]);
				break;
			case PRINT:
				value = dataStack.pop();
				print(value.toString());
				break;
			case ADD:
				a = (Integer) dataStack.pop();
				b = (Integer) dataStack.pop();
				dataStack.push(a + b);
				break;
			case SUB:
				a = (Integer) dataStack.pop();
				b = (Integer) dataStack.pop();
				dataStack.push(b - a);
				break;
			case MUL:
				a = (Integer) dataStack.pop();
				b = (Integer) dataStack.pop();
				dataStack.push(b * a);
				break;
			case DIV:
				a = (Integer) dataStack.pop();
				b = (Integer) dataStack.pop();
				dataStack.push(b / a);
				break;
			case NOT:
				bool = (Boolean) dataStack.pop();
				dataStack.push(!bool);
				break;
			case DUP: // ( a -- a a )
				o = dataStack.pop();
				dataStack.push(o);
				dataStack.push(o);
				break;
			case OVER: // ( a b -- a b a )
				o1 = dataStack.pop();
				o2 = dataStack.pop();
				dataStack.push(o2);
				dataStack.push(o1);
				dataStack.push(o2);
				break;
			case ROT: // ( a b c -- b c a )
				o1 = dataStack.pop();
				o2 = dataStack.pop();
				o3 = dataStack.pop();
				dataStack.push(o2);
				dataStack.push(o1);
				dataStack.push(o3);
				break;
			case MINUSROT: // ( a b c -- c a b )
				o1 = dataStack.pop();
				o2 = dataStack.pop();
				o3 = dataStack.pop();
				dataStack.push(o1);
				dataStack.push(o3);
				dataStack.push(o2);
				break;
			case NIP: // ( a b -- b )
				o1 = dataStack.pop();
				o2 = dataStack.pop();
				dataStack.push(o1);
				break;
			case TUCK: // ( a b -- b a b )
				o1 = dataStack.pop();
				o2 = dataStack.pop();
				dataStack.push(o1);
				dataStack.push(o2);
				dataStack.push(o1);
				break;

			case SWAP: // ( a b -- b a )
				o1 = dataStack.pop();
				o2 = dataStack.pop();
				dataStack.push(o1);
				dataStack.push(o2);
				break;
			case TWOSWAP: // ( a b c d -- c d a b)
				o1 = dataStack.pop();
				o2 = dataStack.pop();
				o3 = dataStack.pop();
				o4 = dataStack.pop();
				dataStack.push(o2);
				dataStack.push(o1);
				dataStack.push(o4);
				dataStack.push(o3);
				break;
			case DROP: // ( n -- )
				dataStack.pop();
				break;
			case CREATE:
				create();
				break;
			case STACKCREATE:
				createFromStack();
				break;
			case CREATENOP:
				DefinedWord nop = new DefinedWord();
				nop.setType(DefinedWord.Type.NOP);
				String name = parser.getNextWord();
				dictionary.put(name, nop);
				guiEventListener.newWord(name);
				break;
			case DOES:
				code = this.code;

				// Find where DOES> is
				int i = code.length - 1;
				while (code[i].getBaseWord() != BaseWord.DOES) {
					i--;
				}
				i++; // We don't want to copy DOES> now do we
				// Copy words over TODO use array stuff for this?
				for (; i < code.length; i++) {
					currentDefinitionWords.add(code[i]);
				}
				finishCompilation();
				ip = code.length; // Don't execute stuff after DOES>
				break;
			case IMMEDIATE:
				currentDefinition.setImmediate(true);
				break;
			case COMPILE:
				wrd = getNextExecutableWord();
				currentDefinitionWords.add(wrd);
				break;
			case COLONCREATE:
				create();
				break;
			case ANONCREATE:
				anonCreate();
				break;
			case FINISHCOMPILATION:
				finishCompilation();
				break;
			case DO:
				returnStack.push(ip);
				break;
			case LOOP:
				a = (Integer) dataStack.pop();
				a++;
				b = (Integer) dataStack.pop();
				if (a < b) {
					ip = returnStack.pop();
					returnStack.push(ip);
					dataStack.push(b);
					dataStack.push(a);
				} else {
					returnStack.pop();
				}
				break;
			case IF:
				bool = (Boolean) dataStack.pop();
				// If false, skip to endif
				if (!bool) {
					ip += (Integer) word.getParam();
					System.out.println("IF Jumping to: "
							+ this.code[ip + 1].toString());
				}
				break;
			case ELSE:
				ip += (Integer) word.getParam();
				System.out.println("ELSE Jumping to what is after: "
						+ this.code[ip].toString());
				break;
			case ENDIF:
				// Find latest IF
				// set current address minus one as parameter
				i = currentDefinitionWords.size() - 1;
				int jumpDest = i;
				while (currentDefinitionWords.get(i).getBaseWord() != BaseWord.IF) {
					if (currentDefinitionWords.get(i).getBaseWord() == BaseWord.ELSE) {
						currentDefinitionWords.get(i).setParam(jumpDest - i);
						jumpDest = i; // IF jumps to word after else
					}
					i--;

					// TODO should we check if we are stepping out of array?
					// Nah.
				}
				currentDefinitionWords.get(i).setParam(jumpDest - i);
				break;
			case BEGIN:
				returnStack.push(ip);
				break;
			case WHILE:
				bool = (Boolean) dataStack.pop();
				if (!bool) {
					code = this.code;
					while (code[ip].getBaseWord() != BaseWord.REPEAT) {
						ip++;
					}
					returnStack.pop();
				}
				break;
			case REPEAT:
				ip = returnStack.pop();
				returnStack.push(ip); // TODO might be off by one
				break;
			case LESSTHANZERO:
				a = (Integer) dataStack.pop();
				dataStack.push((Boolean) (a < 0));
				break;
			case ZERO:
				a = (Integer) dataStack.pop();
				dataStack.push((Boolean) (a == 0));
				break;
			case GREATERTHANZERO:
				a = (Integer) dataStack.pop();
				dataStack.push((Boolean) (a > 0));
				break;
			case LESSTHAN:
				a = (Integer) dataStack.pop();
				b = (Integer) dataStack.pop();
				dataStack.push((Boolean) (b < a));
				break;
			case EQUALS:
				a = (Integer) dataStack.pop();
				b = (Integer) dataStack.pop();
				dataStack.push((Boolean) (b.intValue() == a.intValue()));
				break;
			case GREATERTHAN: // (a b -- a > b )
				b = (Integer) dataStack.pop();
				a = (Integer) dataStack.pop();
				dataStack.push((Boolean) (a > b));
				break;

			case ISXT: // ( XT? -- Boolean )
				dataStack.push(dataStack.pop() instanceof DefinedWord);
				break;
			case WORDS:
				printWords();
				break;

			case BEGINCOMMENT:
				str = parser.getNextWord();
				while (!")".equals(str)) {
					str = parser.getNextWord();
				}
				break;
			case ENDCOMMENT:
				// Doesn't do squat (yet?)
				break;
			case BEGININTERPRET:
				isCompiling = false;
				break;
			case ENDINTERPRET:
				isCompiling = true;
				break;
			case STRTOINT:
				str = dataStack.pop().toString();
				dataStack.push(str.isEmpty() ? 0 : Integer.parseInt(str));
				break;
			case INTTOSTR:
				a = (Integer) dataStack.pop();
				dataStack.push(a.toString());
				break;
			case TICK: // ( str -- xt )
				// wrd = dictionary.get(parser.getNextWord());
				str = (String) dataStack.pop();
				wrd = dictionary.get(str);
				dataStack.push(wrd);
				break;
			case BRACKETTICK:
				str = (String) dataStack.pop();
				wrd = dictionary.get(str);
				currentDefinitionWords.add(wrd);
				break;
			case FIND: // ( str -- xt )
				str = (String) dataStack.pop();
				wrd = dictionary.get(str);
				if (wrd == null) {
					print("FIND did not find word " + str);
				}
				dataStack.push(wrd);
				break;
			case EXECUTE:
				wrd = (DefinedWord) dataStack.pop();
				returnStack.push(ip);
				codeStack.push(this.code);
				execute(wrd);
				ip = returnStack.pop();
				this.code = codeStack.pop();
				break;
			case WORD:
				dataStack.push(parser.getNextWord());
				break;
			case CAT:
				str = (String) dataStack.pop();
				str2 = (String) dataStack.pop();
				dataStack.push(str2 + str);
				break;
			case NULL:
				dataStack.push(Util.NULL_OBJECT);
				break;
			case OBJEQUALS:
				o1 = dataStack.pop();
				o2 = dataStack.pop();
				dataStack.push(o1.equals(o2));
				break;

			case LIST_TERMINATOR:
				dataStack.push(Util.LIST_TERMINATOR);
				break;
			case EXECBUFFER:
				str = (String) dataStack.pop();
				interpret(source.get(str));
				break;

			case PRINTSTACK:
				printStack();
				break;

			case LOG:
				print((String) dataStack.pop());
				break;
			/******************************
			 * Here starteth the Vaadin words.
			 *******************************/

			case NEWBUTTON:
				btn = new Button("", this);
				dataStack.push(btn);
				break;
			case SETCLICKLISTENER:
				o = dataStack.pop();
				btn = (Button) dataStack.pop();
				btn.setData(o);
				dataStack.push(btn);
				break;
			case NEWHL:
				HorizontalLayout hl = new HorizontalLayout();
				hl.setSpacing(true);
				dataStack.push(hl);
				break;
			case NEWVL:
				VerticalLayout vl = new VerticalLayout();
				vl.setSpacing(true);
				dataStack.push(vl);
				break;
			case NEWGL: // ( x y - gl )
				b = (Integer) dataStack.pop();
				a = (Integer) dataStack.pop();
				dataStack.push(new GridLayout(a, b));
				break;
			case GLNEWLINE:
				((GridLayout) dataStack.get(0)).newLine();
				break;
			case NEWWINDOW:
				w = new Window();
				((VerticalLayout) w.getContent()).setSpacing(true);
				dataStack.push(w);
				break;
			case MAINPANEL:
				dataStack.push(mainComponentContainer);
				break;
			case ADDWINDOW:
				w = (Window) dataStack.pop();
				guiEventListener.getUI().addWindow(w);
				break;
			case ADDCOMPONENT:
				Component comp = (Component) dataStack.pop();
				cc = (ComponentContainer) dataStack.pop();
				cc.addComponent(comp);
				dataStack.push(cc);
				break;
			case SETCAPTION:
				str = (String) dataStack.pop();
				comp = (Component) dataStack.pop();
				comp.setCaption(str);
				dataStack.push(comp);
				break;
			case SETVALUE:
				o = dataStack.pop();
				f = (Field) dataStack.pop();
				f.setValue(o);
				dataStack.push(f);
				break;
			case GETVALUE:
				f = (Field) dataStack.pop();
				dataStack.push(f);
				dataStack.push(f.getValue());
				break;
			case SETSIZEFULL:
				comp = (Component) dataStack.pop();
				comp.setSizeFull();
				dataStack.push(comp);
				break;
			case SETSIZEUNDEFINED:
				comp = (Component) dataStack.pop();
				comp.setSizeUndefined();
				dataStack.push(comp);
				break;
			case SETHEIGHT:
				str = (String) dataStack.pop();
				comp = (Component) dataStack.pop();
				comp.setHeight(str);
				dataStack.push(comp);
				break;
			case SETWIDTH:
				str = (String) dataStack.pop();
				comp = (Component) dataStack.pop();
				comp.setWidth(str);
				dataStack.push(comp);
				break;
			case CLEARCONTAINER:
				cc = (ComponentContainer) dataStack.pop();
				cc.removeAllComponents();
				break;
			case NEWCHECKBOX:
				dataStack.push(new CheckBox());
				break;
			case NEWDATEFIELD:
				dataStack.push(new DateField());
				final String dfCommand = (String) dataStack.pop();
				DateField df = new DateField();
				df.setImmediate(true);
				df.addListener(new ValueChangeListener() {
					public void valueChange(ValueChangeEvent event) {
						dataStack.push(event.getProperty().getValue());
						interpret(dfCommand);
					}
				});
				dataStack.push(df);
				break;
			case NEWLABEL:
				dataStack.push(new Label());
				break;
			case NEWTEXTFIELD: // ( caption -- textfield)
				final String tfCommand = getNextNonNopWord();
				TextField tf = new TextField();
				tf.setCaption((String) dataStack.pop());
				tf.setValue("");
				tf.setImmediate(true);
				tf.addValueChangeListener(new ValueChangeListener() {
					/**
		 * 
		 */
					private static final long serialVersionUID = 4325104922208051065L;

					public void valueChange(ValueChangeEvent event) {
						dataStack.push(event.getProperty().getValue());
						interpret(tfCommand);
					}
				});
				dataStack.push(tf);
				break;
			/* Tables */
			case NEWTABLE:
				final String tableCommand = getNextNonNopWord();
				table = new Table();
				table.setCaption((String) dataStack.pop());
				table.setImmediate(true);
				table.setSelectable(true);
				table.addListener(new ItemClickListener() {

					/**
		 * 
		 */
					private static final long serialVersionUID = 3585546076571010729L;

					public void itemClick(ItemClickEvent event) {

						dataStack.push(event.getItem());
						executeDefinedWord(dictionary.get(tableCommand));
					}
				});
				dataStack.push(table);
				break;

			case NEWCOMBOBOX:
				final String newItemCommand = getNextNonNopWord();
				final String itemSelectedCommand = getNextNonNopWord();
				final ComboBox cb = new ComboBox();
				cb.setImmediate(true);
				str = (String) dataStack.pop();
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
						dataStack.push(newItemCaption);
						interpret(newItemCommand);
						cb.setImmediate(true);
					}
				});

				cb.addValueChangeListener(new ValueChangeListener() {

					/**
                     * 
                     */
					private static final long serialVersionUID = 2706579869793251379L;

					public void valueChange(ValueChangeEvent event) {
						dataStack.push(cb.getContainerDataSource().getItem(
								event.getProperty().getValue()));
						interpret(itemSelectedCommand);
					}
				});
				dataStack.push(cb);
				break;
			case NEWSELECT:
				final String selCommand = getNextNonNopWord();
				final Select sel = new Select();
				sel.setCaption((String) dataStack.pop());
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
						dataStack.push(item);
						interpret(selCommand);
					}
				});
				dataStack.push(sel);
				break;
			case NEWLISTSELECT:
				final String lselCommand = getNextNonNopWord();
				final ListSelect lsel = new ListSelect();
				lsel.setCaption((String) dataStack.pop());
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
						dataStack.push(item);
						interpret(lselCommand);
					}
				});
				dataStack.push(lsel);
				break;
			case SETCONTAINERDATASOURCE:
				Container cont = (Container) dataStack.pop();
				AbstractSelect as = (AbstractSelect) dataStack.pop();
				as.setContainerDataSource(cont);
				dataStack.push(as);
				break;
			case SETCOLUMHEADERS:
				table = (Table) dataStack.pop();
				table.setColumnHeaders((String[]) sql
						.getArrayFromList(new String[0]));
				break;
			case SETVISIBLECOLUMNS:
				table = (Table) dataStack.pop();
				table.setVisibleColumns((String[]) sql
						.getArrayFromList(new String[0]));
				break;
			/* Database stuff */
			case CREATESQLCONTAINER:
				str = (String) dataStack.pop();
				dataStack.push(sql.createIndexedContainerFromQuery(str, false));
				break;
			case CREATEFILTEREDSQLCONTAINER:
				str = (String) dataStack.pop();
				dataStack.push(sql.createIndexedContainerFromQuery(str, true));
				break;
			case DOQUERY:
				sql.doQuery((String) dataStack.pop());
				break;
			case GETPROPERTY:
				str = (String) dataStack.pop();
				item = (Item) dataStack.pop();
				dataStack.push(item);
				dataStack.push(item.getItemProperty(str).getValue());
				break;
			case SETPROPERTY:
				str = (String) dataStack.pop();
				str2 = (String) dataStack.pop();
				item = (Item) dataStack.pop();
				item.getItemProperty(str2).setValue(str);
				break;
			default:
				break;
			}

		} catch (Exception e) {
			print("Exception " + e.getClass() + " " + parser.getPosition()
					+ " baseword " + word.toString());
		}

	}

	/* Gets the next executable word and skips it in execution */
	private DefinedWord getNextExecutableWord() {
		DefinedWord[] words = this.code;
		DefinedWord w = words[ip + 1];
		ip++;
		return w;
	}

	/* This can be used to skip filling words */
	private String getNextNonNopWord() {

		String word = parser.getNextWord();
		while (!word.isEmpty()) {
			if (dictionary.get(word).getType() != DefinedWord.Type.NOP) {
				return word;
			}
			word = parser.getNextWord();
		}
		return null;
	}

	/* Generates a word which pushes a literal onto the stack */
	private void generateLiteral(Object value) {
		DefinedWord w = new DefinedWord();
		w.setBaseWord(BaseWord.LITERAL);
		w.setName("LITERAL " + value.toString());
		w.setType(DefinedWord.Type.BASE);
		w.setParam(value);
		currentDefinitionWords.add(w);
	}

	private void create() {
		String name = parser.getNextWord();
		createNewWord(name);
		guiEventListener.newWord(name);

	}

	private void createFromStack() {
		createNewWord((String) dataStack.pop());
	}

	private void anonCreate() {
		createNewWord("_anonymous_");
	}

	/*
	 * Prints all the words in the dictionary
	 */
	private void printWords() {
		print(dictionary.keySet().toString());
	}

	public void printStack() {

		ListIterator<Object> iterator = dataStack.listIterator();
		while (iterator.hasNext()) {
			Object o = iterator.next();
			print("Class: " + o.getClass() + " -- [" + o.toString() + "]");
		}
	}

	public void buttonClick(ClickEvent event) {
		Button b = event.getButton();
		DefinedWord command = (DefinedWord) b.getData();
		if (command != null) {
			execute(command);
		}
	}

	private void print(String msg) {
		guiEventListener.print(msg);
	}

	public interface GuiEventListener {
		public void print(String msg);

		public UI getUI();

		public void newWord(String word);

		public ComponentContainer getMainComponentContainer();

		public void disableContinueButton();

		public void enableContinueButton();
	}

	public void addSource(String name, String code) {
		blocks.saveBuffer(name, code);
		source.put(name, code);

	}

	public HashMap<String, Word> getDictionary() {
		return dictionary;
	}

	public void setDictionary(HashMap<String, Word> dictionary) {
		this.dictionary = dictionary;
	}

	public HashMap<String, String> getSource() {
		return source;
	}

	public void setSource(HashMap<String, String> source) {
		this.source = source;
	}

	public Stack<Object> getDataStack() {
		return dataStack;
	}

	public void setDataStack(Stack<Object> dataStack) {
		this.dataStack = dataStack;
	}

	public Stack<Integer> getReturnStack() {
		return returnStack;
	}

	public void setReturnStack(Stack<Integer> returnStack) {
		this.returnStack = returnStack;
	}

	public Stack<Word[]> getCodeStack() {
		return codeStack;
	}

	public void setCodeStack(Stack<Word[]> codeStack) {
		this.codeStack = codeStack;
	}

	public Object[] getHeap() {
		return heap;
	}

	public void setHeap(Object[] heap) {
		this.heap = heap;
	}

	public DefinedWord getCurrentDefinition() {
		return currentDefinition;
	}

	public void setCurrentDefinition(DefinedWord currentDefinition) {
		this.currentDefinition = currentDefinition;
	}

	public ArrayList<Word> getCurrentDefinitionWords() {
		return currentDefinitionWords;
	}

	public void setCurrentDefinitionWords(ArrayList<Word> currentDefinitionWords) {
		this.currentDefinitionWords = currentDefinitionWords;
	}

	public boolean isCompiling() {
		return isCompiling;
	}

	public void setCompiling(boolean isCompiling) {
		this.isCompiling = isCompiling;
	}

	public int getIp() {
		return ip;
	}

	public void setIp(int ip) {
		this.ip = ip;
	}

	public Word[] getCode() {
		return code;
	}

	public void setCode(Word[] code) {
		this.code = code;
	}

	public Parser getParser() {
		return parser;
	}

	public void setParser(Parser parser) {
		this.parser = parser;
	}

	public ComponentContainer getMainComponentContainer() {
		return mainComponentContainer;
	}

	public void setMainComponentContainer(
			ComponentContainer mainComponentContainer) {
		this.mainComponentContainer = mainComponentContainer;
	}

	public Blocks getBlocks() {
		return blocks;
	}

	public void setBlocks(Blocks blocks) {
		this.blocks = blocks;
	}

	public boolean isLogNewWords() {
		return logNewWords;
	}

	public void setLogNewWords(boolean logNewWords) {
		this.logNewWords = logNewWords;
	}

	public boolean isLogExecutedWords() {
		return logExecutedWords;
	}

	public void setLogExecutedWords(boolean logExecutedWords) {
		this.logExecutedWords = logExecutedWords;
	}

	public void createNewWord(String name) {
		if (isCompiling) {
			// TODO is this the right thing to do? "Specs" seem kinda vague
			finishCompilation();
		}
		currentDefinition = new DefinedWord();
		currentDefinition.setType(DefinedWord.Type.DEFINED);
		// wordListSelect.addItem();
		currentDefinition.setName(name);
		dictionary.put(name, currentDefinition);
		isCompiling = true;

	}

	/*
	 * Used to finish compilation of either colon definitions or stuff made with
	 * CREATE. Or anonymous colon definition.
	 */
	public void finishCompilation() {
		String name = currentDefinition.getName();
		currentDefinition.setCode(currentDefinitionWords
				.toArray(new DefinedWord[1]));
		if (logNewWords) {
			print("ADDED: " + name);
		}
		isCompiling = false;

		// if we just created an anonymous word, push it
		if ("_anonymous_".equals(name)) {
			dataStack.push(currentDefinition);
		}
	}

	public void executeDefinedWord(Word[] code) {
		codeStack.push(code);
		returnStack.push(ip);
		this.code = code;
		ip = 0;
		while (ip < code.length) {
			code[ip].execute(this);
			ip++;
		}
	}

}