package parser;
import java.util.*;
import java.util.regex.*;

public class Converter {
	
	public static void main(String[] args) {
		ArrayList<String> params = new ArrayList<String>();
		params.add("j");
		params.add("#1");
		params.add("#2");
		toIJVM(getOpcode("ADD"), params);
	}
	
	//Static Variables
	private static Map<String, Integer> opcodes = new HashMap<String, Integer>();
	static {
		opcodes.put("add", 0x0);
		opcodes.put("sub", 1);
    }
	private static String variable = "[A-Za-z0-9]";
	private static String literal = "#+[0-9]";
	
	public static Integer getOpcode(String instructionName) {
		return (Integer) opcodes.get(instructionName.toLowerCase());
	}
	
	public static ArrayList<String> add(ArrayList<String> params) {
		ArrayList<String> instructions = new ArrayList<String>();
		int vars = 0;
		int literals = 0;
		for (String param : params) {
			if (param.matches(variable)) {
				vars++;
			} else if (param.matches(literal)) {
				literals++;
			} else {
				System.out.println("Invalid parameter");
			}
		}
		if (vars == 1 && literals == 1) {
			instructions.add("ILOAD " + params.get(0));
			instructions.add("ILOAD" + params.get(1));
			instructions.add("IADD");
			instructions.add("ISTORE" + params.get(0));
		} else if (vars == 1 && literals == 2) {
			
		} else if (vars == 2 && literals == 1) {
			
		} else if (vars == 2) {
			
		} else if (vars == 3) {
			
		}
		return instructions;
	}
	
	public static String toIJVM(int opcode, ArrayList<String> params) {
		switch(opcode) {
			case 0: //ADD
				add(params);
			break;
			case 1:
			break;
			default:
			break;
		}
		return "";
	}
}
