package com.github.dynamicschema.android.ui;


public class TestMainGui {

//	private static final String WINDOWS_LOOK = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		try {
//			UIManager.setLookAndFeel(WINDOWS_LOOK);
//		} catch (Exception evt) {}


		// TODO Auto-generated method stub
		MainGui gui = new MainGui("Generation Parameters");

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

		System.out.println("Source File: "+ gui.getSourceFile());
		System.out.println("Gen Path: "+ gui.getGenPath());
		System.out.println("Package Name: "+ gui.getPackName());
		System.out.println("App Name: "+ gui.getAppName());
		
		System.exit(0);


	}

}
