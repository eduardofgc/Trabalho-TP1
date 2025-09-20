package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HelloController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private void handleLogin() {
        Stage prevStage = (Stage) loginButton.getScene().getWindow();
        prevStage.close();



    }
}
