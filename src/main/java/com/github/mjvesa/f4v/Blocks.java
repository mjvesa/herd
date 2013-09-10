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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Manages blocks, which are regular files. This class is named blocks only for
 * amusement of the historical variety.
 * 
 * @author mjvesa@vaadin.com
 * 
 */
public class Blocks implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2085093837272288299L;
	// This needs to be the directory Forth files are stored in
    public static final String BLOCK_DIRECTORY = "/home/mjvesa/f4v/forth/";

    public HashMap<String, String> loadBuffers() {
        HashMap<String, String> blocks = new HashMap<String, String>();

        File dir = new File(BLOCK_DIRECTORY);
        File[] files = dir.listFiles();
        for (File file : files) {
            String name = file.getName();
            FileReader fr;
            try {
                fr = new FileReader(file);
                StringBuffer sb = new StringBuffer();
                char[] chars = new char[100];

                while (fr.ready()) {
                    int count = fr.read(chars);
                    sb.append(chars, 0, count);
                }

                blocks.put(name, sb.toString());
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return blocks;
    }

    /**
     * Saves a buffer by the specified name.
     * @param name
     * @param content
     */
    public void saveBuffer(String name, String content) {
    	
    	StringBuffer cleanName = new StringBuffer();
    	
    	Pattern p = Pattern.compile("[a-zA-Z]");
    	for (char ch : name.toCharArray()) {
        	Matcher m = p.matcher(Character.toString(ch));    		
    		if (m.matches())
    		cleanName.append(ch);
    	}
    	
		try {
			File file = new File(BLOCK_DIRECTORY + cleanName.toString());
			if (file.createNewFile()) {
				FileWriter fw = new FileWriter(file);
				fw.write(content);
				fw.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
