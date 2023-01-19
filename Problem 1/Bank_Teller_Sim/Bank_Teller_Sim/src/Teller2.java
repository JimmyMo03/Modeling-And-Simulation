import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Teller2 {

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

    public void startSimulation()
    {
        int ordinaryID = 1;      //customer number
        int distinguishedID = 1;

        Scanner s = new Scanner(System.in);
        System.out.println("Enter customer number: ");

        int n = s.nextInt();

        ArrayList<Customer> queue = new ArrayList<>();

        ArrayList<Customer> ordinaryQueue = new ArrayList<>();
        ArrayList<Customer> distinguishedQueue = new ArrayList<>();

        Random m = new Random();
        int firstDistinguishedService= m.nextInt(100);

        Random o = new Random();
        int firstOrdinaryService = o.nextInt(100);

        Customer firstOrdinary = new Customer(0, firstOrdinaryService, false);      //manually entering the first customers details as they aren't affected by other customers
        Customer firstDistinguished = new Customer(0, firstDistinguishedService, true);

        ordinaryQueue.add(firstOrdinary);
        distinguishedQueue.add(firstDistinguished);

        addFirstOrdinary(firstOrdinary);
        queue.add(firstOrdinary);
        addFirstDistinguished(firstDistinguished);
        queue.add(firstDistinguished);

        for (int i = 1; i < n; i++){

            Random r = new Random();
            int A = r.nextInt(100);   //generate random arrival
            int S = r.nextInt(100);   //generate random service
            boolean T = r.nextBoolean();    //generate random type (0: ordinary, 1: distinguished)

            Customer C = new Customer(A, S, T);

            queue.add(C);

            if (C.customerType)
            {
                C.type = 'D';
                distinguishedQueue.add(C);
            }

            else{
                C.type = 'O';
                ordinaryQueue.add(C);
            }
        }

        bubbleSort(ordinaryQueue, ordinaryQueue.toArray().length);
        bubbleSort(distinguishedQueue, distinguishedQueue.toArray().length);

        bubbleSort(queue, n);

        for (int i = 1; i < ordinaryQueue.toArray().length; i++){

            int temp = ordinaryQueue.get(i-1).arrivalTime + ordinaryQueue.get(i-1).serviceTime;

            if ((temp > ordinaryQueue.get(i).arrivalTime) && (ordinaryQueue.get(i).arrivalTime >= ordinaryQueue.get(i-1).endTime)){  //Calculating waiting time
                ordinaryQueue.get(i).waitingTime = temp - ordinaryQueue.get(i).arrivalTime;
                ordinaryWaitCount++;
            }


            else if (ordinaryQueue.get(i).arrivalTime < ordinaryQueue.get(i-1).endTime) {
                ordinaryQueue.get(i).waitingTime = ordinaryQueue.get(i-1).endTime - ordinaryQueue.get(i).arrivalTime;
                ordinaryWaitCount++;
            }

            else{
                ordinaryQueue.get(i).waitingTime = 0;
            }


            if ((ordinaryQueue.get(i-1).endTime >= ordinaryQueue.get(i).beginTime) && (ordinaryQueue.get(i-1).endTime >= ordinaryQueue.get(i).arrivalTime)) {  //Calculating beginning time
                ordinaryQueue.get(i).beginTime = ordinaryQueue.get(i-1).endTime;
            }
            else{
                ordinaryQueue.get(i).beginTime = ordinaryQueue.get(i).waitingTime + ordinaryQueue.get(i).arrivalTime;
            }


            ordinaryQueue.get(i).endTime = ordinaryQueue.get(i).serviceTime + ordinaryQueue.get(i).beginTime;
            ordinaryQueue.get(i).systemTime = ordinaryQueue.get(i).waitingTime + ordinaryQueue.get(i).serviceTime;
            totalSystemTimes += ordinaryQueue.get(i).systemTime;

            int temp2 = ordinaryQueue.get(i-1).beginTime + ordinaryQueue.get(i-1).serviceTime;

            if (temp2 < ordinaryQueue.get(i).arrivalTime)
            {
                ordinaryQueue.get(i).tellerIdleTime = ordinaryQueue.get(i).arrivalTime - temp2;
                totalIdleTime += ordinaryQueue.get(i).tellerIdleTime;
            }
            else {
                ordinaryQueue.get(i).tellerIdleTime = 0;
            }

            totalOrdinaryWT += ordinaryQueue.get(i).waitingTime;

            totalServiceTime += ordinaryQueue.get(i).serviceTime;

            if (i == (n-1)){
                simulationEndTime = ordinaryQueue.get(i).endTime;
            }

        }


        for (int i = 1; i < distinguishedQueue.toArray().length; i++){

            int temp = distinguishedQueue.get(i-1).arrivalTime + distinguishedQueue.get(i-1).serviceTime;

            if ((temp > distinguishedQueue.get(i).arrivalTime) && (distinguishedQueue.get(i).arrivalTime >= distinguishedQueue.get(i-1).endTime)){  //Calculating waiting time
                distinguishedQueue.get(i).waitingTime = temp - distinguishedQueue.get(i).arrivalTime;
                distinguishedWaitCount++;
            }


            else if (distinguishedQueue.get(i).arrivalTime < distinguishedQueue.get(i-1).endTime) {
                distinguishedQueue.get(i).waitingTime = distinguishedQueue.get(i-1).endTime - distinguishedQueue.get(i).arrivalTime;     //
                distinguishedWaitCount++;

            }

            else{
                distinguishedQueue.get(i).waitingTime = 0;
            }


            if ((distinguishedQueue.get(i-1).endTime >= distinguishedQueue.get(i).beginTime) && (distinguishedQueue.get(i-1).endTime >= distinguishedQueue.get(i).arrivalTime)) {  //Calculating beginning time
                distinguishedQueue.get(i).beginTime = distinguishedQueue.get(i-1).endTime;
            }
            else{
                distinguishedQueue.get(i).beginTime = distinguishedQueue.get(i).waitingTime + distinguishedQueue.get(i).arrivalTime;
            }


            distinguishedQueue.get(i).endTime = distinguishedQueue.get(i).serviceTime + distinguishedQueue.get(i).beginTime;
            distinguishedQueue.get(i).systemTime = distinguishedQueue.get(i).waitingTime + distinguishedQueue.get(i).serviceTime;
            totalSystemTimes += distinguishedQueue.get(i).systemTime;

            int temp2 = distinguishedQueue.get(i-1).beginTime + distinguishedQueue.get(i-1).serviceTime;

            if (temp2 < distinguishedQueue.get(i).arrivalTime)
            {
                distinguishedQueue.get(i).tellerIdleTime = distinguishedQueue.get(i).arrivalTime - temp2;
                totalIdleTime += distinguishedQueue.get(i).tellerIdleTime;
            }
            else {
                distinguishedQueue.get(i).tellerIdleTime = 0;
            }

            totalDistinguishedWT += distinguishedQueue.get(i).waitingTime;
            totalDistinguishedCustomers++;

            totalServiceTime += distinguishedQueue.get(i).serviceTime;

            if (i == (n-1)){
                simulationEndTime = distinguishedQueue.get(i).endTime;
            }

        }
    }






    private void addFirstDistinguished(Customer firstDistinguished) {
        firstDistinguished.beginTime = firstDistinguished.interArrivalTime;
        firstDistinguished.endTime = firstDistinguished.beginTime + firstDistinguished.serviceTime;
        firstDistinguished.waitingTime = 0;
        firstDistinguished.systemTime = firstDistinguished.waitingTime + firstDistinguished.serviceTime;
        totalSystemTimes += firstDistinguished.systemTime;
        firstDistinguished.tellerIdleTime = 0;
        firstDistinguished.type = 'D';
        totalDistinguishedCustomers++;
    }

    private void addFirstOrdinary(Customer firstOrdinary) {
        firstOrdinary.beginTime = firstOrdinary.interArrivalTime;
        firstOrdinary.endTime = firstOrdinary.beginTime + firstOrdinary.serviceTime;
        firstOrdinary.waitingTime = 0;
        firstOrdinary.systemTime = firstOrdinary.waitingTime + firstOrdinary.serviceTime;
        totalSystemTimes += firstOrdinary.systemTime;
        firstOrdinary.tellerIdleTime = 0;
        firstOrdinary.type = 'O';
        totalOrdinaryCustomers++;
    }

    public Teller2(){

    }


}
