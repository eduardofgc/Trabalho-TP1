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
    Button cadastrarCandidatosButton;
    @FXML
    Button listarCandidatosButton;
    @FXML
    Button statusDaCandidaturaButton;
    @FXML
    Button menuButton;

    @FXML
    private void handleMenuButton() throws IOException {
        mainController.goBackMenu(menuButton);
    }


}
