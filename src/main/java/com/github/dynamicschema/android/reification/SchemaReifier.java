package com.github.dynamicschema.android.reification;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.dynamicschema.reification.Column;
import org.dynamicschema.reification.ColumnModel;
import org.dynamicschema.reification.DBTable;
import org.dynamicschema.reification.Occurrence;
import org.dynamicschema.reification.Relation;
import org.dynamicschema.reification.RelationMember;
import org.dynamicschema.reification.RelationModel;
import org.dynamicschema.reification.Schema;
import org.dynamicschema.reification.columnconstraint.ColumnConstraint;
import org.dynamicschema.reification.columnconstraint.ForeignKey;
import org.dynamicschema.reification.columnconstraint.PrimaryKey;
import org.dynamicschema.sql.RelationCondition;

//import be.dbmodelgen.reification.ColumnTypes;
//import be.dbmodelgen.reification.SchemaElement;
//import be.dbmodelgen.reification.SchemaReifier.RoleMatching;

import com.dbmain.jidbm.DBMAttribute;
import com.dbmain.jidbm.DBMConstraint;
import com.dbmain.jidbm.DBMConstraintMember;
import com.dbmain.jidbm.DBMDataObject;
import com.dbmain.jidbm.DBMEntityRelationshipType;
import com.dbmain.jidbm.DBMEntityType;
import com.dbmain.jidbm.DBMGenericObject;
import com.dbmain.jidbm.DBMGroup;
import com.dbmain.jidbm.DBMLibrary;
import com.dbmain.jidbm.DBMProject;
import com.dbmain.jidbm.DBMRelationshipType;
import com.dbmain.jidbm.DBMRole;
import com.dbmain.jidbm.DBMSchema;
import com.github.dynamicschema.android.except.MissingProjectException;
import com.github.dynamicschema.android.except.MissingRoleException;
import com.github.dynamicschema.android.sql.DefaultRelationCondition;


public class SchemaReifier {

	
private final String PROP_FILE = "schema.properties";
	
	private static final String SCHEMA_TYPE_RELATIONAL= "relational";
	private static final String SCHEMA_TYPE_CONCEPTUAL= "conceptual";
	public static final String NO_ROLE = "NO_ROLE";
	private final String FOREIGN_KEY_PREFIX = "FK";
	private final String PRIMARY_KEY_PREFIX = "ID";

	private  final int MAX_CARD = 65535; 
	private static  int cpt_Rel_Name = 1; 


	private String path;  // path of the .lun file containing schema info
	private List<DBTable> lTables;

	private Map<String,Integer> relationNamesIdentifier; //used to make sure we won't have duplicate relation names
	private List<String> relTypesNames;  //During role names fetching, make sure we don't consider the same DBMaina relType twice
	
	//Project related data
	private DBMProject project;
	private Properties propfile;
	private DBMLibrary lib;
	/**
	 * @param path
	 */
	public SchemaReifier(String path) { 

		this.path = path;
		this.lTables = new ArrayList<DBTable>();
		this.project = loadProject();
		this.relationNamesIdentifier = new HashMap<String, Integer>();
		this.propfile  = new Properties();
		this.relTypesNames = new ArrayList<String>();
		
		try {
			this.propfile.load(new FileInputStream(PROP_FILE));
		} catch (FileNotFoundException e) {
			System.out.println(PROP_FILE + "file not found. Using default values ");
		} catch (IOException e) {	
			System.out.println("Problem to read file: "+ PROP_FILE+ ". Using default values ");
		}
	}

	/*
	 * Get the reified schema from file the .lun file
	 */
	public Schema getSchema(){

		loadProject();
		Schema reified = null;

		if (this.project != null) {
			String schemaTyp = propfile.getProperty(SCHEMA_TYPE_RELATIONAL);	
			if(schemaTyp != null)
				reified = reifySchema(getSChema(schemaTyp));
			else
				reified = reifySchema(getSChema(SCHEMA_TYPE_RELATIONAL));

		}

		try {
			unloadProject();
		} catch (MissingProjectException e) {
			throw new RuntimeException("Error getting Reified Schema: "+ e.getMessage());
		}
		return reified;
	}



