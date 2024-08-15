package com.cero.cm.util;

public class ZeroPlusUtil {

    public static String zeroPlus(int i){

        if(i < 10){
            return "0" + i;
        }

        return "" + i;
    }
}
