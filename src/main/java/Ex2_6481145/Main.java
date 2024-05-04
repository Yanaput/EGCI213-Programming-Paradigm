//Yanaput Makbonsonglop 6481145
package Ex2_6481145;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Enter your city size in square km = ");
        Scanner keyboardInput = new Scanner(System.in);
        int inputCitySize = keyboardInput.nextInt();

        try {
            File inputFile = new File("src/main/java/Ex2_6481145/provinces.txt");
            File outputFile = new File("src/main/java/Ex2_6481145/output.txt");

            System.out.println("Read provinces size from " + inputFile.getPath());
            System.out.println("Write output " + outputFile.getPath());

            Scanner fileScanner = new Scanner(inputFile);
            PrintWriter writer = new PrintWriter(new FileWriter(outputFile, false));

            writer.printf("%-18s%-18s%-18s%-18s\r\n", "Province", "Square km", "Square mile", "ratio to your city");
            writer.println("=".repeat(72));
            writer.printf("%-12s%,14d%,20.2f%,19.2f\r\n", "Your city", inputCitySize, inputCitySize*0.386102, 1.00);

            while (fileScanner.hasNext()){
                String readProvince = fileScanner.next();
                int readSize = fileScanner.nextInt();
                writer.printf("%-12s%,14d%,20.2f%,19.2f\r\n", readProvince, readSize, readSize*0.386102, readSize/(double)inputCitySize);
            }
            fileScanner.close();
            writer.close();
        }

        catch (Exception e){
            System.err.println(e);
        }
    }
}
