import java.io.*;
import java.util.*;

class BestSplit {
    ArrayList<String[]> data;
    HashMap<String, Integer> parent;
    HashMap<String, HashMap<String, Integer>> a1,a2;
    final Integer A1=0, A2=1, CLASS=2;

    public BestSplit(String filename) {
        data = new ArrayList<>();
        parent = new HashMap<>();
        a1 = new HashMap<>();
        a2 = new HashMap<>();
        parseCSV(filename);
    }

    void parseCSV(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
            String line;
            while( (line = br.readLine()) != null ){
                String row[] = line.split(",");
                data.add(row);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    void chooseAttribute() {
        for(String[] row : data) {
            //parent
            Integer count = parent.get(row[CLASS]);
            if(count == null) {
                count = new Integer(0);
            }

            count++;
            parent.put(row[CLASS], count);

            //attribute 1
            HashMap<String, Integer> a1Map = a1.get(row[A1]);
            if(a1Map == null)
                a1Map = new HashMap<>();
            Integer a1count = a1Map.get(row[CLASS]);
            if(a1count == null)
                a1count = new Integer(0);
            a1count++;
            a1Map.put(row[CLASS], a1count);
            a1.put(row[A1], a1Map);

            //attribute 2
            HashMap<String, Integer> a2Map = a2.get(row[A2]);
            if(a2Map == null)
                a2Map = new HashMap<>();
            Integer a2count = a2Map.get(row[CLASS]);
            if(a2count == null)
                a2count = new Integer(0);
            a2count++;
            a2Map.put(row[CLASS], a2count);
            a2.put(row[A2], a2Map);
        }   

        Double giniParent = gini(parent);
        Double gain_gini_A1 = gain(giniParent, a1, true);
        Double gain_gini_A2 = gain(giniParent, a2, true);
        
        Double entropyParent = entropy(parent);
        Double gain_entropy_A1 = gain(entropyParent, a1, false);
        Double gain_entropy_A2 = gain(entropyParent, a2, false);

        System.out.println("Parent Count:");
        System.out.println(parent);

        System.out.println("Attribute 1 Table:");
        System.out.println(a1);
        System.out.println("Attribute 2 Table:");
        System.out.println(a2);

        System.out.println("I(Parent) - Gini:");        
        System.out.println(giniParent);        
        System.out.println("Gain - Gini");
        System.out.println("Attribute 1: " + gain_gini_A1 );        
        System.out.println("Attribute 2: " + gain_gini_A2 );

        System.out.println("I(Parent) - Entropy:");
        System.out.println(entropyParent);
        System.out.println("Gain - Entropy");
        System.out.println("Attribute 1: " + gain_entropy_A1 );        
        System.out.println("Attribute 2: " + gain_entropy_A2 );

        System.out.println("Using Gini Index, Best Attribute to Split By: " + ( (gain_gini_A1 > gain_gini_A2) ? "A1" : "A2") );        
        System.out.println("Using Entropy, Best Attribute to Split By: " + ( (gain_entropy_A1 > gain_entropy_A2) ? "A1" : "A2") );

    }

    double gini(HashMap<String,Integer> map) {
        double gini = 1, total = 0;
        for(String s : map.keySet()) {
            total += map.get(s);
        }

        for(String s : map.keySet()) {
            gini -= Math.pow(map.get(s)/(double)total,2);
        }
        return gini;
    }

    double entropy(HashMap<String,Integer> map) {
        double entropy = 0, total = 0;
        for(String s : map.keySet()) {
            total += map.get(s);
        }

        for(String s : map.keySet()) {
            double temp = (double) map.get(s)/(double)total;
            entropy -= temp*(Math.log10(temp)/Math.log10(2.0));
        }
        return entropy;
    }

    double gain(double parentI, HashMap<String, HashMap<String, Integer>> table, Boolean isGini) {
        double gain = parentI;
        double total = (double) data.size();
        double gainSuffix = 0;
        HashMap<String, Integer> map;

        for(String node : table.keySet()) {
            map = table.get(node);
            double innerI = isGini ? gini(map) : entropy(map);
            double node_total = 0;
            for(Integer i : map.values()) {
                node_total += i;
            }
            gainSuffix += (node_total*innerI/total);
        }

        gain -= gainSuffix;
        return gain;
    }

    public static void main(String args[]){
        String filename = "data.csv";
        BestSplit bs = new BestSplit(filename);
        bs.chooseAttribute();
    }

}