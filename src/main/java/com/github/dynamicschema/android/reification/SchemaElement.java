package com.github.dynamicschema.android.reification;

import java.util.List;

import org.dynamicschema.reification.DBTable;
import org.dynamicschema.reification.RelationModel;

public class SchemaElement {

	private List<DBTable> tables;
	private RelationModel relModel;
	
	public SchemaElement(List<DBTable> tables) {
		this.tables = tables;
	}

	public SchemaElement(RelationModel model) {
		this.relModel = model;
	}
	/**
	 * @return the tables
	 */
	public List<DBTable> getTables() {
		return tables;
	}

	/**
	 * @return the relModel
	 */
	public RelationModel getRelModel() {
		return relModel;
	}

}
