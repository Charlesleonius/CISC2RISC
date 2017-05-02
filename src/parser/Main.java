package parser;
import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws Exception {
//		if (args.length < 2) {
//			System.out.println("Invalid arguments. Compiler requires two args: inputFilePath outFilePath");
//			System.exit(1);
//		}
		String inputFilePath = "/Users/charlesleon/desktop/test.txt";
		String outputFilePath = "";
		try {
			BufferedReader bufferReader = new BufferedReader(new FileReader(inputFilePath));
			while (bufferReader.readLine() != null) {
				System.out.println(bufferReader.readLine());
			}
		} catch (FileNotFoundException e) {
			System.out.println("Input file path not found");
			System.exit(1);
		} catch (Exception e) {
			throw e;
		}
		
	}
}
