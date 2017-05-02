package parser;
import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws Exception {
//		if (args.length < 2) {
//			System.out.println("Invalid arguments. Compiler requires two args: inputFilePath outFilePath");
//			System.exit(1);
//		}
		String inputFilePath = "C:/Users/ryanm_000/Desktop/CS/README.txt";
		String outputFilePath = "";
		try {
			File textFile = new File(inputFilePath);
			CISCFile cisc = new CISCFile(textFile);
			File riscFile = cisc.toRisc();
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("Input file path not found");
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
}
