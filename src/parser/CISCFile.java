package parser;

import java.util.*;
import java.io.*;

public class CISCFile {
	
	private ArrayList<String> labels;
	private ArrayList<String> variables;
	private static Map<String, Integer> constants = new HashMap<String, Integer>();
	private ArrayList<Integer> lineNumbers = new ArrayList<Integer>();
	
//	Boolean constantsBegan = false;
//	Boolean constantsEnded = false;
//	Boolean mainProgramBegan = false;
//	Boolean mainProgramEnded = false;
//	Boolean variablesBegan = false;
//	Boolean variablesEnded = false;
	
	public CISCFile(File assemblyFile) {
		ArrayList<String> legalLines = legalLines(assemblyFile);
		constantsPass(legalLines);
	}
	
	public void printConstants() {
		this.constants.forEach((k,v) -> System.out.println(k+", "+v));
	}
	
	public ArrayList<String> legalLines(File assemblyFile) {
		ArrayList<String> lines = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(assemblyFile));
			String line;
			int i = 0;
		    while ((line = reader.readLine()) != null) {
		    	lines.add(line);
		    	lineNumbers.add(i + 1);
		    	i += 1;
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
		int i = 0;
		while (!lines.get(i).equals(".constant")) {
			i += 1;
		}
		String line;
		i += 1;
		while (!lines.get(i).equals(".end-constant")) {
			line = lines.get(i);
			if (line.contains(":")) {
				String[] parts = line.split(":");
				if (parts.length < 2) {
					throw new IllegalArgumentException("Invalid declaration at line " + lineNumbers.get(i).toString());
				}
				this.constants.put(parts[0], Integer.parseInt(parts[1].trim()));
			} else {
				throw new IllegalArgumentException("Invalid declaration at line " + lineNumbers.get(i).toString());
			}
			i+= 1;
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
