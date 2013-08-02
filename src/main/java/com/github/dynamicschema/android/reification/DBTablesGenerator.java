package com.github.dynamicschema.android.reification;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.dynamicschema.reification.Column;
import org.dynamicschema.reification.ColumnModel;
import org.dynamicschema.reification.DBTable;
import org.dynamicschema.reification.Schema;
import org.dynamicschema.reification.columnconstraint.ColumnConstraint;
import org.dynamicschema.reification.columnconstraint.ForeignKey;
import org.dynamicschema.reification.columnconstraint.PrimaryKey;

/**
 * Generate classes corresponding to tables of the database 
 * @author esp
 *
 */
public class DBTablesGenerator  extends AbstractDBModelGenerator {

	/**
	 * @param sch
	 */
	public DBTablesGenerator(Schema sch, String appName, String packageName, String genPath) {
		super(sch,appName,packageName, genPath);
	}

	@Override
	public void generate() {

		MyFileWriter mf = null ;
		List<DBTable> tables = sch.getTables();

		try {
			for (DBTable table : tables) {
				
				String fName = generationPath + "/";
				fName += table.getName()+ TABLE_NAME_SUFFIX + FILENAME_EXTENSION;
				mf = new MyFileWriter(fName);
				genDBModelClassOfTable(mf,table);
				
				System.out.println("Generated class: "+ fName);	
			}

			mf.closeWriter();


		} catch (IOException e) {
			throw new RuntimeException("Error in method \"generate\" of tables "+ e.getMessage() );
		}


		System.out.println("Number of Generated Classes: "+ tables.size());
	}

	/*
	 * Generate the class table
	 */
	private void genDBModelClassOfTable(MyFileWriter mf, DBTable table){

		StringBuilder sb = new StringBuilder();
		String header  = genMainFileHeader();
		sb.append(header);
		sb.append(genLines(1));
		String body = getClassDefinition(table);
		sb.append(body);
		sb.append(genLines(1));

		try {
			mf.writeIntoFile(sb.toString());
		} catch (IOException e) {
			throw new RuntimeException("Error in table generation method genDBModelClassOfTable: "+ e.getMessage());
		}


	}
	/*
	 * Generate the table class definition for a given reified table
	 */
	private String getClassDefinition(DBTable table){

		StringBuilder sb = new StringBuilder();
		String [] cstrArgs = new String[2];
		String newClassName =  table.getName() + TABLE_NAME_SUFFIX; 

		//Class definition 
		sb.append(PUBLIC + SPACE + CLASS + SPACE + newClassName + SPACE + EXTENDS +
							SPACE + getClassName(DBTable.class)  + SPACE + BRACKET_BEGIN + SPACE + NEW_LINE);
		sb.append(genLines(2)); //adding spacing lines
		sb.append("\t" + PUBLIC + SPACE + STATIC + SPACE + FINAL + SPACE + 
				STRING + SPACE + TABLE_NAME_FIELD + SPACE + VAR_AFFECT + SPACE + "\""+table.getName()+ "\"" + END_STATEMENT_LINE) ;
		sb.append(genLines(2));
		//Adding Column model definition
		String colClassDef = genColumnsClassDeclaration(table.getName(),table.getColumnModel());
		sb.append(colClassDef);
		sb.append(genLines(2));
		cstrArgs[0] = TABLE_NAME_FIELD;
		cstrArgs[1] = NEW + SPACE  + table.getName()+ COLUMNCLASS_NAME_SUFFIX + "()";
		String constructor = genConstructor(newClassName, cstrArgs);
		sb.append("\t" + constructor);
		sb.append(genLines(2));
		sb.append(BRACKET_END);
		return sb.toString();

	}




	/*
	 *  Generate the class defining the columns model of the current table
	 *  
	 */
	private String genColumnsClassDeclaration(String tableName, ColumnModel model){
		
		StringBuilder sb = new StringBuilder();
		String newClassName = tableName + COLUMNCLASS_NAME_SUFFIX;		
		sb.append("\t" + PUBLIC + SPACE + STATIC + SPACE + CLASS + SPACE + newClassName + SPACE +
				EXTENDS + SPACE + getClassName(ColumnModel.class)  + SPACE + BRACKET_BEGIN + SPACE + NEW_LINE);
		sb.append(genLines(1));
		sb.append("\t\t" + COMMENT_BEGIN + "tables column names");
		sb.append(genLines(1));



		//Fetch all the column names
		List<Column> cols = model.getColumns();
		String [] argArray = new String[cols.size()];
	
		
		for (int i = 0; i < cols.size(); i++) {
			String name = cols.get(i).getSimpleName();
			String cstName =name.toUpperCase();
			sb.append("\t\t" + PUBLIC + SPACE + STATIC + SPACE + STRING + SPACE + cstName + SPACE + 
																		VAR_AFFECT + SPACE + "\"" + name + "\"" + END_STATEMENT_LINE);
			argArray[i] = name.toUpperCase();
		}
		sb.append(genLines(1));

		String argList = makeArgsList(argArray);

		//Defining the constructor of the ColumnModel inner class

		sb.append("\t\t" +  PUBLIC + SPACE + newClassName +"()" + SPACE + BRACKET_BEGIN + NEW_LINE);
		
		sb.append("\t\t\t" +  METH_SETCOLUM_NAMES + "(" + getClassName(Arrays.class) + "." + METH_AS_LIST +"("  + argList + ")" + ")" + END_STATEMENT_LINE );

		//Build constraint Code
		sb.append("\t\t\t" + buildConstraintsSettingCode(model));
		
		sb.append("\t\t" + BRACKET_END);

		//end of class defining columns
		sb.append(genLines(1));
		sb.append("\t" + BRACKET_END);

		return sb.toString();
	}


