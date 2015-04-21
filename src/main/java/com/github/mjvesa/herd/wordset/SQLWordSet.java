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
package com.github.mjvesa.herd.wordset;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.github.mjvesa.herd.BaseWord;
import com.github.mjvesa.herd.CompiledWord;
import com.github.mjvesa.herd.DefinedWord;
import com.github.mjvesa.herd.Interpreter;
import com.github.mjvesa.herd.Util;
import com.github.mjvesa.herd.Word;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;

public class SQLWordSet extends WordSet {

    @Override
    public Word[] getWords() {
        return new Word[] {

        new BaseWord("create-SQL-container", "", Word.POSTPONED) {
            private static final long serialVersionUID = -8555637158184948846L;

            @Override
            public void execute(Interpreter interpreter) {

                String str = (String) interpreter.popData();
                interpreter.pushData(createIndexedContainerFromQuery(
                        interpreter, str, false));

            }
        },

        new BaseWord("create-filtered-SQL-container", "", Word.POSTPONED) {
            private static final long serialVersionUID = 3651759768848932939L;

            @Override
            public void execute(Interpreter interpreter) {
                String str = (String) interpreter.popData();
                interpreter.pushData(createIndexedContainerFromQuery(
                        interpreter, str, true));
            }
        },

        new BaseWord("do-query", "", Word.POSTPONED) {
            private static final long serialVersionUID = 7935650989639855151L;

            @Override
            public void execute(Interpreter interpreter) {
                doQuery(interpreter, (String) interpreter.popData());
            }
        },

        new BaseWord("get-property", "", Word.POSTPONED) {
            private static final long serialVersionUID = -2919657094395490760L;

            @Override
            public void execute(Interpreter interpreter) {
                String str = (String) interpreter.popData();
                Item item = (Item) interpreter.popData();
                interpreter.pushData(item);
                interpreter.pushData(item.getItemProperty(str).getValue());
            }
        },

        new BaseWord("set-property", "", Word.POSTPONED) {
            private static final long serialVersionUID = 2414557842750730115L;

            @Override
            public void execute(Interpreter interpreter) {
                String value = (String) interpreter.popData();
                String property = (String) interpreter.popData();
                Item item = (Item) interpreter.popData();
                item.getItemProperty(property).setValue(value);
            }
        }

        };
    }

    /**
     * Creates an indexed container by executing the supplied query.
     * 
     * @param interpreter
     * 
     * @param query
     *            SQL query to execute
     * @param filtered
     *            Tells us if this query is a filtered one, which means it has
     *            parameters. if true, parameters are loaded from stack,
     *            otherwise the query is executed without parameters.
     * 
     * @return
     */
    public Object createIndexedContainerFromQuery(Interpreter interpreter,
            String query, boolean filtered) {
        IndexedContainer container = new IndexedContainer();
        try {
            Connection conn = getConnection();

            PreparedStatement st = conn.prepareStatement(query);
            if (filtered) {
                applyParameterListToPreparedStatement(interpreter, st);
            }
            ResultSet rs = st.executeQuery();

            // Initialize container
            ResultSetMetaData meta = rs.getMetaData();
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                String name = meta.getColumnName(i);
                Class clazz = Object.class;

                switch (meta.getColumnType(i)) {
                case java.sql.Types.VARCHAR:
                case java.sql.Types.LONGVARCHAR:
                    clazz = String.class;
                    break;
                case java.sql.Types.INTEGER:
                    clazz = Integer.class;
                    break;
                default:
                    clazz = String.class;
                    break;
                }
                container.addContainerProperty(name, clazz, null);
            }
            // Go trough all the nasty (what?)
            while (rs.next()) {
                Object id = container.addItem();
                Item item = container.getItem(id);
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String name = meta.getColumnName(i);

                    switch (meta.getColumnType(i)) {
                    case java.sql.Types.VARCHAR:
                    case java.sql.Types.LONGVARCHAR:
                        String s = rs.getString(i);
                        item.getItemProperty(name).setValue(s);
                        break;
                    case java.sql.Types.INTEGER:
                        Integer value = rs.getInt(i);
                        item.getItemProperty(name).setValue(value);
                        break;
                    }
                }
            }
            st.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return container;
    }

    /**
     * Applies a list of parameters to a prepared statement. The parameters are
     * on the stack as a list.
     * 
     * @param interpreter
     * 
     * @param st
     * @throws SQLException
     */
    private void applyParameterListToPreparedStatement(
            Interpreter interpreter, PreparedStatement st)
            throws SQLException {

        Word[] code = ((DefinedWord) interpreter.getDictionary().get("list["))
                .getCode();
        int addr = (Integer) ((CompiledWord) code[0]).getParameter();
        int i = 1;
        while (interpreter.peekHeap(addr) != Util.LIST_TERMINATOR) {
            st.setObject(i, interpreter.peekHeap(addr));
            addr++;
            i++;
        }
    }

    /**
     * Creates a jdbc connection.
     * 
     * @return A JDBC Connection that's ready for action.
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:hsqldb:mem:herd",
                "herd", "herd");
        return conn;
    }

    /*
     * Can be used to do updates and inserts.
     */
    public void doQuery(Interpreter interpreter, String sql) {

        try {
            Connection conn = getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            applyParameterListToPreparedStatement(interpreter, st);
            st.execute();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