	private DBMProject loadProject(){

		this.lib = new DBMLibrary();
		this.lib.loadLUN(path);		
		DBMProject pro = new DBMProject();
		return pro;
	}

	private void unloadProject() throws MissingProjectException{
		if(this.project == null)
			throw new MissingProjectException("Attempt to unload a non existing project");

		this.lib.unloadLUN(this.project.getProjectIdentifier(),path);
		this.project.deleteProject();
		this.project = null;
	}



	/*
	 * Get the schema of the appropriate version
	 */
	private DBMSchema getSChema(String version){

		DBMSchema sch = this.project.getFirstProductSchema();

		while (sch != null) {
			// analyze all schemas in this project ...
			if(sch.getVersion().equals(version))
				return sch; 
			sch = this.project.getNextProductSchema(sch);
		}

		return null;

	}

	/*
	 *  Build a reification of the analyzed schema
	 *  	
	 */
	private Schema reifySchema(DBMSchema schema){

		List<DBTable> tables = getSetTables(schema);
		RelationModel model = getRelationModel(schema);
		Schema reifiedSchema = new Schema();
		reifiedSchema.setTables(tables);
		reifiedSchema.setRelationModel(model);
		return reifiedSchema;
	}

	/*
	 * Get set of tables of the schema
	 */
	private List<DBTable> getSetTables(DBMSchema schema){
		return getSchemaElement(schema, true).getTables();
	}

	/*
	 * Get the relational schema of the schema
	 */
	private RelationModel getRelationModel(DBMSchema schema){
		return getSchemaElement(schema, false).getRelModel();
	}

	/*
	 * Get a schema element which might be either a RelationModel or a set of Tables of the schema
	 */
	private SchemaElement getSchemaElement(DBMSchema schema, boolean setTables){

		SchemaElement schElement = null;
		RelationModel model = null;

		DBMDataObject d = schema.getFirstDataObject();

		if(!setTables)
			model = new RelationModel();

		while( d != null){

			if(d.getObjectType() == DBMDataObject.ENTITY_TYPE){

				if(setTables){
					DBMEntityType eType = (DBMEntityType)d;
					String tableName = eType.getName();
					ColumnModel colModel = buildColumnModel(eType);				
					DBTable dbTab = new DBTable(tableName, colModel);
					this.lTables.add(dbTab);
					schElement = new SchemaElement(this.lTables);

				}else{ //RelationModel
					DBMEntityType eType = (DBMEntityType)d;
					List<Relation> relations = getRelationsOfTable(eType);
					for (Relation rel : relations){	
						model.addRelation(rel);
					}
					schElement = new SchemaElement(model);
				}

			}
			d = schema.getNextDataObject(d);
		}
		return schElement;
	}

	/*
	 * Build the whole column model of the schema being reified	
	 */
	private ColumnModel buildColumnModel(DBMEntityType eType){


		List<Column> columns = buildColumns(eType);
		List<ColumnConstraint> colConstrList = new ArrayList<ColumnConstraint>();	

		//Get group of attributes (Constraints)

		DBMGroup gr = eType.getFirstGroup();

		while( gr != null){

			if(gr.getName().startsWith(PRIMARY_KEY_PREFIX)){ //primary keys
				List<ColumnConstraint> pKList = buildPrimaryKeyConstraint(gr);
				colConstrList.addAll(pKList);
			}

			if(gr.getName().startsWith(FOREIGN_KEY_PREFIX)){ //Foreign Keys
				List<ColumnConstraint> fkList = buildForeignKeyConstraint(gr);
				colConstrList.addAll(fkList);
			}

			gr = eType.getNextGroup(gr);
		}

		return new ColumnModel(columns, colConstrList);
	}


