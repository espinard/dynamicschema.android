package com.github.dynamicschema.android.reification;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.dynamicschema.reification.Schema;
import org.dynamicschema.sql.RelationCondition;

public abstract class AbstractDBModelGenerator implements IDBModelGenerator {

	public static final String FILENAME_EXTENSION = ".java";
	// Special Android ID column to import
	protected static final String ANDROID_ID ="_ID";
	protected static final String ANDROID_ID_PACKAGE = "android.provider.BaseColumns";
	
	
	protected static final String TABLE_NAME_SUFFIX = "Table";
	protected static final String COLUMNCLASS_NAME_SUFFIX = "Columns";
	protected static final String RELATION_CLASSNAME_SUFFIX = "Relations";
	protected static final String TABLE_NAME_FIELD = "NAME";
	protected static final String SCHEMA_NAME_SUFFIX = "Schema";


	//Defines all the necessary keywords that will be used. 

	//Java Language keywords
	protected static final String PUBLIC = "public";
	protected static final String PRIVATE = "private";
	protected static final String CLASS = "class";
	protected static final String IMPORT = "import";
	protected static final String PACKAGE = "package";
	protected static final String EXTENDS = "extends";
	protected static final String STATIC = "static";
	protected static final String SUPER = "super";
	protected static final String NEW = "new";
	protected static final String FINAL = "final";
	protected static final String COMMENT_BEGIN = "//";
	protected static final String CUSTOM_COMMENT_BEGIN = "/*";
	protected static final String CUSTOM_COMMENT_END = "*/";
	protected static final String NULL = "null";
	protected static final String RETURN = "return";
	protected static final String VOID = "void";
	protected static final String FOR = "for";
	protected static final String THIS = "this";
	protected static final String IF = "if";





	//
	protected static final String END_STATEMENT_LINE = "; \n";
	protected static final String NEW_LINE = "\n";
	protected static final String SPACE = " "; 
	protected static final String VAR_AFFECT = "=";
	protected static final String BRACKET_BEGIN = "{";
	protected static final String BRACKET_END = "}";

	//Data types keywords
	protected static final String INT = "int";
	protected static final String STRING = "String";

	//Method Names used in the code generation 
	protected static final String METH_SETCOLUM_NAMES = "setColumnsNames";
	protected static final String METH_AS_LIST = "asList";
	protected static final String METH_SET_CONDITION = "setCondition";
	protected static final String METH_SET_CONSTRAINTS = "setColumnsConstraints";
	protected static final String METH_EVAL = RelationCondition.EVAL_METHOD_NAME;
	protected static final String METH_EQ = "eq";
	protected static final String METH_GET_COL = "col";
	protected static final String METH_SET_RELATIONS = "setRelations";
	protected static final String METH_UPDATE_TABLES = "updateTables";
	protected static final String METH_UPDATE_ALL_MEMBERS = "updateAllRelationMembers";
	protected static final String METH_GET_MEMBERS = "getRelationMembers";
	protected static final String METH_GET_RELATIONS = "getRelations";
	protected static final String METH_GET_FROM_LIST = "get";
	protected static final String METH_GET_TABLE = "getTable";
	protected static final String METH_GET_NAME = "getName";
	protected static final String METH_EQUALS = "equals";
	protected static final String METH_SET_TABLE = "setTable";
	protected static final String METH_INIT_TABLES = "initTables";
	protected static final String METH_INIT_FILTERING = "initFilterings";
	protected static final String METH_SET_RELATION_MODEL = "setRelationModel";
	protected static final String METH_LIST_ADD = "add";
	protected static final String METH_SET_FILTERING = "setFiltering";
	protected static final String METH_SET_SCH_TABLES = "setTables";


	
	






	//Reified schema to work on
	protected Schema sch;
	private Map<String,String> classNameMap;
	protected String packName;
	protected String appName;
	protected String generationPath;

	/**
	 * @param sch
	 */
	public AbstractDBModelGenerator(Schema sch, String appName, String packageName, String genPath) {
		this.classNameMap = new HashMap<String, String>();
		this.sch = sch;
		this.generationPath = genPath;
		this.appName = appName;
		this.packName = packageName;
	}


	/*
	 * Generates a given number of Carriage Return
	 */
	protected String genLines(int number){
		String res= "";
		for (int i = 0; i < number; i++) {
			res+=NEW_LINE;
		}
		return res;
	}



	/*
	 * Ends the definition of a class
	 */
	protected String genEndClassDefinition(String className) {
		String res = "";
		res+=genLines(2);
		res+= BRACKET_END + SPACE + COMMENT_BEGIN + "End of " + className;
		return res;
	}
	
	/*
	 * Get the name of the class associated to the key passed as parameter
	 */
	protected String getClassName(String key){
		
		Set<String> keySet = this.classNameMap.keySet();
		for (String currKey : keySet) {
			if(currKey.equals(key)){
				return this.classNameMap.get(currKey);
			}
		}
		return null; //no Name was found
	}
	
	
	protected String getClassName(Class c){
		return c.getSimpleName();
	}
	
	protected String getPackageName(Class c){
		String packName = c.getPackage().getName();
		return packName;
	}

	protected String genImports(){
		
		String tempComment ="";
		StringBuilder sb = new StringBuilder();
		Set<Class> propKeys = RequiredClasses.getReqClasses();

		for (Class key : propKeys) {
			String packName; 
			String clName;
			packName = key.getPackage().getName();
			clName =  key.getSimpleName();
			sb.append(IMPORT + SPACE + packName + "." + clName + END_STATEMENT_LINE );
		}
		
		
		
		return sb.toString();
	}

	/*
	 * Generate header with imports and package declaration 
	 */
	protected String genMainFileHeader(){
		
		String temp_commenting ="";
		StringBuilder sb = new StringBuilder();
		String currPackageName = packName;
		//Build the package declaration 
		sb.append(PACKAGE + SPACE +  currPackageName + END_STATEMENT_LINE);	
		sb.append(genLines(2));
		//Building the import statements
		sb.append(genImports());
		
		//TODO TEMPORARY FOR AVOID COMPILE ERRORS ===================
		if(ANDROID_ID_PACKAGE.contains("android"))
			temp_commenting = COMMENT_BEGIN;
		else
			temp_commenting = "";

		sb.append(temp_commenting + IMPORT + SPACE + STATIC + SPACE + ANDROID_ID_PACKAGE + "." + ANDROID_ID + END_STATEMENT_LINE);
		//================================
	
		sb.append(genLines(2));


		return sb.toString();
	}
	

	public abstract void generate();
	
}
