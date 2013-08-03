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



public class PreferenceTable extends DBTable { 


	public static final String NAME = "Preference"; 


	public static class PreferenceColumns extends ColumnModel { 

		//tables column names
		public static String ID_INGREDIENT = "id_ingredient"; 
		public static String ID_CLIENT = "id_client"; 
		public static String PREF = "pref"; 

		public PreferenceColumns() {
			setColumnsNames(Arrays.asList(ID_INGREDIENT, ID_CLIENT, PREF)); 
			setColumnsConstraints(Arrays.asList((ColumnConstraint) new PrimaryKey(Arrays.asList("id_ingredient" ,"id_client")),
 (ColumnConstraint)new ForeignKey(Arrays.asList("id_client"),
"Client",
Arrays.asList("_id")
),
 (ColumnConstraint)new ForeignKey(Arrays.asList("id_ingredient"),
"Ingredient",
Arrays.asList("_id")
)
)); 
		}
	}

	public PreferenceTable(){
		super (NAME, new PreferenceColumns()); 

	}

}
