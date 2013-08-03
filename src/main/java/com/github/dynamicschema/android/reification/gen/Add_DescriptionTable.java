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



public class Add_DescriptionTable extends DBTable { 


	public static final String NAME = "Add_Description"; 


	public static class Add_DescriptionColumns extends ColumnModel { 

		//tables column names
		public static String ID_ADDRESS = "id_address"; 
		public static String ID_LANGUAGE = "id_language"; 
		public static String STREET_NAME = "Street_Name"; 
		public static String CITY_NAME = "City_Name"; 

		public Add_DescriptionColumns() {
			setColumnsNames(Arrays.asList(ID_ADDRESS, ID_LANGUAGE, STREET_NAME, CITY_NAME)); 
			setColumnsConstraints(Arrays.asList((ColumnConstraint) new PrimaryKey(Arrays.asList("id_language" ,"id_address")),
 (ColumnConstraint)new ForeignKey(Arrays.asList("id_language"),
"Language",
Arrays.asList("_id")
),
 (ColumnConstraint)new ForeignKey(Arrays.asList("id_address"),
"Address",
Arrays.asList("_id")
)
)); 
		}
	}

	public Add_DescriptionTable(){
		super (NAME, new Add_DescriptionColumns()); 

	}

}
