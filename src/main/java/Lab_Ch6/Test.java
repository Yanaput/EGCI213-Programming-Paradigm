package Lab_Ch6;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class  InvalidInputException extends Exception{
    public InvalidInputException(String message) {super(message);}
}

abstract class ParentClass {
    protected String name;
    public ParentClass(String name){
        this.name = name;
    }
    abstract void print();
     void print(int n){
        System.out.println("Name : " + this.name + n);
    }
}

class ChildClass extends ParentClass {
    private int num;
    public ChildClass(String name){
        super(name);
    }
    public ChildClass(String name, int num){
        this(name);
        this.num = num;
    }

    @Override
    void print() {
        System.out.println(this.name + this.num);
    }
}

class GrandChildClass extends ChildClass{
    private int num;
    public GrandChildClass(String name, int num) {
        super(name, num);
    }
    @Override
    void print(){
        System.out.println("Name Child: " + this.name);
    }
}

interface Shape {
    static void funcStatic(){
        System.out.println("funcStatic");
    }

    int num = 20;
    default void func1(){
        System.out.println("HI");
    }
    void func2();
}

class Square implements Shape {
    static void funcStatic(){
        System.out.println("funcStatic");
    }
    private int width;
    public int getWidth(){return this.width;}
    public Square(int width){
        this.width = width + num;
    }
    @Override
    public void func1(){
        System.out.println("func1");
    }
    @Override
    public void func2(){
        System.out.println(this.width);
    }
}

class OrderTest implements Comparable<OrderTest>{
    private int x;
    public int getX(){return this.x;}
    public OrderTest(int x) throws InvalidInputException{
        if(x<0)
            throw new InvalidInputException("x<0 nigger");
        this.x = x;
    }
    @Override
    public int compareTo(OrderTest other) {
        //return Integer.compare(this.x, other.x);
        //ascending order
        if(this.x < other.x)    return -1;
        else if(this.x > other.x)    return 1;
        else return 0;
    }
}

class KeyValue<T,E extends Number> {

    private T key;
    private E value;

    public KeyValue(T key, E value){
        this.key = key;
        this.value = value;
    }

    public T getKey() {
        return key;
    }
    public void setKey(T key) {
        this.key = key;
    }
    public E getValue() {
        return value;
    }
    public void setValue(E value) {
        this.value = value;
    }

}

class KeyValueName <T, E extends Number> extends KeyValue{
    KeyValueName(T key, E value){
        super(key, value);
    }
}


public class Test{
    public static void main(String[] args) {
        ArrayList<ParentClass> test = new ArrayList<>();
        test.add(new ChildClass("1"));
        test.add(new ChildClass("2",2));

        for(Object i : test)
            if(i instanceof ChildClass)
                ((ChildClass)i).print(6);
//        Shape.funcStatic();
//        KeyValueName<String, Double> kv = new KeyValueName<>("name", 0.321312);
//
//        ArrayList<String> nig = new ArrayList<>();
//        nig.add("niger");
//        if(nig.contains("niger"))
//            System.out.println("ger");
//        System.out.println(kv.getKey());
//        System.out.println(kv.getValue());


    }
}
