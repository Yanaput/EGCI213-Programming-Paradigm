//Yanaput Makbonsonglop 6481145
package Ex4_6481145;

import java.io.File;
import java.util.*;

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
        int result = Integer.compare(other.omega3, this.omega3);
        if(result != 0)
            return result;

        result = Integer.compare(this.cholesterol, other.cholesterol);
        if(result != 0)
            return result;

        result = Double.compare(this.mercury, other.mercury);
        if(result != 0)
            return  result;

        return  this.name.compareToIgnoreCase(other.name);
    }

    public void print(Character selectedType){
        if(this.type.charAt(0) == selectedType || selectedType == 'a')
            System.out.printf("%-24s%-10s%11d%17d%18.3f\n",
                    this.name, this.type, this.omega3, this.cholesterol, this.mercury);
    }
}

public class Main {
    public static void printHeader(){
        System.out.printf("%-18s%10s%20s%20s%17s\n",
                "Seafood (3 oz)", "Type", "Omega-3 (mg)", "Cholesterol (mg)", "Mercury (ppm)");
        System.out.println("=".repeat(85));
    }

    public static void main(String[] args) {
        try {
            Scanner fileScanner = new Scanner(new File("src/main/java/Ex4_6481145/seafoods.txt"));
            fileScanner.nextLine();
            ArrayList<Seafood> seafoodArrayList= new ArrayList<Seafood>();
            while(fileScanner.hasNext()){
                String readLine = fileScanner.nextLine();
                String[] cols = readLine.split(",");
                String type = "";
                switch(cols[0].toLowerCase()){
                    case "f" :
                        type = "fish";
                        break;
                    case "m" :
                        type = "mollusk";
                        break;
                    case "c" :
                        type = "crustacean";
                        break;
                    default :
                        System.err.println("Unmatched type appear");
                        break;
                }
                seafoodArrayList.add(new Seafood(type, cols[1].trim(), Integer.parseInt(cols[2].trim()),
                        Integer.parseInt(cols[3].trim()), Double.parseDouble(cols[4].trim())));
            }

            Collections.sort(seafoodArrayList);
            Scanner keyboardScanner = new Scanner(System.in);

            while(true){
                System.out.println("Choose filter -> a =all, f = fish, c = crustacean, m = mollusk, others = quit");
                String input = keyboardScanner.next().toLowerCase();

                if((input.equals("a")||input.equals("f")||input.equals("c")||input.equals("m"))){
                    printHeader();
                    for(Seafood i: seafoodArrayList)
                        i.print(input.charAt(0));
                }
                else {
                    System.out.println("_".repeat(85));
                    break;
                }
                System.out.println();
            }
            fileScanner.close();
            keyboardScanner.close();
        }
        catch (Exception e){

        }
    }
}