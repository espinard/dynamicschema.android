/**
 * 
 */
package com.github.dynamicschema.android.filterings;

import org.dynamicschema.reification.Table;
import org.dynamicschema.sql.RelationCondition;
import org.dynamicschema.sql.Sql;
import org.dynamicschema.sql.SqlCondition;

import com.github.dynamicschema.android.reification.gen.AddressTable.AddressColumns;


/**
 * @author esp
 *
 */
public class LocationFiltering extends RelationCondition {

	
	private double latitude;
	private double longitude;
	private double range;
	/**
	 * 
	 */
	public LocationFiltering(double latitude, double longitude, double range) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.range = range;
	}
	
	
	/*
	 * Calculating Distance Between two coordinates using Taxicab geometry = Not the real geometry because SQLite do not support trigonometric functions 
	 * http://en.wikipedia.org/wiki/Taxicab_geometry
	 *  Distance = abs(latitude1 - latitude2) + abs(longitude1 - longitude2)
	 */
	

	/* (non-Javadoc)
	 * @see org.dynamicschema.sql.RelationCondition#eval(org.dynamicschema.reification.Table[])
	 */
	@Override
	public SqlCondition eval(Table... tables) {
		
		SqlCondition finalCond = new SqlCondition();
		SqlCondition locCond = null;
		for (int i = 0; i < tables.length; i++) {
			String distanceCalc = Sql.ABS+ "(" + tables[i].col(AddressColumns.LATITUDE)+  " - "+ this.latitude + ") + " + 
											Sql.ABS+ "(" + tables[i].col(AddressColumns.LONGITUDE)+  " - "+ this.longitude+ ")";
			
			locCond =new SqlCondition().lEq(distanceCalc, this.range);
			finalCond.and(locCond.toString());
		}
		
		return finalCond;
	}



}
