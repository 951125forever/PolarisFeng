package com.company;

import java.beans.Transient;
import java.util.HashMap;

public class Main {
    @Transient
    public static void main(String[] args) {

        HashMap map = new HashMap();
        map.put("1","123");
        map.put("1","456");
        map.put("2","45678");
        System.out.println(map.get("1"));
    }
}
