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

/*
 * Forth Words. These can be built in atomic instructions or complex defined
 * words.
 */
public class DefinedWord {

    public enum Type {
        BASE, DEFINED, NOP
    };

    public enum BaseWord {
        /* Basic actions */
        LITERAL("LITERAL"), GENLITERAL(","), STORE("!"), LOAD("@"), ADD("+"), SUB(
                "-"), MUL("*"), DIV("/"), NOT("NOT"), DUP("DUP"), OVER("OVER"), ROT(
                "ROT"), MINUSROT("-ROT"), TWOSWAP("2SWAP"), NIP("NIP"), TUCK(
                "TUCK"), SWAP("SWAP"), DROP("DROP"), PRINT("."), CREATE(
                "CREATE"), STACKCREATE("STACKCREATE"), CREATENOP("CREATENOP"), IMMEDIATE(
                "IMMEDIATE"), COMPILE("COMPILE"), DOES("DOES>"), COLONCREATE(
                ":"), ANONCREATE("ANONCREATE"), FINISHCOMPILATION(";"), DO("DO"), LOOP(
                "LOOP"), BEGIN("BEGIN"), REPEAT("REPEAT"), WHILE("WHILE"), IF(
                "IF"), ENDIF("ENDIF"), ELSE("ELSE"), LESSTHANZERO("0<"), ZERO(
                "0="), GREATERTHANZERO("0>"), LESSTHAN("<"), EQUALS("="), GREATERTHAN(
                ">"), OBJEQUALS("EQUALS"), ISXT("ISXT"), WORDS("WORDS"), BEGINCOMMENT(
                "("), ENDCOMMENT(")"), BEGININTERPRET("["), ENDINTERPRET("]"), WORD(
                "WORD"), TICK("'"), BRACKETTICK("[']"), FIND("FIND"), EXECUTE(
                "EXECUTE"), STRTOINT("STRTOINT"), INTTOSTR("INTTOSTR"), CAT(
                "CAT"), NULL("NULL"), BREAKPOINT("BP"), LIST_TERMINATOR(
                "LIST_TERMINATOR"), EXECBUFFER("EXECBUFFER"), PRINTSTACK(
                "PRINTSTACK"), LOG("LOG"),
        /* Vaadin API words */
        NEWHL("newHl"), NEWVL("newVl"), NEWGL("newGl"), GLNEWLINE("glNewline"), SETCAPTION(
                "setCaption"), SETVALUE("setValue"), GETVALUE("getValue"), NEWBUTTON(
                "newButton"), SETCLICKLISTENER("setClickListener"), ADDCOMPONENT(
                "addComponent"), MAINPANEL("mainPanel"), ADDWINDOW("addWindow"), NEWWINDOW(
                "newWindow"), CLEARCONTAINER("clearContainer"), SETSIZEFULL(
                "setSizeFull"), SETSIZEUNDEFINED("setSizeUndefined"), SETWIDTH(
                "setWidth"), SETHEIGHT("setHeight"), NEWLABEL("newLabel"), NEWTEXTFIELD(
                "newTextField"), NEWDATEFIELD("newDateField"), NEWCHECKBOX(
                "newCheckBox"), NEWCOMBOBOX("newComboBox"), NEWSELECT(
                "newSelect"), NEWLISTSELECT("newListSelect"),
        /* Table */
        NEWTABLE("newTable"), SETCONTAINERDATASOURCE("setContainerDatasource"), SETCOLUMHEADERS(
                "setColumnHeaders"), SETVISIBLECOLUMNS("setVisibleColumns"),
        /* SQL queries and containers */
        CREATESQLCONTAINER("createSQLContainer"), CREATEFILTEREDSQLCONTAINER(
                "createFilteredSQLContainer"), DOQUERY("doQuery"), GETPROPERTY(
                "getProperty"), SETPROPERTY("setProperty");

        String s;

        private BaseWord(String s) {
            this.s = s;
        }

        public String getString() {
            return s;
        }
    };

    private String name;
    private DefinedWord[] code;
    private Type type;
    private BaseWord baseWord;
    private Object param;
    private boolean immediate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DefinedWord[] getCode() {
        return code;
    }

    public void setCode(DefinedWord[] code) {
        this.code = code;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public BaseWord getBaseWord() {
        return baseWord;
    }

    public void setBaseWord(BaseWord baseWord) {
        this.baseWord = baseWord;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public boolean isImmediate() {
        return immediate;
    }

    public void setImmediate(boolean immediate) {
        this.immediate = immediate;
    }

    public String toString() {
        return name;
    }

}
