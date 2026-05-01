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

    @FXML
    public void onHover(javafx.scene.input.MouseEvent e) {
        ((Button)e.getSource()).setStyle(
                "-fx-background-color: #e8eef5; -fx-text-fill: #2c3e50; -fx-font-size: 14px; " +
                        "-fx-padding: 10 20 10 20; -fx-background-radius: 8; -fx-cursor: hand; -fx-scale-x: 1.03; -fx-scale-y: 1.03;"
        );
    }

    @FXML
    public void onExit(javafx.scene.input.MouseEvent e) {
        ((Button)e.getSource()).setStyle(
                "-fx-background-color: white; -fx-text-fill: #2c3e50; -fx-font-size: 14px; " +
                        "-fx-padding: 10 20 10 20; -fx-background-radius: 8; -fx-cursor: hand; -fx-scale-x: 1; -fx-scale-y: 1;"
        );
    }

    @FXML
    public void onPress(javafx.scene.input.MouseEvent e) {
        ((Button)e.getSource()).setStyle(((Button)e.getSource()).getStyle() + "-fx-scale-x: 0.97; -fx-scale-y: 0.97;");
    }

    @FXML
    public void onRelease(javafx.scene.input.MouseEvent e) {
        ((Button)e.getSource()).setStyle(((Button)e.getSource()).getStyle() + "-fx-scale-x: 1.03; -fx-scale-y: 1.03;");
    }

    @FXML
    public void onHoverDark(javafx.scene.input.MouseEvent e) {
        ((Button)e.getSource()).setStyle(
                "-fx-background-color: #1f2b38; -fx-text-fill: white; -fx-font-size: 14px; " +
                        "-fx-padding: 10 20 10 20; -fx-background-radius: 8; -fx-cursor: hand; -fx-scale-x: 1.04; -fx-scale-y: 1.04;"
        );
    }

    @FXML
    public void onExitDark(javafx.scene.input.MouseEvent e) {
        ((Button)e.getSource()).setStyle(
                "-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-font-size: 14px; " +
                        "-fx-padding: 10 20 10 20; -fx-background-radius: 8; -fx-cursor: hand; -fx-scale-x: 1; -fx-scale-y: 1;"
        );
    }

    @FXML
    public void onReleaseDark(javafx.scene.input.MouseEvent e) {
        ((Button)e.getSource()).setStyle(
                "-fx-background-color: #1f2b38; -fx-text-fill: white; -fx-font-size: 14px; " +
                        "-fx-padding: 10 20 10 20; -fx-background-radius: 8; -fx-cursor: hand; -fx-scale-x: 1.04; -fx-scale-y: 1.04;"
        );
    }



    private MainController mainController;

    @FXML



    private AnchorPane contentArea;




    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    @FXML
    private void voltarMenu() {

        // TÉCNICA: TRATAMENTO DE EXCEÇÕES (Checked Exception)
        // O metodo 'goBackMenu' provavelmente lança uma 'IOException' (exceção checada),
        // que é um risco ao carregar arquivos FXML.
        // O bloco 'try' tenta executar o código arriscado.
        try {

            mainController.goBackMenu(menuButton);
        } catch (IOException e) {
            // O bloco 'catch' é o "plano de contingência". Se o 'try' falhar,
            // o programa não quebra. Ele captura o erro e o imprime no console
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
                // O "plano B" se o FXML da entrevista não for encontrado.
                e.printStackTrace();
            }
        }
        else{
            AlertHelper.showInfo("Erro: você não tem permissão de recrutador.");
            //aviso se  o catch pegar alguma excessão
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