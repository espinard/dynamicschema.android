/**
 *  RelationModelGenerator
 *  Generates the relation model of a given reified schema
 */
package com.github.dynamicschema.android.reification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dynamicschema.annotation.Role;
import org.dynamicschema.reification.DBTable;
import org.dynamicschema.reification.Occurrence;
import org.dynamicschema.reification.Relation;
import org.dynamicschema.reification.RelationMember;
import org.dynamicschema.reification.RelationModel;
import org.dynamicschema.reification.Schema;
import org.dynamicschema.reification.Table;
import org.dynamicschema.sql.RelationCondition;
import org.dynamicschema.sql.SqlCondition;

import com.github.dynamicschema.android.except.MissingRoleException;
import com.github.dynamicschema.android.sql.DefaultRelationCondition;


/**
 * @author esp
 *
 */
public class RelationModelGenerator extends AbstractDBModelGenerator {


	private static final String RELVAR_PREFIX = "rel_";
	private static final String DEFAULT_COMMENT_ROLE = "Add annotation if needed";
	private static final int cptRelDescr = 1;
	private static final int cptRelVarName = 1;
	private static final int cptRelVar = 1;



	private SchemaReifier reificator;
	private StringBuilder headerLateImportBuilder;
	private Map<String,Boolean> importedColModelClasses;
	private List<String> relationNames;
	
	private Map<String, Integer> relationDescriptions; //ensures unicity of relations descriptions (names);
	private Map<String, Integer> relationVarNames; //ensures unicity of relations variable names constant;
	private Map<String, Integer> relationVar; //ensures unicity variables defining a relation.
	private Map<String,String> allRelationVar ; //holds all relation variable names used for the same relation 
	/**
	 * @param sch
	 */
	public RelationModelGenerator(Schema sch, SchemaReifier reif, String appName, String packName, String genPath) {
		super(sch, appName, packName, genPath);
		this.reificator = reif;
		this.headerLateImportBuilder = new StringBuilder();
		this.importedColModelClasses = new HashMap<String, Boolean>();
		this.relationNames = new ArrayList<String>();
		this.relationDescriptions = new HashMap<String, Integer>();
		this.relationVarNames = new HashMap<String, Integer>();
		this.relationVar = new HashMap<String, Integer>();
		
		this.allRelationVar = new HashMap<String, String>();
	}

	/* (non-Javadoc)
	 * @see reification.DBModelGenerator#generate()
	 *  
	 */
	@Override
	public void generate() {

		MyFileWriter mf = null ;
		RelationModel model = sch.getRelationModel();
		String fName = generationPath+ "/";

		try {
			fName += getMainClassName() + FILENAME_EXTENSION;
			mf = new MyFileWriter(fName);

			genCodeOfRelations(model, mf);

			mf.closeWriter();
		} catch (IOException e) {
			throw new RuntimeException("Error in method \"generate\" "+ e.getMessage() );
		}

		System.out.println("Generation done : " + fName	);

	}
	/*
	 * build code for the current relation model
	 */
	private void genCodeOfRelations(RelationModel schemaRelModel, MyFileWriter mf){

		StringBuilder genSb =  new StringBuilder();
		StringBuilder innerClassDefBuilder = new StringBuilder();
		String headerCode, mainClassDefCode;

		headerCode = genMainFileHeader();
		mainClassDefCode = genMainClassDefinition();

		List<Relation> listRel = schemaRelModel.getRelations();
		String precFocusTable = "" ;

		for (Relation relation : listRel) {
			String currFocusTable = getFocusTableName(relation);

			if(!currFocusTable.equals(precFocusTable)){//encountered a new focus table -> New static class should be generated

				//Ends declaration of previous class if exists
				if(!precFocusTable.isEmpty())
					innerClassDefBuilder.append(genEndClassDefinition(getFinalNameInnerClass(precFocusTable)));
				
				innerClassDefBuilder.append(genLines(3));
				
				innerClassDefBuilder.append(genRelations(relation, true, currFocusTable));
			}else{ // continue code generation if already began

				innerClassDefBuilder.append(genRelations(relation, false, currFocusTable));
			}
			precFocusTable = currFocusTable;
			
			//Save the name of the relation for further use in the constructor
			this.relationNames.add(getMainClassName()+ "."+ getFinalNameInnerClass(currFocusTable)+ "."+ getNextRelationVarForConstructor(relation));
			
		}

		//End declaration of last generated inner class
		innerClassDefBuilder.append(genEndClassDefinition(getFinalNameInnerClass(precFocusTable)));

		//Fill the StringBuilder with the final code
		genSb.append(headerCode); 
		//Builds the imports of the late generated classes 
		genSb.append(this.headerLateImportBuilder.toString());
		genSb.append(genLines(2));
		genSb.append(mainClassDefCode);
	
		//Generate constructor here 
		genSb.append(genConstructorCode());
		genSb.append(genLines(2));
		//Append additional methods here 
		genSb.append(genUpdateTablesMethod());
		//
		genSb.append(genLines(2));
		genSb.append(genUpdateAllRelationMembers());

		genSb.append(innerClassDefBuilder.toString());

		//Don't forget to close the main class too
		genSb.append(genEndClassDefinition(getMainClassName()));

		//Write everything on the disk in a file
		try {
			mf.writeIntoFile(genSb.toString());
		} catch (IOException e) {
			throw new RuntimeException("Error While Writing Relation model code in method \"genCodeOfRelations\" : "+ e.getMessage());
		}

	}

