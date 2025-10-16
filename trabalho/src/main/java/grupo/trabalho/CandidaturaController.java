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

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    @FXML
    private Button cadastrarCandidatosButton;
    @FXML
    private Button listarCandidatosButton;
    @FXML
    private Button statusDaCandidaturaButton;
    @FXML
    Button menuButton;

    @FXML
    private void goToCadastrar() throws IOException {
        Stage prevStage = (Stage) cadastrarCandidatosButton.getScene().getWindow();
        prevStage.close();

        Stage cadastrarStage = new Stage();
        FXMLLoader cadastrarFXMLLoader = new FXMLLoader(getClass().getResource("/grupo/trabalho/cadastrar-view.fxml"));
        Parent cadastrarRoot = cadastrarFXMLLoader.load();

        CadastrarController cadastrarController = cadastrarFXMLLoader.getController();
        cadastrarController.setCandidaturaController(this);

        Scene scene = new Scene(cadastrarRoot);
        cadastrarStage.setTitle("Cadastrar Candidato");
        cadastrarStage.setScene(scene);
        cadastrarStage.setResizable(false);
        cadastrarStage.show();
    }
    @FXML
    private void goToListar() throws IOException {
        Stage prevStage = (Stage) listarCandidatosButton.getScene().getWindow();
        prevStage.close();

        Stage listarStage = new Stage();
        FXMLLoader listarFXMLLoader = new FXMLLoader(getClass().getResource("/grupo/trabalho/listar-view.fxml"));
        Parent listarRoot = listarFXMLLoader.load();

        ListarController listarController = listarFXMLLoader.getController();
        listarController.setCandidaturaController(this);

        Scene scene = new Scene(listarRoot);
        listarStage.setTitle("Listar");
        listarStage.setScene(scene);
        listarStage.setResizable(false);
        listarStage.show();
    }
    @FXML
    private void goToStatus() throws IOException {
        Stage prevStage = (Stage) statusDaCandidaturaButton.getScene().getWindow();
        prevStage.close();

        Stage statusStage = new Stage();
        FXMLLoader statusFXMLLoader = new FXMLLoader(getClass().getResource("/grupo/trabalho/status-view.fxml"));
        Parent statusRoot = statusFXMLLoader.load();

        StatusController statusController = statusFXMLLoader.getController();
        statusController.setCandidaturaController(this);

        Scene scene = new Scene(statusRoot);
        statusStage.setTitle("Listar");
        statusStage.setScene(scene);
        statusStage.setResizable(false);
        statusStage.show();
    }
    @FXML
    private void handleMenuButton() throws IOException {
        mainController.goBackMenu(menuButton);
    }


}
