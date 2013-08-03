/**
 * 
 */
package com.github.dynamicschema.android.filterings;

import org.dynamicschema.reification.Table;
import org.dynamicschema.sql.RelationCondition;
import org.dynamicschema.sql.SqlCondition;

import com.github.dynamicschema.android.reification.gen.LanguageTable;

/**
 * @author esp
 *
 */
public class LanguageFiltering extends RelationCondition{


	private int langID;

	/**
	 * 
	 */
	public LanguageFiltering(int idLang) {
		this.langID = idLang;
	}

	/* (non-Javadoc)
	 * @see org.dynamicschema.sql.RelationCondition#eval(org.dynamicschema.reification.Table[])
	 */
	@Override
	public SqlCondition eval(Table... tables) {

		SqlCondition finalCond = new SqlCondition();
		SqlCondition locCond = null;

		for (int i = 0; i < tables.length; i++) {
			locCond = new SqlCondition().eq(tables[i].col(LanguageTable.LanguageColumns._ID), this.langID);
			finalCond.and(locCond.toString());
		}

		return finalCond;	
		
	}

}
