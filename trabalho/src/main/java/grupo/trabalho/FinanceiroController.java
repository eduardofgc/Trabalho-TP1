package grupo.trabalho;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class FinanceiroController {

    private MainController mainController;

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    @FXML
    Button menuButton;

    @FXML
    private void handleMenuButton() throws IOException {
        mainController.goBackMenu(menuButton);
    }
    @FXML
    public void goBackFinanceiro(Button exitButton) throws IOException {
        Stage prevStage = (Stage) exitButton.getScene().getWindow();
        prevStage.close();

        Stage mainStage = new Stage();
        FXMLLoader loginFXMLLoader = new FXMLLoader(getClass().getResource("/grupo/trabalho/cadastro-view.fxml"));
        Parent loginRoot = loginFXMLLoader.load();

        Scene scene = new Scene(loginRoot);
        mainStage.setTitle("Gestão de RH - Menu Principal");
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }
}
