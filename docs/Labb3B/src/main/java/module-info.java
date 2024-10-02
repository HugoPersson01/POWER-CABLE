module se.kth.adamfhhugoper.labb3b {
    requires javafx.controls;
    requires javafx.fxml;


    opens se.kth.adamfhhugoper.labb3b to javafx.fxml;
    exports se.kth.adamfhhugoper.labb3b;
    exports se.kth.adamfhhugoper.labb3b.model;
    opens se.kth.adamfhhugoper.labb3b.model to javafx.fxml;
    exports se.kth.adamfhhugoper.labb3b.model.Exceptions;
    opens se.kth.adamfhhugoper.labb3b.model.Exceptions to javafx.fxml;
}