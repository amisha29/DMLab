import java.util.*;


public class Label {
    ArrayList<Record> data;
    HashMap<String, Double> probability;
    double mean, variance, prior;

    public Label() {
        data = new ArrayList<>();
        probability  =new HashMap<>();
        mean = variance = prior = 0;
    }

    void addRecord(Record record) {
        data.add(record);
    }

    void calculateMean() {
        for(Record record : data) {
            mean += record.att1;
        }
        mean = mean/(double)data.size();
    }

    void calculateVariance() {
        for(Record record : data) {
            variance += Math.pow((record.att1-mean), 2);
        }
        variance = variance/(double)(data.size() * (data.size()-1));
    }

    void calculateProbability() {
        HashMap<String, Integer> frequency = new HashMap<>();
        int count;
        for(Record record : data) {
            count = 0;
            if(frequency.containsKey(record.att2))
                count = frequency.get(record.att2) + 1;
            else
                count = 1;
            frequency.put(record.att2, count);
        }

        for(String attr : frequency.keySet()) {
            probability.put(attr, (double)frequency.get(attr)/(double)data.size());
        }
    }

    double getProbability(String attr) {
        if(probability.containsKey(attr))	{
			return probability.get(attr);
		}
		return 0.0;
    }
}