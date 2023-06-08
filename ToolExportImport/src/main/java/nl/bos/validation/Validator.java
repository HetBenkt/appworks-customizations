package nl.bos.validation;

import javafx.concurrent.Worker;
import nl.bos.ControllersRegistry;
import nl.bos.controllers.MainView;

public class Validator implements IValidator {
    private static Validator instance;

    private Validator() {

    }

    public static Validator getInstance() {
        if (instance == null) {
            instance = new Validator();
        }
        return instance;
    }

    @Override
    public boolean isReady() {
        ValidationTask task = new ValidationTask();
        Thread backgroundThread = new Thread(task);
        backgroundThread.setDaemon(true);
        backgroundThread.start();

        MainView mainView = (MainView) ControllersRegistry.get(MainView.class.getSimpleName());
        mainView.getTxaMessages().textProperty().bind(task.messageProperty());
        mainView.getBtnExecute().disableProperty().bind(task.stateProperty().isNotEqualTo(Worker.State.SUCCEEDED));
        mainView.getBtnValidate().disableProperty().bind(task.stateProperty().isNotEqualTo(Worker.State.FAILED));
        return true;
    }
}
