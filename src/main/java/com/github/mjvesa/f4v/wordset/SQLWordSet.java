package com.github.mjvesa.f4v.wordset;

import com.github.mjvesa.f4v.BaseWord;
import com.github.mjvesa.f4v.Interpreter;
import com.github.mjvesa.f4v.Word;

public class SQLWordSet extends WordSet {

	protected Word[] words = {

	new BaseWord("", "") {

		@Override
		public void execute(Interpreter interpreter) {
		}
	}

	};

	// /* Database stuff */
	// case CREATESQLCONTAINER:
	// str = (String) dataStack.pop();
	// dataStack.push(sql.createIndexedContainerFromQuery(str, false));
	// break;
	// case CREATEFILTEREDSQLCONTAINER:
	// str = (String) dataStack.pop();
	// dataStack.push(sql.createIndexedContainerFromQuery(str, true));
	// break;
	// case DOQUERY:
	// sql.doQuery((String) dataStack.pop());
	// break;
	// case GETPROPERTY:
	// str = (String) dataStack.pop();
	// item = (Item) dataStack.pop();
	// dataStack.push(item);
	// dataStack.push(item.getItemProperty(str).getValue());
	// break;
	// case SETPROPERTY:
	// str = (String) dataStack.pop();
	// str2 = (String) dataStack.pop();
	// item = (Item) dataStack.pop();
	// item.getItemProperty(str2).setValue(str);
	// break;
	//

}
