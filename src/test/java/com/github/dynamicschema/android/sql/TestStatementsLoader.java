/**
 * 
 */
package com.github.dynamicschema.android.sql;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.dynamicschema.reification.DBTable;
import org.dynamicschema.reification.Schema;

import com.github.dynamicschema.android.config.Configuration;
import com.github.dynamicschema.android.reification.SchemaReifier;

/**
 * @author esp
 *
 */
public class TestStatementsLoader {

	/**
	 * 
	 */
	public TestStatementsLoader() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
//		Properties prop  = new Properties();
//		try {
//			prop.load(new FileInputStream(Configuration.PROP_FILE));
//			
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		String path = prop.getProperty(Configuration.DB_MAIN_PROJECT_KEY);
//		SchemaReifier schR = new SchemaReifier(path);
//		Schema sch = schR.getSchema();
//		
//		List<DBTable> tableList = sch.getTables();
//		for (DBTable dbTable : tableList) {
//			System.out.println(dbTable.createTableStatement());
//		}

		
		
		SQLStatementsLoader loader = new SQLStatementsLoader();
		String [] instrList = loader.getInsertInstructions();
		for (String string : instrList) {
			System.out.println(string);
		}
		
		

	}

}
