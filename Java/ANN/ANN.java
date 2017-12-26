import java.io.*;
import java.util.*;

class ANN {

    int epoch, numFeatures;
    double alpha;
    double[] weights;
    static ArrayList<double[]> x, dataset;
    static ArrayList<Double> y;

    public ANN(int epoch, double alpha) {
        this.alpha = alpha;
        this.epoch = epoch;
        x = new ArrayList<>();
        y = new ArrayList<>();
        numFeatures = dataset.get(0).length-1;
        weights = new double[numFeatures+1];
        Random r = new Random();
        for(int i =0; i <= numFeatures; i++)
            weights[i] = r.nextDouble();
    }

    double activation(double val) {
        return (val>=0) ? 1 : 0;
    }

    double predict(double[] x) {
        double result = weights[0];;
        for(int  i = 0; i < numFeatures; i++) {
            result += weights[i+1]*x[i];
        }
        return activation(result);
    }

    void trainWeights() {
        for(int i = 0; i < epoch; i++) {
            for(int j = 0; j < x.size(); j++) {
                double prediction = predict(x.get(j));
                double error = y.get(j) - prediction;
                weights[0] = weights[0] + alpha*error;

                for(int k = 0; k < numFeatures; k++) {
                    weights[k+1] = weights[k+1] + alpha*error*x.get(j)[k];
                }
            }
        }
    }
    void perceptron(double[] test)
    { 
        System.out.println(predict(test));
    }

    public static void main(String[] args)throws IOException
    {
        csvReader csv = new csvReader("input.txt",",");
        dataset = csv.returnData();
        //Epoch, learning rate
        ANN ann = new ANN(5000,0.01);
        for(int i=0;i<dataset.size();i++)
        {
            double[] temp = dataset.get(i);
            int len = temp.length;
            x.add(Arrays.copyOfRange(temp,0,len-1));
            y.add(temp[len-1]);
        }     
        ann.trainWeights();
        double[] test = {1,0};
        ann.perceptron(test);
    } 
} 