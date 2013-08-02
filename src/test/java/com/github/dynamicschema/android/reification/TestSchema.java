package com.github.dynamicschema.android.reification;

import java.util.ArrayList;
import java.util.List;

import org.dynamicschema.reification.Column;
import org.dynamicschema.reification.ColumnModel;
import org.dynamicschema.reification.DBTable;
import org.dynamicschema.reification.Relation;
import org.dynamicschema.reification.RelationMember;
import org.dynamicschema.reification.Schema;

public class TestSchema {

	public  static void printSchema(Schema schema){

		
		List<DBTable> lTables = schema.getTables();
		List<String> membersNames = new ArrayList<String>();
		
		
		ColumnModel colMod = null;
		System.out.println("--------- Tables ------------");
		for (DBTable dbTable : lTables) {
			System.out.println("Table: "+ dbTable.getName());
			System.out.println("\t -------Columns --------");

			colMod = dbTable.getColumnModel();
			List<Column> lCol =  colMod.getColumns();
			for (Column column : lCol) {
				System.out.println("\t" + column.getSimpleName()+ " " + column.getType());
			}


		}

		System.out.println("--------- Relation Model  ------------");

		List<Relation> lRel = schema.getRelationModel().getRelations();
		System.out.println("Number of Relations: " + lRel.size());
		
		for (int i = 0; i < lRel.size(); i++) {
			
			Relation relation = lRel.get(i);
			
			System.out.print("Rel_"+ (i+1)+ ": "+ relation.getName() + "\t");
			
			if(contains(membersNames, relation.getName()))
				System.out.println("!!!! Relation may be DUPLICATE");
			else
				membersNames.add(relation.getName());
			
			List<RelationMember> members = relation.getRelationMembers();
			if(members.size()> 0){
				System.out.println("\t" + members.get(0).getTable().getName()+ "<==>"+ members.get(1).getTable().getName());
			
			}else
				System.err.println("No relation members found for relation: "+relation.getName());

		}
		
		
		


	}
	
	private static boolean contains(List<String> lst, String name){
		for (String currname : lst) {
			if (currname.startsWith(name))
					return true;
		}
		
		return false;
		
	}

}
