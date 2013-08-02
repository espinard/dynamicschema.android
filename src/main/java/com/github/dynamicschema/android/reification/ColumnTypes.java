package com.github.dynamicschema.android.reification;

public class ColumnTypes {
	
		public static final String INTEGER = "INTEGER";
		public static final String VARCHAR = "VARCHAR";
		public static final String REAL = "REAL";
		public static final String BOOLEAN = "BOOLEAN";
		public static final String NUMERIC= "NUMERIC";
		public static final String DATE = "DATE";
		public static final String FLOAT = "FLOAT";
		
		
		public static String getType(String name){
			
			if (name.equalsIgnoreCase(INTEGER))
				return INTEGER;
		
			if (name.equalsIgnoreCase(VARCHAR))
				return VARCHAR;
			
			if (name.equalsIgnoreCase(REAL))
				return REAL;
			
			if (name.equalsIgnoreCase(NUMERIC))
				return NUMERIC;
			
			if (name.equalsIgnoreCase(BOOLEAN))
				return BOOLEAN;
			
			if (name.equalsIgnoreCase(DATE))
				return DATE;
			
			if (name.equalsIgnoreCase(FLOAT))
				return FLOAT;
			
			return null;
		}
		
}
