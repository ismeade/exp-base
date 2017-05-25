package com.ade.exp.base.test.math;

/**
 *
 * Created by liyang on 2017/5/25.
 */
public class Shift {

    public static void main(String[] args) {
        // 乘除 a * (2 ^ n) == a << n
        System.out.println(5 << 2);
        System.out.println(5 << 3);
        System.out.println(5 << 4);
        System.out.println(5 << 1);

        // 除法 a / (2 ^ n) == a >> n
        System.out.println(5 >> 2);

        // 奇数偶数判断 0=偶数 1=奇数
        System.out.println(1234 & 1);
        System.out.println(1233 & 1);

        // 取平均值
        int x = 5;
        int y = 9;
        System.out.println((x & y) + ((x ^ y) >> 1));

        // 交换整数
        x ^= y;
        y ^= x;
        x ^= y;
        System.out.println(x + " " + y);

        // 余运算 a % 2 == a & 1
        System.out.println(10 & 1);

    }

}
