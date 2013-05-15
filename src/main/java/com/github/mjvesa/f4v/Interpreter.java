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
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Set;
import java.util.Stack;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;

/**
 * Meat of the program. This contains the outer and inner interpreters and
 * actually pretty much all of the logic.
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
	private DefinedWord currentDefinition; // These two are used to define new
											// words
	private ArrayList<CompiledWord> currentDefinitionWords;
	private boolean isCompiling;

	// ip and code are the defined word currently being executed
	private int ip;
	private CompiledWord[] code;

	private Parser parser;
	private ComponentContainer mainComponentContainer;
	private Blocks blocks;
	private GuiEventListener guiEventListener;
	private SQL sql;
	private boolean logNewWords;
	private boolean logExecutedWords;

	/**
	 * Default constructor
	 */
	public Interpreter() {
		logNewWords = false;
		logExecutedWords = false;
	}

	/**
	 * Does actual setting up of the interpreters.
	 */
	public void setup() {
		setUpStorage();
		fillDictionary();
		loadBuffers();
		print("<b>Executing core buffer...</b>");
		executeCore();

		sql = new SQL();
		sql.setDictionary(dictionary);
		sql.setHeap(heap);

	}

	public void setGuiEventListener(GuiEventListener listener) {
		guiEventListener = listener;
		mainComponentContainer = listener.getMainComponentContainer();
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

	private void executeCore() {
		runBuffer(source.get("Core"));
	}

	/**
	 * Initializes stacks, dictionary and heap
	 */
	private void setUpStorage() {

		dataStack = new Stack<Object>();
		returnStack = new Stack<Integer>();
		codeStack = new Stack<Word[]>();
		heap = new Object[2000];
		dictionary = new HashMap<String, Word>();
	}

	/**
	 * Pulls basic words from the list and
	 */
	private void fillDictionary() {

		// TODO install words instead

		// for (BaseWord bw : BaseWord.values()) {
		// DefinedWord word = new DefinedWord();
		// word.setType(DefinedWord.Type.BASE);
		// word.setBaseWord(bw);
		// word.setName(bw.getString());
		// guiEventListener.newWord(bw.getString());
		// dictionary.put(bw.getString(), word);
		// }

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

		isCompiling = false;
		parser = new Parser();
		parser.setString(str);

		String word = parser.getNextWord();

		while (!word.isEmpty()) {

			if (isCompiling) {
				compileWord(word);
			} else {

				if (dictionary.containsKey(word)) {
					Word w = dictionary.get(word);
					w.execute(this);
				} else {
					if (word.charAt(0) == '"') {
						dataStack.push(word.substring(1, word.length() - 1));
					} else if (Character.isDigit(word.charAt(0))) {
						dataStack.push(new Integer(Integer.parseInt(word)));
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

		if (dictionary.containsKey(word)) {
			Word w = dictionary.get(word);
			if (w.isImmediate()) {
				w.execute(this);
			} else {
				currentDefinitionWords.add(new CompiledWord(w));
			}
		} else {
			if (word.charAt(0) == '"') {
				generateLiteral(word.substring(1, word.length() - 1));
			} else if (Character.isDigit(word.charAt(0))) {
				generateLiteral(new Integer(Integer.parseInt(word)));
			} else {
				print("COMPILER ERROR: didn't quite get this: " + word
						+ parser.getPosition());
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

		word.execute(this);

	}

	/**
	 * Executes a defined word. This is done in the Interpreter so that the
	 * innards of the defined word are exposed.
	 * 
	 * @param word
	 */
	public void executeDefinedWord(DefinedWord word) {
		// TODO implement
	}

	/* Gets the next executable word and skips it in execution */
	private Word getNextExecutableWord() {
		CompiledWord[] words = this.code;
		CompiledWord w = words[ip + 1];
		ip++;
		return w;
	}

	/* This can be used to skip filling words */
	private String getNextNonNopWord() {

		String word = parser.getNextWord();
		while (!word.isEmpty()) {
			if (!"NOP".equals(dictionary.get(word).getName())) {
				return word;
			}
			word = parser.getNextWord();
		}
		return null;
	}

	/* Generates a word which pushes a literal onto the stack */
	public void generateLiteral(Object value) {
		Word w = dictionary.get("GENLITERAL");
		CompiledWord cw = new CompiledWord(w, value);
		currentDefinitionWords.add(cw);
	}

	public void create() {
		createNewWord(parser.getNextWord());
	}

	public void createFromStack() {
		createNewWord((String) dataStack.pop());
	}

	public void anonCreate() {
		createNewWord("_anonymous_");
	}

	private void createNewWord(String name) {
		if (isCompiling) {
			// TODO is this the right thing to do? "Specs" seem kinda vague
			finishCompilation();
		}
		currentDefinition = new DefinedWord();
		guiEventListener.newWord(name);
		// wordListSelect.addItem();
		currentDefinition.setName(name);
		dictionary.put(name, currentDefinition);
		currentDefinitionWords = new ArrayList<CompiledWord>();
		isCompiling = true;
	}

	/*
	 * Used to finish compilation of either colon definitions or stuff made with
	 * CREATE. Or anonymous colon definition.
	 */
	public void finishCompilation() {
		String name = currentDefinition.getName();
		currentDefinition.setCode(currentDefinitionWords.toArray(new Word[1]));
		if (logNewWords) {
			print("ADDED: " + name);
		}
		isCompiling = false;

		// if we just created an anonymous word, push it
		if ("_anonymous_".equals(name)) {
			dataStack.push(currentDefinition);
		}
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

	public void print(String msg) {
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

	public String getSource(String value) {
		return source.get(value);
	}

	public void continueExecution() {
		// TODO Auto-generated method stub

	}

	public void setLogNewWords(boolean b) {
		logNewWords = b;
	}

	public void setLogExecutedWords(boolean b) {
		logExecutedWords = b;
	}

	public HashMap<String, Word> getDictionary() {
		return dictionary;
	}

	public Object popData() {
		return dataStack.pop();
	}

	public void pushData(Object obj) {
		dataStack.push(obj);
	}

	public Object peekData() {
		return dataStack.peek();
	}

	public Integer popReturn() {
		return returnStack.pop();
	}

	public void pushReturn(Integer value) {
		returnStack.push(value);
	}

	public int getIp() {
		return ip;
	}

	public void setIp(int ip) {
		this.ip = ip;
	}

	public Word getExecutedWord() {
		return code[ip];
	}

	public int getCurrentDefinitionSize() {
		return currentDefinitionWords.size();
	}

	public CompiledWord getFromCurrentDefinition(int index) {
		return currentDefinitionWords.get(index);
	}

	public void addToCurrentDefinition(CompiledWord word) {
		currentDefinitionWords.add(word);

	}

	public CompiledWord[] getCode() {
		return code;
	}

	public Object peek(int index) {
		return heap[index];
	}

	public void poke(int index, Object value) {
		heap[index] = value;
	}

	public void setCompiling(boolean b) {
		isCompiling = b;

	}

	public Parser getParser() {
		return parser;
	}

	public DefinedWord getCurrentDefinition() {
		return currentDefinition;
	}

	public Object getCurrentParam() {
		return ((CompiledWord) getExecutedWord()).getParameter();
	}

	public SQL getSQL() {
		return sql;
	}

}