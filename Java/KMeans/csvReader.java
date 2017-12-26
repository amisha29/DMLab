import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class csvReader {
	String fileName;
	String dilimiter;
	ArrayList<double[]> data;
	BufferedReader br;
	csvReader(String fileName,String dilimiter) throws IOException{
		this.fileName = fileName;
		this.dilimiter = dilimiter;
		data = new ArrayList<>();
		br = new BufferedReader(new FileReader(new File(this.fileName)));
		String line;
		while ((line = br.readLine()) !=null) {
			String[] singleLine = line.split(this.dilimiter);
			int length = singleLine.length;
            double[] newLine = new double[length];
            int i =0;
            for(String data:singleLine)
            {
                newLine[i] = Double.parseDouble(data);
                i++;
            } 
            data.add(newLine);
		}
	}
    public ArrayList<double[]> returnData()
    {
        return data;
    }
}