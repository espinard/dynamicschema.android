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



public class OpeningTable extends DBTable { 


	public static final String NAME = "Opening"; 


	public static class OpeningColumns extends ColumnModel { 

		//tables column names
		public static String ID_DAY = "id_day"; 
		public static String ID_RESTAURANT = "id_restaurant"; 
		public static String OPEN = "open"; 

		public OpeningColumns() {
			setColumnsNames(Arrays.asList(ID_DAY, ID_RESTAURANT, OPEN)); 
			setColumnsConstraints(Arrays.asList((ColumnConstraint) new PrimaryKey(Arrays.asList("id_restaurant" ,"id_day")),
 (ColumnConstraint)new ForeignKey(Arrays.asList("id_restaurant"),
"Restaurant",
Arrays.asList("_id")
),
 (ColumnConstraint)new ForeignKey(Arrays.asList("id_day"),
"Day",
Arrays.asList("_id")
)
)); 
		}
	}

	public OpeningTable(){
		super (NAME, new OpeningColumns()); 

	}

}
