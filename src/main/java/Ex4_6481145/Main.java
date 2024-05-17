//Yanaput Makbonsonglop 6481145
package Ex4_6481145;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Seafood implements Comparable<Seafood> {
    private String name, type;
    private int omega3, cholesterol;
    private double mercury;
    public Seafood(String type, String name, int omega3, int cholesterol, double mercury){
        this.name = name;
        this.type = type;
        this.omega3 = omega3;
        this.cholesterol = cholesterol;
        this.mercury = mercury;
    }

    public int compareTo(Seafood other) {
        if(this.omega3 < other.omega3)
            return 1;
        else if(this.omega3 > other.omega3)
            return -1;
        else{
            if(this.cholesterol > other.cholesterol)
                return 1 ;
            else if(this.cholesterol < other.cholesterol)
                return -1;
            else{
                if(this.mercury > other.mercury)
                    return 1 ;
                else if(this.mercury < other.mercury)
                    return -1;
                else{
                    return this.name.compareToIgnoreCase(other.name);
                }
            }
        }
    }

    public void print(){
        System.out.printf("%-24s%-10s%11d%17d%18.3f\n", this.name, this.type, this.omega3, this.cholesterol, this.mercury);
    }
}

class fish extends Seafood {
     public fish(String type,String name,  int omega3, int cholesterol, double mercury){
        super(type, name, omega3, cholesterol, mercury);
    }
}

class crustacean extends Seafood{
    public crustacean(String type,String name, int omega3, int cholesterol, double mercury){
        super(type, name, omega3, cholesterol, mercury);
    }
}

class mollusk extends Seafood{
    public mollusk(String type,String name, int omega3, int cholesterol, double mercury){
        super(type, name, omega3, cholesterol, mercury);
    }
}


public class Main {
    public static void printFormat(){
        System.out.printf("%-18s%10s%20s%20s%17s\n", "Seafood (3 oz)", "Type", "Omega-3 (mg)", "Cholesterol (mg)", "Mercury (ppm)");
        System.out.println("=".repeat(85));
    }

    public static void main(String[] args) {
        String path = "src/main/java/Ex4_6481145/seafoods.txt";
        try {
            Scanner fileScanner = new Scanner(new File(path));
            fileScanner.nextLine();
            ArrayList<Seafood> seafoodArrayList= new ArrayList<Seafood>();
            while(fileScanner.hasNext()){
                String readLine = fileScanner.nextLine();
                String[] cols = readLine.split(",");
                if(cols[0].equalsIgnoreCase("f")){
                    seafoodArrayList.add(new fish("fish", cols[1].trim(), Integer.parseInt(cols[2].trim()),
                            Integer.parseInt(cols[3].trim()), Double.parseDouble(cols[4].trim())));
                }
                else if(cols[0].equalsIgnoreCase("c")){
                    seafoodArrayList.add(new crustacean("crustacean" ,cols[1].trim(), Integer.parseInt(cols[2].trim()),
                            Integer.parseInt(cols[3].trim()), Double.parseDouble(cols[4].trim())));
                }
                else if(cols[0].equalsIgnoreCase("m")){
                    seafoodArrayList.add(new mollusk("mollusk",cols[1].trim(), Integer.parseInt(cols[2].trim()),
                            Integer.parseInt(cols[3].trim()), Double.parseDouble(cols[4].trim())));
                }
                else {
                    System.err.println("Unmatched type");
                    break;
                }
            }

            Collections.sort(seafoodArrayList);
            Scanner keyboardScanner = new Scanner(System.in);
            boolean loopRunning = true;

            while(loopRunning){
                System.out.println("Choose filter -> a =all, f = fish, c = crustacean, m = mollusk, others = quit");
                String input = keyboardScanner.next();
                switch (input.toLowerCase()){
                    case "a" :
                        printFormat();
                        for(Seafood i: seafoodArrayList)
                            i.print();
                        break;

                    case "f" :
                        printFormat();
                        for(Seafood i: seafoodArrayList)
                            if(i instanceof fish)
                                i.print();
                        break;

                    case "c" :
                        printFormat();
                        for(Seafood i: seafoodArrayList)
                            if(i instanceof crustacean)
                                i.print();
                        break;

                    case "m" :
                        printFormat();
                        for(Seafood i: seafoodArrayList)
                            if(i instanceof mollusk)
                                i.print();
                        break;

                    default:
                        System.out.println("_".repeat(85));
                        loopRunning = false;
                        break;
                }
                System.out.println();
            }

            fileScanner.close();
            keyboardScanner.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
