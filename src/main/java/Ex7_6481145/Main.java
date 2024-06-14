//Yanaput Makbonsonglop 6481145
package Ex7_6481145;

import java.util.*;
import java.util.concurrent.*;

////////////////////////////////////////////////////////////////////////////////
class BankThread extends Thread {
    private Account sharedAccount;    // threads from the same bank work on the same account
    private Exchanger<Account> exchanger;
    private CyclicBarrier barrier;
    private int transaction = 1;

    public BankThread(String name, Account sharedAccount){
        super(name);
        this.sharedAccount = sharedAccount;
    }

    public void setBarrier(CyclicBarrier barrier){
        this.barrier = barrier;
    }

    public void setExchanger(Exchanger<Account> exchanger){
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        int temp = -1;
        try { temp = barrier.await();}  catch(Exception e) { }

        if (temp == 0)
            System.out.println(Thread.currentThread().getName() + "  >>  start deposit");

        for(int i = 0; i < 3; i++){
            sharedAccount.deposit(transaction);
            transaction++;
        }

        try {barrier.await();}  catch(Exception e) { }

        if(exchanger != null) {
            try {
             System.out.printf("%-5s >>  exchange\n", Thread.currentThread().getName());
                sharedAccount = exchanger.exchange(sharedAccount);
            } catch (Exception e) {
                System.err.println(e);
            }
        }

        temp = -1;
        try { temp = barrier.await();}  catch(Exception e) { }
        if(temp == 0)
            System.out.printf("%-5s >>  start withdraw\n", Thread.currentThread().getName());
        for(int i = 0; i < 3; i++){
            this.sharedAccount.withdraw(transaction);
            transaction++;
        }
    }
}

////////////////////////////////////////////////////////////////////////////////
class Account {
    private String  name;
    private int     balance;
    
    public Account(String name, int balance)   { this.name = name; this.balance = balance; }
    public String getName()            { return name; }
    public int    getBalance()         { return balance; }
    
    public synchronized void deposit(int transaction)
    {
        int deposit = new Random().nextInt(1,101);
        this.balance += deposit;
        System.out.printf("%-5s  >>  transaction %-8d %s %5s  balance  =  %5d\n", Thread.currentThread().getName(),
                transaction, this.getName(),"+" + deposit, this.getBalance());
    }
    
    public synchronized void withdraw(int transaction)
    {
        if(this.getBalance() > 0){
            int upperBound = Math.min(100, this.getBalance());
            int withdraw = new Random().nextInt(1, upperBound+1);
            this.balance -= withdraw;
            System.out.printf("%-5s  >>  transaction %-8d %s %5s  balance  =  %5d\n", Thread.currentThread().getName(),
                    transaction, this.getName(),"-" + withdraw, this.getBalance());
        }
        else{
            System.out.printf("%-5s  >>  transaction %-8d %s closed\n", Thread.currentThread().getName(),
                    transaction, this.getName());
        }
    }
}

////////////////////////////////////////////////////////////////////////////////
public class Main {
    public static void main(String[] args) {
        Main mainApp = new Main();
        mainApp.runSimulation();
    }

    public void runSimulation()
    {
        Account [] accounts = { new Account("account A", 0), 
                                new Account(".".repeat(35) + "account B", 0) };   

        Scanner keyboardScan = new Scanner(System.in);
        System.out.printf("%-5s  >>  Enter #threads per bank = \n", Thread.currentThread().getName());
        int num = keyboardScan.nextInt();
        keyboardScan.close();

        Exchanger<Account> exchanger = new Exchanger<>();
        CyclicBarrier barrier        = new CyclicBarrier(num*2);   

        ArrayList<BankThread> allThreads = new ArrayList<>();

        for(int i = 0; i < num; i++){
            allThreads.add(new BankThread("A_"+i, accounts[0]));
            allThreads.add(new BankThread("B_"+i, accounts[1]));
        }

        for(int i = 0; i < allThreads.size(); i++){
            allThreads.get(i).setBarrier(barrier);
            allThreads.get(i).setExchanger((i < 2) ? exchanger : null);
        }

        for(BankThread thread: allThreads)
            thread.start();

        try{
            for(BankThread thread: allThreads)
                thread.join();
            System.out.printf("%-5s  >>  \n",Thread.currentThread().getName());
        }catch (Exception e){
            System.err.println(e);
        }

        for(Account acc: accounts)
            System.out.printf("%-5s  >>  final balance %s = %5d\n",
                    Thread.currentThread().getName(), acc.getName(), acc.getBalance());
    }
}
