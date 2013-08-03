package com.github.dynamicschema.android.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.dynamicschema.android.reification.MyFileWriter;

public class SqliteTransformer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String fileIn= "D:\\Documents\\Dropbox\\UCL\\Master22\\Thesis\\GourmetTestFiles\\gourmetBigRecursiveDataPARSABLE_SQLITE_TRANSFORMER.sql";
		BufferedReader reader = null;
		MyFileWriter writer = null;
		try {
		
			reader = new BufferedReader(new FileReader(fileIn));
			String line = null;
			writer  = new MyFileWriter("D:\\Documents\\Dropbox\\UCL\\Master22\\Thesis\\GourmetTestFiles\\SQLITE_Manager_insertion.sql");
			String precTabName = null;
			String tabName = null;
			int cptID = -1;
			while ((line = reader.readLine()) != null){
				String str2w = "";

				System.out.println(line);

				if(line.startsWith("INSERT")){

					
					tabName = line.substring(line.indexOf('#')+ 1, line.indexOf('('));
					tabName = tabName.trim();
					
					if(tabName.equals(precTabName)){ //still inserting for the same table
						List<String> vals = getValues(line, cptID, tabName);
						str2w+=" UNION SELECT " ;
						for (int i = 0; i < vals.size(); i++) {
							
							if(i == vals.size()- 1){
								str2w+=vals.get(i) ;
								
							}else{
								str2w+=vals.get(i) + ", ";
							}
						}
						
						cptID++;
						
					}else{ // start insertion of a new table
						
						if(precTabName != null){
							writer.writeIntoFile(" ; " + "\n");
						}
						
						str2w+= "DELETE FROM "+ tabName + " ; \n ";
						
						cptID = 1;
						str2w += "INSERT INTO " + tabName + "\n" + "\t\t";
						str2w += "SELECT ";
						List<String> vals = getValues(line, cptID, tabName);
						List<String> cols = getColumns(line, tabName);		
						//cols.size == vals.size
						for (int i = 0; i < cols.size(); i++) {
							if( i == cols.size() - 1){
								str2w+= vals.get(i) + " AS " + cols.get(i);
							}else{
								str2w+= vals.get(i) + " AS " + cols.get(i)+ ", ";
							}
						}
						
						cptID++;
					}
				}
				//write in file
				writer.writeIntoFile(str2w + "\n");
				
				precTabName = tabName;
			}
		
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

	}
	
	
	private static List<String> getValues(String line, int id, String tabName){
		
		List<String> values = new ArrayList<String>();
		int idxDoll = line.indexOf('$');
		String tmpLine = line.substring(idxDoll);
		String valString = tmpLine.substring(tmpLine.indexOf('(')+ 1, tmpLine.indexOf(')'));
		String [] vals = valString.split(",");
		
		
		for (int i = 0; i < vals.length; i++) {
			values.add(vals[i]);
		}
		return values;
		
	}
	private static List<String> getColumns(String line, String tabName){
	
		List<String> columns = new ArrayList<String>();
		int idxBegin  = line.indexOf('(')+1 ;
		int idxEnd = line.indexOf(')');
		String cols  = line.substring(idxBegin, idxEnd);
		String [] colArr = cols.split(",");
		
		for (String col : colArr) {
			columns.add(col);
		}
		return columns;
	}
	
	

}
