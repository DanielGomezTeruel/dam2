module org.example.f4_4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.f4_4 to javafx.fxml;
    exports org.example.f4_4;
}