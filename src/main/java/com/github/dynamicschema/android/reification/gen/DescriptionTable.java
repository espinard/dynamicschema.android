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



public class DescriptionTable extends DBTable { 


	public static final String NAME = "Description"; 


	public static class DescriptionColumns extends ColumnModel { 

		//tables column names
		public static String ID_MEAL = "id_meal"; 
		public static String ID_LANGUAGE = "id_language"; 
		public static String DESCRIPTION = "description"; 

		public DescriptionColumns() {
			setColumnsNames(Arrays.asList(ID_MEAL, ID_LANGUAGE, DESCRIPTION)); 
			setColumnsConstraints(Arrays.asList((ColumnConstraint) new PrimaryKey(Arrays.asList("id_meal" ,"id_language")),
 (ColumnConstraint)new ForeignKey(Arrays.asList("id_language"),
"Language",
Arrays.asList("_id")
),
 (ColumnConstraint)new ForeignKey(Arrays.asList("id_meal"),
"Meal",
Arrays.asList("_id")
)
)); 
		}
	}

	public DescriptionTable(){
		super (NAME, new DescriptionColumns()); 

	}

}
