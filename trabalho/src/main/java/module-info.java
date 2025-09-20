module grupo.trabalho {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    opens grupo.trabalho to javafx.fxml;   // allow FXML reflection
    exports grupo.trabalho;                // export package to other modules
}
