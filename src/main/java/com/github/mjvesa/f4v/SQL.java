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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;

/**
 * 
 * Persistence layer. Simple stuff for building containers using SQL trough
 * JDBC.
 * 
 * @author mjvesa@vaadin.com
 * 
 */
public class SQL {

    private Object[] heap;
    private HashMap<String, DefinedWord> dictionary;

    public void setHeap(Object[] heap) {
        this.heap = heap;
    }

    public void setDictionary(HashMap<String, DefinedWord> dic) {
        dictionary = dic;
    }

    /**
     * Creates an indexed container by executing the supplied query.
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
    Object createIndexedContainerFromQuery(String query, boolean filtered) {
        IndexedContainer container = new IndexedContainer();
        try {
            Connection conn = getConnection();

            PreparedStatement st = conn.prepareStatement(query);
            if (filtered) {
                applyParameterListToPreparedStatement(st);
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

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return container;
    }

    /*
     * Can be used to do updates and inserts.
     */
    void doQuery(String sql) {

        try {
            Connection conn = getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            applyParameterListToPreparedStatement(st);
            st.execute();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Applies a list of parameters to a prepared statement. The parameters are
     * on the stack as a list.
     * 
     * @param st
     * @throws SQLException
     */
    private void applyParameterListToPreparedStatement(PreparedStatement st)
            throws SQLException {

        DefinedWord[] code = dictionary.get("list[").getCode();
        int addr = (Integer) code[0].getParam();
        int i = 1;
        while (heap[addr] != Util.LIST_TERMINATOR) {
            st.setObject(i, heap[addr]);
            addr++;
            i++;
        }
    }

    /**
     * Constructs an array from a list on the stack.
     * 
     * @param array
     *            Array whose type will be nicked.
     * 
     * @return
     */
    public <T> T[] getArrayFromList(T[] array) {

        ArrayList<Object> list = new ArrayList<Object>();

        // The first word of 'list[' is a literal with the address of our
        // list as parameter
        DefinedWord[] code = dictionary.get("list[").getCode();
        int addr = (Integer) code[0].getParam();

        while (heap[addr] != Util.LIST_TERMINATOR) {
            list.add(heap[addr]);
            addr++;
        }

        return list.toArray(array);
    }

    /**
     * Creates a jdbc connection.
     * 
     * @return A JDBC Connection that's ready for action.
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:hsqldb:mem:forth","forth","forth");
        return conn;
    }

}
