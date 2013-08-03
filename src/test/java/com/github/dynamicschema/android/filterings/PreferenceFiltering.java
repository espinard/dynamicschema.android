/**
 * 
 */
package com.github.dynamicschema.android.filterings;

import org.dynamicschema.reification.Table;
import org.dynamicschema.sql.RelationCondition;
import org.dynamicschema.sql.SqlCondition;

import com.github.dynamicschema.android.reification.gen.PreferenceTable;

/**
 * @author esp
 *
 */
public class PreferenceFiltering extends RelationCondition{

	private boolean pref;
	private int clientID; 
	
	/**
	 * 
	 */
	public PreferenceFiltering(boolean prefers, int clientID) {
		this.pref = prefers;
		this.clientID = clientID;
	}

	
	/* (non-Javadoc)
	 * @see org.dynamicschema.sql.RelationCondition#eval(org.dynamicschema.reification.Table[])
	 */
	@Override
	public SqlCondition eval(Table... tables) {
		
		SqlCondition finalCond = new SqlCondition();
		SqlCondition secCond  = null;
		SqlCondition locCond = null;
		for (int i = 0; i < tables.length; i++) {
			
			String preference;
			if(this.pref)
				preference ="1";
			else
				preference = "0";
	
			
			locCond = new SqlCondition().eq(tables[i].col(PreferenceTable.PreferenceColumns.PREF), preference);
			secCond = new SqlCondition().eq(tables[i].col(PreferenceTable.PreferenceColumns.ID_CLIENT), this.clientID);
			locCond.and(secCond.toString());		
			finalCond.and(locCond.toString());
			
		}
		
		return finalCond;
	}


	
	

}
