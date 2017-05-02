package parser;

import java.util.*;
import java.io.*;

public class CISCFile {
	
	private ArrayList<String> labels;
	private ArrayList<String> variables;
	private Map<String, Integer> constants;
	
//	Boolean constantsBegan = false;
//	Boolean constantsEnded = false;
//	Boolean mainProgramBegan = false;
//	Boolean mainProgramEnded = false;
//	Boolean variablesBegan = false;
//	Boolean variablesEnded = false;
	
	public CISCFile(File assemblyFile) {
		ArrayList<String> legalLines = legalLines(assemblyFile);
		constantsPass(legalLines);
		this.constants.forEach((k,v) -> System.out.println(k+", "+v));
	}
	
	public ArrayList<String> legalLines(File assemblyFile) {
		ArrayList<String> lines = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(assemblyFile));
			String line;
		    while ((line = reader.readLine()) != null) {
		    	System.out.println(line);
		    	char c;
		    	for(int i = 0; i < line.length(); i++) {
		    		c = line.charAt(i);
					if (!Character.isWhitespace(c)) { //Not space
//						if (c == ';') { //Skip line if comment
//							continue;
//						}
						lines.add(line);
//						if (!Character.isAlphabetic(c)) { //Invalid Case
//							throw new Exception("Invalid Line");
//						} else { //Valid Case
//							lines.add(line);
//						}
					}
				}
		    }
		    reader.close();
		} catch (Exception e) {
			if (e.getMessage() == "Invalid Line") {
				System.out.println(e.getMessage());
				System.exit(1);
			}
			e.printStackTrace();
		}
		return lines;
	}
	
	private void constantsPass(ArrayList<String> lines) {
		final int size = lines.size();
		System.out.println("Starting Constants Pass");
		Boolean constantBegan = false;
		Boolean constantEnd = false;
		for (int i = 0; i < size; i++) {
			if (lines.get(i).equals(".constant")) {
				System.out.println("Starting Constants");
				constantBegan = true;
				String line;
				while (!lines.get(i).equals(".end-constant")) {
					line = lines.get(i);
					System.out.println(line);
//					String[] parts = line.split(":");
					//System.out.println(parts[0]);
					//System.out.println(parts[1]);
					//this.constants.put(parts[0], Integer.parseInt(parts[1])); 
				}
				break;
			}
		}
	}
	
	public ArrayList<String> labelPass(ArrayList<String> lines) {
		for (String line : lines) {
			
		}
		return lines;
	}
	
	public File toRisc() {
		return new File("");
	}
	
}
