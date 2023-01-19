//CUSTOMER CLASS
public class Customer {
    static int lastArrivalTime = 0;
    public int arrivalTime = 0;
    int id;
    int arrivalTimeProbability;
    int waitingTime;
    int interArrivalTime;
    int serviceTime;
    int serviceTimeProb;
    int beginTime = 0;
    int endTime = 0;
    int systemTime = 0;
    int tellerIdleTime;
    boolean customerType;
    boolean occupied = false;
    char type;

    public int getArrivalTime(){
        return interArrivalTime;
    }
    public int getArrival(){
        return arrivalTime;
    }


    public Customer(int a, int s, boolean t)
    {
        arrivalTimeProbability = a;
        serviceTimeProb = s;
        customerType = t;

        //ASSIGNING THE VALUES ACCORDING TO THE RANDOM ASSIGNED VARIABLE
        if (customerType)   //if distinguished
        {
            if (arrivalTimeProbability <= 10){
                interArrivalTime = 0;
            }
            if(arrivalTimeProbability > 10 && arrivalTimeProbability <= 30){
                interArrivalTime = 1;

            }
            if(arrivalTimeProbability > 30 && arrivalTimeProbability <= 60){
                interArrivalTime = 2;

            }
            if(arrivalTimeProbability > 60 && arrivalTimeProbability <= 100){
                interArrivalTime = 3;

            }



            if(serviceTimeProb <= 10) {
                serviceTime  = 1;
            }
            if(serviceTimeProb > 10 && serviceTimeProb <= 40) {
                serviceTime  = 2;
            }
            if(serviceTimeProb > 40 && serviceTimeProb <= 78) {
                serviceTime  = 3;
            }
        }


        else{    //if ordinary

            if (arrivalTimeProbability <= 9){
                interArrivalTime = 0;
            }
            if(arrivalTimeProbability > 9 && arrivalTimeProbability <= 26){
                interArrivalTime = 1;
            }
            if(arrivalTimeProbability > 26 && arrivalTimeProbability <= 53){
                interArrivalTime = 2;
            }
            if(arrivalTimeProbability > 53 && arrivalTimeProbability <= 73){
                interArrivalTime = 3;
            }
            if(arrivalTimeProbability > 73 && arrivalTimeProbability <= 88){
                interArrivalTime = 4;
            }
            if(arrivalTimeProbability > 88 && arrivalTimeProbability <= 100){
                interArrivalTime = 5;
            }



            if(serviceTimeProb <= 20) {
                serviceTime  = 1;
            }
            if(serviceTimeProb > 20 && serviceTimeProb <= 60) {
                serviceTime  = 2;
            }
            if(serviceTimeProb > 60 && serviceTimeProb <= 88) {
                serviceTime  = 3;
            }
        }


        if(serviceTimeProb > 78 && serviceTimeProb <= 100) {
            serviceTime  = 4;
        }

        arrivalTime += lastArrivalTime+interArrivalTime;
        lastArrivalTime = arrivalTime;
    }

}