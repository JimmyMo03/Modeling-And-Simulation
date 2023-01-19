import java.util.ArrayList;

//CLASS THAT PROVES THAT A SECOND TELLER IS NEEDED BY CALCULATING THE MEAN OF MEANS
public class stats {
    public stats(){

        double meanAvgWtOrd;
        double meanAvgWtDist;

        double totalOrd = 0;
        double totalDist = 0;

        ArrayList<Double> tempOrd = new ArrayList<>();
        ArrayList<Double> tempDist = new ArrayList<>();

        for (int i = 0; i < 30; i++){

            Teller t = new Teller();
            t.startSimulation(100);

            totalOrd += t.avgOrdinaryWT;
            tempOrd.add(totalOrd);
            totalDist += t.avgDistinguishedWT;
            tempDist.add(totalDist);
        }

        meanAvgWtOrd = totalOrd / 30.0;
        meanAvgWtDist = totalDist / 30.0;

        System.out.println("\n");
        System.out.println("mean of means for waiting time of ordinary customers: " + String.format("%.3f", meanAvgWtOrd));
        System.out.println("mean of means for waiting time of distinguished customers: " + String.format("%.3f", meanAvgWtDist));
    }

    public static void main(String[] args)
    {
        stats s = new stats();
    }
}
