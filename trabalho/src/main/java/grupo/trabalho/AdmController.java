package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

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
    private StackPane contentArea;
    @FXML
    private Button activeButton;

    private void loadUI(String fxml) {
        try {
            URL resource = getClass().getResource(fxml);
            System.out.println(">> Tentando carregar: " + resource);

            if (resource == null) {
                throw new IllegalStateException("FXML não encontrado: " + fxml);
            }

            Pane pane = FXMLLoader.load(resource);
            contentArea.getChildren().clear();
            contentArea.getChildren().add(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setActiveButton(Button button) {
        if (activeButton != null) {
            activeButton.setId("buttonLateral");
        }
        button.setId("buttonLateralActive");
        activeButton = button;
    }

    //VIDE CODIGO DC (eu mesmo) PARA FRAME QUE MUDA

    @FXML
    private void voltarMenu() throws IOException {

        mainController.goBackMenu(voltarMenuButton);
    }

}
