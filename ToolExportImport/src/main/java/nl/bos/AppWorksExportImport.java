package nl.bos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.bos.validation.Validator;

import java.io.IOException;

public class AppWorksExportImport extends Application {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new HandleShutdown());
        launch(args);
    }

    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main_view.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setTitle("AppWorks Export/Import");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        Validator validator = Validator.getInstance();
        validator.isReady();
    }
}
