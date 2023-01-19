import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

//THIS IS THE TELLER CLASS THAT CONTAINS THE QUEUES AND ARRANGES CUSTOMERS
public class Teller {

    public double avgSystemTime;
    public int totalOrdinaryWT = 0;
    public int totalDistinguishedWT = 0;
    public int totalServiceTime = 0;
    public int totalSystemTimes = 0;
    public int totalIdleTime = 0;
    public int simulationEndTime = 0;
    public double percentageOrdinary;
    public double percentageDistinguished;
    public double avgOrdinaryWT;
    public double avgDistinguishedWT;
    public double avgServiceTime;
    public double avgServiceTimeOrd;
    public double avgServiceTimeDist;
    public int totalOrdServiceTime = 0;
    public int totalDistServiceTime = 0;
    public double avgInterTimeOrd;
    public double avgInterTimeDIst;
    public int totalInterTimeOrd = 0;
    public int totalInterTimeDist = 0;
    public double PWT;


    public int totalOrdinaryCustomers = 0;
    public int totalDistinguishedCustomers = 0;

    public int ordinaryWaitCount = 0;
    public int distinguishedWaitCount = 0;

    static void bubbleSort(ArrayList<Customer> arr, int n)    //Bubble sort function to sort the Customer queue according to type of customer.
    {
        int i, j;
        boolean swapped;

        

        for (i = 0; i < n - 1; i++)
        {
            swapped = false;
            for (j = 0; j < n - i - 1; j++)
            {
                if (arr.get(j).arrivalTime > arr.get(j+1).arrivalTime)
                {
                    Collections.swap(arr, j+1, j);
                    swapped = true;
                }
            }


            if ((i > 0) && arr.get(i).arrivalTime == arr.get(i-1).arrivalTime)
            {
                if ((arr.get(i).type != arr.get(i-1).type) && (arr.get(i-1).type == 'O')) {

                    Collections.swap(arr, i, i-1);
                }
            }

            // IF no two elements were
            // swapped by inner loop, then break

            if (!swapped)
                break;
        }
    }


    public void startSimulation(int n){
        int id = 1;      //customer number

        ArrayList<Customer> queue = new ArrayList<>();

        ArrayList<Customer> ordinaryQueue = new ArrayList<>();
        ArrayList<Customer> distinguishedQueue = new ArrayList<>();

        Random m = new Random();
        int firstService = m.nextInt(100);
        boolean firstType = m.nextBoolean();

        Customer firstCustomer = new Customer(0, firstService, firstType);      //manually entering the first customers details as they aren't affected by other customers

        queue.add(firstCustomer);
        addFirstCustomer(ordinaryQueue, distinguishedQueue, firstCustomer);


        for (int i = 1; i < n; i++){

            Random r = new Random();
            int A = r.nextInt(100);   //generate random arrival
            int S = r.nextInt(100);   //generate random service
            boolean T = r.nextBoolean();    //generate random type (0: ordinary, 1: distinguished)

            Customer C = new Customer(A, S, T);

            queue.add(C);
        }

        System.out.println('\n');

        bubbleSort(queue, n);     //sort the queue according to arrival time using bubble sort algorithm

        for (int i = 1; i < n; i++){

            int temp = queue.get(i-1).arrivalTime + queue.get(i-1).serviceTime;

            if ((temp > queue.get(i).arrivalTime) && (queue.get(i).arrivalTime >= queue.get(i-1).endTime)){  //Calculating waiting time
                queue.get(i).waitingTime = temp - queue.get(i).arrivalTime;
                if (queue.get(i).customerType)
                {
                    distinguishedWaitCount++;
                }
                else{
                    ordinaryWaitCount++;
                }
            }


            else if (queue.get(i).arrivalTime < queue.get(i-1).endTime) {
                queue.get(i).waitingTime = queue.get(i-1).endTime - queue.get(i).arrivalTime;     //Calculating waiting time for each customer
                if (queue.get(i).customerType)
                {
                    distinguishedWaitCount++;
                }
                else{
                    ordinaryWaitCount++;
                }
            }

            else{
                queue.get(i).waitingTime = 0;
            }


            if ((queue.get(i-1).endTime >= queue.get(i).beginTime) && (queue.get(i-1).endTime >= queue.get(i).arrivalTime)) {  //Calculating beginning time
                queue.get(i).beginTime = queue.get(i-1).endTime;
            }
            else{
                queue.get(i).beginTime = queue.get(i).waitingTime + queue.get(i).arrivalTime;
            }


            queue.get(i).endTime = queue.get(i).serviceTime + queue.get(i).beginTime;
            queue.get(i).systemTime = queue.get(i).waitingTime + queue.get(i).serviceTime;
            totalSystemTimes += queue.get(i).systemTime;

            int temp2 = queue.get(i-1).beginTime + queue.get(i-1).serviceTime;

            if (temp2 < queue.get(i).arrivalTime)
            {
                queue.get(i).tellerIdleTime = queue.get(i).arrivalTime - temp2;
                totalIdleTime += queue.get(i).tellerIdleTime;
            }
            else {
                queue.get(i).tellerIdleTime = 0;
            }

            if (queue.get(i).customerType)
            {
                queue.get(i).type = 'D';
                totalDistinguishedWT += queue.get(i).waitingTime;
                totalDistinguishedCustomers++;
                totalDistServiceTime += queue.get(i).serviceTime;
                totalInterTimeDist += queue.get(i).interArrivalTime;

                distinguishedQueue.add(queue.get(i));
            }

            else{
                queue.get(i).type = 'O';
                totalOrdinaryWT += queue.get(i).waitingTime;
                totalOrdinaryCustomers++;
                totalOrdServiceTime += queue.get(i).serviceTime;
                totalInterTimeOrd += queue.get(i).interArrivalTime;

                ordinaryQueue.add(queue.get(i));
            }

            totalServiceTime += queue.get(i).serviceTime;

            if (i == (n-1)){
                simulationEndTime = queue.get(i).endTime;
            }

        }

        bubbleSort(ordinaryQueue, ordinaryQueue.toArray().length);
        bubbleSort(distinguishedQueue, distinguishedQueue.toArray().length);

        System.out.println("#  " + "IAT\t" + "AT\t" + "ST\t" + "CT\t" + "WT\t" + "BT\t" + "ET\t" + "TT\t" + "I\t");

        for (int i = 0; i < n; i++) {

            queue.get(i).id = id;
            id++;

            System.out.println(queue.get(i).id + ") " + queue.get(i).interArrivalTime + "\t"
                    + queue.get(i).arrivalTime + "\t" + queue.get(i).serviceTime + "\t"
                    + queue.get(i).type + "\t" + queue.get(i).waitingTime + "\t"
                    + queue.get(i).beginTime + "\t" + queue.get(i).endTime + "\t"
                    + queue.get(i).systemTime + "\t" + queue.get(i).tellerIdleTime);
        }

        printStatistics(n);
    }


