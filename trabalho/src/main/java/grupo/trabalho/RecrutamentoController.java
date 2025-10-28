package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class RecrutamentoController {

    @FXML
    public Button menuButton;
    @FXML
    public Button cadastrarVagasButton;
    @FXML
    public Button listarVagasButton;
    @FXML
    public Button agendarEntrevistaButton;
    @FXML
    public AnchorPane contentArea;
    @FXML
    private Button activeButton;

    private MainController mainController;

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    @FXML
    private void voltarMenu() throws IOException {
        mainController.goBackMenu(menuButton);
    }

    public Object loadUI(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            AnchorPane pane = loader.load();

            contentArea.getChildren().clear();
            contentArea.getChildren().add(pane);

            return loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setActiveButton(Button button){
        if (activeButton != null){
            activeButton.setId("buttonLateral");
            activeButton = button;
        }

        button.setId("buttonLateralActive");
        activeButton = button;
    }

    @FXML
    public void showCadastrarVagas(){
        loadUI("/grupo/trabalho/cadastrarVaga-view.fxml");
        setActiveButton(cadastrarVagasButton);
    }

    @FXML
    public void showListarVagas(){
        loadUI("/grupo/trabalho/listarVagas-view.fxml");
        setActiveButton(listarVagasButton);
    }

    @FXML
    public void showAgendarEntrevista(){
        loadUI(""); //TODO: add agendarEntrevista fxml
        setActiveButton(agendarEntrevistaButton);
    }
}