	/*
	 *  
	 * The concept of focus table determines the table for which we are currently generating relations.
	 * A table is a focus table when it has at least one foreign key among its attributes. This means that there is 
	 * a relation such that the foreign key references the primary key of another table.
	 * 
	 * IMPORTANT NOTE: This method relies assumes that the focus table will always be the SECOND RelationMember of the Relation. 
	 * It is the schema reificator that makes sure of this. So if you suspect this method to return bad results, should also check how
	 * the Relation members are build by the Schema Reificator.
	 *
	 */
	private String getFocusTableName(Relation relation){
		//Get the second member
		RelationMember focusMemb = relation.getRelationMembers().get(1);
		return focusMemb.getTable().getName();
	}

	private String genMainClassDefinition(){

		StringBuilder sb = new StringBuilder();
		String newClassName = getMainClassName();
		sb.append(buildClassDefinition(newClassName, getClassName(RelationModel.class), false));
		sb.append(genLines(2));
		return sb.toString();
	}


	private String getMainClassName(){

		String newClassName = appName+getClassName(RelationModel.class);
		return newClassName;
	}


	
	

	private String genConstructorCode(){
		StringBuilder sb = new StringBuilder();
		String relVar = "relations";
		String relDecl =getClassName(List.class) + "<"+ getClassName(Relation.class)+ ">" + SPACE + relVar + SPACE + VAR_AFFECT + SPACE +  
				getClassName(Arrays.class) +"." + METH_AS_LIST+ "(" + NEW_LINE;

		String endDecl =")"+ END_STATEMENT_LINE;
		sb.append(PUBLIC + SPACE + getMainClassName() + "()" + BRACKET_BEGIN + NEW_LINE);

		sb.append(relDecl);

		// Append all relations here
		int nbRelations = this.relationNames.size();
		for (int i = 0; i < nbRelations; i++) {
			
			if (i != (nbRelations - 1)){
				 sb.append(this.relationNames.get(i) + "," + NEW_LINE);
			}else{
				sb.append(this.relationNames.get(i) + NEW_LINE);
			}
				
		}

		sb.append(endDecl);
		sb.append(METH_SET_RELATIONS+ "("+ relVar +")" + END_STATEMENT_LINE);
		sb.append(BRACKET_END);

		return sb.toString();	
	}


	private String genUpdateTablesMethod(){

		String paramName = "tables";
		String tableParam = "dbTable";
		StringBuilder sb = new StringBuilder();
		String params = getClassName(List.class) + "<"+ getClassName(DBTable.class)+"> "+ paramName;
		sb.append(PUBLIC + SPACE + VOID  + SPACE + METH_UPDATE_TABLES + "(" + params + ")" + BRACKET_BEGIN + NEW_LINE);
		sb.append(genLines(2));
		String forLoopParams = getClassName(DBTable.class) + SPACE + tableParam + " : " + paramName;

		sb.append("\t" + FOR + SPACE +  "(" + forLoopParams + ")" + BRACKET_BEGIN + NEW_LINE);
		sb.append("\t\t" + METH_UPDATE_ALL_MEMBERS + "(" + tableParam + ")" + END_STATEMENT_LINE);

		sb.append("\t"+ BRACKET_END + NEW_LINE); // End loop 
		sb.append(BRACKET_END + NEW_LINE); // End method


		return sb.toString();

	}

