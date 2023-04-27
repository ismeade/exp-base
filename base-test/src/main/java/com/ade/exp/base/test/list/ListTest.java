package com.ade.exp.base.test.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListTest {

    private static void testAsList() {
        List<Integer> list = Arrays.asList(1,2,3);
        list = new ArrayList<>(list);
        list.add(4);
        list.forEach(System.out::println);
    }

    public static void main(String[] args) {
//        testAsList();
        System.out.println(-1 << 8);
    }
}
