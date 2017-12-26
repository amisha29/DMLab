import java.util.*;
import java.io.*;

class MissingValues {
    ArrayList<String[]> data;

    void replaceAttributes(int colnum) {
        int sum = 0;
        double mean;
        for(String[] row : data) {
            String attribute = row[colnum];
            if (!attribute.equals(" "))
                sum += Integer.parseInt(attribute);
        }

        mean = (sum*1.0)/data.size();
        for(String[] row : data) {
            if(row[colnum].equals(" "))
                row[colnum] = String.valueOf(mean);
            for(String item : row)
                System.out.print(item + "\t");
            System.out.println();
        }
    }

    void replaceCategorical(int colnum) {
        HashMap<String, Integer> categorical = new HashMap<>();
        for(String[] row : data) {
            String attribute = row[colnum];
            if(!attribute.equals(" ")) {
                Integer count = categorical.get(attribute);
                if(count == null)
                    count = 0;
                categorical.put(attribute, count+1);
            }
        }

        int maxValue = Integer.MIN_VALUE;
        String maxKey ="";
        for(String attribute : categorical.keySet()) {
            if(maxValue < categorical.get(attribute)) {
                maxValue = categorical.get(attribute);
                maxKey = attribute;
            }
                
        }

        for(String[] row :data)
        {
            for(int j=0;j<row.length;j++)
            {
                if(row[colnum].equals(" "))
                {
                    row[colnum]=maxKey;
                }
                System.out.print(row[j]+"\t");
             }
        System.out.println();
        }
    }

    public static void main(String[] args) throws IOException { 
        CsvReader csvReader = new CsvReader("input.csv", ",");
        MissingValues missingVal = new MissingValues();
        missingVal.data = csvReader.getData();
        System.out.println("\nNumeric Attributes\n");
        missingVal.replaceAttributes(4);
        System.out.println("\nCategorical\n");
        missingVal.replaceCategorical(5);
    }
}