	/* TEMP COMMENT
	 *				   new PrimaryKey(colNam1,colName2,coln),
		           		new ForeignKey(
		           			asList(col1,col2,col3,coln), 
		           			foreignTableName, (!!! What is the name of the table? Like in the database or In the DB model in Java being generated? )
		           			asList(col1,col2,col3,coln)
		           		)
	 */

	private String buildConstraintsSettingCode(ColumnModel model){
	
		StringBuilder sb = new StringBuilder();
		String mainArgs = "";
		List<ColumnConstraint> colConstrList = model.getColumnsConstraints();
		for (int i = 0; i < colConstrList.size()- 1; i++) {
			ColumnConstraint colConstr = colConstrList.get(i);
			String cstrCode = buildConstraintCode(colConstr);
			mainArgs += cstrCode + "," + NEW_LINE;
		}
		
		mainArgs+=buildConstraintCode(colConstrList.get(colConstrList.size()-1)) + NEW_LINE ;
		
		sb.append(METH_SET_CONSTRAINTS + "(" + getClassName(Arrays.class) + "." + METH_AS_LIST + "(" + mainArgs + ")" +")" + END_STATEMENT_LINE);
		return sb.toString();
	}

	/*
	 * Generate the constructor of the class of the table being generated
	 */

	private String genConstructor(String tableName, String[] bodyArgs){

		StringBuilder sb  = new StringBuilder();
		sb.append(PUBLIC + SPACE + tableName + "()" + BRACKET_BEGIN + NEW_LINE);
		String args =makeArgsList(bodyArgs);
		sb.append("\t\t" + SUPER + SPACE + "("+ args + ")" + END_STATEMENT_LINE);
		sb.append(genLines(1));
		sb.append("\t" + BRACKET_END);
		return sb.toString();

	}


	private String makeArgsList(String[] argList) {
		String args = "";	
		//Preparing arguments 
		for (int i = 0; i < argList.length - 1; i++) {
			args+=argList[i];
			args+="," + SPACE;
		}
		args+= argList[argList.length - 1];
		return args;
	}

 
	private String buildConstraintCode(ColumnConstraint constr){
		
		StringBuilder sb = new StringBuilder();
		StringBuilder innerSb = new StringBuilder();
		
		if(constr instanceof PrimaryKey){ 
			PrimaryKey pkConstr = (PrimaryKey) constr;
			
			List<String> cols = pkConstr.getColumnsNames();

			for (int i = 0; i < cols.size()-1; i++) {
				innerSb.append("\"" + cols.get(i)+ "\" ,");
			}
			innerSb.append("\"" + cols.get(cols.size()-1) + "\"");
						
			sb.append("(" + getClassName(ColumnConstraint.class)+ ")" + SPACE + NEW  + SPACE + getClassName(PrimaryKey.class) + "(" + getClassName(Arrays.class) + "." + METH_AS_LIST + "("+ innerSb.toString() +")" + ")");
		}else{ //Foreign key
			
			String nameArgs = "";
			String foreignNames ="";
			ForeignKey fkConstr = (ForeignKey)constr; 
			List<String> localNames = fkConstr.getLocalColumnsNames();
			
			for (int i = 0; i < localNames.size()-1; i++) {
				nameArgs+="\"" + localNames.get(i) + "\"" + ",";
			}
			nameArgs+="\"" + localNames.get(localNames.size()-1) + "\"";
			innerSb.append(getClassName(Arrays.class) + "." + METH_AS_LIST + "(" + nameArgs + ")" +"," + NEW_LINE);
			innerSb.append("\"" + fkConstr.getForeignTableName() + "\"" + "," + NEW_LINE);
				
			List<String> foreignTabNames = fkConstr.getForeignColumnsNames();
			for (int i = 0; i < foreignTabNames.size()-1; i++) {
				foreignNames+= "\"" + foreignTabNames.get(i)+ "\"" + ",";
			}
			foreignNames +="\""+ foreignTabNames.get(foreignTabNames.size()-1) + "\"";
			innerSb.append(getClassName(Arrays.class) + "." + METH_AS_LIST + "(" + foreignNames + ")" + NEW_LINE);
			sb.append(SPACE + "(" + getClassName(ColumnConstraint.class)+ ")" + NEW + SPACE +  getClassName(ForeignKey.class) + "(" + innerSb.toString() +")");
			
		}
		
		return sb.toString();
	}

}
