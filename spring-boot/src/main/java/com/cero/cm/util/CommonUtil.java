package com.cero.cm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

    private final Logger logger = LoggerFactory.getLogger(CommonUtil.class);


    /**
     * 친구추천 코드
     *  나올 수 있는 갯수: 26 * 26 * 26 * 26 * 26 * 26 * 26 * 26
     * @return 대문자로 이루어진 8 자리 코드
     */
    public static String inviteCode() {
        // 26 * 26 * 26 * 26 * 26 * 26 * 26 * 26
        Random rnd = new Random();
        StringBuffer buf = new StringBuffer();
        for( int i=0 ; i < 8 ; i++ ) {
            buf.append((char)(rnd.nextInt(26)+65));   // 0~25(26개) + 65
        }
        return buf.toString();
    }

    /**
     * 패스워드 패턴 검증
     * @param pw
     * @return true or false
     */
    public static boolean passwordValid(String pw) {
        // 문자, 숫자, 특수문자 필수
//        Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%])[A-Za-z\\d~!@#$%]{8,16}$");
        // (문자, 숫자 필수) + 특수문자 가능
        Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%]{8,16}$");
        Matcher matcher = pattern.matcher(pw.trim());
        if(!matcher.find()) {
            return false;
        }
        return true;
    }


    /**
     * 8자리 랜덤 패스워드
     * @return
     */
    public static String randomPassword() {
        char[] charNumberSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
        };
        char[] charTextSet = new char[] {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'
                ,'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T'
                ,'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd'
                ,'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n'
                ,'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x'
                , 'y', 'z'
        };
        char[] charSpecialSet = new char[] {
                '!', '@', '#', '$', '%', '^', '&'
        };
        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());
        int idx = 0;
        int numberLen = charNumberSet.length;
        int textLen = charTextSet.length;
        int specialLen = charSpecialSet.length;
        char val ;
        for (int i = 0; i < 8; i++) {
//			 idx = (int) (len * Math.random());
            if(i < 3) {
                idx = sr.nextInt(textLen);
                val = charTextSet[idx];
            } else if(i < 6) {
                idx = sr.nextInt(numberLen);
                val = charNumberSet[idx];
            } else {
                idx = sr.nextInt(specialLen);
                val = charSpecialSet[idx];
            }
            sb.append(val);
        }
        return sb.toString();
    }


    /**
     * 패스워드 인코딩 -> sha256 + md5
     * @param pw
     * @return
     */
    public static String encodePw(String pw) {
        String encodePw = sha256(pw);
        encodePw = md5(encodePw);
        return encodePw;
    }

    /**
     * 테스트용
     * @param pw
     * @return
     */
    public static String sha256Pw(String pw) {
        return sha256(pw);
    }


    /**
     * sha 256 인코딩
     * @param pw
     * @return
     */
    private static String sha256(String pw) {
        String encodePassword = null;
        try {

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(pw.getBytes(StandardCharsets.UTF_8));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                //ff = 1111 1111
                if(hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            encodePassword = hexString.toString();
        } catch ( Exception e ) {

        }

        return encodePassword;
    }

    private static String md5(String pw) {
        String md5 = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pw.getBytes());
            byte[] byteData = md.digest();
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            md5 = sb.toString();
        } catch ( Exception e ) {
        }

        return md5;
    }


}
