package br.com.bertolucci.mtgtools.pngsvgtools.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexCodeValidator {

    public static boolean validate(String str) {
        if (str == null) {
            return false;
        }

        String regex = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();
    }

}
