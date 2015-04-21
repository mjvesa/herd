package com.github.mjvesa.herd;

public class CompiledWord extends Word {

    private static final long serialVersionUID = 348059460007992869L;
    private Word word;
    private Object parameter;
    private int line;
    private int col;

    public CompiledWord() {

    }

    public CompiledWord(Word word, Object parameter) {
        this.word = word;
        this.parameter = parameter;
    }

    public CompiledWord(Word word, int line, int col) {
        this.word = word;
        this.parameter = null;
        this.line = line;
        this.col = col;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Object getParameter() {
        return parameter;
    }

    public void setParameter(Object parameter) {
        this.parameter = parameter;
    }

    @Override
    public void execute(Interpreter interpreter) {
        word.execute(interpreter);
    }

    public boolean isImmediate() {
        return word.isImmediate();
    }

    public void setImmediate(boolean immediate) {
        word.setImmediate(immediate);
    }

    public String getName() {
        return word.getName();
    }

    public void setName(String name) {
        word.setName(name);
    }

    public String getDescription() {
        return word.getDescription();
    }

    public void setDescription(String description) {
        word.setDescription(description);
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    
}
