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


import com.github.dynamicschema.android.reification.gen.Add_DescriptionTable.Add_DescriptionColumns; 
import com.github.dynamicschema.android.reification.gen.Add_DescriptionTable; 
import com.github.dynamicschema.android.reification.gen.LanguageTable.LanguageColumns; 
import com.github.dynamicschema.android.reification.gen.LanguageTable; 
import com.github.dynamicschema.android.reification.gen.AddressTable.AddressColumns; 
import com.github.dynamicschema.android.reification.gen.AddressTable; 
import com.github.dynamicschema.android.reification.gen.RestaurantTable.RestaurantColumns; 
import com.github.dynamicschema.android.reification.gen.RestaurantTable; 
import com.github.dynamicschema.android.reification.gen.BelongingTable.BelongingColumns; 
import com.github.dynamicschema.android.reification.gen.BelongingTable; 
import com.github.dynamicschema.android.reification.gen.SeasonTable.SeasonColumns; 
import com.github.dynamicschema.android.reification.gen.SeasonTable; 
import com.github.dynamicschema.android.reification.gen.IngredientTable.IngredientColumns; 
import com.github.dynamicschema.android.reification.gen.IngredientTable; 
import com.github.dynamicschema.android.reification.gen.ChefTable.ChefColumns; 
import com.github.dynamicschema.android.reification.gen.ChefTable; 
import com.github.dynamicschema.android.reification.gen.ClientTable.ClientColumns; 
import com.github.dynamicschema.android.reification.gen.ClientTable; 
import com.github.dynamicschema.android.reification.gen.ContenanceTable.ContenanceColumns; 
import com.github.dynamicschema.android.reification.gen.ContenanceTable; 
import com.github.dynamicschema.android.reification.gen.MealTable.MealColumns; 
import com.github.dynamicschema.android.reification.gen.MealTable; 
import com.github.dynamicschema.android.reification.gen.DescriptionTable.DescriptionColumns; 
import com.github.dynamicschema.android.reification.gen.DescriptionTable; 
import com.github.dynamicschema.android.reification.gen.Description2Table.Description2Columns; 
import com.github.dynamicschema.android.reification.gen.Description2Table; 
import com.github.dynamicschema.android.reification.gen.Meal_RatingTable.Meal_RatingColumns; 
import com.github.dynamicschema.android.reification.gen.Meal_RatingTable; 
import com.github.dynamicschema.android.reification.gen.MealOfDayTable.MealOfDayColumns; 
import com.github.dynamicschema.android.reification.gen.MealOfDayTable; 
import com.github.dynamicschema.android.reification.gen.OpeningTable.OpeningColumns; 
import com.github.dynamicschema.android.reification.gen.OpeningTable; 
import com.github.dynamicschema.android.reification.gen.DayTable.DayColumns; 
import com.github.dynamicschema.android.reification.gen.DayTable; 
import com.github.dynamicschema.android.reification.gen.PreferenceTable.PreferenceColumns; 
import com.github.dynamicschema.android.reification.gen.PreferenceTable; 
import com.github.dynamicschema.android.reification.gen.ProhibitionTable.ProhibitionColumns; 
import com.github.dynamicschema.android.reification.gen.ProhibitionTable; 
import com.github.dynamicschema.android.reification.gen.User_ConstraintTable.User_ConstraintColumns; 
import com.github.dynamicschema.android.reification.gen.User_ConstraintTable; 
import com.github.dynamicschema.android.reification.gen.RegionTable.RegionColumns; 
import com.github.dynamicschema.android.reification.gen.RegionTable; 
import com.github.dynamicschema.android.reification.gen.RequirementTable.RequirementColumns; 
import com.github.dynamicschema.android.reification.gen.RequirementTable; 
import com.github.dynamicschema.android.reification.gen.RestoDescriptionTable.RestoDescriptionColumns; 
import com.github.dynamicschema.android.reification.gen.RestoDescriptionTable; 
import com.github.dynamicschema.android.reification.gen.Taste_BelongingTable.Taste_BelongingColumns; 
import com.github.dynamicschema.android.reification.gen.Taste_BelongingTable; 
import com.github.dynamicschema.android.reification.gen.TasteTable.TasteColumns; 
import com.github.dynamicschema.android.reification.gen.TasteTable; 
import com.github.dynamicschema.android.reification.gen.Taste_PreferenceTable.Taste_PreferenceColumns; 
import com.github.dynamicschema.android.reification.gen.Taste_PreferenceTable; 
import com.github.dynamicschema.android.reification.gen.VisitTable.VisitColumns; 
import com.github.dynamicschema.android.reification.gen.VisitTable; 


