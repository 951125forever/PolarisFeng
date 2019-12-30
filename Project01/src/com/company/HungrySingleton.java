package com.company;

import javax.sound.midi.Soundbank;

public class HungrySingleton {
    private static final HungrySingleton b=new HungrySingleton();
    private HungrySingleton(){}
    public static HungrySingleton  getB(){
        return b;
    }

    public static void main(String[] args) {
        //getB();
        System.out.println(b);
    }
}
