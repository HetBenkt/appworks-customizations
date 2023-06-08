package nl.bos;

import nl.bos.auth.Authentication;
import nl.bos.auth.AuthenticationImpl;
import nl.bos.awp.AppWorksPlatform;
import nl.bos.awp.AppWorksPlatformImpl;
import nl.bos.config.Configuration;
import nl.bos.config.ConfigurationImpl;
import nl.bos.exception.GeneralAppException;

import java.io.Console;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Base64;
import java.util.Scanner;
import java.util.logging.Logger;

public class ConsoleUI {

    private static final Logger logger = Logger.getLogger(ConsoleUI.class.getName());


    public static void main(String[] args) throws IOException {
        Configuration config = new ConfigurationImpl("config.properties");
        AppWorksPlatform awp = AppWorksPlatformImpl.getInstance(config);

        if(!awp.ping()) {
            System.out.println("Platform not available! Check your URL in the config.properties");
        }
        else {

            System.out.println("""
                    Welcome to the CLI for AppWorks; These are the valid inputs to use:
                        0 -> Exit
                        1 -> Login
                        2 -> Get organizations
                    """);
            Scanner sc = new Scanner(System.in);
            String input = "";
            do {
                input = sc.nextLine();

                //Positive and negative numbers
                if (!input.matches("^-?\\d*\\.{0,1}\\d+$")) {
                    System.out.println("Invalid option!");
                } else {
                    switch (Integer.parseInt(input)) {
                        case 0 -> doExit(sc);
                        case 1 -> doLogin(sc);
                        case 2 -> getOrganization(sc);
                        default -> noMethod();
                    }
                }
            } while (!input.equals("0"));
            sc.close();
        }
    }

    private static void doExit(Scanner sc) {
        System.out.println("doExit");
    }

    private static void doLogin(Scanner sc) {
        System.out.println("doLogin");
        boolean isOtds = false;
        String resourceId = "n/a";

        System.out.print("Is OTDS [y|n]? ");
        if(sc.nextLine().equalsIgnoreCase("y")) {
            isOtds = true;
            System.out.print("Provide OTDS resource ID: ");
            resourceId = sc.nextLine();
        }
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        char[] password = readPassword(sc);

        String message = MessageFormat.format("Username: {0}, Password: {1}, isOtds: {2}, resourceId: {3}",
                username,
                Base64.getEncoder().encodeToString(String.valueOf(password).getBytes()),
                isOtds,
                resourceId);
        logger.finest(message);

        Authentication authentication = AuthenticationImpl.getInstance();
        if(isOtds) {
            authentication.enableOtds();
        }
        try {
            authentication.getSamlArtifactId();
        } catch (IOException e) {
            throw new GeneralAppException(e);
        }
    }

    private static char[] readPassword(Scanner sc) {
        Console console = System.console();
        if(System.console() != null) {
            return console.readPassword();
        }
        return sc.nextLine().toCharArray();
    }

    private static void getOrganization(Scanner sc) {
        System.out.println("getOrganization");
    }

    private static void noMethod() {
        System.out.println("No method available!");
    }
}
