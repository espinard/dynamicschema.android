package com.github.dynamicschema.android.filterings;

import org.dynamicschema.reification.Table;
import org.dynamicschema.sql.RelationCondition;
import org.dynamicschema.sql.SqlCondition;

import com.github.dynamicschema.android.reification.gen.MealTable.MealColumns;
import com.github.dynamicschema.android.reification.gen.RestaurantTable.RestaurantColumns;



public class RestaurantFiltering extends RelationCondition {
	
	private int idRest;
	private boolean inMealTable;// determine to consider foreign key in meal Table or not
	
	/**
	 * @param idRest
	 * @param inMealTable
	 */
	public RestaurantFiltering(int idRest, boolean inMealTable) {
		this.idRest = idRest;
		this.inMealTable = inMealTable;
	}

	/* (non-Javadoc)
	 * @see org.dynamicschema.sql.RelationCondition#eval(org.dynamicschema.reification.Table[])
	 */
	@Override
	public SqlCondition eval(Table... tables) {
		
		SqlCondition finalCond = new SqlCondition();
		SqlCondition locCond = null;

		for (int i = 0; i < tables.length; i++) {
			
			if(this.inMealTable){
				locCond = new SqlCondition().eq(tables[i].col(MealColumns.ID_RESTAURANT), this.idRest);
			}else{ //In Restaurant table
				locCond = new SqlCondition().eq(tables[i].col(RestaurantColumns._ID), this.idRest);
			}
			finalCond.and(locCond.toString());

		}

		return finalCond;	
	}

	
	
	
}