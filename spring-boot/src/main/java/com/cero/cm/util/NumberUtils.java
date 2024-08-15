package com.cero.cm.util;

import java.text.DecimalFormat;

/**
 * 숫자 Util 공용 클래스
 */
public class NumberUtils {


  // 숫자-> 천단위 콤마 찍기
  public static String comma(int value) {
    DecimalFormat df = new DecimalFormat("###,###");
    return df.format(value);
  }

  public static String comma(double value) {
    DecimalFormat df = new DecimalFormat("###,###");
    return df.format(value);
  }

  // 숫자-> 천단위 콤마 찍기
  public static String comma(String value) {
    DecimalFormat df = new DecimalFormat("###,###");
    return df.format(Integer.parseInt(value));
  }
  
}
