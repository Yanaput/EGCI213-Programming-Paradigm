//Yanaput Makbonsonglop 6481145
package Ex5_6481145;

import java.io.*;
import java.util.*;


class Seafood implements Comparable<Seafood> {
    private String name, type;
    private int omega3, cholesterol;
    private double mercury;
    public Seafood(String type, String name, int omega3, int cholesterol, double mercury) throws InvalidInputException {
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
    public String getName(){return this.name;}
    public void print(Character selectedType){
        if(this.type.charAt(0) == selectedType || selectedType == 'a')
            System.out.printf("%-24s%-10s%11d%17d%18.3f\n",
                    this.name, this.type, this.omega3, this.cholesterol, this.mercury);
    }
}


class  InvalidInputException extends Exception{
    public InvalidInputException(String message) {super(message);}
}


public class Main {
    public static void printHeader(){
        System.out.printf("%-18s%10s%20s%20s%17s\n",
                "Seafood (3 oz)", "Type", "Omega-3 (mg)", "Cholesterol (mg)", "Mercury (ppm)");
        System.out.println("=".repeat(85));
    }


    public static void main(String[] args) throws InterruptedException {
        Scanner keyboardScanner = new Scanner(System.in);
        String readLine = "", fileName = "seafoods_errors.";
        boolean fileLoaded = false;

        while(!fileLoaded) {
            try {
                Scanner fileScanner = new Scanner(new File("src/main/java/Ex5_6481145/" + fileName));
                fileLoaded = true;
                fileScanner.nextLine();
                ArrayList<Seafood> seafoodArrayList = new ArrayList<Seafood>();
                while (fileScanner.hasNext()) {
                    try {
                        readLine = fileScanner.nextLine();
                        String[] cols = readLine.split(",");
                        String type = switch (cols[0].toLowerCase()) {
                            case "f" -> "fish";
                            case "m" -> "mollusk";
                            case "c" -> "crustacean";
                            default -> throw new InvalidInputException(": For input :\"" + cols[0] + "\"");
                        };

                        for (int i = 2; i < cols.length; i++) {
                            if (Double.parseDouble(cols[i].trim()) < 0)
                                throw new InvalidInputException(": For input :\"" + cols[i].trim() + "\"");
                        }

                        seafoodArrayList.add(new Seafood(type, cols[1].trim(), Integer.parseInt(cols[2].trim()),
                                Integer.parseInt(cols[3].trim()), Double.parseDouble(cols[4].trim())));
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException | InvalidInputException e) {
                        System.err.println(e + "\n" + readLine + "\n");
                    }
                }

                Collections.sort(seafoodArrayList);

                while (true) {
                    System.out.println("Choose filter -> a =all, f = fish, c = crustacean, m = mollusk, others = quit");
                    String input = keyboardScanner.next().toLowerCase();

                    if ((input.equals("a") || input.equals("f") || input.equals("c") || input.equals("m"))) {
                        printHeader();
                        for (Seafood i : seafoodArrayList)
                            i.print(input.charAt(0));
                    } else {
                        System.out.println("_".repeat(85));
                        break;
                    }
                    System.out.println();
                }
                fileScanner.close();
                keyboardScanner.close();

            } catch (FileNotFoundException e) {
                System.err.println(e);
                Thread.sleep(100); // Delay waiting for System.out to be fully buffered
                System.out.println("New file name = ");
                fileName = keyboardScanner.nextLine();
            }
        }
    }
}