package Ex3_6481145;


import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Racing[] races= new Racing[13];
        try{
            File inputFIle = new File("src/main/java/Ex3_6481145/racing.txt");
            Scanner fileScanner = new Scanner(inputFIle);
            int idx = 0;
            while (fileScanner.hasNext()){
                String[] cols = fileScanner.nextLine().split(",");
                if(cols[0].equals("m")) {
                    //MotorRacing(String nm, String vn, int year, double lapLength, double lapTimeMS, double speed)
                    String[] lapTime = cols[5].split(":");
                    double lapTimeMS = Double.parseDouble(lapTime[0])*60000 + Double.parseDouble(lapTime[1])*1000;
                    double distance = Double.parseDouble(cols[4]);
                    double speed = (3600000*distance)/lapTimeMS;
                    races[idx] = new MotorRacing(cols[1].trim(), cols[2].trim(), Integer.parseInt(cols[3].trim()), distance, cols[5] ,lapTimeMS, speed);
                }
                else if(cols[0].equals("h")) {
                    double distanceFurlong = Double.parseDouble(cols[4].trim());
                    races[idx] = new HorseRacing(cols[1].trim(), cols[2].trim(), Integer.parseInt(cols[3].trim()), distanceFurlong, distanceFurlong/5);
                }
                idx++;
            }
            System.out.println("=== All races (reverse order) ===");
            for(int i = races.length-1; i >= 0; i--)
                races[i].printVenue();

            System.out.println("\n=== Only Horse races (input order) ===");
            for(Racing i:races)
                if(i instanceof HorseRacing)
                    i.printDetails();
            System.out.println("\n=== Only Motor races (input order) ===");
            for(Racing i:races)
                if(i instanceof MotorRacing)
                    i.printDetails();


            fileScanner.close();
        }
        catch (Exception e){
            System.err.println(e);
        }
    }
}

class Racing {
    public static final int CURRENT_YEAR = 2024;
    private String event, venue;
    protected int venueOpened, venueAge;
    public Racing(String nm, String vn) { event = nm; venue = vn; }
    public String getEvent() { return event; }
    public String getVenue() { return venue; }
    public void printVenue() { /* override this in child class */ }
    public void printDetails() { /* override this in child class */ }
}

class HorseRacing extends Racing{
    protected double distanceFurlong, distanceKM;
    protected int age;
    HorseRacing(String nm, String vn, int age, double distanceFurlong, double distanceKM){
        super(nm, vn);
        this.distanceFurlong = distanceFurlong;
        this.distanceKM = distanceKM;
        this.age = age;
    }

    @Override
    public void printVenue(){
        System.out.printf("%-20s venue = %-20s  (opened %d, %4d years ago)\n",this.getEvent(), this.getVenue(), CURRENT_YEAR-this.age, this.age);
    }

    @Override
    public void printDetails(){
        System.out.printf("%-20s distance = %5.2f  furlongs%4s %5.2f km\n", this.getEvent(), this.distanceFurlong,"=", this.distanceKM);
    }
}

class MotorRacing extends Racing{
    protected double lapLength, lapTimeMS, speed;
    protected int year;
    protected String lapTime;
    MotorRacing(String nm, String vn, int year, double lapLength, String lapTime, double lapTimeMS, double speed){
        super(nm, vn);
        this.lapLength = lapLength;
        this.lapTime = lapTime;
        this.lapTimeMS = lapTimeMS;
        this.speed = speed;
        this.year = year;
    }


    @Override
    public void printVenue(){
        System.out.printf("%-20s venue = %-20s  (opened %d, %4d years ago)\n",this.getEvent(), this.getVenue(), this.year, CURRENT_YEAR-this.year);
    }

    @Override
    public void printDetails(){
        System.out.printf("%-20s lap = %5.3f km %15s%s  mins%15s%6.1f  km/hr\n", this.getEvent(), this.lapLength,"lab time = ", this.lapTime, "avg speed = ", this.speed);
    }
}