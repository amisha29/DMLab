import java.io.*;
import java.util.*;

class StrongRules {

    Double supportThreshold, confidenceThreshold;
    String[] items;
    String[] freqItemsets;

    public StrongRules(String line, Double supportThreshold, Double confidenceThreshold) {
        this.supportThreshold = supportThreshold;
        this.confidenceThreshold = confidenceThreshold;
        items = line.split(",");
        freqItemsets = new String[items.length-1];
    }

    void generateFrequentSubsets() {
        for(int i = 0; i < items.length-1; i++) {
            genkitems(i);
            String[] temp = freqItemsets[i].split(",");
            System.out.print(Arrays.toString(temp));
            System.out.println();
        }
    }

    void genkitems(int k) {
        if(k-1<0) {
            freqItemsets[0] = String.join(",", items);
        }
        else {
            HashSet<String> kitems = new HashSet<>();
            String[] fk_1 = freqItemsets[k-1].split(",");

            for(int i = 0; i < fk_1.length; i++) {
                for(int j = i+1; j < fk_1.length; j++) {
                    if(kitems.contains(fk_1[i]+fk_1[j]) || kitems.contains(fk_1[j]+fk_1[1]))
                        continue;
                    else {
                        String elements = fk_1[i]+fk_1[j];
                        HashSet<Character> charArray = new HashSet<>();
                        for(Character c : elements.toCharArray())  
                            charArray.add(c);
                        elements = "";
                        for(Character c : charArray)
                            elements += c;
                        if(elements.length() == k+1)
                            kitems.add(elements) ;
                    }
                }
            }
            // convert the string of the form "[a, b, c]" to "a,b,c"
            freqItemsets[k] = kitems.toString().replaceAll(" ", "");
            freqItemsets[k] = freqItemsets[k].substring(1, freqItemsets[k].length()-1);
        }
    }

    void getRules() {
        ArrayList<String> strongRules = new ArrayList<>();
        Double sup,conf;
        HashSet<String> rules = new HashSet<>();
        Random in = new Random();
        for(int i = 0; i < freqItemsets.length; i++) {
            String[] f_k = freqItemsets[i].split(",");

            for(String item : f_k) {
                HashSet<Character> s = new HashSet<>();
                for(Character c : String.join("",items).toCharArray())
                    s.add(c);

                for(Character c : item.toCharArray())
                    s.remove(c);

                String antecedent = Arrays.toString(item.toCharArray());
                String consequent = s.toString();
                String rule = antecedent + "->" + consequent;
                // String opp_rule = consequent + "-> " + antecedent;
                System.out.println(rule);
                rules.add(rule);
                // sup = in.nextDouble();
                conf = in.nextDouble();
                if (conf > confidenceThreshold){
                    strongRules.add(rule);
                }
            }
        }
        System.out.println("The strong rules are:");
        for (String rule : strongRules){
            System.out.println(rule);
        }
    }

    public static void main(String args[])
    {
        String line;
        Double supportThreshold, confidenceThreshold;

        System.out.println("Enter the comma separted 4-frequent itemset elements:");
        Scanner in = new Scanner(System.in);
        line = in.nextLine();
        System.out.println("Enter the Support Threshold & Confidence Threshold");
        supportThreshold = in.nextDouble();
        confidenceThreshold = in.nextDouble();
        StrongRules generator = new StrongRules(line, supportThreshold,confidenceThreshold);
        System.out.println("Frequent Subsets:");
        generator.generateFrequentSubsets();
        System.out.println("Candidate Rules:");
        generator.getRules();
    }
}