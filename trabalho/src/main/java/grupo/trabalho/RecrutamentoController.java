package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;   // <- POLIMORFISMO: Parent é superclasse de vários layouts
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class RecrutamentoController {

    @FXML
    public Button cadastrarVagasButton;
    @FXML
    public Button menuButton;

    // ENCAPSULAMENTO → atributo privado acessado apenas pela classe
    private MainController mainController;

    @FXML
    private AnchorPane contentArea;
    // ↑ ENCAPSULAMENTO: privado, só acessado pela própria classe

    // ENCAPSULAMENTO → uso de setter para controlar o acesso ao atributo
    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    @FXML
    private void voltarMenu() {
        try {
            // DELEGAÇÃO: passa a responsabilidade para outro controller
            mainController.goBackMenu(menuButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showCadastrarVagas() {
        // MODULARIDADE: cada tela tem seu próprio FXML
        loadSubView("/grupo/trabalho/cadastrarVaga-view.fxml");
    }

    @FXML
    private void showListarVagas() {
        // MODULARIDADE: separação clara entre telas
        loadSubView("/grupo/trabalho/listarVagas-view.fxml");
    }

    @FXML
    private void showAgendarEntrevista() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/grupo/trabalho/agendarEntrevista-view.fxml")
            );

            /*
               POLIMORFISMO:
               loader.load() retorna um Parent.
               Parent é superclasse de muitos tipos diferentes de layout
               (AnchorPane, VBox, Pane, GridPane, BorderPane…)
               O código funciona independentemente do tipo real.
            */
            Parent view = loader.load();

            // COMPOSIÇÃO / DELEGAÇÃO:
            // Usa outro controller dentro deste controller
            EntrevistaController controller = loader.getController();
            controller.setRecrutamentoController(this);

            // MODULARIZAÇÃO DA INTERFACE
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);

            // Organização visual
            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);

        } catch (IOException e) {
            // TRATAMENTO DE EXCEÇÕES
            e.printStackTrace();
        }
    }

    private void loadSubView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

            /*
               POLIMORFISMO novamente:
               O retorno de loader.load() é Parent.
               Não sabemos qual elemento específico está no FXML,
               mas funciona porque Parent é classe genérica.
            */
            Parent view = loader.load();

            // MODULARIDADE: troca de telas organizada
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);

            // Ajustes de layout
            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);

        } catch (IOException e) {
            // EXCEÇÕES
            e.printStackTrace();
        }
    }
}
