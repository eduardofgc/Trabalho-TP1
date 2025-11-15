package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class RecrutamentoController {

    @FXML
    public Button listarVagasButton;
    @FXML
    public Button cadastrarVagasButton;
    @FXML
    public Button agendarEntrevistaButton;
    @FXML
    public Button menuButton;

    private MainController mainController;

    @FXML
    private AnchorPane contentArea;

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    @FXML
    private void voltarMenu() {
        try {
            mainController.goBackMenu(menuButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showCadastrarVagas() {
        if (HelloController.currentUser.isGestor){
            loadSubView("/grupo/trabalho/cadastrarVaga-view.fxml");
        }
        else{
            AlertHelper.showInfo("Erro: você não tem permissão de gestor.");
        }
    }

    @FXML
    private void showListarVagas(){
        if (HelloController.currentUser.isGestor || HelloController.currentUser.isRecrutador){
            loadSubView("/grupo/trabalho/listarVagas-view.fxml");
        }
        else{
            AlertHelper.showInfo("Erro: você não tem permissão de gestor ou recrutador.");
        }
    }

    @FXML
    private void showAgendarEntrevista() {

        if (HelloController.currentUser.isRecrutador){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/grupo/trabalho/agendarEntrevista-view.fxml"));
                Parent view = loader.load();

                EntrevistaController controller = loader.getController();
                controller.setRecrutamentoController(this);

                contentArea.getChildren().clear();
                contentArea.getChildren().add(view);
                AnchorPane.setTopAnchor(view, 0.0);
                AnchorPane.setBottomAnchor(view, 0.0);
                AnchorPane.setLeftAnchor(view, 0.0);
                AnchorPane.setRightAnchor(view, 0.0);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            AlertHelper.showInfo("Erro: você não tem permissão de recrutador.");
        }

    }

    private void loadSubView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();

            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);
            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
