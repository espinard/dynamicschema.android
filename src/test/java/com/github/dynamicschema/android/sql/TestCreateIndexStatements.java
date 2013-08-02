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
public class TestCreateIndexStatements {


	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Properties prop  = new Properties();
		try {
			prop.load(new FileInputStream(Configuration.PROP_FILE));


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String path = prop.getProperty(Configuration.DB_MAIN_PROJECT_KEY);
		SchemaReifier schR = new SchemaReifier(path);
		Schema sch = schR.getSchema();
		int cpt = 0;
		List<DBTable> tableList = sch.getTables();
		for (DBTable dbTable : tableList) {
			System.out.println("Table:" + dbTable.getName());
			for (String index : dbTable.createIndexStatements()) {
				System.out.println("\t " + index);
				cpt++;
			}

		}
		System.out.println("# Indices: " + cpt);
	}

}
