/**
 * 
 */
package com.github.dynamicschema.android.reification;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.dynamicschema.context.ContextedQueryBuilder;
import org.dynamicschema.reification.DBTable;
import org.dynamicschema.reification.Relation;
import org.dynamicschema.reification.Schema;
import org.dynamicschema.reification.TableRelation;

import com.github.dynamicschema.android.reification.gen.ClientTable;
import com.github.dynamicschema.android.reification.gen.ClientTable.ClientColumns;
import com.github.dynamicschema.android.reification.gen.GourmetRelationModel.MealRelations;
import com.github.dynamicschema.android.reification.gen.GourmetRelationModel.RegionRelations;
import com.github.dynamicschema.android.reification.gen.GourmetSchema;
import com.github.dynamicschema.android.reification.gen.IngredientTable;
import com.github.dynamicschema.android.reification.gen.IngredientTable.IngredientColumns;
import com.github.dynamicschema.android.reification.gen.LanguageTable;
import com.github.dynamicschema.android.reification.gen.LanguageTable.LanguageColumns;
import com.github.dynamicschema.android.reification.gen.MealTable;
import com.github.dynamicschema.android.reification.gen.MealTable.MealColumns;
import com.github.dynamicschema.android.reification.gen.RegionTable;
import com.github.dynamicschema.android.reification.gen.RegionTable.RegionColumns;
import com.github.dynamicschema.android.reification.gen.RestaurantTable;
import com.github.dynamicschema.android.reification.gen.RestaurantTable.RestaurantColumns;
import com.github.dynamicschema.android.reification.gen.User_ConstraintTable;

/**
 * @author esp
 *
 */
public class TestLazySelect extends TestCase {


	private final String DB_PATH_BIG_RECURSIVE = "jdbc:sqlite:D:\\Documents\\Dropbox\\UCL\\Master22\\Thesis\\GourmetTestFiles\\gourmet.sqlite";

	private Connection connection = null;  
	private ResultSet resultSet = null;  
	private Statement statement = null;  	

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		try {

			Class.forName("org.sqlite.JDBC");  
			connection = DriverManager.getConnection(DB_PATH_BIG_RECURSIVE);


		} catch (Exception e1) {

			fail("Message:" +  e1.getMessage());
		}  
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();

