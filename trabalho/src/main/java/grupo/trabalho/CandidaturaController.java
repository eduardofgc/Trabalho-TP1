package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class CandidaturaController {

    @FXML
    private Button cadastrarCandidatosButton;
    @FXML
    private Button listarCandidatosButton;
    @FXML
    private Button statusDaCandidaturaButton;
    @FXML
    private Button menuButton;

    @FXML
    private void goToCadastrar() throws IOException {
        Stage stage = (Stage) cadastrarCandidatosButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/grupo/trabalho/cadastrar-controller.fxml"));
        stage.setScene(new Scene(root));
    }

    @FXML
    private void goToListar() throws IOException {
        Stage stage = (Stage) listarCandidatosButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/grupo/trabalho/listar.fxml"));
        stage.setScene(new Scene(root));
    }

    @FXML
    private void goToStatus() throws IOException {
        Stage stage = (Stage) statusDaCandidaturaButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/grupo/trabalho/status.fxml"));
        stage.setScene(new Scene(root));
    }

    @FXML
    private void handleMenuButton() throws IOException {
        Stage stage = (Stage) menuButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/grupo/trabalho/second-view.fxml"));
        stage.setScene(new Scene(root));
    }
}