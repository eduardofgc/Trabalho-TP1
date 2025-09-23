package grupo.trabalho;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class RecrutamentoController {

    @FXML
    Button menuButton;

    private MainController mainController;

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    @FXML
    private void voltarMenu() throws IOException {
        mainController.goBackMenu(menuButton);
    }


}