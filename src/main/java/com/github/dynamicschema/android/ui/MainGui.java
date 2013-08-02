package com.github.dynamicschema.android.ui;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainGui extends JFrame implements Runnable {
	
	private static final boolean IN_TEST = true;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int STATUS_KO = -1;
	public static final int STATUS_OK = 0;
	public static final int STATUS_WAIT = -2;
	
	
	private String appName;
	private String packName;
	private String sourceFile; 
	private String genPath;
	
	private int globalStatus = STATUS_OK;
	
	
	//Components
	FileChoosingPanel jpGenPath, jpSource;
	TextFieldPanel jpAppName, jpPackageName ;
	
	
	/**
	 * @param arg0
	 * @throws HeadlessException
	 */
	public MainGui(String name) throws HeadlessException {
		super(name);
		this.globalStatus = STATUS_WAIT;
	}
	
	
	
	private void basicSettings() {
		
		//Set Layout
		this.setLayout(new GridLayout(5, 1));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setSize(1200, 800);
		this.pack();
	}

	private void setUpConfirmationArea() {
		JButton startButt = new JButton("Start Generation");
		startButt.addActionListener(new MainButtonHandler()); 
		this.getContentPane().add(startButt);
	}

	private void setUpPackNameArea() {
		this.jpPackageName = new TextFieldPanel("Package Name");
		this.getContentPane().add(jpPackageName);
		
		//TODO remove 
		if(IN_TEST)
			this.jpPackageName.getTextField().setText("be.dbmodelgen.reification.gen");
	}


	private void setUpAppNameArea() {
		this.jpAppName = new TextFieldPanel("Application Name");
		this.getContentPane().add(jpAppName);
		
		//TODO remove 
		if(IN_TEST)
			this.jpAppName.getTextField().setText("Gourmet");
	}



	private void setUpGenPathArea() {
		this.jpGenPath = new FileChoosingPanel("Gen Path",this, false);
		this.getContentPane().add(jpGenPath);
		//TODO remove 
		if(IN_TEST){
			String path ="C:\\Users\\esp\\workspace\\dbModelGen\\src\\main\\java\\be\\dbmodelgen\\reification\\gen";
			this.jpGenPath.getTextField().setText(path);
		}
	}


	private void setUpSourceArea() {

		this.jpSource = new FileChoosingPanel("Source file", this, true);
		this.getContentPane().add(jpSource);
		//TODO remove 
		if(IN_TEST){
			String path ="D:\\Documents\\Dropbox\\UCL\\Master22\\Thesis\\DBMain\\projects\\Gourmet.lun";
			this.jpSource.getTextField().setText(path);
		}
	}


	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}
	/**
	 * @return the packName
	 */
	public String getPackName() {
		return packName;
	}
	/**
	 * @return the sourceFile
	 */
	public String getSourceFile() {
		return sourceFile;
	}
	/**
	 * @return the genPath
	 */
	public String getGenPath() {
		return genPath;
	}
	
	private  void updateStatus(){
		
		synchronized (this) {
			//TODO remove
			if(IN_TEST)
				this.globalStatus = updateVarInTest();
			else
				this.globalStatus = updateVariables();
			
			this.setVisible(false);
			this.notify();
		}
	
	}
	
	//TODO remove after test
	private int updateVarInTest(){
	
		this.sourceFile =  this.jpSource.getTextField().getText();
		this.genPath =  this.jpGenPath.getTextField().getText();
		this.appName = this.jpAppName.getTextField().getText();
		this.packName = this.jpPackageName.getTextField().getText();
		return STATUS_OK;
	}
	
	private int updateVariables(){
		String source = this.jpSource.getFieldValue();
		if(source == null || source.equals(FileChoosingPanel.NONE))
			return STATUS_KO; 
		this.sourceFile = source;
		String genPath = this.jpGenPath.getFieldValue();
		if(genPath == null || genPath.equals(FileChoosingPanel.NONE))
			return STATUS_KO;
		this.genPath = genPath;
		this.appName = this.jpAppName.getFieldValue();
		this.packName = this.jpPackageName.getFieldValue();
		
		return STATUS_OK;
	}
	
	public void display(){
		this.setVisible(true);
	}
	
	
	class MainButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			updateStatus();
		}
	}


	/**
	 * @return the globalStatus
	 */
	public int getGlobalStatus() {
		return this.globalStatus;
	}



	public void run() {
		// TODO Auto-generated method stub
		setUpSourceArea();
		setUpGenPathArea();
		setUpAppNameArea();
		setUpPackNameArea();
		setUpConfirmationArea();
		basicSettings();

		display();
	}	

}
