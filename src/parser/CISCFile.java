package parser;

import java.util.*;
import java.io.*;

public class CISCFile {
	
	private final static ArrayList<String> predefinedInstruction = new ArrayList<String>();
	static {
			predefinedInstruction.add("ADD");
			predefinedInstruction.add("BIPUSH");
			predefinedInstruction.add("DUP");
			predefinedInstruction.add("GOTO");
			predefinedInstruction.add("IADD");
			predefinedInstruction.add("IAND");
			predefinedInstruction.add("IFEQ");
			predefinedInstruction.add("IFLT");
			predefinedInstruction.add("IF_ICMPEQ");
			predefinedInstruction.add("IINC");
			predefinedInstruction.add("ILOAD");
			predefinedInstruction.add("INVOKEVIRTUAL");
			predefinedInstruction.add("IOR");
			predefinedInstruction.add("IRETURN");
			predefinedInstruction.add("ISTORE");
			predefinedInstruction.add("ISUB");
			predefinedInstruction.add("LDC_W");
			predefinedInstruction.add("NOP");
			predefinedInstruction.add("POP");
			predefinedInstruction.add("SWAP");
			predefinedInstruction.add("WIDE");
			predefinedInstruction.add("HALT");
			predefinedInstruction.add("ERR");
			predefinedInstruction.add("OUT");
			predefinedInstruction.add("IN");
	}
	
	private ArrayList<String> labels = new ArrayList<String>();
	private ArrayList<String> variables = new ArrayList<String>();
	private Map<String, Integer> constants = new HashMap<String, Integer>();
	private ArrayList<Integer> lineNumbers = new ArrayList<Integer>();
	private ArrayList<String> instructions = new ArrayList<String>();
	private static String variable = "[A-Za-z0-9]";
	private static String constant = "[A-Za-z0-9]";
	private static String label = "[A-Za-z0-9]+:";
	
	public CISCFile(File assemblyFile) {
		ArrayList<String> legalLines = legalLines(assemblyFile);
		constantsPass(legalLines);
		variablePass(legalLines);
		labelPass(legalLines);
		instructionPass(legalLines);
		testSuite();
	}
	
	private void testSuite() {
		printConstants();
		printVariables();
		printLabels();
		printInstructions();
	}
	
	public void printConstants() {
		System.out.println("Constants:");
		this.constants.forEach((k,v) -> System.out.println(k+": "+v));
	}
	
	public void printVariables() {
		System.out.println("Variables:");
		this.variables.forEach((i) -> System.out.println(i));
	}
	
	public void printLabels() {
		System.out.println("Labels:");
		this.labels.forEach((i) -> System.out.println(i.replace(":", "")));
	}
	
	public void printInstructions() {
		System.out.println("Instructions:");
		this.instructions.forEach((i) -> System.out.println(i));
	}
	
	public ArrayList<String> legalLines(File assemblyFile) {
		ArrayList<String> lines = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(assemblyFile));
			String line;
			int i = 0;
		    while ((line = reader.readLine()) != null) {
		    	String[] lineSplit = line.split(";");
		    	if (lineSplit.length > 0) {
		    		lines.add(lineSplit[0].trim());
		    	} else {
		    		lines.add(line);
		    	}
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
		return;
	}
	
	private void variablePass(ArrayList<String> lines) {
		final int size = lines.size();
		int i = 0;
		while (!lines.get(i).equals(".var")) {
			i += 1;
		}
		String line;
		i += 1;
		while (!lines.get(i).equals(".end-var")) {
			line = lines.get(i);
			if (!Character.isAlphabetic(line.charAt(0))) {
				throw new IllegalArgumentException("Invalid declaration at line " + lineNumbers.get(i).toString() + ". '" + line + "' is an invalid variable name. Variable names must start with an alphabetical character");
			}
			if (constants.containsKey(line)) {
				throw new IllegalArgumentException("Invalid declaration at line " + lineNumbers.get(i).toString() + ". '" + line + "' is already delcared as a constant.");
			}
			if(!line.matches(variable)) {
				throw new IllegalArgumentException("Invalid declaration at line " + lineNumbers.get(i).toString() + ". '" + line + "' is an invalid variable name. Variable names must be alphanumerical");
			}
			this.variables.add(line);
			i+= 1;
		}
		return;
	}
	
	public void labelPass(ArrayList<String> lines) {
		final int size = lines.size();
		int i = 0;
		while (!lines.get(i).equals(".end-var")) {
			i += 1;
		}
		String line;
		i += 1;
		while (!lines.get(i).equals(".end-main")) {
			line = lines.get(i);
			if (line.contains(":")) {
				if (!line.matches(label)) {
					throw new IllegalArgumentException("Invalid declaration at line " + lineNumbers.get(i).toString() + ". '" + line + "' is an invalid label. Labels should follow the format: [A-Za-z0-9_-]: ");
				}
				if (!Character.isAlphabetic(line.charAt(0))) {
					throw new IllegalArgumentException("Invalid declaration at line " + lineNumbers.get(i).toString() + ". '" + line + "' is an invalid label. Labels must start with an alphabetical character");
				}
				if (labels.contains(line)) {
					throw new IllegalArgumentException("Invalid declaration at line " + lineNumbers.get(i).toString() + ". '" + line + "' has alread been defined.");
				}
				this.labels.add(line);
			}
			i+= 1;
		}
		return;
	}
	
	public void instructionPass(ArrayList<String> lines) {
		final int size = lines.size();
		int i = 0;
		while (!lines.get(i).equals(".end-var")) {
			i += 1;
		}
		String line;
		i += 1;
		while (!lines.get(i).equals(".end-main")) {
			line = lines.get(i);
			if(!line.contains(":")) {
				Boolean instructionStarted = false;
				String instruction = "";
				for (int j = 0; j < line.length(); j++) {
					if (instructionStarted == false) {
						if (!Character.isWhitespace(line.charAt(j))) {
							instructionStarted = true;
							instruction += line.charAt(j);
						}
					} else {
						if (!Character.isWhitespace(line.charAt(j))) {
							instruction += line.charAt(j);
						}
					}
				}
				if (predefinedInstruction.contains(instruction)) {
					this.instructions.add(line);
				}
				i+= 1;
			}
		}
		return;
	}
	
	public File toRisc() {
		return new File("");
	}
	
}
