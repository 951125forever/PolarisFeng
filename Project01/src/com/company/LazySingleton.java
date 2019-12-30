package com.company;


public class LazySingleton {
    private static volatile LazySingleton a=null;
    private LazySingleton(){}
    public static synchronized LazySingleton getA(){
        if(a==null){
            a=new LazySingleton();
        }
        return a;
    }
    public static void main(String[] args) {
        getA();
        System.out.println(a);
    }
}