	/*
	 *  Needed method for updating tables instances of relationMembers
	 *  
	 */
	private String genUpdateAllRelationMembers(){
		StringBuilder sb = new StringBuilder();
		String relParam = "relation";
		String tableParam = "table";

		String params= getClassName(DBTable.class) + " "+ tableParam;
		sb.append(PRIVATE + SPACE + VOID  + SPACE + METH_UPDATE_ALL_MEMBERS + "(" + params + ")" + BRACKET_BEGIN + NEW_LINE );

		String forParams = getClassName(Relation.class) + " " + relParam + " : "+ THIS + "." + METH_GET_RELATIONS +"()";
		sb.append("\t" + FOR + "("+ forParams+")" + BRACKET_BEGIN + NEW_LINE);
		String memberVar ="members";
		String declaration =getClassName(List.class)+"<"+ getClassName(RelationMember.class)+">" +SPACE + memberVar + VAR_AFFECT + relParam+ "."+ 
				METH_GET_MEMBERS + "()" + END_STATEMENT_LINE ;
		sb.append("\t\t" + declaration + NEW_LINE);
		String member ="member";
		String innerParams= getClassName(RelationMember.class) + SPACE + member+ " : "+ memberVar; 
		sb.append("\t\t" + FOR + "(" +innerParams+")" + BRACKET_BEGIN + NEW_LINE);

		String paramEQ = tableParam+ "." + METH_GET_NAME+ "()";
		String condition = member+ "." + METH_GET_TABLE+ "()"+"."+ METH_GET_NAME+"()"+ "."+ METH_EQUALS + "(" + paramEQ + ")";
		sb.append("\t\t\t" + IF + "(" + condition + ")" + NEW_LINE);
		sb.append("\t\t\t\t" + member + "." + METH_SET_TABLE + "(" + tableParam+ ")" + END_STATEMENT_LINE);

		sb.append("\t\t" + BRACKET_END + NEW_LINE);
		sb.append("\t" + BRACKET_END + NEW_LINE);
		sb.append(BRACKET_END + NEW_LINE); //End method
		return sb.toString();
	}



	/*
	 * Generate piece of code for declaring relations objects which are encapsulated in a dedicated static class
	 * 
	 * focusTableName: table for which relations are being generated
	 */
	private String genRelations(Relation relation, boolean startNewClass, String focusTableName){

		StringBuilder sb = new StringBuilder();
		String varNameRelDescr; // Ex: POI_DESCRIPTION_LANGUAGE = "Poi description is in a language";
		String varNameRelDef; // Ex: language = new Relation(...) 


		if(startNewClass){ // generate new static class for the current relation
			String classDef = genInnerClassHeader(focusTableName); 
			sb.append(classDef);

		}else{ //continue in the same class
			sb.append(genLines(2));	
		}

		//Generate the variable declaration of the relation description
		varNameRelDescr = getRelationDescriptionVarName(relation);
		String relDescr = buildRelationDescription(relation,varNameRelDescr);
		sb.append(relDescr + NEW_LINE) ;

		varNameRelDef = getVarNameRelDefinition(relation);
		String relDefinition = buildRelationDefinition(relation, varNameRelDescr, varNameRelDef);
		sb.append(relDefinition + NEW_LINE);

		return sb.toString();
	}


	/*
	 *  Generates the header of the inner class of the 
	 *  tableName: name of the table for which relations are being generated
	 */
	private String genInnerClassHeader(String tableName){

		StringBuilder sb = new StringBuilder();
		String finalClassName = getFinalNameInnerClass(tableName); 
		String res = buildClassDefinition(finalClassName, getClassName(RelationModel.class), true);
		sb.append(res);
		return sb.toString();
	}

	/*
	 * 
	 */
	private String getFinalNameInnerClass(String tableName){
		return tableName + RELATION_CLASSNAME_SUFFIX;
	}
	/*  Build the class definition statement 
	 * 
	 * Example of output
	 * public static class PoiDescriptionRelations extends RelationModel {
	 */
	private String buildClassDefinition(String className, String extendingClassName, boolean isStatic){

		StringBuilder sb = new StringBuilder();
		String keyWordStatic ="";
		if(isStatic)
			keyWordStatic  = STATIC;

		sb.append(PUBLIC + SPACE + keyWordStatic + SPACE +  CLASS + SPACE + className + SPACE + 
				EXTENDS + SPACE + extendingClassName  + SPACE + BRACKET_BEGIN + SPACE + NEW_LINE);

		return sb.toString();
	}

