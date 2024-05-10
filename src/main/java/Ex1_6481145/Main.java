//Yanaput Makbonsonglop 6481145

package Ex1_6481145;

import java.util.*;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int x = 0;
        while(x < 2 || x > 1000){
            System.out.println("Enter integer x (2-1000) = ");
            x = scanner.nextInt();
        }
        if(isPrime(x))
            System.out.println(x + " is prime");
        else{
            System.out.println(x + " is not prime");
            for(int i = x-1; i > 0; i--){
                if(isPrime(i)){
                    System.out.println("The immediate smaller value that is prime = " + i);
                    break;
                }
            }
        }
    }

    public static boolean isPrime(int value){
        for(int i = 2; i < value/2 ; i++){
            if(value % i == 0)
                return false;
        }
        return true;
    }
}

