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



public class RestaurantTable extends DBTable { 


	public static final String NAME = "Restaurant"; 


	public static class RestaurantColumns extends ColumnModel { 

		//tables column names
		public static String _ID = "_id"; 
		public static String FOOD_TYPE = "Food_Type"; 
		public static String AGE_MIN = "Age_min"; 
		public static String ID_REGION = "id_region"; 

		public RestaurantColumns() {
			setColumnsNames(Arrays.asList(_ID, FOOD_TYPE, AGE_MIN, ID_REGION)); 
			setColumnsConstraints(Arrays.asList((ColumnConstraint) new PrimaryKey(Arrays.asList("_id")),
 (ColumnConstraint)new ForeignKey(Arrays.asList("id_region"),
"Region",
Arrays.asList("_id")
)
)); 
		}
	}

	public RestaurantTable(){
		super (NAME, new RestaurantColumns()); 

	}

}
