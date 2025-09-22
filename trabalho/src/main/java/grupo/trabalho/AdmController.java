package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdmController {

    private MainController mainController;

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    @FXML
    private Button cadastrarUsuarioButton;
    @FXML
    private Button listarUsuariosButton;
    @FXML
    private Button gerarRelatoriosButton;
    @FXML
    private Button voltarMenuButton;

    @FXML
    private void voltarMenu() throws IOException {

        mainController.goBackMenu(voltarMenuButton);
    }

}
