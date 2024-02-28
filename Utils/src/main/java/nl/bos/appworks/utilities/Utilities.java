package nl.bos.appworks.utilities;

import com.eibus.util.logger.CordysLogger;
import com.eibus.xml.nom.Node;
import com.eibus.xml.xpath.NodeSet;
import com.eibus.xml.xpath.ResultNode;

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

    /*
    nl.bos.appworks.utilities.Utilities.matches (
        string(ns2:Readsaved_searchOutput/ns2:Readsaved_searchResponse/ns3:saved_search/ns3:ss_reg_ex/text()),
        string(instance:iterator_case)
    )
    */
    public static boolean matches(String regex, String input) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.matches();
    }

    public static boolean matches(String regex, int xmlInput) {
        if(logger.isDebugEnabled()) {
            logger.debug(xmlInput);
            logger.debug(Node.writeToString(xmlInput, true));
        }
        return matches(regex, Node.writeToString(xmlInput, false));
    }

    /*
    nl.bos.appworks.utilities.Utilities.matches (
        string(ns2:Readsaved_searchOutput/ns2:Readsaved_searchResponse/ns3:saved_search/ns3:ss_reg_ex/text()),
        instance:iterator_case
    )
    */
    public static boolean matches(String regex, NodeSet nodeSetInput) {
        StringBuilder input = new StringBuilder();
        while (nodeSetInput.hasNext()) {
            long nextNode = nodeSetInput.next();
            int node = ResultNode.getElementNode(nextNode);
            input.append(Node.writeToString(node, false));
        }
        if(logger.isDebugEnabled()) {
            logger.debug(String.valueOf(input));
        }
        return matches(regex, String.valueOf(input));
    }
}
