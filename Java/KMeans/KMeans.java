import java.io.*;
import java.util.*;

class KMeans {
    ArrayList<double[]> dataset, centroids;
    int numFeatures, k, maxIters;
    HashMap<Integer, ArrayList<double[]>> finalClusters = new HashMap<>();


    public KMeans(int k, int maxIters) {
        this.k = k;
        this.maxIters = maxIters;
        centroids = new ArrayList<>();
    }

    //Randomly pick first k points as centroids
    void initCentroids() {
        for(int i = 0; i < k; i++) 
            centroids.add(dataset.get(i));
    }

    double calcDistance(double[] first, double[] second) {
        double dist = 0;
        for(int i = 0; i < numFeatures; i++) {
            dist += Math.pow((first[i] - second[i]), 2);
        }
        return Math.sqrt(dist);
    }

    double[] findDistFromAllCentroid(double[] point) {
        double[] dist = new double[k];
        for(int i = 0; i < k; i++) {
            dist[i] = calcDistance(centroids.get(i), point);
        }
        return dist;
    }

    int findCluster(double[] distances) {
        int cluster = 0;
        double minDist = Double.MAX_VALUE;
        for(int i = 0; i < distances.length; i++) {
            if(minDist > distances[i]) {
                minDist = distances[i];
                cluster = i;
            }
        }
        return cluster;
    }

    void recomputeCentroid(HashMap<Integer, ArrayList<double[]>> clusters) {
        
        for(int clusterNum : clusters.keySet()) {
            double[] newCentroid = new double[numFeatures];
            ArrayList<double[]> cluster = clusters.get(clusterNum);
            for(double[] point : cluster) {
                for(int i = 0; i < point.length; i++) {
                    newCentroid[i] += (point[i]/cluster.size());
                }
            }
            centroids.add(clusterNum, newCentroid);
        }
    }

    void findCentroids() {
        for(int i = 0; i < maxIters; i++) {
            
            HashMap<Integer, ArrayList<double[]>> clusters = new HashMap<>();
            for(double[] point : dataset) {
                double[] distances = findDistFromAllCentroid(point);
                int clusterNum = findCluster(distances);
                ArrayList<double[]> temp = clusters.get(clusterNum);
                if(temp == null) {
                    temp = new ArrayList<>();
                }
                temp.add(point);
                clusters.put(clusterNum, temp);
            }
            recomputeCentroid(clusters);
            finalClusters = clusters;
        }
    }

    void printClusters() {
        for(int clusterNum : finalClusters.keySet())
        {
            System.out.println("Cluster " + clusterNum+":");
            ArrayList<double[]> temp = finalClusters.get(clusterNum);
            for(int i=0;i<temp.size();i++)
            {
                System.out.print(Arrays.toString(temp.get(i)) +", ");
            }
            System.out.println();
        }
    }

    void predict(double[] test) {
        double[] distances = findDistFromAllCentroid(test);
        int cluster = findCluster(distances);
        System.out.println(Arrays.toString(test) + " belongs to cluster " + cluster);
    }

    public static void main(String[] args)throws IOException
    {
        //pass value of k and max iter val
        KMeans means = new KMeans(3,100);
        csvReader reader = new csvReader("input.txt",",");
        means.dataset = reader.returnData();
        means.numFeatures = means.dataset.get(0).length;
        means.initCentroids();
        means.findCentroids();
        means.printClusters();
        double[] test = {2.0,3.0,4.5};
        means.predict(test);
    }
}