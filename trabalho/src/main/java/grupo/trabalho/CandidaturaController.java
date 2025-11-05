package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class CandidaturaController {
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grupo/trabalho/cadastrar-controller.fxml"));
        Parent root = loader.load();

        CadastrarController controller = loader.getController();
        controller.setCandidaturaController(this);

        stage.setScene(new Scene(root));
        stage.setTitle("Cadastrar Candidato");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void goToListar() throws IOException {
        Stage stage = (Stage) listarCandidatosButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grupo/trabalho/listar.fxml"));
        Parent root = loader.load();

        // ❌ Removido: controller.setCandidaturaController(this);

        stage.setScene(new Scene(root));
        stage.setTitle("Listar Candidatos");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void goToStatus() throws IOException {
        Stage stage = (Stage) statusDaCandidaturaButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grupo/trabalho/status.fxml"));
        Parent root = loader.load();

        StatusController controller = loader.getController();
        controller.setCandidaturaController(this);

        stage.setScene(new Scene(root));
        stage.setTitle("Status da Candidatura");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void handleMenuButton() throws IOException {
        if (mainController != null) {
            mainController.goBackMenu(menuButton);
        }
    }
}