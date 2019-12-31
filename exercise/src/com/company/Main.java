package com.company;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        HashMap map = new HashMap();
        map.put("1","123");
        map.put("1","456");
        map.put("2","45678");
        System.out.println(map.get("1"));
    }
}
