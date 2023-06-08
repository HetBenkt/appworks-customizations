package nl.bos.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import nl.bos.Configuration;
import nl.bos.ControllersRegistry;
import nl.bos.action.ActionExportTask;
import nl.bos.action.ActionImportTask;
import nl.bos.action.EnumActionTypes;
import nl.bos.message.Log;
import nl.bos.validation.Validator;

public class MainView {
    @FXML
    private Button btnExit;
    @FXML
    private Button btnValidate;
    @FXML
    private Button btnExecute;
    @FXML
    private Button btnStop;
    @FXML
    private TextArea txaMessages;
    @FXML
    private CheckBox optionDryRun;
    @FXML
    private CheckBox optionOnlyMetadata;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label lblBatchNumber;

    private ActionExportTask actionExportTask;
    private ActionImportTask actionImportTask;

    public MainView() {
        ControllersRegistry.put(this.getClass().getSimpleName(), this);
        Log.getInstance().send("MainView Controller");
    }

    @FXML
    private void initialize() {
        Log.getInstance().send("MainView Initialize");
    }

    @FXML
    private void handleExecute() {
        //TODO don't getMode, but getImplementationClass!
        EnumActionTypes mode = Configuration.getInstance().getMode();
        switch (mode) {
            case EXPORT:
                actionExportTask = new ActionExportTask(getTxaMessages().getText());
                actionExportTask.execute();
                MainView mainView = (MainView) ControllersRegistry.get(MainView.class.getSimpleName());
                mainView.getLblBatchNumber().textProperty().bind(actionExportTask.valueProperty());
                break;
            case IMPORT:
                actionImportTask = new ActionImportTask(getTxaMessages().getText());
                actionImportTask.execute();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mode);
        }
    }

    @FXML
    private void handleStop() {
        EnumActionTypes mode = Configuration.getInstance().getMode();
        boolean isStopped;
        switch (mode) {
            case EXPORT:
                isStopped = actionExportTask.stop();
                Log.getInstance().send("EXPORT stopped = " + isStopped);
                break;
            case IMPORT:
                isStopped = actionImportTask.stop();
                Log.getInstance().send("IMPORT stopped = " + isStopped);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mode);
        }
    }

    @FXML
    private void handleExit() {
        //TODO are you sure; also when you are running an action!
        Stage primaryStage = (Stage) btnExit.getScene().getWindow();
        primaryStage.hide(); //or .close()
    }

    @FXML
    private void handleActionExport() {
        Configuration.getInstance().setMode(EnumActionTypes.EXPORT);
    }

    @FXML
    private void handleActionImport() {
        Configuration.getInstance().setMode(EnumActionTypes.IMPORT);
    }

    @FXML
    private void handleOptionDryRun() {
        Configuration.getInstance().setDoDryRun(optionDryRun.isSelected());
    }

    @FXML
    private void handleOptionOnlyMetadata() {
        Configuration.getInstance().setDoDryRun(optionOnlyMetadata.isSelected());
    }

    @FXML
    private void handleValidation() {
        Validator.getInstance().isReady();
    }

    public TextArea getTxaMessages() {
        return txaMessages;
    }

    public Button getBtnExecute() {
        return btnExecute;
    }

    public Button getBtnStop() {
        return btnStop;
    }

    public Button getBtnValidate() {
        return btnValidate;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public Label getLblBatchNumber() {
        return lblBatchNumber;
    }
}
