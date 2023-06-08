package nl.bos.action;

import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import nl.bos.ControllersRegistry;
import nl.bos.controllers.MainView;

public abstract class AbstractAction extends Task<String> {

    public void execute() {
        MainView mainView = (MainView) ControllersRegistry.get(MainView.class.getSimpleName());

        Thread backgroundThread = new Thread(this);
        backgroundThread.setDaemon(true);
        backgroundThread.start();

        mainView.getTxaMessages().textProperty().bind(this.messageProperty());
        mainView.getBtnExecute().disableProperty().bind(this.stateProperty().isNotEqualTo(Worker.State.SUCCEEDED));
        mainView.getBtnStop().disableProperty().bind(this.stateProperty().isEqualTo(Worker.State.SUCCEEDED));
        mainView.getProgressBar().progressProperty().bind(this.progressProperty());
    }

    public boolean stop() {
        if (!this.isCancelled()) {
            return this.cancel();
        }
        return false;
    }

}
