package nl.bos.validation;

import javafx.concurrent.Task;
import nl.bos.Configuration;
import nl.bos.ControllersRegistry;
import nl.bos.Storage;
import nl.bos.controllers.MainView;
import nl.bos.exceptions.MyRuntimeException;
import nl.bos.mapper.Mapper;
import nl.bos.message.Confirm;
import nl.bos.message.Log;
import nl.bos.message.Mail;
import nl.bos.message.Monitor;
import nl.bos.service.Service;
import org.apache.commons.configuration.ConfigurationException;

import java.io.IOException;

public class ValidationTask extends Task<Object> {
    private final StringBuilder message = new StringBuilder();

    @Override
    protected Object call() throws IOException, NoSuchFieldException, ConfigurationException {
        IValidator configuration = Configuration.getInstance();
        IValidator service = Service.getInstance();
        IValidator mapper = Mapper.getInstance();
        IValidator storage = Storage.getInstance();

        IValidator mail = Mail.getInstance();
        IValidator monitor = Monitor.getInstance();
        IValidator confirm = Confirm.getInstance();
        IValidator log = Log.getInstance();

        updateMessages("App starting and running validations...");

        try {
            configuration.isReady();
            updateMessages("\tConfiguration is ready.");
            service.isReady();
            updateMessages("\tService is ready.");
            mapper.isReady();
            updateMessages("\tMapper is ready.");
            log.isReady();
            updateMessages("\tLogging is ready.");
            storage.isReady();
            updateMessages("\tStorage is ready.");
            mail.isReady();
            updateMessages("\tMail is ready.");
            monitor.isReady();
            updateMessages("\tMonitor is ready.");
            confirm.isReady();
            updateMessages("\tConfirmation is ready.");
        } catch (Exception e) {
            updateMessages("\t" + e.getMessage());
            throw new MyRuntimeException();
        }

        return null; //TODO send back correct message!
    }

    @Override
    protected void cancelled() {
        super.cancelled();
        updateMessages("Validation was cancelled.");
    }

    @Override
    protected void failed() {
        super.failed();
        updateMessages("Validation not ready.");
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        updateMessages("Validation done.");
        MainView mainView = (MainView) ControllersRegistry.get(MainView.class.getSimpleName());
        mainView.getTxaMessages().textProperty().unbind();
        mainView.getBtnExecute().disableProperty().unbind();
    }

    private void updateMessages(String text) {
        message.insert(0, System.lineSeparator());
        message.insert(0, text);
        updateMessage(String.valueOf(message));
    }
}
