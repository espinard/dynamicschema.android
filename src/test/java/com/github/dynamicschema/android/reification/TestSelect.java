package com.github.dynamicschema.android.reification;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import junit.framework.TestCase;

import org.dynamicschema.context.ContextedQueryBuilder;
import org.dynamicschema.context.RelationalContextManager;
import org.dynamicschema.reification.ContextedTable;
import org.dynamicschema.reification.DBTable;
import org.dynamicschema.reification.Schema;

import com.github.dynamicschema.android.filterings.AgeFiltering;
import com.github.dynamicschema.android.filterings.LocationFiltering;
import com.github.dynamicschema.android.reification.gen.AddressTable;
import com.github.dynamicschema.android.reification.gen.ClientTable;
import com.github.dynamicschema.android.reification.gen.GourmetSchema;
import com.github.dynamicschema.android.reification.gen.IngredientTable;
import com.github.dynamicschema.android.reification.gen.MealTable;
import com.github.dynamicschema.android.reification.gen.RestaurantTable;

public class TestSelect extends TestCase {

	private final String SQLITE_DB_PATH = "jdbc:sqlite:D:\\Documents\\Dropbox\\UCL\\Master22\\Thesis\\GourmetTestFiles\\gourmetNew.sqlite";


	private Connection connection = null;  
	private ResultSet resultSet = null;  
	private Statement statement = null;  

	protected void setUp() throws Exception {

		super.setUp();

		try {
			Class.forName("org.sqlite.JDBC");  
			connection = DriverManager.getConnection(SQLITE_DB_PATH);


		} catch (Exception e1) {
			fail(e1.getMessage());
		}  

	}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public final void testEagerSelectFromRestaurant() {

		try {
			System.setOut(new PrintStream("Output\\outputSelectRest.txt"));

		}catch (Exception e1) {
			// TODO Auto-generated catch block

			fail(e1.getMessage());
		}  

		Schema sch = new GourmetSchema();
		DBTable rest = sch.getTable(RestaurantTable.NAME);

		ContextedQueryBuilder qb = null;
		qb = rest.select();

		assertTrue(qb != null);
		System.out.println(qb.toString());

		
		RelationalContextManager ctx =  qb.getRelationalContext();
		Map<ContextedTable, ContextedTable> depend =  ctx.getTablesDependencies();

		for( ContextedTable table : depend.keySet()){
			ContextedTable dependencies = depend.get(table);
			System.out.println("Dependencies for " + table.getAlias() + " " + dependencies.getAlias());
		}	
		
				try {
					statement = connection.createStatement();
					resultSet = statement.executeQuery(qb.toString());	
		
					int cptCol = 0;
					int offset = qb.getRelationalContext().getQueryOffset();
					
					while(resultSet.next()){
						
						for (int i = 0; i < offset; i++) {
							System.out.print(resultSet.getString(i+1) + "\t");
						}
						System.out.println();
						//				System.out.println("Row "+ cptCol);
						//				System.out.println(resultSet.getString(2));
						cptCol++;
					}
					System.out.println("Nb Rows: "+ cptCol);
		
				} catch (SQLException e) {
					fail(e.getMessage());
				}  
			}

	
	
	public final void testEagerSelectFromRestaurantWithSeveralFilerings() {

		try {
			System.setOut(new PrintStream("Output\\outputSelectRest.txt"));

		}catch (Exception e1) {
			// TODO Auto-generated catch block

			fail(e1.getMessage());
		}  

		Schema sch = new GourmetSchema();
		DBTable rest = sch.getTable(RestaurantTable.NAME);
		DBTable addr = sch.getTable(AddressTable.NAME);
		addr.setFiltering(new LocationFiltering(50.232323, 4.3737,0.12));

		ContextedQueryBuilder qb = null;
		rest.setFiltering(null);
		rest.addMoreFiltering(new AgeFiltering(15));
	
		qb = rest.select();

		assertTrue(qb != null);
		System.out.println(qb.toString());

		
		RelationalContextManager ctx =  qb.getRelationalContext();
		Map<ContextedTable, ContextedTable> depend =  ctx.getTablesDependencies();

		for( ContextedTable table : depend.keySet()){
			ContextedTable dependencies = depend.get(table);
			System.out.println("Dependencies for " + table.getAlias() + " " + dependencies.getAlias());
		}	
		
				try {
					statement = connection.createStatement();
					resultSet = statement.executeQuery(qb.toString());	
		
					int cptCol = 0;
					int offset = qb.getRelationalContext().getQueryOffset();
					
					while(resultSet.next()){
						
						for (int i = 0; i < offset; i++) {
							System.out.print(resultSet.getString(i+1) + "\t");
						}
						System.out.println();
						//				System.out.println("Row "+ cptCol);
						//				System.out.println(resultSet.getString(2));
						cptCol++;
					}
					
					System.out.println("Nb Rows: "+ cptCol);
		
				} catch (SQLException e) {
					fail(e.getMessage());
				}  
			}

	
	
