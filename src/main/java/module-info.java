module com.example.c482part2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.c482part2 to javafx.fxml;
    exports com.example.c482part2;
    opens com.example.c482part2.model to javafx.fxml;
    exports com.example.c482part2.model;

}