	/*
	 * Build the declaration of variable holding the description of the relation
	 * 
	 * Example of output: 
	 * 	public static final String POI_DESCRIPTION_LANGUAGE = "Poi description is in a language";

	 */
	private String buildRelationDescription(Relation relation, String varNameRelDescr){
		StringBuilder sb = new StringBuilder();

		List<RelationMember> members = relation.getRelationMembers();
		//default reading direction of the relation
		// Doesn't deal with fact that a relation can be ternary because the latter can be broken down into multiple relation using an intermediate entity 
		String descr; 
		descr = members.get(0).getTable().getName() + " has (or is in) a " + members.get(1).getTable().getName();

		if(members.get(0).getOccurrence().equals(Occurrence.MANY)){ // Many member[0] - One member[1]
			descr = members.get(1).getTable().getName() + " has (or is in) a" + members.get(0).getTable().getName();			
		}
		descr = buildUniqueDescription(descr);
		//Variable declaring the relation description should be the relation put in uppercase (constant style)
		sb.append(PUBLIC + SPACE + STATIC + SPACE + FINAL + SPACE + STRING + SPACE + varNameRelDescr + SPACE + 
				VAR_AFFECT + SPACE +  "\"" + descr + "\"" + END_STATEMENT_LINE );

		return sb.toString();

	}

	private String buildUniqueDescription(String relDescrip){
		Integer flag = this.relationDescriptions.get(relDescrip);
		String finalDescr;
		if(flag == null){
			flag = new Integer(cptRelDescr);
			this.relationDescriptions.put(relDescrip, flag);
			finalDescr = relDescrip;
		}else{
			int val = flag.intValue();
			finalDescr = relDescrip+ "_" + (val+1);
			flag = new Integer(val+1);
		}
		this.relationDescriptions.put(relDescrip, flag);

		return finalDescr;
	}
	
	
	
	

	/*
	 * Get the name of the variable that is going to be used to declare relation description ( as a JAVA static  constant) 
	 */
	private String getRelationDescriptionVarName(Relation rel){
		
		String varNAme = rel.getName().toUpperCase();	
	
		Integer flag = this.relationVarNames.get(varNAme);
		String finalVarName;
		if(flag == null){
			flag = new Integer(cptRelVarName);
			this.relationVarNames.put(varNAme, flag);
			finalVarName = varNAme;
		}else{
			int val = flag.intValue();
			finalVarName = varNAme+ "_" + (val+1);
			flag = new Integer(val+1);
		}
		this.relationVarNames.put(varNAme, flag);

		return finalVarName;
	}

	/*
	 * Get the name of the variable used to define the relation
	 * Example: public Relation <var> = new Relation(...) 
	 * Here we're trying to build <var> 
	 */
	private String getVarNameRelDefinition(Relation rel){
		String relName  =rel.getName();
		
		String relVar  =  RELVAR_PREFIX + relName;
		
		Integer flag = this.relationVar.get(relVar);
		String finalRelVar;
		if(flag == null){
			flag = new Integer(cptRelVar);
			this.relationVar.put(relVar, flag);
			finalRelVar = relVar;
		}else{
			int val = flag.intValue();
			finalRelVar = relVar+ "_" + (val+1);
			flag = new Integer(val+1);
		}
		this.relationVar.put(relVar, flag);
		this.allRelationVar.put(rel.getName(), finalRelVar);		
		return finalRelVar;
	}
	
	private String getNextRelationVarForConstructor(Relation relation){
		
		String relName = relation.getName();
		Set<String> keys = this.allRelationVar.keySet();
		List<String> listRelVar = new ArrayList<String>();
		String finalRelVar; 
		for (String relKey : keys) {
			if(relName.equals(relKey))
					listRelVar.add(allRelationVar.get(relKey)); 
		}
		finalRelVar = listRelVar.get(0);
		return finalRelVar; 
	}
	

	/*
	 * 	Build the actual declaration of the relation
	 * 
	 */