    //FUNCTION TO MANUALLY ADD FIRST CUSTOMER TO QUEUE
    private void addFirstCustomer(ArrayList<Customer> ordinaryQueue, ArrayList<Customer> distinguishedQueue, Customer firstCustomer) {
        firstCustomer.beginTime = firstCustomer.interArrivalTime;
        firstCustomer.endTime = firstCustomer.beginTime + firstCustomer.serviceTime;
        firstCustomer.waitingTime = 0;
        firstCustomer.systemTime = firstCustomer.waitingTime + firstCustomer.serviceTime;
        totalSystemTimes += firstCustomer.systemTime;
        firstCustomer.tellerIdleTime = 0;

        if (firstCustomer.customerType)
        {
            firstCustomer.type = 'D';
            totalDistinguishedCustomers++;
            distinguishedQueue.add(firstCustomer);
        }

        else{
            firstCustomer.type = 'O';
            totalOrdinaryCustomers++;
            ordinaryQueue.add(firstCustomer);
        }
    }


    public Teller() {         //Default constructor

    }


    //FUNCTION THAT PRINTS ALL STATISTICS GATHERED

    public void printStatistics(int n){
        avgSystemTime = totalSystemTimes /(double) n;      //calculating average time a customer spends in the whole system

        avgServiceTime = totalServiceTime / (double) n;      //calculating average service time of the teller

        avgOrdinaryWT = totalOrdinaryWT / (double) ordinaryWaitCount;                //calculating average waiting time for both types of customers
        avgDistinguishedWT = totalDistinguishedWT / (double) distinguishedWaitCount;
        System.out.println('\n');

        avgServiceTimeOrd = totalOrdServiceTime / (double) totalOrdinaryCustomers;         //calculating average service time for both types of customers
        avgServiceTimeDist = totalDistServiceTime / (double) totalDistinguishedCustomers;

        avgInterTimeOrd = totalInterTimeOrd / (double) totalOrdinaryCustomers;
        avgInterTimeDIst = totalInterTimeDist / (double) totalDistinguishedCustomers;


        System.out.println("Avg service time for teller: " + String.format("%.3f", avgServiceTime));
        System.out.println('\n');

        System.out.println("Avg waiting time for ordinary customers: " + String.format("%.3f", avgOrdinaryWT));
        System.out.println("Avg waiting time for distinguished customers: " + String.format("%.3f",avgDistinguishedWT));
        System.out.println('\n');

        percentageOrdinary = (ordinaryWaitCount / (double) totalOrdinaryCustomers) * 100;
        percentageDistinguished = (distinguishedWaitCount / (double) totalDistinguishedCustomers) * 100;
        System.out.println("Percentage of Ordinary waiting customers: " + String.format("%.3f", ((ordinaryWaitCount / (double) totalOrdinaryCustomers) * 100)) + "%");
        System.out.println("Percentage of Distinguished waiting customers: " + String.format("%.3f", ((distinguishedWaitCount / (double) totalDistinguishedCustomers) * 100)) + "%");
        System.out.println('\n');

        System.out.println("Avg time the teller is idle: " + String.format("%.3f", (totalIdleTime/ (double) simulationEndTime)));
        System.out.println('\n');

        //EXTRAS:
        System.out.println("Number of ordinary customers: " + totalOrdinaryCustomers);
        System.out.println("Number of distinguished customers: " + totalDistinguishedCustomers);
        System.out.println('\n');

        System.out.println("Avg Service time for ordinary customers: " + String.format("%.3f", avgServiceTimeOrd));
        System.out.println("Avg Service time for distinguished customers:" + String.format("%.3f", avgServiceTimeDist));
        System.out.println('\n');

        System.out.println("Avg Inter-Arrival time for ordinary customers: " + String.format("%.3f", avgInterTimeOrd));
        System.out.println("Avg Inter-Arrival time for distinguished customers:" + String.format("%.3f", avgInterTimeDIst));
        System.out.println('\n');

        System.out.println("Avg time any type of customer takes in system: " + String.format("%.3f", avgSystemTime));
        System.out.println('\n');

        PWT = ((ordinaryWaitCount + distinguishedWaitCount)/ (double) n ) * 100;
        System.out.println("Percentage of waiting customers: " + String.format("%.3f", PWT) + "%");
    }
}
