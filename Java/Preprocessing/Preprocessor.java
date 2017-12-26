import java.io.*;
import java.util.*;

public class Preprocessor {
    Scanner sc;
    ArrayList<String[]> data;
    public Preprocessor() {
        sc = new Scanner(System.in) ;
    }

    void aggregation(int startcol, int endcol) {
        int sum;
        for(String[] row  : data) {
            sum = 0;
            for (int i = startcol; i <= endcol; i++) {
                sum += Integer.parseInt(row[i]);
            }
            double avgSalary = (sum * 1.0)/(endcol-startcol+1);
            for(int j = 0; j < startcol; j++)
                System.out.print(row[j] + "\t");
            System.out.print(avgSalary);
            System.out.println();
        }
    }

    void discretization(int colnum) {
        int bins, binsize, diff, rem, age;
        System.out.println("\nEnter number of bins : ");
        bins = sc.nextInt();
        int maxAge = Integer.MIN_VALUE;
        int minAge = Integer.MAX_VALUE;
        for(String[] line : data) {
            age = Integer.parseInt(line[colnum]);
            if(age < minAge)
                minAge = age;
            if(age > maxAge)
                maxAge = age;
        }
        diff = maxAge - minAge;
        rem = diff%bins;
        diff += (bins - rem);
        binsize = diff/bins;

        int temp = minAge;
        System.out.println("Categorical Attributes are");
        for(int i = 1; i <= bins; i++) {
            System.out.println(temp + " - " + (temp+binsize) + " -> " + i);
            temp += binsize;
        }

        temp = minAge;
        int bincount = 1;
        for(String[] row : data) {
            age = Integer.parseInt(row[colnum]);
            while(age > (temp+binsize)) {
                temp += binsize;
                bincount++;
            }
            row[colnum] = String.valueOf(bincount);
            bincount = 1;
            temp = minAge;
            for (int j = 0; j < row.length; j++) {
                System.out.print(row[j] + "\t");
            }
            System.out.println();
        }
    }

    void stratifiedSampling() {
        HashMap<String, ArrayList<String[]>> genderMap = new HashMap<>();
        System.out.println("\n\nStratified sampling : \nEnter sample size  :");
        int samplesize = sc.nextInt();

        for(String[] row: data) {
            ArrayList<String[]> temp = genderMap.get(row[2]);
            if(temp == null)   
                temp = new ArrayList<>();
            temp.add(row);
            genderMap.put(row[2], temp);
        }

        ArrayList<String[]> stratifiedSample = new ArrayList<>();

        for(String gender : genderMap.keySet()) {
            ArrayList<String[]> items = genderMap.get(gender);
            int class_size = items.size();
            System.out.println("Gender: " + gender + "Class size: " + class_size);
            int strat_sample_size = (int) Math.rint(samplesize/(float)data.size()*class_size);
            System.out.println("Gender: " + gender + "Class sample size: " + strat_sample_size);        
            Collections.shuffle(items);
            for(int i =0; i < strat_sample_size; i++)
                stratifiedSample.add(items.get(i));
        }
        for(String[] row : stratifiedSample) {
            for(String item  :row)
                System.out.print(item+"\t");
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
        Preprocessor preprocessor = new Preprocessor();
        CsvReader csvReader = new CsvReader("input.csv", ",");
        preprocessor.data = csvReader.getData();
        preprocessor.aggregation(4,7);
        preprocessor.discretization(3);
        preprocessor.stratifiedSampling();
        
    }
}