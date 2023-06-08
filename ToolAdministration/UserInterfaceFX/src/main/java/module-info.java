module nl.bos.userinterfacefx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;

    opens nl.bos.userinterfacefx to javafx.fxml;
    exports nl.bos.userinterfacefx;
}