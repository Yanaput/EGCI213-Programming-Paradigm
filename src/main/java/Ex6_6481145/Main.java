//Yanaput Makbonsonglop 6481145
package Ex6_6481145;

import java.util.*;
import java.io.*;


class PrimeThread extends Thread {
    private PrintWriter out;
    private ArrayList<Integer> numbers = new ArrayList<>();
    private int totalPrime, target, rounds ;
    public PrimeThread(String name, int target) { super(name); this.target = target; }
    public boolean isPrime(int value){
        if(value % 2 == 0) return false;

        for(int i = 3; i <= Math.sqrt(value); i += 2){
            if(value % i == 0) return false;
        }

        return true;
    }

    @Override
    public void run(){
        String fileName = "src/main/java/Ex6_6481145/output" + this.getName() + ".txt";
        try{
            out = new PrintWriter(fileName);
            out.printf("%-10s, target = %d\n",this.getName(), this.target);
            while(totalPrime < target){
                rounds++;
                out.printf("Round%4d  >>  ", rounds);

                for (int i = 0; i < 5; i++)
                    numbers.add(new Random().nextInt(2,101));

                Collections.sort(numbers);

                for(int aRandNumber: numbers) {
                    if (isPrime(aRandNumber)) {
                        totalPrime += aRandNumber;
                        out.printf("%6s","+" + aRandNumber);
                    }
                    else
                        out.printf("%6d",aRandNumber);
                }
                numbers.clear();
                out.printf("%20s%6d\n", "total prime = ", totalPrime);
            }
            System.out.println(this.getName()+" finished in " + rounds + " rounds");
            this.out.close();
        }
        catch(Exception e){
            System.err.println(e);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner keyboardScanner = new Scanner(System.in);
        System.out.println("Enter #Threads =");
        int threadsNumber = keyboardScanner.nextInt();
        System.out.println("Enter target =");
        int target = keyboardScanner.nextInt();
        keyboardScanner.close();

        ArrayList<PrimeThread> threads = new ArrayList<>();
        for (int i = 0; i < threadsNumber; i++) {
            threads.add(new PrimeThread("Thread_"+Integer.toString(i), target));
        }

        for(PrimeThread thread: threads)
            thread.start();

    }
}
