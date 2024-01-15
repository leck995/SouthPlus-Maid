package net.southplus.southplusmaid.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: AsmrPlayer
 * @description:
 * @author: Leck
 * @create: 2023-03-06 21:48
 */
public class RJUtils {
    public static String getRj(String path){
        path=path.toUpperCase();
        Pattern r = Pattern.compile("RJ\\d+");
        Matcher m = r.matcher(path);
        if (m.find( )) {
            String rj = m.group(0);
            if (rj.substring(2).length() == 7){ //命名不规范的七位数RJ号时
                rj=rj.replace("RJ","RJ0");
            }
            return rj;
        } else {
            return null;
        }
    }

    public static Integer getRjNum(String path){
        path=path.toUpperCase();
        Pattern r = Pattern.compile("RJ\\d+");
        Matcher m = r.matcher(path);
        if (m.find( )) {
            String rj = m.group(0).substring(2);
            if (rj.length() == 7){
                rj="0"+rj;
            }
            return Integer.parseInt(rj);
        } else {
            return null;
        }
    }

    public static String getRjNumString(String path){
        path=path.toUpperCase();
        Pattern r = Pattern.compile("RJ\\d+");
        Matcher m = r.matcher(path);
        if (m.find( )) {
            String rj = m.group(0).substring(2);
            if (rj.length() == 7){
                rj="0"+rj;
            }
            return rj;
        } else {
            return null;
        }
    }
}