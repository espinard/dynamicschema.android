package com.github.dynamicschema.android.reification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dynamicschema.reification.DBTable;
import org.dynamicschema.reification.RelationModel;
import org.dynamicschema.reification.Schema;
import org.dynamicschema.sql.RelationCondition;

import com.github.dynamicschema.android.sql.EmptyFilteringCondition;

public class SchemaGenerator extends AbstractDBModelGenerator {

	public SchemaGenerator(Schema sch, String appName, String packageName,	String genPath) {
		super(sch, appName, packageName, genPath);

	}

	@Override
	public void generate() {
		MyFileWriter mf = null ;
		List<DBTable> tables = sch.getTables();

		try {

			String fName = generationPath + "/";
			fName += appName+ SCHEMA_NAME_SUFFIX + FILENAME_EXTENSION;
			mf = new MyFileWriter(fName);
			
			String fileContent = generateSchemaClass(tables);
			mf.writeIntoFile(fileContent);
			System.out.println("Generated class: "+ fName);	
			mf.closeWriter();

		} catch (IOException e) {
			throw new RuntimeException("Error in method \"generate\" of Schema class "+ e.getMessage() );
		}

	}


	private String generateSchemaClass(List<DBTable> tables){
		StringBuilder sb = new StringBuilder();
		sb.append(genMainFileHeader());
		sb.append(genLines(2));
		sb.append(genMainClassDef());
		sb.append(genConstructor());
		sb.append(genInitTablesMethod(tables));
		sb.append(genLines(2));
		sb.append(COMMENT_BEGIN + "Default method. Set your desired initial filtering != null" + NEW_LINE);
		sb.append(genSetDefaultFilteringMethod());
		sb.append(genLines(2));
		sb.append(genEndClassDefinition(getMainClassName()));
		return sb.toString();
	}
	private String genMainClassDef(){
		StringBuilder sb = new StringBuilder();
		sb.append(PUBLIC + SPACE + CLASS + SPACE + getMainClassName() +
					SPACE + EXTENDS + SPACE  + getClassName(Schema.class) + BRACKET_BEGIN + NEW_LINE);
		return sb.toString();	
	}
	
	private String getMainClassName(){
		return appName+ SCHEMA_NAME_SUFFIX;
	}
	
	private String getExpectedRelationModelClassName(){
		return appName  + getClassName(RelationModel.class);
	}
	
	private String getExpectedTableClassName(String tableName){
		return tableName + TABLE_NAME_SUFFIX;
	}

	private String genConstructor(){
		StringBuilder sb = new StringBuilder();

		String decl = PUBLIC + SPACE + getMainClassName() + "()" + BRACKET_BEGIN + NEW_LINE;
		sb.append("\t"+ decl);
		String tablesVar = "tables";
		sb.append("\t\t" + getClassName(List.class) + "<" +getClassName(DBTable.class)+ ">" + SPACE + tablesVar + VAR_AFFECT + SPACE + METH_INIT_TABLES + "()" + END_STATEMENT_LINE);
		String args = tablesVar + "," + NEW + SPACE +  getClassName(EmptyFilteringCondition.class) + "()";
		sb.append("\t\t" + METH_INIT_FILTERING + "(" +args +")" + END_STATEMENT_LINE );
		String modelVar = "model";
		sb.append("\t\t" + getExpectedRelationModelClassName() + SPACE + modelVar + VAR_AFFECT + NEW + SPACE +  getExpectedRelationModelClassName()+ "()" + END_STATEMENT_LINE);
		sb.append("\t\t" + modelVar + "." + METH_UPDATE_TABLES + "(" + tablesVar + ")"+ END_STATEMENT_LINE);
		sb.append("\t\t"  + METH_SET_SCH_TABLES + "(" + tablesVar + ")"+ END_STATEMENT_LINE);
		sb.append("\t\t" + METH_SET_RELATION_MODEL + "(" + modelVar + ")" + END_STATEMENT_LINE);
		sb.append("\t" + BRACKET_END + NEW_LINE); //end method
		return sb.toString();	

	}

	private String genInitTablesMethod(List<DBTable> listTables){
		StringBuilder sb = new StringBuilder();
		String tablesVar = "tables";
		String returnType = getClassName(List.class) + "<" +getClassName(DBTable.class)+ ">";
		
		sb.append(PRIVATE + SPACE + returnType +SPACE + METH_INIT_TABLES + "()" + BRACKET_BEGIN + NEW_LINE);
		sb.append("\t\t" + returnType + SPACE + tablesVar + VAR_AFFECT + SPACE + NEW + SPACE +  getClassName(ArrayList.class)+ "<" + getClassName(DBTable.class)+ ">" + "()" + END_STATEMENT_LINE);
		
		for (DBTable dbTable : listTables) {
			String className = getExpectedTableClassName(dbTable.getName());
			sb.append("\t\t" + tablesVar + "." + METH_LIST_ADD + "("+  NEW + SPACE +  className + "()"+")" + END_STATEMENT_LINE);
		}
		
		sb.append(RETURN+ SPACE + tablesVar + END_STATEMENT_LINE);
		sb.append(BRACKET_END + NEW_LINE); //End method
		return sb.toString();	
	}

	private String genSetDefaultFilteringMethod(){
		StringBuilder sb = new StringBuilder();
		String tablesVar = "tables";
		String filteringVar = "filtering";
		String args =getClassName(List.class) + "<" +getClassName(DBTable.class)+ ">" + SPACE + tablesVar + "," + getClassName(RelationCondition.class) + SPACE  +  filteringVar; 
		sb.append("\t" + PRIVATE + SPACE + VOID + SPACE + METH_INIT_FILTERING + "(" + args +")" + BRACKET_BEGIN + NEW_LINE);
		String dbTableVar = "dbTable";
		String forArgs = getClassName(DBTable.class) + SPACE + dbTableVar + " : " + tablesVar;
		sb.append("\t\t" + FOR + "(" + forArgs + ")" + BRACKET_BEGIN + NEW_LINE);
		sb.append("\t\t\t" + dbTableVar + "." + METH_SET_FILTERING + "(" + filteringVar + ")" + END_STATEMENT_LINE);
		sb.append("\t\t" + BRACKET_END + NEW_LINE); // end for
		sb.append(BRACKET_END + NEW_LINE); //End method
		
		return sb.toString();	
	}
}