	private String buildRelationDefinition(Relation relation, String relDescrVar, String relVarName){

		StringBuilder sb = new StringBuilder();
		StringBuilder sbArgsRel = new StringBuilder();
		StringBuilder sbArgsList = new StringBuilder();
		String card1,card2, newMembName1, newMembName2;
		RelationMember memb1 = relation.getRelationMembers().get(0) ; 
		RelationMember memb2 = relation.getRelationMembers().get(1) ;

		card1 = buildCardinalityType(memb1);
		card2 = buildCardinalityType(memb2);

		newMembName1 =  memb1.getTable().getName()+ TABLE_NAME_SUFFIX; 
		newMembName2 =  memb2.getTable().getName() + TABLE_NAME_SUFFIX;

		//Build arguments passed to method (asList) inside Relation constructor 
		sbArgsList.append(NEW + SPACE + getClassName(RelationMember.class) + "(" + NEW+ SPACE + newMembName1+ "()"  + "," + card1 + ")" + "," + NEW_LINE);
		sbArgsList.append(NEW + SPACE + getClassName(RelationMember.class) + "(" + NEW + SPACE +  newMembName2+ "()" + "," + card2 + ")");

		//build arguments passed to the Relation constructor 
		sbArgsRel.append(relDescrVar+ "," + NEW_LINE + getClassName(Arrays.class) + "." + METH_AS_LIST + "(" + sbArgsList.toString() + ")," + 
				NEW_LINE + genRelationConditionCode(relation));			
		//Add the whole thing
		sb.append(PUBLIC + SPACE + STATIC + SPACE+  getClassName(Relation.class) + SPACE + relVarName + SPACE + VAR_AFFECT + SPACE + NEW + SPACE + 
				getClassName(Relation.class) + "(" + sbArgsRel.toString() + ")" + END_STATEMENT_LINE);
		return sb.toString();

	}


	/*
	 * Get the appropriate type of cardinality
	 */
	private String buildCardinalityType(RelationMember member){
		String occType; 

		if(member.getOccurrence().equals(Occurrence.MANY))
			occType = getClassName(Occurrence.class) + ".MANY";
		else
			occType = getClassName(Occurrence.class) + ".ONE";

		return occType;
	}


	/*
	 * Generates the portion of code setting up the relation condition for the current relation.
	 *
	 */
	private String genRelationConditionCode(Relation relation){

	
		StringBuilder sb  = new StringBuilder(); 

		//build method call for setting relation condition on the relation
		sb.append(NEW + SPACE + getClassName(RelationCondition.class) + "()" + SPACE +  BRACKET_BEGIN + NEW_LINE);

		//Build the arguments passed to the set condition methods
		String primaryTabName = getTableName(relation, true); // primary: table contains primary key referenced by a foreign key in secondary table
		String secondaryTabName = getTableName(relation, false); 

		//Get the roles
		List<String> tableNames = new ArrayList<String>(2);
		tableNames.add(0,primaryTabName);
		tableNames.add(1,secondaryTabName);
		List<String> roles = null;
		try {
			roles = this.reificator.getRoleNames(tableNames);
		} catch (MissingRoleException e) {
			throw new RuntimeException(e.getMessage());
		}

		String rolePrimary, roleSecond;	 
		List<String> rolesForCode = buildRolesForCode(roles);
		rolePrimary = rolesForCode.get(0);
		roleSecond = rolesForCode.get(1);

		String primaryVarName, secondVarName;

		if(inRecursiveRelation(primaryTabName, secondaryTabName)){ //same tables => use roles to differentiate them
			primaryVarName = primaryTabName.concat(roles.get(0)).toLowerCase();
			secondVarName = secondaryTabName.concat(roles.get(1)).toLowerCase();
		}else{
			primaryVarName= primaryTabName.toLowerCase();
			secondVarName = secondaryTabName.toLowerCase();
		}

		String evalMethArgs =rolePrimary + SPACE +  getClassName(Table.class) + SPACE + primaryVarName;
		evalMethArgs+="," + SPACE;
		evalMethArgs+=roleSecond + SPACE + getClassName(Table.class) + SPACE + secondVarName;

		sb.append("\t" + PUBLIC + SPACE + getClassName(SqlCondition.class) + SPACE + METH_EVAL +"(" + evalMethArgs + ")" + BRACKET_BEGIN + COMMENT_BEGIN + DEFAULT_COMMENT_ROLE + NEW_LINE);
		String eqMethArgs = buildArgsOfSqlConditionEqMethod(relation, primaryTabName, secondaryTabName, roles);
		
		sb.append("\t\t" + RETURN + SPACE + NEW + SPACE + getClassName(SqlCondition.class) +"()" + "." + 	METH_EQ + "(" + eqMethArgs + ")" + END_STATEMENT_LINE);
		sb.append("\t" + BRACKET_END);
		sb.append(BRACKET_END);


		return sb.toString();
	}

