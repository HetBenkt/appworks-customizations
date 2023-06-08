package nl.bos;

import com.vaadin.flow.component.page.AppShellConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VaadinApp implements AppShellConfigurator {
    public static void main(String[] args) {
        SpringApplication.run(VaadinApp.class, args);
    }
}
