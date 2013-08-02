package com.github.dynamicschema.android.reification;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MyFileWriter{
	
	private FileWriter fstream;
	private BufferedWriter out; 
	
	public MyFileWriter(String fileName) throws IOException{
		
			this.fstream = new FileWriter(fileName);
			this.out = new BufferedWriter(fstream);
	}
	
	public void writeIntoFile(String content) throws IOException{
		this.out.write(content);
		this.out.flush();
	}
	
	public void closeWriter() throws IOException{
		this.out.close();
	}

}