	/*
	 * Get set of columns of the current entity type
	 */
	private List<Column> buildColumns(DBMEntityType eType){
		List<Column> columns = new ArrayList<Column>();
		DBMAttribute attr = eType.getFirstAttribute(); 

		//Get normal attributes
		while (attr != null ){
			Column currCol = new Column(attr.getName(), getType(attr));
			columns.add(currCol);
			attr = eType.getNextAttribute(attr);
		}
		return columns; 
	}

	/*
	 * Get the primary keys
	 */
	private List<ColumnConstraint> buildPrimaryKeyConstraint(DBMGroup gr){

		List<ColumnConstraint> colConstr = new ArrayList<ColumnConstraint>();	

		DBMGenericObject obj =  gr.getFirstComponent();
		List<String> cols = new ArrayList<String>();

		while(obj != null ){

			if(obj.getObjectType() == DBMGenericObject.SI_ATTRIBUTE){
				DBMAttribute at =  (DBMAttribute) obj;
				cols.add(at.getName());
			}
			obj = gr.getNextComponent(obj);
		}
		colConstr.add(new PrimaryKey(cols));
		return colConstr;
	}
	/*
	 * Get the foreign Keys of the current group
	 */
	private List<ColumnConstraint> buildForeignKeyConstraint(DBMGroup gr){

		List<ColumnConstraint> colConstrList = new ArrayList<ColumnConstraint>();

		String orig=null, targ =null, targTableName = null;
		ForeignKey fK = null;
		DBMConstraint cstr = gr.getFirstConstraint();
		List<String> localCol = new ArrayList<String>();
		List<String> forCol = new ArrayList<String>();

		while (cstr != null){

			if(isForeignKeyConstraint(cstr)){
				DBMConstraintMember cm = (DBMConstraintMember) cstr.getFirstConstraintMember();

				while(cm != null ){
					if(cm.getMemberRole() == DBMConstraintMember.TAR_MEM_CST ){
						targ = cm.getGroup().getFirstComponentSimpleAttribute().getName();
						targTableName  = cm.getGroup().getDataObject().getName();
					}
					if(cm.getMemberRole() == DBMConstraintMember.OR_MEM_CST ){
						orig = cm.getGroup().getFirstComponentSimpleAttribute().getName();
					}
					cm = cstr.getNextConstraintMember(cm);
				}

				if( targ != null  && orig != null && targTableName != null ){
					localCol.add(orig);
					forCol.add(targ);
					fK = new ForeignKey(localCol, targTableName, forCol);
				}
				colConstrList.add(fK);

			}
			cstr =  gr.getNextConstraint(cstr);
		}
		return colConstrList;
	}


	/*
	 * Returns the name of the type of the current attribute. 
	 * Since the target DBMS will be SQLite => should return attribute types that are compatible with SQLite
	 */
	private String getType(DBMAttribute attrib){


		if(attrib.isVariableCharacter())
			return ColumnTypes.VARCHAR;

		if(attrib.isNumeric())
			return ColumnTypes.INTEGER; 

		if(attrib.isDate())
			return ColumnTypes.DATE; 

		if(attrib.isBoolean())
			return ColumnTypes.BOOLEAN;

		if(attrib.isFloat()) 
			return ColumnTypes.REAL;

		return null;

	}

	/*
	 * Get all relations of the corresponding tables 
	 * Relations of a table are detected by checking whether the table has a foreign key referencing another table
	 * 
	 */
	private List<Relation> getRelationsOfTable(DBMEntityRelationshipType eType){

		List<Relation> relations = new ArrayList<Relation>();
		DBMGroup gr = eType.getFirstGroup();

		while( gr != null){

			if(gr.getName().startsWith(FOREIGN_KEY_PREFIX)){ //Foreign Keys
				Relation rel = buildRelation(gr);
				relations.add(rel);
			}

			gr = eType.getNextGroup(gr);
		}
		return relations;
	}


