package com.github.mjvesa.f4v;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import com.github.mjvesa.f4v.Interpreter.GuiEventListener;
import com.vaadin.ui.ComponentContainer;

/**
 * Contains the current interpreter state. 
 *  
 * @author mjvesa
 *
 */
public class State {
	
    private HashMap<String, Word> dictionary;
    private HashMap<String, String> source;
    private Stack<Object> dataStack;
    private Stack<Integer> returnStack;
    private Stack<Word[]> codeStack;
    private Object[] heap;
    private DefinedWord currentDefinition;
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
	public void setMainComponentContainer(ComponentContainer mainComponentContainer) {
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
	private ArrayList<Word> currentDefinitionWords;
    private boolean isCompiling;
    private int ip;
    private Word[] code;
    private Parser parser;
    private ComponentContainer mainComponentContainer;
    private Blocks blocks;
    private boolean logNewWords;
    private boolean logExecutedWords;
    
    

}
