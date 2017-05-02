package parser;

import java.util.*;
import java.io.*;

public class CISCFile {
    
    ArrayList<String> lines = new ArrayList<String>();
    
    public CISCFile(File assemblyFile) {
        ArrayList<String> lines = getLines(assemblyFile);
    }

    public ArrayList<String> getLines(File assemblyFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(assemblyFile));
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                String[] lineSplit = line.split("//");
                if (lineSplit.length > 0) {
                    lines.add(lineSplit[0].trim());
                } else {
                    lines.add(line);
                }
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
    
    public File toRisc() {
        Converter converter = new Converter();
        converter.convertToRisc(lines);
        return new File("");
    }
    
}