	/*
	 * build a Relation object according the group of an entity object. A group holds information about constraints
	 * such as Foreign key;
	 */
	private Relation buildRelation(DBMGroup gr){

		Relation relation = null; 
		DBMConstraint cstr = gr.getFirstConstraint();
		List<RelationMember> relMembers = new ArrayList<RelationMember>();
		String origAttr=null, origTabName = null, targAttr =null, targTableName = null;					

		while (cstr != null){

			if(isForeignKeyConstraint(cstr)){
				// A constraint is composed of 2 constraint members: An origin and a target. The origin is the attribute in the current table which is 
				// the foreign key referencing a target attribute in another table
				RelationMember memb2 = null , memb1 = null;
				DBMConstraintMember cm = (DBMConstraintMember) cstr.getFirstConstraintMember();
				while(cm != null ){

					if(cm.getMemberRole() == DBMConstraintMember.TAR_MEM_CST){
						targAttr = cm.getGroup().getFirstComponentSimpleAttribute().getName();
						targTableName = cm.getGroup().getDataObject().getName();
						//The target member is the table where the referenced primary key is located
						memb1 = new RelationMember(getDBTable(targTableName), Occurrence.ONE);

					}else if(cm.getMemberRole() == DBMConstraintMember.OR_MEM_CST){
						origAttr = cm.getGroup().getFirstComponentSimpleAttribute().getName();
						origTabName = cm.getGroup().getDataObject().getName();

						if(cm.getGroup().getMaximumCardinality() >= MAX_CARD)
							memb2 = new RelationMember(getDBTable(origTabName), Occurrence.MANY);
						else
							memb2  = new RelationMember(getDBTable(origTabName), Occurrence.ONE);

					}
					//get the next constraint member
					cm = cstr.getNextConstraintMember(cm);
				}

				if( memb1 != null & memb2 != null ){		
					relMembers.add(memb1);
					relMembers.add(memb2);
				}

				if( targAttr != null  || origAttr != null ){

					// Get info about Roles

					RelationCondition cond = new DefaultRelationCondition(getDBTable(targTableName), getDBTable(origTabName),targAttr, origAttr);
					String relationName = buildUniqueRelationName(targTableName, origTabName);			
					//Finally build the relation
					relation = new Relation(relationName, relMembers, cond);
				}
			}
			cstr =  gr.getNextConstraint(cstr);
		}


		return relation;
	}

	private boolean isForeignKeyConstraint(DBMConstraint cstr){
		return cstr.getType() == DBMConstraint.EQ_CONSTRAINT || cstr.getType() == DBMConstraint.REF_CONSTRAINT ;
	}

	/*
	 * Get the DBTable object whose name matches the one passed as parameter
	 */
	private DBTable getDBTable(String name){

		for (DBTable tab: this.lTables) {
			if(tab.getName().equals(name))
				return  tab;
		}
		return null;
	}

