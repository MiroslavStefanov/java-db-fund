package Phony;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Caller {
    default void Call(String number)
    {
        String sPattern = ".*[^0-9].*";
        Pattern pattern = Pattern.compile(sPattern);
        Matcher m = pattern.matcher(number);
        if(m.matches())
            System.out.println("Invalid number!");
        else
            System.out.println("Calling... " + number);
    }
}
