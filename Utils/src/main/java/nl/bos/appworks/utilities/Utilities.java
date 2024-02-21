package nl.bos.appworks.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {
    private Utilities() throws InstantiationException {
        throw new InstantiationException("Not instantiatable utilities class");
    }

    public static boolean matches(String regex, String input) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.matches();
    }
}
