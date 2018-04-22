package Phony;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Browser {
    default void Browse(String URL)
    {
        String sPattern = ".*[0-9].*";
        Pattern pattern = Pattern.compile(sPattern);
        Matcher m = pattern.matcher(URL);
        if(m.matches())
            System.out.println("Invalid URL!");
        else
            System.out.println("Browsing: " + URL + "!");
    }
}
