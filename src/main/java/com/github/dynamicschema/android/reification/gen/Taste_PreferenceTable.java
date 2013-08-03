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



public class Taste_PreferenceTable extends DBTable { 


	public static final String NAME = "Taste_Preference"; 


	public static class Taste_PreferenceColumns extends ColumnModel { 

		//tables column names
		public static String ID_TASTE = "id_taste"; 
		public static String ID_CLIENT = "id_client"; 

		public Taste_PreferenceColumns() {
			setColumnsNames(Arrays.asList(ID_TASTE, ID_CLIENT)); 
			setColumnsConstraints(Arrays.asList((ColumnConstraint) new PrimaryKey(Arrays.asList("id_taste" ,"id_client")),
 (ColumnConstraint)new ForeignKey(Arrays.asList("id_client"),
"Client",
Arrays.asList("_id")
),
 (ColumnConstraint)new ForeignKey(Arrays.asList("id_taste"),
"Taste",
Arrays.asList("_id")
)
)); 
		}
	}

	public Taste_PreferenceTable(){
		super (NAME, new Taste_PreferenceColumns()); 

	}

}
