package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
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
        stage.setTitle("Cadastro de Candidatos");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void goToListar() throws IOException {
        Stage stage = (Stage) listarCandidatosButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/grupo/trabalho/listar.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Listar Candidatos");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void goToStatus() throws IOException {
        Stage stage = (Stage) statusDaCandidaturaButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/grupo/trabalho/status.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Status de Candidatos");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void handleMenuButton() throws IOException {
        Stage prevStage = (Stage) menuButton.getScene().getWindow();
        prevStage.close();

        Stage mainStage = new Stage();
        FXMLLoader loginFXMLLoader = new FXMLLoader(getClass().getResource("/grupo/trabalho/second-view.fxml"));
        Parent loginRoot = loginFXMLLoader.load();
        mainStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo trabalho tp.png")));
        Scene scene = new Scene(loginRoot);
        mainStage.setTitle("Gestão de RH - Menu Principal");
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }
}