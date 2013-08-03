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




public class GourmetSchema extends Schema{
	public GourmetSchema(){
		List<DBTable> tables= initTables(); 
		initFilterings(tables,new EmptyFilteringCondition()); 
		GourmetRelationModel model=new GourmetRelationModel(); 
		model.updateTables(tables); 
		setTables(tables); 
		setRelationModel(model); 
	}
private List<DBTable> initTables(){
		List<DBTable> tables= new ArrayList<DBTable>(); 
		tables.add(new Add_DescriptionTable()); 
		tables.add(new AddressTable()); 
		tables.add(new BelongingTable()); 
		tables.add(new ChefTable()); 
		tables.add(new ClientTable()); 
		tables.add(new ContenanceTable()); 
		tables.add(new DayTable()); 
		tables.add(new DescriptionTable()); 
		tables.add(new Description2Table()); 
		tables.add(new IngredientTable()); 
		tables.add(new LanguageTable()); 
		tables.add(new MealTable()); 
		tables.add(new Meal_RatingTable()); 
		tables.add(new MealOfDayTable()); 
		tables.add(new OpeningTable()); 
		tables.add(new PreferenceTable()); 
		tables.add(new ProhibitionTable()); 
		tables.add(new RegionTable()); 
		tables.add(new RequirementTable()); 
		tables.add(new RestaurantTable()); 
		tables.add(new RestoDescriptionTable()); 
		tables.add(new SeasonTable()); 
		tables.add(new TasteTable()); 
		tables.add(new Taste_BelongingTable()); 
		tables.add(new Taste_PreferenceTable()); 
		tables.add(new User_ConstraintTable()); 
		tables.add(new VisitTable()); 
return tables; 
}


//Default method. Set your desired initial filtering != null
	private void initFilterings(List<DBTable> tables,RelationCondition filtering){
		for(DBTable dbTable : tables){
			dbTable.setFiltering(filtering); 
		}
}




} //End of GourmetSchema