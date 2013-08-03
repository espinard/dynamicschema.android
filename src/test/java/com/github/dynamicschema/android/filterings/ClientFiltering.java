package com.github.dynamicschema.android.filterings;

import org.dynamicschema.reification.Table;
import org.dynamicschema.sql.RelationCondition;
import org.dynamicschema.sql.SqlCondition;

import com.github.dynamicschema.android.reification.gen.ClientTable.ClientColumns;

public class ClientFiltering extends RelationCondition {

	private int idClient;
	
	public ClientFiltering(int clientID) {
		this.idClient = clientID;
	}

	/* (non-Javadoc)
	 * @see org.dynamicschema.sql.RelationCondition#eval(org.dynamicschema.reification.Table[])
	 */
	@Override
	public SqlCondition eval(Table... tables) {
		
		
		SqlCondition finalCond = new SqlCondition();
		SqlCondition locCond = null;

		for (int i = 0; i < tables.length; i++) {
			locCond = new SqlCondition().eq(tables[i].col(ClientColumns._ID), this.idClient);
			finalCond.and(locCond.toString());
		}

		return finalCond;	
		
	}
	
	


}
