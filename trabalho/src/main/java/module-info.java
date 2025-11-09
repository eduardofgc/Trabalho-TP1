module grupo.trabalho {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.desktop;



    opens grupo.trabalho to javafx.fxml;
    exports grupo.trabalho;
}
