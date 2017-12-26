import java.io.*;
import java.util.*;

class Classifier {

    ArrayList<Record> data;
    Label label[];

    public Classifier(String filename) {
        data = new ArrayList<>();
        parseCSV(filename);
	}

    public void parseCSV(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
            String line;
            while( (line = reader.readLine()) != null ){
                String row[] = line.split(",");
                Double att1 = Double.parseDouble(row[0]);
                String att2 = row[1];
                Integer label = Integer.parseInt(row[2]);
                data.add(new Record(att1, att2, label));
            }
            reader.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    void splitDataByClass() {
        label = new Label[2];
        label[0] = new Label();
        label[1] = new Label();
        for(Record record : data) {
            label[record.label].addRecord(record);
        }
    }

    void priorProbability() {
        for(int i = 0; i < label.length; i++) {
            label[i].prior = (double) label[i].data.size() / (double) data.size();
            System.out.println("Class: " + i );
            System.out.println("Prior Probability: " + label[i].prior );
            System.out.println();
        }
    }

    void continuousValues() {

        System.out.println("Attribute1 - Numerical Value: ");
        for(int i = 0; i < label.length; i++) {
            label[i].calculateMean();
            label[i].calculateVariance();
            System.out.println("Class: " + i );
            System.out.println("Mean: " + label[i].mean);
            System.out.println("Variance: "+ label[i].variance);
            System.out.println();
        }
    }

    void categoricalValues() {

        System.out.println("Atrribute2 - Categorical Value: ");
        for(int i = 0; i < label.length; i++) {
            label[i].calculateProbability();
            System.out.println("Class: " + i );
            for(String attr : label[i].probability.keySet()) {
                System.out.println("Attribute Value: " + attr + " = " + label[i].getProbability(attr));
            }
        }
    }

    void classifyNewRecord() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter attribute1 and attribute2: ");
		double att1 = sc.nextDouble();
		String att2 = sc.next();

		double posteriorProb[] = new double[2];

        for(int i = 0; i < label.length; i++) {
            double exp = -1 *(Math.pow((att1 - label[i].mean), 2)/(2*label[i].variance));
            double prob = Math.exp(exp)/Math.sqrt(2*3.14*label[i].variance);

            double conditionalProb = prob*label[i].getProbability(att2);

            posteriorProb[i] = label[i].prior * conditionalProb;
        }

        System.out.println("New record belongs to class : " + ((posteriorProb[0] > posteriorProb[1]) ? 0 :1)) ;
    }

    public static void main (String[] args)	{
        String filename = "data.csv";
        Classifier cl = new Classifier(filename);
        cl.splitDataByClass();

        System.out.println("Calculating prior and class conditional probability");
        cl.priorProbability();
        cl.continuousValues();
        cl.categoricalValues();

        System.out.println("Classify new record");
        cl.classifyNewRecord();
	}
}