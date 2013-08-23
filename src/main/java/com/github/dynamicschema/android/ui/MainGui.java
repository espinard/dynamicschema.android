package com.github.dynamicschema.android.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainGui extends JFrame implements Runnable {
	
	private static final boolean IN_TEST = true; //TODO, set to false for normal usage
	
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
	
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel fillP1; 
	private JPanel btPanel;
	private JPanel fillP3; 
	private JPanel fillButt; 


	
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
		this.leftPanel = new JPanel(new GridLayout(4,1));
		this.rightPanel = new JPanel(new GridLayout(4,1));
		this.fillP1 =  new JPanel();
		this.btPanel =  new JPanel();
		this.fillP3 =  new JPanel();
		this.fillButt =  new JPanel();

		this.add(leftPanel, BorderLayout.WEST);
		this.add(rightPanel,BorderLayout.EAST);
		this.add(fillButt, BorderLayout.SOUTH);
		this.fillButt.add(fillP1, BorderLayout.WEST);
		this.fillButt.add(btPanel, BorderLayout.CENTER);
		this.fillButt.add(fillP3, BorderLayout.EAST);
	}
	
	
	
	private void basicSettings() {
		
		//Set Layout
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setSize(1200, 800);
		this.pack();
	}

	private void setUpConfirmationArea() {
		JButton startButt = new JButton("Start Reification");
//		startButt.setSize(20,100);
		startButt.addActionListener(new MainButtonHandler()); 
		this.btPanel.add(startButt);
	}

	private void setUpPackNameArea() {
		JLabel labPack = new JLabel("Default Package declaration");
		this.leftPanel.add(labPack);
		this.jpPackageName = new TextFieldPanel("");
		this.rightPanel.add(jpPackageName);
		
		
		if(IN_TEST)
			this.jpPackageName.getTextField().setText("com.gourmet.database.gen");
	}


	private void setUpAppNameArea() {
		JLabel appName = new JLabel("Target Application Name");
		this.leftPanel.add(appName);
		this.jpAppName = new TextFieldPanel("");
		this.rightPanel.add(jpAppName);
		

		if(IN_TEST)
			this.jpAppName.getTextField().setText("Gourmet");
	}



	private void setUpGenPathArea() {
		JLabel genPath = new JLabel("Generated Files Folder");
		this.leftPanel.add(genPath);
		this.jpGenPath = new FileChoosingPanel("",this, false);
		this.rightPanel.add(jpGenPath);

		if(IN_TEST){
			String path ="C:\\Users\\esp\\git\\gourmet\\src\\com\\gourmet\\database\\gen";
			this.jpGenPath.getTextField().setText(path);
		}
	}


	private void setUpSourceArea() {
		JLabel srcFile = new JLabel("DBMain project file");
		leftPanel.add(srcFile);
		this.jpSource = new FileChoosingPanel("", this, true);
		this.rightPanel.add(jpSource);

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
		setUpAppNameArea();
		setUpPackNameArea();
		setUpSourceArea();
		setUpGenPathArea();
		setUpConfirmationArea();
		basicSettings();
		display();
	}	

}