public  class GourmetRelationModel extends RelationModel { 


public GourmetRelationModel(){
List<Relation> relations = Arrays.asList(
GourmetRelationModel.Add_DescriptionRelations.rel_Language_Add_Description,
GourmetRelationModel.Add_DescriptionRelations.rel_Address_Add_Description,
GourmetRelationModel.AddressRelations.rel_Restaurant_Address,
GourmetRelationModel.BelongingRelations.rel_Season_Belonging,
GourmetRelationModel.BelongingRelations.rel_Ingredient_Belonging,
GourmetRelationModel.ChefRelations.rel_Restaurant_Chef,
GourmetRelationModel.ClientRelations.rel_Language_Client,
GourmetRelationModel.ContenanceRelations.rel_Meal_Contenance,
GourmetRelationModel.ContenanceRelations.rel_Ingredient_Contenance,
GourmetRelationModel.DescriptionRelations.rel_Language_Description,
GourmetRelationModel.DescriptionRelations.rel_Meal_Description,
GourmetRelationModel.Description2Relations.rel_Language_Description2,
GourmetRelationModel.Description2Relations.rel_Ingredient_Description2,
GourmetRelationModel.MealRelations.rel_Restaurant_Meal,
GourmetRelationModel.Meal_RatingRelations.rel_Client_Meal_Rating,
GourmetRelationModel.Meal_RatingRelations.rel_Meal_Meal_Rating,
GourmetRelationModel.MealOfDayRelations.rel_Restaurant_MealOfDay,
GourmetRelationModel.MealOfDayRelations.rel_Meal_MealOfDay,
GourmetRelationModel.OpeningRelations.rel_Restaurant_Opening,
GourmetRelationModel.OpeningRelations.rel_Day_Opening,
GourmetRelationModel.PreferenceRelations.rel_Client_Preference,
GourmetRelationModel.PreferenceRelations.rel_Ingredient_Preference,
GourmetRelationModel.ProhibitionRelations.rel_User_Constraint_Prohibition,
GourmetRelationModel.ProhibitionRelations.rel_Ingredient_Prohibition,
GourmetRelationModel.RegionRelations.rel_Region_Region,
GourmetRelationModel.RequirementRelations.rel_Client_Requirement,
GourmetRelationModel.RequirementRelations.rel_User_Constraint_Requirement,
GourmetRelationModel.RestaurantRelations.rel_Region_Restaurant,
GourmetRelationModel.RestoDescriptionRelations.rel_Restaurant_RestoDescription,
GourmetRelationModel.RestoDescriptionRelations.rel_Language_RestoDescription,
GourmetRelationModel.Taste_BelongingRelations.rel_Taste_Taste_Belonging,
GourmetRelationModel.Taste_BelongingRelations.rel_Ingredient_Taste_Belonging,
GourmetRelationModel.Taste_PreferenceRelations.rel_Client_Taste_Preference,
GourmetRelationModel.Taste_PreferenceRelations.rel_Taste_Taste_Preference,
GourmetRelationModel.VisitRelations.rel_Client_Visit,
GourmetRelationModel.VisitRelations.rel_Restaurant_Visit
); 
setRelations(relations); 
}

public void updateTables(List<DBTable> tables){


	for (DBTable dbTable : tables){
		updateAllRelationMembers(dbTable); 
	}
}


private void updateAllRelationMembers(DBTable table){
	for(Relation relation : this.getRelations()){
		List<RelationMember> members=relation.getRelationMembers(); 

		for(RelationMember member : members){
			if(member.getTable().getName().equals(table.getName()))
				member.setTable(table); 
		}
	}
}



public static class Add_DescriptionRelations extends RelationModel { 
public static final String LANGUAGE_ADD_DESCRIPTION = "Language has (or is in) a Add_Description"; 

public static Relation rel_Language_Add_Description = new Relation(LANGUAGE_ADD_DESCRIPTION,
Arrays.asList(new RelationMember(new LanguageTable(),Occurrence.ONE),
new RelationMember(new Add_DescriptionTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval( Table language,  Table add_description){//Add annotation if needed
		return new SqlCondition().eq(language.col(LanguageColumns._ID),add_description.col(Add_DescriptionColumns.ID_LANGUAGE)); 
	}}); 



public static final String ADDRESS_ADD_DESCRIPTION = "Address has (or is in) a Add_Description"; 

public static Relation rel_Address_Add_Description = new Relation(ADDRESS_ADD_DESCRIPTION,
Arrays.asList(new RelationMember(new AddressTable(),Occurrence.ONE),
new RelationMember(new Add_DescriptionTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval( Table address,  Table add_description){//Add annotation if needed
		return new SqlCondition().eq(address.col(AddressColumns._ID),add_description.col(Add_DescriptionColumns.ID_ADDRESS)); 
	}}); 



} //End of Add_DescriptionRelations


public static class AddressRelations extends RelationModel { 
public static final String RESTAURANT_ADDRESS = "Restaurant has (or is in) a Address"; 

public static Relation rel_Restaurant_Address = new Relation(RESTAURANT_ADDRESS,
Arrays.asList(new RelationMember(new RestaurantTable(),Occurrence.ONE),
new RelationMember(new AddressTable(),Occurrence.ONE)),
new RelationCondition() {
	public SqlCondition eval( Table restaurant,  Table address){//Add annotation if needed
		return new SqlCondition().eq(restaurant.col(RestaurantColumns._ID),address.col(AddressColumns.ID_RESTAURANT)); 
	}}); 



} //End of AddressRelations


public static class BelongingRelations extends RelationModel { 
public static final String SEASON_BELONGING = "Season has (or is in) a Belonging"; 

public static Relation rel_Season_Belonging = new Relation(SEASON_BELONGING,
Arrays.asList(new RelationMember(new SeasonTable(),Occurrence.ONE),
new RelationMember(new BelongingTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval(@Role("produces") Table season,  Table belonging){//Add annotation if needed
		return new SqlCondition().eq(season.col(SeasonColumns._ID),belonging.col(BelongingColumns.ID_SEASON)); 
	}}); 



public static final String INGREDIENT_BELONGING = "Ingredient has (or is in) a Belonging"; 

public static Relation rel_Ingredient_Belonging = new Relation(INGREDIENT_BELONGING,
Arrays.asList(new RelationMember(new IngredientTable(),Occurrence.ONE),
new RelationMember(new BelongingTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval(@Role("produced in") Table ingredient,  Table belonging){//Add annotation if needed
		return new SqlCondition().eq(ingredient.col(IngredientColumns._ID),belonging.col(BelongingColumns.ID_INGREDIENT)); 
	}}); 



} //End of BelongingRelations


public static class ChefRelations extends RelationModel { 
public static final String RESTAURANT_CHEF = "Restaurant has (or is in) a Chef"; 

public static Relation rel_Restaurant_Chef = new Relation(RESTAURANT_CHEF,
Arrays.asList(new RelationMember(new RestaurantTable(),Occurrence.ONE),
new RelationMember(new ChefTable(),Occurrence.ONE)),
new RelationCondition() {
	public SqlCondition eval( Table restaurant,  Table chef){//Add annotation if needed
		return new SqlCondition().eq(restaurant.col(RestaurantColumns._ID),chef.col(ChefColumns.ID_RESTAURANT)); 
	}}); 



} //End of ChefRelations


public static class ClientRelations extends RelationModel { 
public static final String LANGUAGE_CLIENT = "Language has (or is in) a Client"; 

public static Relation rel_Language_Client = new Relation(LANGUAGE_CLIENT,
Arrays.asList(new RelationMember(new LanguageTable(),Occurrence.ONE),
new RelationMember(new ClientTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval(@Role("spoken by") Table language, @Role("speaks") Table client){//Add annotation if needed
		return new SqlCondition().eq(language.col(LanguageColumns._ID),client.col(ClientColumns.ID_LANGUAGE)); 
	}}); 



} //End of ClientRelations


public static class ContenanceRelations extends RelationModel { 
public static final String MEAL_CONTENANCE = "Meal has (or is in) a Contenance"; 

public static Relation rel_Meal_Contenance = new Relation(MEAL_CONTENANCE,
Arrays.asList(new RelationMember(new MealTable(),Occurrence.ONE),
new RelationMember(new ContenanceTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval(@Role("contains") Table meal,  Table contenance){//Add annotation if needed
		return new SqlCondition().eq(meal.col(MealColumns._ID),contenance.col(ContenanceColumns.ID_MEAL)); 
	}}); 



public static final String INGREDIENT_CONTENANCE = "Ingredient has (or is in) a Contenance"; 

public static Relation rel_Ingredient_Contenance = new Relation(INGREDIENT_CONTENANCE,
Arrays.asList(new RelationMember(new IngredientTable(),Occurrence.ONE),
new RelationMember(new ContenanceTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval(@Role("contained in") Table ingredient,  Table contenance){//Add annotation if needed
		return new SqlCondition().eq(ingredient.col(IngredientColumns._ID),contenance.col(ContenanceColumns.ID_INGREDIENT)); 
	}}); 



} //End of ContenanceRelations


public static class DescriptionRelations extends RelationModel { 
public static final String LANGUAGE_DESCRIPTION = "Language has (or is in) a Description"; 

public static Relation rel_Language_Description = new Relation(LANGUAGE_DESCRIPTION,
Arrays.asList(new RelationMember(new LanguageTable(),Occurrence.ONE),
new RelationMember(new DescriptionTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval(@Role("describes") Table language,  Table description){//Add annotation if needed
		return new SqlCondition().eq(language.col(LanguageColumns._ID),description.col(DescriptionColumns.ID_LANGUAGE)); 
	}}); 



public static final String MEAL_DESCRIPTION = "Meal has (or is in) a Description"; 

public static Relation rel_Meal_Description = new Relation(MEAL_DESCRIPTION,
Arrays.asList(new RelationMember(new MealTable(),Occurrence.ONE),
new RelationMember(new DescriptionTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval(@Role("described by") Table meal,  Table description){//Add annotation if needed
		return new SqlCondition().eq(meal.col(MealColumns._ID),description.col(DescriptionColumns.ID_MEAL)); 
	}}); 



} //End of DescriptionRelations


public static class Description2Relations extends RelationModel { 
public static final String LANGUAGE_DESCRIPTION2 = "Language has (or is in) a Description2"; 

public static Relation rel_Language_Description2 = new Relation(LANGUAGE_DESCRIPTION2,
Arrays.asList(new RelationMember(new LanguageTable(),Occurrence.ONE),
new RelationMember(new Description2Table(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval(@Role("describes") Table language,  Table description2){//Add annotation if needed
		return new SqlCondition().eq(language.col(LanguageColumns._ID),description2.col(Description2Columns.ID_LANGUAGE)); 
	}}); 



public static final String INGREDIENT_DESCRIPTION2 = "Ingredient has (or is in) a Description2"; 

public static Relation rel_Ingredient_Description2 = new Relation(INGREDIENT_DESCRIPTION2,
Arrays.asList(new RelationMember(new IngredientTable(),Occurrence.ONE),
new RelationMember(new Description2Table(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval(@Role("described by") Table ingredient,  Table description2){//Add annotation if needed
		return new SqlCondition().eq(ingredient.col(IngredientColumns._ID),description2.col(Description2Columns.ID_INGREDIENT)); 
	}}); 



} //End of Description2Relations


public static class MealRelations extends RelationModel { 
public static final String RESTAURANT_MEAL = "Restaurant has (or is in) a Meal"; 

public static Relation rel_Restaurant_Meal = new Relation(RESTAURANT_MEAL,
Arrays.asList(new RelationMember(new RestaurantTable(),Occurrence.ONE),
new RelationMember(new MealTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval( Table restaurant,  Table meal){//Add annotation if needed
		return new SqlCondition().eq(restaurant.col(RestaurantColumns._ID),meal.col(MealColumns.ID_RESTAURANT)); 
	}}); 



} //End of MealRelations


public static class Meal_RatingRelations extends RelationModel { 
public static final String CLIENT_MEAL_RATING = "Client has (or is in) a Meal_Rating"; 

public static Relation rel_Client_Meal_Rating = new Relation(CLIENT_MEAL_RATING,
Arrays.asList(new RelationMember(new ClientTable(),Occurrence.ONE),
new RelationMember(new Meal_RatingTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval(@Role("rates") Table client,  Table meal_rating){//Add annotation if needed
		return new SqlCondition().eq(client.col(ClientColumns._ID),meal_rating.col(Meal_RatingColumns.ID_CLIENT)); 
	}}); 



public static final String MEAL_MEAL_RATING = "Meal has (or is in) a Meal_Rating"; 

public static Relation rel_Meal_Meal_Rating = new Relation(MEAL_MEAL_RATING,
Arrays.asList(new RelationMember(new MealTable(),Occurrence.ONE),
new RelationMember(new Meal_RatingTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval(@Role("rated by") Table meal,  Table meal_rating){//Add annotation if needed
		return new SqlCondition().eq(meal.col(MealColumns._ID),meal_rating.col(Meal_RatingColumns.ID_MEAL)); 
	}}); 



} //End of Meal_RatingRelations


public static class MealOfDayRelations extends RelationModel { 
public static final String RESTAURANT_MEALOFDAY = "Restaurant has (or is in) a MealOfDay"; 

public static Relation rel_Restaurant_MealOfDay = new Relation(RESTAURANT_MEALOFDAY,
Arrays.asList(new RelationMember(new RestaurantTable(),Occurrence.ONE),
new RelationMember(new MealOfDayTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval( Table restaurant,  Table mealofday){//Add annotation if needed
		return new SqlCondition().eq(restaurant.col(RestaurantColumns._ID),mealofday.col(MealOfDayColumns.ID_RESTAURANT)); 
	}}); 



public static final String MEAL_MEALOFDAY = "Meal has (or is in) a MealOfDay"; 

public static Relation rel_Meal_MealOfDay = new Relation(MEAL_MEALOFDAY,
Arrays.asList(new RelationMember(new MealTable(),Occurrence.ONE),
new RelationMember(new MealOfDayTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval( Table meal,  Table mealofday){//Add annotation if needed
		return new SqlCondition().eq(meal.col(MealColumns._ID),mealofday.col(MealOfDayColumns.ID_MEAL)); 
	}}); 



} //End of MealOfDayRelations


public static class OpeningRelations extends RelationModel { 
public static final String RESTAURANT_OPENING = "Restaurant has (or is in) a Opening"; 

public static Relation rel_Restaurant_Opening = new Relation(RESTAURANT_OPENING,
Arrays.asList(new RelationMember(new RestaurantTable(),Occurrence.ONE),
new RelationMember(new OpeningTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval( Table restaurant,  Table opening){//Add annotation if needed
		return new SqlCondition().eq(restaurant.col(RestaurantColumns._ID),opening.col(OpeningColumns.ID_RESTAURANT)); 
	}}); 



public static final String DAY_OPENING = "Day has (or is in) a Opening"; 

public static Relation rel_Day_Opening = new Relation(DAY_OPENING,
Arrays.asList(new RelationMember(new DayTable(),Occurrence.ONE),
new RelationMember(new OpeningTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval( Table day,  Table opening){//Add annotation if needed
		return new SqlCondition().eq(day.col(DayColumns._ID),opening.col(OpeningColumns.ID_DAY)); 
	}}); 



} //End of OpeningRelations


public static class PreferenceRelations extends RelationModel { 
public static final String CLIENT_PREFERENCE = "Client has (or is in) a Preference"; 

public static Relation rel_Client_Preference = new Relation(CLIENT_PREFERENCE,
Arrays.asList(new RelationMember(new ClientTable(),Occurrence.ONE),
new RelationMember(new PreferenceTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval(@Role("prefers") Table client,  Table preference){//Add annotation if needed
		return new SqlCondition().eq(client.col(ClientColumns._ID),preference.col(PreferenceColumns.ID_CLIENT)); 
	}}); 



public static final String INGREDIENT_PREFERENCE = "Ingredient has (or is in) a Preference"; 

public static Relation rel_Ingredient_Preference = new Relation(INGREDIENT_PREFERENCE,
Arrays.asList(new RelationMember(new IngredientTable(),Occurrence.ONE),
new RelationMember(new PreferenceTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval(@Role("preferred by") Table ingredient,  Table preference){//Add annotation if needed
		return new SqlCondition().eq(ingredient.col(IngredientColumns._ID),preference.col(PreferenceColumns.ID_INGREDIENT)); 
	}}); 



} //End of PreferenceRelations


public static class ProhibitionRelations extends RelationModel { 
public static final String USER_CONSTRAINT_PROHIBITION = "User_Constraint has (or is in) a Prohibition"; 

public static Relation rel_User_Constraint_Prohibition = new Relation(USER_CONSTRAINT_PROHIBITION,
Arrays.asList(new RelationMember(new User_ConstraintTable(),Occurrence.ONE),
new RelationMember(new ProhibitionTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval(@Role("prohibits") Table user_constraint,  Table prohibition){//Add annotation if needed
		return new SqlCondition().eq(user_constraint.col(User_ConstraintColumns._ID),prohibition.col(ProhibitionColumns.ID_CONSTR)); 
	}}); 



public static final String INGREDIENT_PROHIBITION = "Ingredient has (or is in) a Prohibition"; 

public static Relation rel_Ingredient_Prohibition = new Relation(INGREDIENT_PROHIBITION,
Arrays.asList(new RelationMember(new IngredientTable(),Occurrence.ONE),
new RelationMember(new ProhibitionTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval(@Role("prohibited by") Table ingredient,  Table prohibition){//Add annotation if needed
		return new SqlCondition().eq(ingredient.col(IngredientColumns._ID),prohibition.col(ProhibitionColumns.ID_INGREDIENT)); 
	}}); 



} //End of ProhibitionRelations


public static class RegionRelations extends RelationModel { 
public static final String REGION_REGION = "Region has (or is in) a Region"; 

public static Relation rel_Region_Region = new Relation(REGION_REGION,
Arrays.asList(new RelationMember(new RegionTable(),Occurrence.ONE),
new RelationMember(new RegionTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval(@Role("parent") Table regionparent, @Role("child") Table regionchild){//Add annotation if needed
		return new SqlCondition().eq(regionparent.col(RegionColumns._ID),regionchild.col(RegionColumns.PARENT)); 
	}}); 



} //End of RegionRelations


public static class RequirementRelations extends RelationModel { 
public static final String CLIENT_REQUIREMENT = "Client has (or is in) a Requirement"; 

public static Relation rel_Client_Requirement = new Relation(CLIENT_REQUIREMENT,
Arrays.asList(new RelationMember(new ClientTable(),Occurrence.ONE),
new RelationMember(new RequirementTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval(@Role("requires") Table client,  Table requirement){//Add annotation if needed
		return new SqlCondition().eq(client.col(ClientColumns._ID),requirement.col(RequirementColumns.ID_CLIENT)); 
	}}); 



public static final String USER_CONSTRAINT_REQUIREMENT = "User_Constraint has (or is in) a Requirement"; 

public static Relation rel_User_Constraint_Requirement = new Relation(USER_CONSTRAINT_REQUIREMENT,
Arrays.asList(new RelationMember(new User_ConstraintTable(),Occurrence.ONE),
new RelationMember(new RequirementTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval(@Role("required by") Table user_constraint,  Table requirement){//Add annotation if needed
		return new SqlCondition().eq(user_constraint.col(User_ConstraintColumns._ID),requirement.col(RequirementColumns.ID_CONSTR)); 
	}}); 



} //End of RequirementRelations


public static class RestaurantRelations extends RelationModel { 
public static final String REGION_RESTAURANT = "Region has (or is in) a Restaurant"; 

public static Relation rel_Region_Restaurant = new Relation(REGION_RESTAURANT,
Arrays.asList(new RelationMember(new RegionTable(),Occurrence.ONE),
new RelationMember(new RestaurantTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval( Table region,  Table restaurant){//Add annotation if needed
		return new SqlCondition().eq(region.col(RegionColumns._ID),restaurant.col(RestaurantColumns.ID_REGION)); 
	}}); 



} //End of RestaurantRelations


public static class RestoDescriptionRelations extends RelationModel { 
public static final String RESTAURANT_RESTODESCRIPTION = "Restaurant has (or is in) a RestoDescription"; 

public static Relation rel_Restaurant_RestoDescription = new Relation(RESTAURANT_RESTODESCRIPTION,
Arrays.asList(new RelationMember(new RestaurantTable(),Occurrence.ONE),
new RelationMember(new RestoDescriptionTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval( Table restaurant,  Table restodescription){//Add annotation if needed
		return new SqlCondition().eq(restaurant.col(RestaurantColumns._ID),restodescription.col(RestoDescriptionColumns.ID_RESTAURANT)); 
	}}); 



public static final String LANGUAGE_RESTODESCRIPTION = "Language has (or is in) a RestoDescription"; 

public static Relation rel_Language_RestoDescription = new Relation(LANGUAGE_RESTODESCRIPTION,
Arrays.asList(new RelationMember(new LanguageTable(),Occurrence.ONE),
new RelationMember(new RestoDescriptionTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval( Table language,  Table restodescription){//Add annotation if needed
		return new SqlCondition().eq(language.col(LanguageColumns._ID),restodescription.col(RestoDescriptionColumns.ID_LANGUAGE)); 
	}}); 



} //End of RestoDescriptionRelations


public static class Taste_BelongingRelations extends RelationModel { 
public static final String TASTE_TASTE_BELONGING = "Taste has (or is in) a Taste_Belonging"; 

public static Relation rel_Taste_Taste_Belonging = new Relation(TASTE_TASTE_BELONGING,
Arrays.asList(new RelationMember(new TasteTable(),Occurrence.ONE),
new RelationMember(new Taste_BelongingTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval(@Role("possessed by") Table taste,  Table taste_belonging){//Add annotation if needed
		return new SqlCondition().eq(taste.col(TasteColumns._ID),taste_belonging.col(Taste_BelongingColumns.ID_TASTE)); 
	}}); 



public static final String INGREDIENT_TASTE_BELONGING = "Ingredient has (or is in) a Taste_Belonging"; 

public static Relation rel_Ingredient_Taste_Belonging = new Relation(INGREDIENT_TASTE_BELONGING,
Arrays.asList(new RelationMember(new IngredientTable(),Occurrence.ONE),
new RelationMember(new Taste_BelongingTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval(@Role("has") Table ingredient,  Table taste_belonging){//Add annotation if needed
		return new SqlCondition().eq(ingredient.col(IngredientColumns._ID),taste_belonging.col(Taste_BelongingColumns.ID_INGREDIENT)); 
	}}); 



} //End of Taste_BelongingRelations


public static class Taste_PreferenceRelations extends RelationModel { 
public static final String CLIENT_TASTE_PREFERENCE = "Client has (or is in) a Taste_Preference"; 

public static Relation rel_Client_Taste_Preference = new Relation(CLIENT_TASTE_PREFERENCE,
Arrays.asList(new RelationMember(new ClientTable(),Occurrence.ONE),
new RelationMember(new Taste_PreferenceTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval(@Role("prefers") Table client,  Table taste_preference){//Add annotation if needed
		return new SqlCondition().eq(client.col(ClientColumns._ID),taste_preference.col(Taste_PreferenceColumns.ID_CLIENT)); 
	}}); 



public static final String TASTE_TASTE_PREFERENCE = "Taste has (or is in) a Taste_Preference"; 

public static Relation rel_Taste_Taste_Preference = new Relation(TASTE_TASTE_PREFERENCE,
Arrays.asList(new RelationMember(new TasteTable(),Occurrence.ONE),
new RelationMember(new Taste_PreferenceTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval(@Role("preferred by") Table taste,  Table taste_preference){//Add annotation if needed
		return new SqlCondition().eq(taste.col(TasteColumns._ID),taste_preference.col(Taste_PreferenceColumns.ID_TASTE)); 
	}}); 



} //End of Taste_PreferenceRelations


public static class VisitRelations extends RelationModel { 
public static final String CLIENT_VISIT = "Client has (or is in) a Visit"; 

public static Relation rel_Client_Visit = new Relation(CLIENT_VISIT,
Arrays.asList(new RelationMember(new ClientTable(),Occurrence.ONE),
new RelationMember(new VisitTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval(@Role("visits") Table client,  Table visit){//Add annotation if needed
		return new SqlCondition().eq(client.col(ClientColumns._ID),visit.col(VisitColumns.ID_CLIENT)); 
	}}); 



public static final String RESTAURANT_VISIT = "Restaurant has (or is in) a Visit"; 

public static Relation rel_Restaurant_Visit = new Relation(RESTAURANT_VISIT,
Arrays.asList(new RelationMember(new RestaurantTable(),Occurrence.ONE),
new RelationMember(new VisitTable(),Occurrence.MANY)),
new RelationCondition() {
	public SqlCondition eval(@Role("visited by") Table restaurant,  Table visit){//Add annotation if needed
		return new SqlCondition().eq(restaurant.col(RestaurantColumns._ID),visit.col(VisitColumns.ID_RESTAURANT)); 
	}}); 



} //End of VisitRelations

} //End of GourmetRelationModel