package nl.bos.components.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.logging.Logger;


@ShellComponent
public class Connect {
    Logger log = Logger.getLogger(Connect.class.getName());

    @ShellMethod(value = "Connect to OpenText cloud")
    public void login(@ShellOption(value = {"-H", "--host"}) String hostname) {
        log.info(String.format("Hostname = %s", hostname));
    }

}
