package com.github.dynamicschema.android.reification;

import javax.swing.UIManager;

import org.dynamicschema.reification.Schema;

import com.github.dynamicschema.android.ui.MainGui;


public class DBModelGeneratorFull {
	
	private static final String WINDOWS_LOOK = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";


	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(WINDOWS_LOOK);
		} catch (Exception evt) {}
		
		MainGui gui = new MainGui("Generation settings");
		
		Thread t1 = new Thread(gui);
		t1.start();
		synchronized (gui) {
			try {
				gui.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(gui.getGlobalStatus() == MainGui.STATUS_KO)
			System.exit(1);
		
		SchemaReifier rf = new SchemaReifier(gui.getSourceFile());
		
		Schema sch = rf.getSchema();
		IDBModelGenerator dbm = new DBTablesGenerator(sch,gui.getAppName(),gui.getPackName(),gui.getGenPath());
		dbm.generate();
		
		//Generate Relation Model
		IDBModelGenerator dbmRel = new RelationModelGenerator(sch, rf, gui.getAppName(), gui.getPackName(), gui.getGenPath());
		dbmRel.generate();
		
		//Generate Schema class
		IDBModelGenerator schema = new SchemaGenerator(sch, gui.getAppName(), gui.getPackName(), gui.getGenPath());
		schema.generate();
		System.exit(0);
	}
	


}
