import java.io.*;
import java.util.*;

class KNN {
    ArrayList<double[]> data;
    ArrayList<double[]> neighbours;
    ArrayList<Distance> distances;
    int k ;

    public KNN() {
        distances = new ArrayList<>();
        neighbours = new ArrayList<>();
        k = 3;
    }

    double eucledianDist(double[] first, double[] second) {
        int len = first.length;
        double dist = 0;
        for(int i = 0; i < len-1; i++) {
            dist += Math.pow((first[i] - second[i]), 2 );
        }
        return Math.sqrt(dist);
    }

    void calculateDistance(double[] test) {
        for (double[] row : data) {
            double dist = eucledianDist(row, test);
            distances.add(new Distance(row, dist));
        }
    }

    void findNeighbours() {
        Collections.sort(distances);
        for(int i = 0; i < k; i++) {
            neighbours.add(distances.get(i).features);
        }
    }

    double predict() {
        HashMap<Double, Integer> nearest = new HashMap<>();
        for(double[] neighbour : neighbours) {
            double label = neighbour[neighbour.length-1];
            int count = 1;
            if(nearest.containsKey(label))
                count = nearest.get(label)+1;
            nearest.put(label, count);
        }

        double maxkey = 0;
        int maxval = Integer.MIN_VALUE;
        for(double key : nearest.keySet()) {
            int val = nearest.get(key);
            if(val > maxval) {
                maxval = val;
                maxkey  =key;
            }
        }
        return maxkey;
    }

    public static void main(String[] args) throws IOException {
        CsvReader csvReader = new CsvReader("input.csv", ",");
        KNN knn = new KNN();
        knn.data = csvReader.getData();
        double[] testData = {6.0,3.5,4.1};
        knn.calculateDistance(testData);
        knn.findNeighbours();
        double predictedclass = knn.predict();
        System.out.println("Predicted class : " + predictedclass + "\n");
    }
}