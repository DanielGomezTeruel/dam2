module org.f4_3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.f4_3 to javafx.fxml;
    exports org.f4_3;
}