	/*
	 *  Get the List of Roles Names corresponding to the List of table names provided as parameter
	 *  Note: the role name at index i of the returned list corresponds the one of the table whose name is at index i in the list passed as parameter 
	 */
	public List<String> getRoleNames(List<String> relTableNames) throws MissingRoleException{

		if(this.project == null)
			this.project = loadProject();

		List<String> rolesNamesList = null;
		RoleMatching matching = getMatchingRelationShipType(relTableNames);
		
		if(matching == null ) //shouldn't happen but never knows
			return null;
		DBMRelationshipType relType = matching.getRelType();

		rolesNamesList = getRoles(matching, relType, relTableNames);
		
		//Check whether we are not missing roles where they should be mandatory
		/*
		 *  Case 1: Tab 1 (role tab 1) ------ (role tab2) Tab 2
		 *  Both tables have roles mentioned
		 *  Case 2: Tab1 (role tab 1) -------- Tab 2  or symmetric case  Tab1 ------- (role tab 2) Tab 2
		 *   One of the tables have a role mentioned but not the other. Check whether table with missing role appears more than once in the relation
		 *   (normally not) 
		 *  Case 3: Tab1 -------- Tab 2: No role is mentioned for both tables -> Check whether each table doesn't appear more than once in relation
		 */
		
		for (int i = 0; i < rolesNamesList.size(); i++) {
			String currRoleName = rolesNamesList.get(i);
			if(currRoleName.equals(NO_ROLE) || rolesNamesList.size() != 2){
				String correspondingTableName = relTableNames.get(i);
				if(appearsMoreThanOnceInRelation(correspondingTableName, relTableNames))
					throw new MissingRoleException("Role Mandatory for table: "+
							correspondingTableName + " because this table appears in more than once in relation");
			}
		}
		try {
			unloadProject();
		} catch (MissingProjectException e) {
			throw new RuntimeException("Error while trying to get roles: " + e.getMessage() );	
		}
		
		return rolesNamesList;
	}


	
	private List<String> getRoles(RoleMatching matching,DBMRelationshipType relType, List<String> relTableNames){
		
		String [] rolesNamesArr = new String [relTableNames.size()];
	
		DBMRole role  = relType.getFirstRole();
		int idxFirst = -1; 
		boolean recursive = isRecursiveRelation(relTableNames);
	
		//Parse all roles (2) and fetch their respective names
		while (role != null){
			boolean roleRecorded = false;
			
			for (int i = 0; i < relTableNames.size(); i++) {
				String roleName = role.getName();
				String roleEntityType = role.getFirstEntityType().getName();
				
				if(recursive && roleRecorded){//otherwise the role names table will be filled twice
					continue;
				
				}
				
				if(role.getFirstEntityType().getName().equals(relTableNames.get(i))){
					if(i != idxFirst){
						rolesNamesArr[i] = buildUniqueRoleName(role.getName(), relTableNames);
						roleRecorded = true;
					}
					
					if(idxFirst == -1)
						idxFirst = i; //keep the index of the first inserted element
				}
			}
			role = relType.getNextRole(role);
		}

		if(matching.getMatching() == RoleMatching.FOUND_ONE){
			for (int i = 0; i < relTableNames.size(); i++) {
				if(i != idxFirst)
					rolesNamesArr [i] = buildUniqueRoleName(relType.getName(), relTableNames);
			}
		}

		List<String> listRoles = new ArrayList<String>(rolesNamesArr.length);
		for (int i = 0; i < rolesNamesArr.length; i++) {
			
				listRoles.add(i, rolesNamesArr[i]);
			
		}
		 return listRoles;
	}
	
	private boolean isRecursiveRelation(List<String> tableNames){
		return tableNames.get(0).equals(tableNames.get(1));
	}
	
	
	/*
	 * Returns true when the given table name appears more than once in given relation 
	 * 
	 */
	private boolean appearsMoreThanOnceInRelation(String tableName, List<String> relTableNames){
		
		int i=0;
		for (int j = 0; j < relTableNames.size(); j++) {
			if(relTableNames.get(j) == tableName)
					i++;
		}
		return i > 1;
	}
	
	
	
