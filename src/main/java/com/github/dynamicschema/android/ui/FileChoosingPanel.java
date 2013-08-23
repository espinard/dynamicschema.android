/**
 * 
 */
package com.github.dynamicschema.android.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author esp
 *
 */
public class FileChoosingPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NONE = "N/A";
	private static final int FIELD_SIZE = 25;

	
	private String textFieldLabelName;
	private String fieldValue;
	private JFrame mainFrame;
	private boolean fileOnlySelectionMode;
	private JTextField textField;


	/**
	 * @param textFieldLabelName
	 * 
	 */
	public FileChoosingPanel(String textFieldLabelName, JFrame parentFrame, boolean fileOnly) {
		super();
		this.textFieldLabelName = textFieldLabelName;
		this.mainFrame = parentFrame;
		this.fileOnlySelectionMode = fileOnly;
		buildPanel();
	}



	/**
	 * @return the textFieldLabelName
	 */
	public String getTextFieldLabelName() {
		return textFieldLabelName;
	}
	
	private void buildPanel() {
		
		//Create and initialize components
		JLabel lab = new JLabel(textFieldLabelName);
		this.textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(FIELD_SIZE);
		JButton chooseButton= new JButton("Browse...");
		chooseButton.addActionListener(new ButtonHandler());
		
		//Customize content panel and Fill the panel with created elements 
	//	this.setPreferredSize(new Dimension(10,10));
		
		this.add(lab);
		this.add(textField);
		this.add(chooseButton);
		
	}
	
	

	/**
	 * @return the fieldValue
	 */
	public String getFieldValue() {
		return fieldValue;
	}
	
	
	
	
	class ButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
			if(!fileOnlySelectionMode)
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			int option = chooser.showOpenDialog(mainFrame);
			if(option == JFileChooser.APPROVE_OPTION) {
				
				File chosenFile = chooser.getSelectedFile();
				
				if(chosenFile != null ){
					fieldValue = chosenFile.getAbsolutePath();
					textField.setText(fieldValue);
					
				}else{
					fieldValue = NONE;
				}
			}

			if(option == JFileChooser.CANCEL_OPTION) {
				fieldValue = NONE;
			}
		}
	}




	/**
	 * @return the textField
	 */
	public JTextField getTextField() {
		return textField;
	}

	
}
