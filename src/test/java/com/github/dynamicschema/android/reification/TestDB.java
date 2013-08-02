package com.github.dynamicschema.android.reification;


import java.io.IOException;

import com.dbmain.jidbm.DBMAttribute;
import com.dbmain.jidbm.DBMConstraint;
import com.dbmain.jidbm.DBMConstraintMember;
import com.dbmain.jidbm.DBMDataObject;
import com.dbmain.jidbm.DBMEntityRelationshipType;
import com.dbmain.jidbm.DBMGenericObject;
import com.dbmain.jidbm.DBMGroup;
import com.dbmain.jidbm.DBMLibrary;
import com.dbmain.jidbm.DBMProject;
import com.dbmain.jidbm.DBMRelationshipType;
import com.dbmain.jidbm.DBMRole;
import com.dbmain.jidbm.DBMSchema;

public class TestDB {
	private static  final int MAX_CARD = 65535; 

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException { 
		// TODO Auto-generated method stub
		

		String path= "D:\\Documents\\Dropbox\\UCL\\Master22\\Thesis\\DBMain\\projects\\Gourmet.lun" ;

		DBMLibrary lib = new DBMLibrary();
		//DBMConsole con = new DBMConsole();

		int i = lib.loadLUN(path);		
		System.out.println("Current project file: "+path);


		DBMProject pro = new DBMProject();

		if (pro != null) {

			DBMSchema sch = pro.getFirstProductSchema();

			while (sch != null) {
				// analyze all schemas in this project ...
//				if(sch.getVersion().equals("NONE")){
//
//					System.out.println("Schema Name: "+ sch.getName());
//					System.out.println("Schema Version: "+ sch.getVersion());
//
//					inspectSchema(sch);
//				}
				
				if(sch.getVersion().equals("ConceptualRoles")){
					
					System.out.println("Schema Name: "+ sch.getName());
					System.out.println("Schema Version: "+ sch.getVersion());
					
					
					printRelationShipInfo(sch);
				}

				sch = pro.getNextProductSchema(sch);

			}
			// save the project into file named args[0].
			lib.unloadLUN(pro.getProjectIdentifier(),path);
			pro.deleteProject();
		}
	}

	public static void inspectSchema(DBMSchema sch){
		printEntityInfo(sch);
	}