	/*
	 * Get the appropriate relation - The are 2 possible ways of matching
	 *  Either both tables are in the conceptual schema 
	 *  The result is encapsulated in RoleMatching object
	 */
	private RoleMatching getMatchingRelationShipType(List<String> relTableNames){
		//Get the conceptual
		String schemaType = this.propfile.getProperty(SCHEMA_TYPE_CONCEPTUAL);
			
		DBMSchema dbSch = null;
		
		if(schemaType != null)
			dbSch = getSChema(schemaType);
		else
			dbSch = getSChema(SCHEMA_TYPE_CONCEPTUAL);
		
		DBMDataObject d = dbSch.getFirstDataObject();
		int nbTables = relTableNames.size();

		//First loop aims at finding whether there exists both tables names appear in the 
		// conceptual schema as entities. 
		//Otherwise there should be an entity whose name is the parameter list and the other name of the list correspond to a name of the relationship
		boolean recursive = isRecursiveRelation(relTableNames);
		 //Determines whether should loop again because both tables do not appear in conceptual schema
		boolean loopAgain = false;
		boolean incr = false ; 
		//if multiple relationships exit between same tables => prevent from stopping at relationship already found 
		//Serves at keep searching in the conceptual schema
		boolean keepGoing = false; 
		int nbOcc = 0;
		while (d != null) {	

			if(d.getObjectType() == DBMGenericObject.REL_TYPE ){
				DBMRelationshipType relType = (DBMRelationshipType) d;
				DBMRole role = relType.getFirstRole();
				 nbOcc=0;
				 String prevEntityNameForRole =""; 
				//Parse roles
				while(role != null ){
					String entityName = role.getFirstEntityType().getName();
					
					if(recursive){
						if(entityName.equals(relTableNames.get(0)))
								nbOcc++;
						
					}else{
						for (int i = 0; i < nbTables; i++) {
							//When having couple of tables where one of them participates in another relation which is recursive 
							//the first condition need to prevent from counting the other recursive relation as the matched one
							if(!prevEntityNameForRole.equals(entityName) && (entityName.equals(relTableNames.get(i)))){
								nbOcc++;
							}
							
							if(loopAgain && !incr && relTableNames.get(i).equals(relType.getName())){
								nbOcc++;
								incr = true;
							}	
						}
					}
						
					role = relType.getNextRole(role);
					
					if(nbOcc == nbTables){
//						String chosenRoleName = relType.getName();
						if(loopAgain)
							return new RoleMatching(relType, RoleMatching.FOUND_ONE);
						else {
							
							if(!relTypeAlreadyFound(relType)){
								registerFoundRelTypeName(relType);
								return new RoleMatching(relType, RoleMatching.FOUND_BOTH); 
							}
						}
					}
					prevEntityNameForRole = entityName;

				}
			}
			d = dbSch.getNextDataObject(d);
			//Test whether should loop again
			if(d == null && !loopAgain && nbOcc != nbTables ){ // All relationships have been parsed at the first loop without any result
				d = dbSch.getFirstDataObject();
				loopAgain = true;
			}else if(!keepGoing) {
				keepGoing = true; 
				d = dbSch.getFirstDataObject(); 
			}
		}

		return null;
	}

	
	private void registerFoundRelTypeName(DBMRelationshipType relType){
		this.relTypesNames.add(relType.getName());
	}
	
	private boolean relTypeAlreadyFound(DBMRelationshipType relType){
		return this.relTypesNames.contains(relType.getName());
	}
	
	
	/*
	 * Constructs a unique role name because duplicate role names are not allowed
	 */
	private String buildUniqueRoleName(String foundRoleName, List<String> involvedTableNames){

		
		String finalRoleName = foundRoleName;
		
		if(finalRoleName.isEmpty() || involvedTableNames.contains(foundRoleName)){//No role specified at design time was available
				finalRoleName = NO_ROLE; 
		}
			
		return finalRoleName;
	}
	
	
	
	private String buildUniqueRelationName(String targTabName, String origTabName){
	
		String name = targTabName+ "_" +  origTabName;
		String finalName = name;
		Integer val = this.relationNamesIdentifier.get(name);
		if(val != null) { //relation Name already exists
			int newVal = cpt_Rel_Name++;
			finalName+="_"+ newVal++;
			val = Integer.valueOf(newVal);
			this.relationNamesIdentifier.put(name, val);
		}
		return finalName;
		
	}


	private class RoleMatching {

		private static final int FOUND_BOTH = 1;
		private static final int  FOUND_ONE = 2;

		DBMRelationshipType relType;
		int matching ;
		/**
		 * @param relType
		 * @param matching
		 */
		public RoleMatching(DBMRelationshipType relType, int matching) {
			this.relType = relType;
			this.matching = matching;
		}
		/**
		 * @return the relType
		 */
		public DBMRelationshipType getRelType() {
			return relType;
		}
		/**
		 * @return the matching
		 */
		public int getMatching() {
			return matching;
		}


	}

}
