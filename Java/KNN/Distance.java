import java.util.*;
import java.io.*;

public class Distance implements Comparable<Distance> {

    double[] features;
    double distance;

    Distance(double[] features, double distance) {
        this.features = features;
        this.distance = distance;
    }

    @Override
    public int compareTo(Distance d) {
        if(distance == d.distance)
            return 0;
        else if(distance > d.distance)
            return 1;
        return -1;
    }
}