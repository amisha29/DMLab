import java.io.*;
import java.util.*;

public class CsvReader {
    String filename, delimiter;
    ArrayList<double[]> data;
    BufferedReader br;

    public CsvReader(String filename, String delimiter) throws IOException{
        this.filename = filename;
        this.delimiter = delimiter;
        data = new ArrayList<>();
        br = new BufferedReader(new FileReader(new File(filename)));
        String line;

        while ((line = br.readLine()) != null) {
            String[] items = line.split(delimiter);
            int len = items.length;
            double[] row = new double[len];
            int i = 0;
            for(String item : items) {
                row[i] = Double.parseDouble(item);
                i++;
            }
            data.add(row);
        }
    }

    public ArrayList<double[]> getData() {
        return data;
    }

    public void printData() {
        for(double[] line:data){
			for(double word:line)	{
				System.out.print(word + "\t");
			}
			System.out.println();
		}
    }

}