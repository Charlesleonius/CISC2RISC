package parser;
import java.util.*;
import java.util.regex.*;

public class Converter {
	
	private ArrayList<String> instructions = new ArrayList<String>();
	
	public static void main(String[] args) {
		ArrayList<String> params = new ArrayList<String>();
		params.add("Z");
		params.add("label");
		ArrayList<String> al = toIJVM(5, (params));
		for (int i = 0; i < al.size(); i++){
			System.out.println(al.get(i));
		}
	}
	
	//Static Variables
	private static Map<String, Integer> opcodes = new HashMap<String, Integer>();
	static {
		opcodes.put("add", 0x0); //
		opcodes.put("sub", 1); //
		opcodes.put("and", 2); 
		opcodes.put("or", 3); //
		opcodes.put("inc", 4); //
		opcodes.put("bra", 5); //
	
    }
	private static String variable = "[A-Za-z0-9]";
	private static String literal = "#+[0-9]";
	
	public static Integer getOpcode(String instructionName) {
		return (Integer) opcodes.get(instructionName.toLowerCase());
	}
	
	public static ArrayList<String> INC(ArrayList<String> params){
		ArrayList<String> instructions = new ArrayList<String>();
		instructions.add("IINC 1");
		instructions.add("ISTORE " + params.get(0));
		return instructions;
	}
	
	public static ArrayList<String> BRA(ArrayList<String> params){
		ArrayList<String> instructions = new ArrayList<String>();
		
		if(params.get(0) == "Z") {
			instructions.add("IFEQ " + params.get(1));
			return instructions;
		}
		else if(params.get(0) == "LT")
			instructions.add("IFLT " + params.get(1));
			return instructions;
		
	}
	
		//OPERATIONS INCLLUDE IADD,ISUB,IAND,IOR
	public static ArrayList<String> operate(ArrayList<String> params, String instruction) {
		
		ArrayList<String> instructions = new ArrayList<String>();
		ArrayList<Integer> al = new ArrayList<Integer>();
		int vars = 0;
		int literals = 0;
		for (int i = 0; i < params.size(); i++) {
			String param = params.get(i);
			if (param.matches(variable)) {
				al.add(0);
				vars++;
			} else if (param.matches(literal)) {
				al.add(1);
				literals++;
			} 
		}
		if (vars == 1 && literals == 1) {
			if(al.get(0) == 0) instructions.add("ILOAD " + params.get(0));
			else instructions.add("BIPUSH " + params.get(0));
			if(al.get(1) == 0) instructions.add("ILOAD " + params.get(1));
			else instructions.add("BIPUSH " + params.get(1));
			instructions.add(instruction);
			instructions.add("ISTORE " + params.get(0));
		} else if (vars == 1 && literals == 2) {
			instructions.add("BIPUSH " + params.get(1));
			instructions.add("BIPUSH " + params.get(2));
			instructions.add(instruction);
			instructions.add("ISTORE" + params.get(0));
		} else if (vars == 2 && literals == 1) {
			if(al.get(0) == 0) instructions.add("ILOAD " + params.get(1));
			else instructions.add("BIPUSH " + params.get(1));
			if(al.get(1) == 0) instructions.add("ILOAD " + params.get(2));
			else instructions.add("BIPUSH " + params.get(2));
			instructions.add(instruction);
			instructions.add("ISTORE" + params.get(0));
		} else if (vars == 2) {
			instructions.add("ILOAD " + params.get(0));
			instructions.add("ILOAD" + params.get(1));
			instructions.add(instruction);
			instructions.add("ISTORE" + params.get(0));
		} else if (vars == 3) {
			instructions.add("ILOAD " + params.get(1));
			instructions.add("ILOAD" + params.get(2));
			instructions.add(instruction);
			instructions.add("ISTORE" + params.get(0));
		}
		return instructions;
	}
	
	public void instructionPass(ArrayList<String> lines) {
        int lineNumber = 0;
        System.out.println("Searching for CISC instructions");
        for (String line : lines) {
                Boolean instructionStarted = false;
                String instruction = "";
                final int lineLength = line.length();
                name: for (int j = 0; j < lineLength; j++) {
                    if (instructionStarted == false) {
                        if (!Character.isWhitespace(line.charAt(j))) {
                            instructionStarted = true;
                            instruction += line.charAt(j);
                        }
                    } else {
                        if (!Character.isWhitespace(line.charAt(j))) {
                            instruction += line.charAt(j);
                        } else {
                            break name;
                        }
                    }
                }
                if (opcodes.containsKey(instruction.toLowerCase())) {
                	
                    String paramsLine = line.replace(instruction, "").replaceAll(" ", "");
                    String[] rawParams  = paramsLine.split(",");
                    ArrayList<String> params = new ArrayList<String>(Arrays.asList(rawParams));
                    
                    ArrayList<String> replacementInstructions = toIJVM(opcodes.get(instruction), params);
                    for (String replacement : replacementInstructions) {
                        System.out.println(replacement);
                    }
                }
            lineNumber += 1;
        }
    }
    
    public ArrayList<String> convertToRisc(ArrayList<String> lines) {
        instructionPass(lines);
        return lines;
    }
	
	public static ArrayList<String> toIJVM(int opcode, ArrayList<String> params) {
		ArrayList<String> risc = new ArrayList<String>();
		switch(opcode) {
			case 0: 
				risc = operate(params,"IADD");
			break;
			case 1:
				risc = operate(params,"ISUB");
			break;
			case 2:
				risc = operate(params,"IAND");
			break;
			case 3:
				risc = operate(params,"IOR");
			break;
			case 4:
				risc = INC(params);
			break;
			case 5:
				risc = BRA(params);
			break;
			default:
			break;
		}
		return risc;
	}
}
