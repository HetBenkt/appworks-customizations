package nl.bos.action;

import nl.bos.Configuration;
import nl.bos.ControllersRegistry;
import nl.bos.Processor;
import nl.bos.controllers.MainView;
import nl.bos.item.IItem;
import nl.bos.service.Service;
import org.apache.commons.configuration.ConfigurationException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class ActionExportTask extends AbstractAction {

    private final StringBuilder message = new StringBuilder();
    private final String startMessage;

    public ActionExportTask(String startMessage) {
        super();
        this.startMessage = startMessage;
    }

    @Override
    protected String call() throws NoSuchFieldException, XPathExpressionException, SOAPException, IOException, ConfigurationException, TransformerException, ParserConfigurationException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String runId = dtf.format(LocalDateTime.now());

        updateMessages(startMessage);
        updateMessages("Export started");
        updateProgress(0, 0);

        int batchSize = Integer.parseInt(Configuration.getInstance().getValue("batch_size"));
        List<IItem> entities = Service.getInstance().getEntityInstances();
        int totalNrOfBatches = (int) Math.ceil((double) entities.size() / (double) batchSize);

        if (totalNrOfBatches > 0) {
            for (int batchNr = 1; batchNr <= totalNrOfBatches; batchNr++) {
                updateProgress(batchNr, totalNrOfBatches);
                updateValue(String.format("Batch #: %s of %s", batchNr, totalNrOfBatches));
                IProcessor processor = new Processor();
                processor.execute(entities, runId);
            }
        } else {
            updateProgress(0, 0);
            return "No full batch to process!";
        }

        return "Run complete!";
    }

    @Override
    protected void cancelled() {
        super.cancelled();
        updateMessages("Export was cancelled.");
    }

    @Override
    protected void failed() {
        super.failed();
        updateMessages("Export failed.");
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        updateMessages("Export done.");

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