		public final void testEagerSelectFromMeal() {
	
			try {
	
				System.setOut(new PrintStream("Output\\outputSelectMeal.txt"));
	
			}catch (Exception e1) {	
				fail(e1.getMessage());
			}  
	
	
			Schema sch = new GourmetSchema();
			DBTable meal = sch.getTable(MealTable.NAME);
			
			ContextedQueryBuilder qb = null;
			
			qb = meal.select();
			
			assertTrue(qb != null);	
			System.out.println(qb.toString());
			
//			System.out.println("Offset: " + qb.getRelationalContext().getQueryOffset());
			
			RelationalContextManager ctx =  qb.getRelationalContext();
			Map<ContextedTable, ContextedTable> depend =  ctx.getTablesDependencies();

			for( ContextedTable table : depend.keySet()){
				ContextedTable dependencies = depend.get(table);
				System.out.println("Dependencies for " + table.getAlias() + " " + dependencies.getAlias());

			}	
	
			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(qb.toString());
	
			} catch (SQLException e) {
				fail(e.getMessage());
			}  
	
	
	
		}
	
		
		public final void testEagerSelectFromClient() {
	
			try {
	
				System.setOut(new PrintStream("Output\\outputSelectClient.txt"));
	
			}catch (Exception e1) {
				// TODO Auto-generated catch block
	
				fail(e1.getMessage());
			}  
	
			DBTable client= null;
			Schema sch = new GourmetSchema();
			client = sch.getTable(ClientTable.NAME);
		
			ContextedQueryBuilder qb = null;
			qb = client.select();
			assertTrue(qb != null);	
			System.out.println(qb.toString());
			
			RelationalContextManager ctx =  qb.getRelationalContext();
			Map<ContextedTable, ContextedTable> depend =  ctx.getTablesDependencies();

			for( ContextedTable table : depend.keySet()){
				ContextedTable dependencies = depend.get(table);
				System.out.println("Dependencies for " + table.getAlias() + " " + dependencies.getAlias());

			}	
	
			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(qb.toString());
	
//				int cptCol = 0;
//				int offset = qb.getRelationalContext().getQueryOffset();
//				while(resultSet.next()){
//					//				System.out.println("Row "+ cptCol);
//					//				System.out.println(resultSet.getString(2));
//					cptCol++;
//				}
//				System.out.println("Nb Rows: "+ cptCol);
	
			} catch (SQLException e) {
				fail(e.getMessage());
			}  
	
	
		}
	
		public final void testEagerSelectFromIngredient() {
	
			try {
	
				System.setOut(new PrintStream("Output\\outputSelectIngr.txt"));
	
			}catch (Exception e1) {
				// TODO Auto-generated catch block
	
				fail(e1.getMessage());
			}  
	
			Schema sch = new GourmetSchema();
			DBTable ingr = sch.getTable(IngredientTable.NAME);
			
			ContextedQueryBuilder qb = null;
			qb = ingr.select();
			assertTrue(qb != null);	
			System.out.println(qb.toString());
			
			RelationalContextManager ctx =  qb.getRelationalContext();
			Map<ContextedTable, ContextedTable> depend =  ctx.getTablesDependencies();

			for( ContextedTable table : depend.keySet()){
				ContextedTable dependencies = depend.get(table);
				System.out.println("Dependencies for " + table.getAlias() + " " + dependencies.getAlias());

			}	
	
			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(qb.toString());
	
//				int cptCol = 0;
//				int offset = qb.getRelationalContext().getQueryOffset();
//				while(resultSet.next()){
//					//				System.out.println("Row "+ cptCol);
//					//				System.out.println(resultSet.getString(2));
//					cptCol++;
//				}
//				System.out.println("Nb Rows: "+ cptCol);
	
			} catch (SQLException e) {
				fail(e.getMessage());
			}  
	
		}
}