	private List<String> buildRolesForCode(List<String> roles){

		String rolePrimary = "";
		String roleSecond ="";

		List<String> finalRoles = new ArrayList<String>(roles.size());
		if(roles != null){
			//Build the roles using the role class
			String primRole = roles.get(0);
			String secRole = roles.get(1);

			if(!primRole.equals(SchemaReifier.NO_ROLE))
				rolePrimary = "@" + getClassName(Role.class) + "(\"" + primRole.toLowerCase() + "\")";
			else
				rolePrimary ="";

			if(!secRole.equals(SchemaReifier.NO_ROLE))
				roleSecond ="@" + getClassName(Role.class) + "(\"" + secRole.toLowerCase() + "\")" ;
			else
				roleSecond ="";

		}

		finalRoles.add(0,rolePrimary);
		finalRoles.add(1,roleSecond);

		return finalRoles;
	}

	private boolean inRecursiveRelation(String tab1Name, String tab2Name){
		return tab1Name.equals(tab2Name);
	}


	/*
	 * Building the set of arguments passed to "eval" method of the RelationCondition class 
	 * 
	 */
	private String buildArgsOfSqlConditionEqMethod(Relation rel, String primaryTableName, String secTableName, List<String> roles){

		
		String args = "";
		String fkColumnName = getColumnForeignKeyInSecondaryTable(rel).toUpperCase();
		String pkColumnName = getPrimaryColumnInPrimaryTable(rel).toUpperCase();

		
		String tableName =  getTableName(rel, false);
		String correspondingSecTableClassName = tableName + TABLE_NAME_SUFFIX;
		String primTabName = getTableName(rel, true);
		String correspondingPrimTableClassName = primTabName + TABLE_NAME_SUFFIX;
		
		//Example of class to import: PoiDescriptionColumns
		String secTableColModelClassName = tableName+ COLUMNCLASS_NAME_SUFFIX;
		String primaryTableColModelClassName = getTableName(rel, true) + COLUMNCLASS_NAME_SUFFIX;
		
		if(!this.importedColModelClasses.containsKey(secTableColModelClassName)){
			//Register class for import
			buildLateImport(secTableColModelClassName, correspondingSecTableClassName);
			this.importedColModelClasses.put(secTableColModelClassName, new Boolean(true));
		}
		//Same here
		if(!this.importedColModelClasses.containsKey(primaryTableColModelClassName)){
			
			buildLateImport(primaryTableColModelClassName, correspondingPrimTableClassName);
			this.importedColModelClasses.put(primaryTableColModelClassName, new Boolean(true))	;
		}
		
		String primaryVar, secondVar;
		if(inRecursiveRelation(primaryTableName, secTableName)){
			primaryVar = primaryTableName.concat(roles.get(0)).toLowerCase();
			secondVar = secTableName.concat(roles.get(1)).toLowerCase();
		}else{
			primaryVar = primaryTableName.toLowerCase();
			secondVar = secTableName.toLowerCase();
		}
		
		args+=primaryVar + "."+ METH_GET_COL + "(" + primaryTableColModelClassName+ "."+ pkColumnName +")";
		args+=",";
		args+=secondVar + "." + METH_GET_COL + "(" + secTableColModelClassName+ "." + fkColumnName +")";

		return args;
	}

	
	/*
	 * 
	 * In the table where the foreign key of the relation is located, retrieves the column name
	 */
	private String getPrimaryColumnInPrimaryTable(Relation rel){

		DefaultRelationCondition cond = (DefaultRelationCondition) rel.getCondition();
		return cond.getColParent();
	}
	/*
	 * 
	 * In the table where the foreign key of the relation is located, retrieves the column name
	 */
	private String getColumnForeignKeyInSecondaryTable(Relation rel){

		DefaultRelationCondition cond = (DefaultRelationCondition) rel.getCondition();
		return cond.getColChild();
	}

	/*
	 * The primary table is one of the members of the relation.
	 * By primary table we mean the table containing primary key referenced by foreign key in the other member of the relation
	 */
	private String getTableName(Relation rel, boolean primary ){

		DefaultRelationCondition cond = (DefaultRelationCondition) rel.getCondition();
		if(primary)
			return cond.getTabParent().getName();
		//Otherwise get foreign key
		return cond.getTabChild().getName();
	}
	/*
	 * Append the current class name in the list of classes to be imported
	 */
	private void buildLateImport(String columnClassName , String encapsulatingClassName){
		//Build import 
		this.headerLateImportBuilder.append(IMPORT + SPACE + packName + "." + encapsulatingClassName + "." + columnClassName + END_STATEMENT_LINE );
		this.headerLateImportBuilder.append(IMPORT + SPACE + packName + "." + encapsulatingClassName + END_STATEMENT_LINE );
	}


}
