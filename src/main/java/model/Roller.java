package model;

import java.util.Random;

public class Roller {

    public static int d20(){
        return (new Random()).nextInt(20) + 1;
    }

    public static int d12(){
        return (new Random()).nextInt(12) + 1;
    }

    public static int d10(){
        return (new Random()).nextInt(10) + 1;
    }

    public static int d8(){
        return (new Random()).nextInt(8) + 1;
    }

    public static int d6(){
        return (new Random()).nextInt(6) + 1;
    }
    public static int d4(){
        return (new Random()).nextInt(4) + 1;
    }


}
