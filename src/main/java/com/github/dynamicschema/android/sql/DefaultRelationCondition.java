package com.github.dynamicschema.android.sql;

import org.dynamicschema.annotation.Role;
import org.dynamicschema.reification.Table;
import org.dynamicschema.sql.RelationCondition;
import org.dynamicschema.sql.SqlCondition;


public class DefaultRelationCondition extends RelationCondition{

	
	private Table tabParent; // The parent table has the primary key which is referenced in the child table
	private Table tabChild;  // The child table has foreign key referencing a primary key of parent table
	private String colParent;
	private String colChild;


	public DefaultRelationCondition(Table tableParent, Table tableChild, String colTabParent, String colTabChild){
		super();
		this.tabParent = tableParent;
		this.tabChild = tableChild;
		this.colParent = colTabParent;
		this.colChild = colTabChild;
	}

	//TODO incorporate information about roles (Use both conceptual and relational schema for getting information
	public SqlCondition eval(@Role("roleTableParent") Table tabParent, @Role("roleTableChild") Table tabChild) {
		return new SqlCondition().eq(tabParent.col(colParent), tabChild.col(colChild)); 
		// in this case the join condition is a regular match of foreign keys and primary keys, but it can be of arbitrary complexity
	}


	/**
	 * @return the tabParent
	 */
	public Table getTabParent() {
		return tabParent;
	}

	/**
	 * @return the tabChild
	 */
	public Table getTabChild() {
		return tabChild;
	}

	/**
	 * @return the colParent
	 */
	public String getColParent() {
		return colParent;
	}

	/**
	 * @return the colChild
	 */
	public String getColChild() {
		return colChild;
	}
}
