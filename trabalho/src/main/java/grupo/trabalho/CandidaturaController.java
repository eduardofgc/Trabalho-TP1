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
    private Button cadastrarCandidatosButton,cadafunc,menuButton,statusDaCandidaturaButton,listarCandidatosButton;

    private MainController mainController;
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    @FXML
    private void goToCadaFunc() throws IOException {
        Stage stage = (Stage) cadafunc.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grupo/trabalho/cadastroFunc-view.fxml"));
        Parent root = loader.load();

        CadastroFunc controller = loader.getController();
        controller.setCandidaturaController(this);

        stage.setScene(new Scene(root));
        stage.setTitle("Cadastrar Funcionário");
        stage.setResizable(false);
        stage.show();
    }
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
        if (mainController != null) {
            mainController.goBackMenu(menuButton);
        } else {
            System.err.println("MainController não foi configurado!");
        }
    }
}