		try {
			if(connection != null)
				connection.close();
			if(statement != null)
				statement.close();
			if(resultSet != null)
				resultSet.close();

		} catch (Exception e) {
			fail("Message" + e.getMessage());
		}
	}

	
	public final void testLazyRelationSelectClassicRestaurant() {

		try {
			System.setOut(new PrintStream("Output\\outputLazySelectRestaurant.txt"));

		}catch (Exception e1) {
			fail(e1.getMessage());
		}  

		Schema sch = new GourmetSchema();
		DBTable rest = sch.getTable(RestaurantTable.NAME);
		
		ContextedQueryBuilder qb = null;

		TableRelation tabRelMeal = rest.getTabRelation(MealRelations.RESTAURANT_MEAL, null);
		assertTrue(tabRelMeal !=null);
		Map<String,Object> bindings = new HashMap<String, Object>();
		bindings.put(RestaurantColumns._ID, new Integer(1));
		
		qb = rest.lazyRelationSelect(tabRelMeal, bindings);

		assertTrue(qb != null);
		System.out.println(qb.toString());

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(qb.toString());

		} catch (SQLException e) {
			fail(e.getMessage());
		}  
	}


	public final void testLazySelectFromTableWithRecursiveRel() {

		try {
			System.setOut(new PrintStream("Output\\outputLazySelectFromTableWithRecursiveRel.txt"));
		}catch (Exception e1) {
			fail(e1.getMessage());
		}  

		Schema sch = new GourmetSchema();
		DBTable region = sch.getTable(RegionTable.NAME);
		
		ContextedQueryBuilder qb = null;

		TableRelation tabRelParent = region.getTabRelation(RegionRelations.REGION_REGION, "parent");
		assertTrue( tabRelParent != null);
		Map<String,Object> bindings = new HashMap<String, Object>();
		bindings.put(RegionColumns.PARENT, new Integer(1));
		bindings.put(RegionColumns._ID, new Integer(2));

		qb = region.lazyRelationSelect(tabRelParent, bindings);

		assertTrue(qb != null);

		System.out.println(qb.toString());

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(qb.toString());
		} catch (SQLException e) {
			fail(e.getMessage());
		}  
	}
	
	private final void lazyRelationSelectTest(TableRelation tabRelation, Map<String,Object> bindings, DBTable tableInTest, int i,
																	List<Relation> relations2Traverse){
		
		try {
			
			String fileName = null;
			fileName = "Output\\outputLazySelect"+ tableInTest.getName() +i+".txt";
			
			System.setOut(new PrintStream(fileName));

		}catch (Exception e1) {
			fail(e1.getMessage());
		}  

		ContextedQueryBuilder qb = null;
		//Testing
		if(relations2Traverse == null)
			qb = tableInTest.lazyRelationSelect(tabRelation, bindings);
		else
			qb =  tableInTest.lazyRelationSelect(tabRelation, bindings, relations2Traverse);
		
		assertTrue(qb != null);
		System.out.println(qb.toString());

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(qb.toString());

		} catch (SQLException e) {
			fail(e.getMessage());
		}  
		
	}
	
	public final void testLazyRelationSelectClassicMeal() {
		
		Schema sch = new GourmetSchema();
		DBTable meal = sch.getTable(MealTable.NAME);

		Map<String,Object> bindings = null;
		List<TableRelation> tabRelations = meal.getTableRelations();
		for (int i = 0; i < tabRelations.size(); i++) {
			
			bindings = new HashMap<String, Object>();
			bindings.put(MealColumns._ID, new Integer(1));
			bindings.put(MealColumns.ID_RESTAURANT, new Integer(1));
			lazyRelationSelectTest(tabRelations.get(i), bindings, meal,i, null);
		}
	}

	public final void testLazyRelationSelectClassicIngredient() {

		Schema sch = new GourmetSchema();
		DBTable ingr = sch.getTable(IngredientTable.NAME);
		
		Map<String,Object> bindings = null;
		List<TableRelation> tabRelations = ingr.getTableRelations();
		for (int i = 0; i < tabRelations.size(); i++) {
			
			bindings = new HashMap<String, Object>();
			bindings.put(IngredientColumns._ID, new Integer(1));
			lazyRelationSelectTest(tabRelations.get(i), bindings, ingr,i, null);
		}
	}
	
	

	public final void testLazyRelationSelectClassicUserConstraint() {

		
		Schema sch = new GourmetSchema();
		DBTable constraint = sch.getTable(User_ConstraintTable.NAME);
		Map<String,Object> bindings = null;
		
		List<TableRelation> tabRelations = constraint.getTableRelations();
		for (int i = 0; i < tabRelations.size(); i++) {
			
			bindings = new HashMap<String, Object>();
			bindings.put("_id", new Integer(1));
			lazyRelationSelectTest(tabRelations.get(i), bindings, constraint,i, null);
		}
	}
	
	public final void testLazyRelationSelectClassicClient() {

		
		Schema sch = new GourmetSchema();
		DBTable client = sch.getTable(ClientTable.NAME);
		
		Map<String,Object> bindings = null;
		List<TableRelation> tabRelations = client.getTableRelations();
		for (int i = 0; i < tabRelations.size(); i++) {
			
			bindings = new HashMap<String, Object>();
			bindings.put(ClientColumns._ID, new Integer(1));
			bindings.put(ClientColumns.ID_LANGUAGE, new Integer(1));

			lazyRelationSelectTest(tabRelations.get(i), bindings, client,i, null);
		}
	}

	public final void testLazyRelationSelectClassicLanguage() {

		Schema sch = new GourmetSchema();
		DBTable lang = sch.getTable(LanguageTable.NAME);
		
		Map<String,Object> bindings = null;
		List<TableRelation> tabRelations = lang.getTableRelations();
		for (int i = 0; i < tabRelations.size(); i++) {
			
			bindings = new HashMap<String, Object>();
			bindings.put(LanguageColumns._ID, new Integer(1));

			lazyRelationSelectTest(tabRelations.get(i), bindings, lang,i, null);
		}
	}
}
