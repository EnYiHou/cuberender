module computer.graphic {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    
    
    opens computer.graphic to javafx.fxml;
    exports computer.graphic;
}