	public static void printEntityInfo(DBMSchema sch){
		
		
		DBMDataObject d = sch.getFirstDataObject();
		while (d != null) {

			
			if(d.getObjectType() == DBMGenericObject.ENTITY_TYPE ){
				
				System.out.println("\n ** \n");

				DBMEntityRelationshipType eType = (DBMEntityRelationshipType)d;	
				System.out.println("===========");
				System.out.println("Entity: " +eType.getName());	
				System.out.println("===========");
				DBMAttribute attr = eType.getFirstAttribute();
				
				System.out.println("---- Attributes -----");
				
				while (attr != null) {		  
					System.out.println("- "+ attr.getName());	
					
					attr = eType.getNextAttribute(attr);
				}	

				System.out.println("---");

				//Get first group of components
				DBMGroup g =  eType.getFirstGroup();

				while (g != null ){

					System.out.println("> "+ g.getName());
					//Either Primary Key
					if(g.getName().startsWith("ID")){
						System.out.println("\tPRIMARY KEY(S): ");
						printGroupInfo(g);
					}
					//Or 
					if(g.getName().startsWith("FK")){	

						System.out.println("\tFOREIGN KEY(S): ");
						printGroupInfo(g);

						//Info about related tables 

						System.out.println("\t CONSTRAINTS");
						DBMConstraint cstr = g.getFirstConstraint();

						while (cstr != null){

							if(cstr.getType() == DBMConstraint.EQ_CONSTRAINT ||  cstr.getType() == DBMConstraint.REF_CONSTRAINT){
								DBMConstraintMember cm = (DBMConstraintMember) cstr.getFirstConstraintMember();
								String orig=null;
								String targ =null;
								String targTab = null, origTab = null;
								
								while(cm != null ){
									if(cm.getMemberRoleName().startsWith("TAR")){
										targ = cm.getGroup().getFirstComponentSimpleAttribute().getName();
										System.out.println("Target CARD: "+
												cm.getGroup().getMinimumCardinality() + "-" + cm.getGroup().getMaximumCardinality());
										targTab =  cm.getGroup().getDataObject().getName();
									}
									if(cm.getMemberRoleName().startsWith("OR")){
										orig = cm.getGroup().getFirstComponentSimpleAttribute().getName();
										System.out.println("Origin CARD: "+ 
													cm.getGroup().getMinimumCardinality()+ "-" +cm.getGroup().getMaximumCardinality());
										origTab =  cm.getGroup().getDataObject().getName();
									}
									//System.out.println("Role: "+ cm.getGroup().getFirstComponentRole().getFirstEntityType().getName());
									cm = cstr.getNextConstraintMember(cm);
								}
								if( targ != null  || orig != null ){
									System.out.println("Orig: " + origTab+ "." + orig+ " --> Targ: " + targTab + "." + targ);
								}
							}
							cstr =  g.getNextConstraint(cstr);
						}
					}
					g = eType.getNextGroup(g);
				}

				System.out.println("\n");
			}
			d = sch.getNextDataObject(d);
			
			
		
		}

	}
	
	
	public static void printRelationShipInfo(DBMSchema sch){
		
		System.out.println("=======================RelationShips Info =====================");

	
		DBMDataObject d = sch.getFirstDataObject();
		
		while (d != null) {	
			
			if(d.getObjectType() == DBMGenericObject.REL_TYPE ){
					
					String entA = null;
					String entB = null;
					String cardRoleA= null;
					String cardRoleB = null;
					String minCardA = null; 
					String maxCardA = null;
					String  minCardB = null ;
					String  maxCardB = null ;
					 
	
					DBMRelationshipType rel = (DBMRelationshipType) d;
					DBMRole role = rel.getFirstRole();
					
					System.out.println("Role Name: " + role.getName());
					
					entA  = role.getFirstEntityType().getName();
					
					if(role.getMinimumCardinality() >= MAX_CARD){
						minCardA = "N";

					}else{
						minCardA = role.getMinimumCardinality()+ "";	
					}
					
					if(role.getMaximumCardinality() >= MAX_CARD){
						maxCardA = "N";
					}else{
						maxCardA = role.getMaximumCardinality()+ "" ;
					}
										
					cardRoleA ="["+minCardA+"-"+maxCardA+ "]";
					role = rel.getNextRole(role);
					
					System.out.println("Second Role:" + role.getName());
						
					if(role.getMinimumCardinality() >= MAX_CARD){
						
						minCardB = "INF";
						System.out.println("\t MinCard:" + role.getMinimumCardinality()+ " >= MAX_CARD= " + minCardB);

					}else{
						minCardB = role.getMinimumCardinality()+"";
						System.out.println("\t  MinCard A= " + minCardB);
					}
					
					if(role.getMaximumCardinality() >= MAX_CARD) {
						
						maxCardB = "INF";
					}else {
						maxCardB = role.getMaximumCardinality()+"";
					}
					
					cardRoleB = "["+minCardB+"-"+maxCardB+ "]";
					entB = role.getFirstEntityType().getName();
					
					System.out.println(entA + cardRoleA +"----<"+ rel.getName() + ">----"+ cardRoleB + entB);
			}
			d = sch.getNextDataObject(d);
			
		}
	}
	
	public static void printGroupInfo(DBMGroup g){
		
		
		DBMGenericObject obj =  g.getFirstComponent();
		
		while(obj != null ){

			if(obj.getObjectType() == DBMGenericObject.SI_ATTRIBUTE){
				DBMAttribute at =  (DBMAttribute) obj;
				System.out.println("\t >> "+ at.getName());
			}
			obj = g.getNextComponent(obj);
		}


	}


}
