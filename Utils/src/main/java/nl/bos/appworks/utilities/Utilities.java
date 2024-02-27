package nl.bos.appworks.utilities;

import com.eibus.util.logger.CordysLogger;
import com.eibus.xml.nom.Node;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {
    private static final CordysLogger logger = CordysLogger.getCordysLogger(Utilities.class);

    private Utilities() throws InstantiationException {
        throw new InstantiationException("Not instantiatable utilities class");
    }

    public static String getHelloString(String arg) {
        return "Hello " + arg;
    }

    public static boolean matches(String regex, String input) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.matches();
    }

    public static boolean matchesXml(String regex, int xmlInput) {
        if(logger.isDebugEnabled()) {
            logger.debug(xmlInput);
            logger.debug(Node.writeToString(xmlInput, true));
        }
        return matches(regex, Node.writeToString(xmlInput, false));
    }
}
