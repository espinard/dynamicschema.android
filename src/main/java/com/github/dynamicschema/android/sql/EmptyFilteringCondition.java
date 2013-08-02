package com.github.dynamicschema.android.sql;

import org.dynamicschema.reification.Table;
import org.dynamicschema.sql.RelationCondition;
import org.dynamicschema.sql.SqlCondition;

public class EmptyFilteringCondition extends RelationCondition {
		

	public SqlCondition eval(Table ...tables) {
		return new SqlCondition(new String[]{""});
	}

}
