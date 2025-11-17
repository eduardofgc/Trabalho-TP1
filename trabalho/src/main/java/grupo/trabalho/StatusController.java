package grupo.trabalho;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class StatusController {
    private CandidaturaController candidaturaController;

    public void setCandidaturaController(CandidaturaController candidaturaController){
        this.candidaturaController = candidaturaController;
    }

    @FXML
    private Label lblStatus;

    @FXML
    public void initialize() {
        int total = CandidatoRepository.getCandidatos().size();
        if (total > 0) {
            lblStatus.setText("Existem " + total + " candidatos cadastrados.");
        } else {
            lblStatus.setText("Nenhum candidato cadastrado ainda.");
        }
    }

    @FXML
    private void voltarMenu(ActionEvent event) {
        // caminho esperado do FXML de candidatura — ajuste aqui caso seu arquivo tenha outro nome
        String caminho = "/grupo/trabalho/candidatura-view.fxml";

        try {
            URL resource = getClass().getResource(caminho);
            if (resource == null) {
                System.err.println("❌ FXML não encontrado em: " + caminho + " (verifique nome/pasta)");
                return;
            }

            Parent root = FXMLLoader.load(resource);
            Scene scene = new Scene(root, 600, 400);

            // tenta aplicar style.css se existir (opcional)
            URL css = getClass().getResource("/grupo/trabalho/style.css");
            if (css != null) {
                scene.getStylesheets().add(css.toExternalForm());
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela de candidatura:");
            e.printStackTrace();
        } catch (Exception ex) {
            System.err.println("Erro inesperado ao voltar para candidatura:");
            ex.printStackTrace();
        }
    }
}