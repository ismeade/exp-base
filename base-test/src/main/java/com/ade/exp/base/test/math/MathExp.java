package com.ade.exp.base.test.math;

/**
 *
 * Created by liyang on 2017/6/9.
 */
public class MathExp {

    private static int getMin1() {
        return 1 << 31;
    }

    private static int getMin2() {
        return 1 << -1;
    }

    private static int getMax1() {
        return (1 << 31) - 1;
    }

    private static int getMax2() {
        return ~(1 << 31);
    }

    private static int mulTwo(int n) {
        return n << 1;
    }

    // 负数不可用
    private static int divTwo(int n) {
        return n >> 1;
    }

    private static int mulTwoPower(int n,int m){//计算n*(2^m)
        return n << m;
    }

    private static int divTwoPower(int n,int m){//计算n/(2^m)
        return n >> m;
    }

    private static void swap(int a,int b) {
        a ^= b;
        b ^= a;
        a ^= b;

    }

    /* n>>31 取得n的符号，若n为正数，n>>31等于0，若n为负数，n>>31等于-1
    若n为正数 n^0=0,数不变，若n为负数有n^-1 需要计算n和-1的补码，然后进行异或运算，
    结果n变号并且为n的绝对值减1，再减去-1就是绝对值 */
    private static int abs(int n){
        return (n ^ (n >> 31)) - (n >> 31);
    }

    /*如果a>=b,(a-b)>>31为0，否则为-1*/
    private static int max(int a,int b){
        return b & ((a-b) >> 31) | a & (~(a-b) >> 31);
    }

    private static int min(int a,int b){
        return a & ((a-b) >> 31) | b & (~(a-b) >> 31);
    /*如果a>=b,(a-b)>>31为0，否则为-1*/
    }

    private static boolean isSameSign(int x, int y){ //有0的情况例外
        return (x ^ y) >= 0; // true 表示 x和y有相同的符号， false表示x，y有相反的符号。
    }

    private static int getFactorialofTwo(int n){//n > 0
        return 2 << (n-1);//2的n次方
    }

    private static boolean isFactorialofTwo(int n){
        return n > 0 ? (n & (n - 1)) == 0 : false;
    /*如果是2的幂，n一定是100... n-1就是1111....
       所以做与运算结果为0*/
    }

    private static int quyu(int m,int n){//n为2的次方
        return m & (n - 1);
    /*如果是2的幂，n一定是100... n-1就是1111....
     所以做与运算结果保留m在n范围的非0的位*/
    }

    private static int getAverage(int x, int y){
        return ((x ^ y) >> 1) + (x & y);
     /*(x^y) >> 1得到x，y其中一个为1的位并除以2，
       x&y得到x，y都为1的部分，加一起就是平均数了*/

    }

    // 从低位到高位,取n的第m位
    private static int getBit(int n, int m){
        return (n >> (m-1)) & 1;
    }

    // 从低位到高位.将n的第m位 置成1
    private static int setBitToOne(int n, int m){
        return n | (1 << (m-1));
    /*将1左移m-1位找到第m位，得到000...1...000
      n在和这个数做或运算*/
    }

    // 从低位到高位,将n的第m位 置成0
    private static int setBitToZero(int n, int m){
        return n & ~(1 << (m-1));
    /* 将1左移m-1位找到第m位，取反后变成111...0...1111
       n再和这个数做与运算*/
    }


    public static void main(String[] args) {
        int count = 100000000;

        // 使用一个byte(1字节)，内存占用低 效率慢
        byte a = (byte) 0x00;
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < 8; j++) {
                a = (byte) (a | (1 << (j)));
            }
        }
        System.out.println("time:" + (System.currentTimeMillis() - start1));
//        for (int i = 0; i < 8; i++) {
//            System.out.println(getBit(a, i + 1));
//        }

        // 使用数组 8个int 占用 32字节，效率提高将近一倍
        int[] b = new int[8];
        for (int i = 0; i < b.length; i++) {
            b[i] = 0;
        }
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < b.length; j++) {
                b[j] = 1;
            }
        }
        System.out.println("time:" + (System.currentTimeMillis() - start2));


        // 使用一个int(4字节)，内存占用低 效率同byte
        int c = 0;
        long start3 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < 8; j++) {
                c = (c | (1 << (j)));
            }
        }
        System.out.println("time:" + (System.currentTimeMillis() - start3));

        // 使用数组 8个byte 占用 8字节，效率同int
        byte[] d = new byte[8];
        for (int i = 0; i < d.length; i++) {
            d[i] = 0;
        }
        long start4 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < d.length; j++) {
                d[j] = 1;
            }
        }
        System.out.println("time:" + (System.currentTimeMillis() - start4));

    }

}
