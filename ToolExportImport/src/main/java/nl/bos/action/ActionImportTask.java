package nl.bos.action;

import nl.bos.ControllersRegistry;
import nl.bos.controllers.MainView;

public class ActionImportTask extends AbstractAction {
    private final StringBuilder message = new StringBuilder();
    private final String startMessage;

    public ActionImportTask(String startMessage) {
        super();
        this.startMessage = startMessage;
    }

    @Override
    protected String call() throws InterruptedException {
        updateMessages(startMessage);
        updateMessages("Import started");

        //TODO here we will get some smart logic to do!

        for (int i = 0; i < 100; i++) {
            updateProgress(i, 100);
            Thread.sleep(10);
        }

        return null;
    }

    @Override
    protected void cancelled() {
        super.cancelled();
        updateMessages("Import was cancelled.");
    }

    @Override
    protected void failed() {
        super.failed();
        updateMessages("Import failed.");
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        updateMessages("Import done.");

        MainView mainView = (MainView) ControllersRegistry.get(MainView.class.getSimpleName());
        mainView.getTxaMessages().textProperty().unbind();
        mainView.getBtnExecute().disableProperty().unbind();
        mainView.getBtnStop().disableProperty().unbind();
    }

    private void updateMessages(String text) {
        message.insert(0, System.lineSeparator());
        message.insert(0, text);
        updateMessage(String.valueOf(message));
    }
}
