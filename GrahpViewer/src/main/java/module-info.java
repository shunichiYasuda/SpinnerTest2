module com.GenSci.tools.GrahpViewer {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    opens com.GenSci.tools.GrahpViewer to javafx.fxml;
    exports com.GenSci.tools.GrahpViewer;
}
