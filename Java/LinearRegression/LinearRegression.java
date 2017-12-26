import java.io.*;
import java.util.*;

class LinearRegression {
    double m, c;

    double mean(double[] x) {
        double mean = 0;
        int len = x.length;
        for(int i = 0; i < len; i++)
            mean += x[i];
        return mean/len;
    }

    double covariance(double[] x, double[] y) {
        double covar = 0;
        double xmean = mean(x), ymean = mean(y);
        int len = x.length;
        for(int i = 0; i < len; i++) {
            covar += ((x[i]-xmean)*(y[i]-ymean));
        }
        return covar/len;
    }

    double variance(double[] x) {
        double var = 0, xmean = mean(x);
        int len = x.length;
        for(int i = 0; i < len; i++) {
            var += Math.pow((x[i]-xmean),2);
        }
        return var/len;
    }

    void calcCoeffeicients(double[] x, double[] y) {
        m = covariance(x,y)/variance(x);
        c = mean(y) - m*mean(x);
    }

    double predict(double x)
    {
        return (x*m+c);
    }

    public static void main(String[] args)throws IOException
    {
        double[] x = { 2, 3, 4, 5, 6, 8, 10, 11 };
        double[] y = { 5, 7, 9, 11, 13, 17, 21, 23};
        //Equation is Y = 2X+1
        LinearRegression linearReg = new LinearRegression();
        linearReg.calcCoeffeicients(x,y);
        double test = 15;
        System.out.println("Value for x = " + test + " = " + linearReg.predict(test));
    }   
}