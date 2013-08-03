package com.github.dynamicschema.android.reification.gen; 


import org.dynamicschema.annotation.Role; 
import org.dynamicschema.sql.RelationCondition; 
import org.dynamicschema.reification.Schema; 
import org.dynamicschema.reification.RelationMember; 
import org.dynamicschema.sql.SqlCondition; 
import org.dynamicschema.reification.Occurrence; 
import org.dynamicschema.reification.DBTable; 
import org.dynamicschema.reification.Relation; 
import org.dynamicschema.reification.Table; 
import java.util.Arrays; 
import org.dynamicschema.reification.ColumnModel; 
import org.dynamicschema.reification.columnconstraint.ColumnConstraint; 
import org.dynamicschema.reification.columnconstraint.PrimaryKey; 
import com.github.dynamicschema.android.sql.EmptyFilteringCondition; 
import java.util.List; 
import org.dynamicschema.reification.RelationModel; 
import org.dynamicschema.reification.ContextedTable; 
import java.util.ArrayList; 
import org.dynamicschema.reification.columnconstraint.ForeignKey; 
import org.dynamicschema.reification.Column; 
//import static android.provider.BaseColumns._ID; 



public class DayTable extends DBTable { 


	public static final String NAME = "Day"; 


	public static class DayColumns extends ColumnModel { 

		//tables column names
		public static String _ID = "_id"; 
		public static String DAYNAME = "dayName"; 

		public DayColumns() {
			setColumnsNames(Arrays.asList(_ID, DAYNAME)); 
			setColumnsConstraints(Arrays.asList((ColumnConstraint) new PrimaryKey(Arrays.asList("_id"))
)); 
		}
	}

	public DayTable(){
		super (NAME, new DayColumns()); 

	}

}
