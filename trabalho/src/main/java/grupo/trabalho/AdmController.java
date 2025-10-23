package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class AdmController {

    private MainController mainController;
    public BorderPane root;

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
    private AnchorPane contentArea;
    @FXML
    private Button activeButton;

    @FXML
    private void voltarMenu() throws IOException {

        mainController.goBackMenu(voltarMenuButton);
    }

    //VIDE CODIGO DC (eu mesmo) PARA FRAME QUE MUDA
    public void loadUI(String fxml){
        try{
            URL resource = getClass().getResource(fxml);

            if (resource == null){
                throw new IllegalStateException("state não encontrado: " + fxml);
            }

            AnchorPane pane = FXMLLoader.load(resource);
            contentArea.getChildren().clear();
            contentArea.getChildren().add(pane);

        } catch (Exception e){
            e.printStackTrace();
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
    public void showCadastrarUsuario(){
        loadUI("/grupo/trabalho/cadastrarUsuario-view.fxml"); //fxml da pagina do cadastrar usuario aqui
        setActiveButton(cadastrarUsuarioButton);
    }

    @FXML
    public void showListarUsuarios(){
        loadUI("/grupo/trabalho/listarUsuarios-view.fxml"); //fxml da pagina do listar usuarios aqui
        setActiveButton(listarUsuariosButton);
    }

    @FXML
    public void showGerarRelatorios(){
        loadUI(""); //fxml da pagina do gerar relatorios aqui TODO: permanencia de dados via .txt
        setActiveButton(gerarRelatoriosButton);
    }

}
