module com.cptgummiball.lifepixelz {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.cptgummiball.lifepixelz.lifepixelz to javafx.fxml;
    exports com.cptgummiball.lifepixelz.lifepixelz;
}
