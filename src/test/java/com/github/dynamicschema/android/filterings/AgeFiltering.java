package com.github.dynamicschema.android.filterings;

import org.dynamicschema.reification.Table;
import org.dynamicschema.sql.RelationCondition;
import org.dynamicschema.sql.SqlCondition;

import com.github.dynamicschema.android.reification.gen.RestaurantTable.RestaurantColumns;

public class AgeFiltering extends RelationCondition {


	private int age;
	
	
	
	/**
	 * @param age
	 */
	public AgeFiltering(int age) {
		this.age = age;
	}

	
	@Override
	public SqlCondition eval(Table... tables) {
		
	
		SqlCondition finalCond = new SqlCondition();
		SqlCondition locCond = null;

		for (int i = 0; i < tables.length; i++) {
			locCond = new SqlCondition().lEq(tables[i].col(RestaurantColumns.AGE_MIN), this.age);
			finalCond.and(locCond.toString());
		}

		return finalCond;	
		
	}
	

}
