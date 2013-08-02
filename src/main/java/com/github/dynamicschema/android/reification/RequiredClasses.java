/*
 * Encapsulate the collection of classes that are during generation
 */

package com.github.dynamicschema.android.reification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dynamicschema.annotation.Role;
import org.dynamicschema.reification.Column;
import org.dynamicschema.reification.ColumnModel;
import org.dynamicschema.reification.ContextedTable;
import org.dynamicschema.reification.DBTable;
import org.dynamicschema.reification.Occurrence;
import org.dynamicschema.reification.Relation;
import org.dynamicschema.reification.RelationMember;
import org.dynamicschema.reification.RelationModel;
import org.dynamicschema.reification.Schema;
import org.dynamicschema.reification.Table;
import org.dynamicschema.reification.columnconstraint.ColumnConstraint;
import org.dynamicschema.reification.columnconstraint.ForeignKey;
import org.dynamicschema.reification.columnconstraint.PrimaryKey;
import org.dynamicschema.sql.RelationCondition;
import org.dynamicschema.sql.SqlCondition;

import com.github.dynamicschema.android.sql.EmptyFilteringCondition;

public class RequiredClasses {

	
	private static Set<Class> reqClassesDBTables = null;
	
	/*
	 * Method that initialiazes the set of required classes. These classes are known in advance
	 *  
	 */
	private static Set<Class> initializeRequiredClassesDBTables(){	
		Set<Class> reqClasses = new HashSet<Class>();
		reqClasses.add(RelationModel.class);
		reqClasses.add(Relation.class);
		reqClasses.add(RelationMember.class);
		reqClasses.add(RelationCondition.class);
		reqClasses.add(SqlCondition.class);
		reqClasses.add(Role.class);
		reqClasses.add(ContextedTable.class);
		reqClasses.add(Occurrence.class);
		reqClasses.add(Column.class);
		reqClasses.add(ColumnConstraint.class);
		reqClasses.add(ForeignKey.class);
		reqClasses.add(PrimaryKey.class);
		reqClasses.add(ColumnModel.class);
		reqClasses.add(DBTable.class);
		reqClasses.add(List.class);
		reqClasses.add(Arrays.class);
		reqClasses.add(Table.class);
		reqClasses.add(EmptyFilteringCondition.class);
		reqClasses.add(ArrayList.class);
		reqClasses.add(Schema.class);
		return reqClasses;
	}
	
	
	/*
	 * Singleton getter
	 */
	public static Set<Class> getReqClasses(){
		if(reqClassesDBTables == null)
			reqClassesDBTables = initializeRequiredClassesDBTables();
		return reqClassesDBTables;
	}

	
	//TODO Same for the classes of the relational model

}
