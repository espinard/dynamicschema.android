/**
 * 
 */
package com.github.dynamicschema.android.filterings;

import org.dynamicschema.reification.Table;
import org.dynamicschema.sql.RelationCondition;
import org.dynamicschema.sql.Sql;
import org.dynamicschema.sql.SqlCondition;

import com.github.dynamicschema.android.reification.gen.SeasonTable.SeasonColumns;

/**
 * @author esp
 *
 */
public class SeasonRangeFiltering  extends RelationCondition{

	
	
	/**
	 * 
	 */
	public SeasonRangeFiltering() {

	}

	/* (non-Javadoc)
	 * @see org.dynamicschema.sql.RelationCondition#eval(org.dynamicschema.reification.Table[])
	 */
	@Override
	public SqlCondition eval(Table... tables) {
		
		SqlCondition finalCond = new SqlCondition();
		SqlCondition locCond = null;
		String cond ="";
		for (int i = 0; i < tables.length; i++) {
			String left = tables[i].col(SeasonColumns.RAN_BEGIN);
			String right = Sql.DATE+ "(" + Sql.NOW + ")";
			locCond = new SqlCondition().lEq(left, right);
			
			left= tables[i].col(SeasonColumns.RAN_END);
			cond = new SqlCondition().gEq(left, right).toString();
			locCond.and(cond);
			
			finalCond.and(locCond.toString());
		}
		
		return finalCond;
	}



}
