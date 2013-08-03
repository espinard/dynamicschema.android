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



public class AddressTable extends DBTable { 


	public static final String NAME = "Address"; 


	public static class AddressColumns extends ColumnModel { 

		//tables column names
		public static String _ID = "_id"; 
		public static String ID_RESTAURANT = "id_restaurant"; 
		public static String POSTAL_CODE = "Postal_Code"; 
		public static String STREET_NUMBER = "Street_Number"; 
		public static String LATITUDE = "Latitude"; 
		public static String LONGITUDE = "Longitude"; 
		public static String COUNTRY = "Country"; 

		public AddressColumns() {
			setColumnsNames(Arrays.asList(_ID, ID_RESTAURANT, POSTAL_CODE, STREET_NUMBER, LATITUDE, LONGITUDE, COUNTRY)); 
			setColumnsConstraints(Arrays.asList((ColumnConstraint) new PrimaryKey(Arrays.asList("_id")),
 (ColumnConstraint)new ForeignKey(Arrays.asList("id_restaurant"),
"Restaurant",
Arrays.asList("_id")
)
)); 
		}
	}

	public AddressTable(){
		super (NAME, new AddressColumns()); 

	}

}
