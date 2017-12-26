import java.io.*;
import java.util.*;

public class CsvReader {
    String filename, delimiter;
    ArrayList<String[]> data;
    BufferedReader br;

    public CsvReader(String filename, String delimiter) throws IOException{
        this.filename = filename;
        this.delimiter = delimiter;
        data = new ArrayList<>();
        br = new BufferedReader(new FileReader(new File(filename)));
        String line;

        while ((line = br.readLine()) != null) {
            String[] items = line.split(delimiter);
            data.add(items);
        }
    }

    public ArrayList<String[]> getData() {
        return data;
    }

    public void printData() {
        for(String[] line:data){
			for(String word:line)	{
				System.out.print(word + "\t");
			}
			System.out.println();
		}
    }

}