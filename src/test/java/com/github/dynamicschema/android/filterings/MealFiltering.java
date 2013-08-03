package com.github.dynamicschema.android.filterings;

import org.dynamicschema.reification.Table;
import org.dynamicschema.sql.RelationCondition;
import org.dynamicschema.sql.SqlCondition;

import com.github.dynamicschema.android.reification.gen.MealTable.MealColumns;

public class MealFiltering extends RelationCondition {

	
	
	private int idMeal;

	public MealFiltering(int mealID, boolean inRestaurantTable) {
		
		this.idMeal = mealID;
	}
	
	/* (non-Javadoc)
	 * @see org.dynamicschema.sql.RelationCondition#eval(org.dynamicschema.reification.Table[])
	 */
	@Override
	public SqlCondition eval(Table... tables) {
		
		
		SqlCondition finalCond = new SqlCondition();
		SqlCondition locCond = null;

		for (int i = 0; i < tables.length; i++) {
			locCond = new SqlCondition().eq(tables[i].col(MealColumns._ID), this.idMeal);
			finalCond.and(locCond.toString());
		}

		return finalCond;	
		
	}
	

}
