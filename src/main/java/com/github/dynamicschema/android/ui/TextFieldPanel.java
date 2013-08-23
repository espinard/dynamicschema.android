/**
 * 
 */
package com.github.dynamicschema.android.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author esp
 *
 */
public class TextFieldPanel extends JPanel {
	

	private static final int FIELD_SIZE = 25;
	private String textFieldLabelName;
	private JTextField textField;
	/**
	 * @param textFieldLabelName
	 * @param fieldValue
	 * @param mainFrame
	 */
	public TextFieldPanel(String textFieldLabelName) {
		super();
		this.textFieldLabelName = textFieldLabelName;
		buildPanel();
		
	}
	/**
	 * @return the fieldValue
	 */
	public String getFieldValue() {
		return this.textField.getText();
	} 

	/*
	 * Build the content of the current panel
	 */
	private void buildPanel(){
	
		this.textField = new JTextField();
		JLabel jLab = new JLabel(textFieldLabelName);
		
		//Customize content panel and Fill the panel with created elements 
		//this.setPreferredSize(new Dimension(100,100));
		this.textField.setColumns(FIELD_SIZE);
		this.add(jLab);
		this.add(textField);
		
	}
	/**
	 * @return the textField
	 */
	public JTextField getTextField() {
		return textField;
	}
	
		

}
