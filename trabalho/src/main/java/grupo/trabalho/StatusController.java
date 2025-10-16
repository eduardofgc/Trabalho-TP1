package grupo.trabalho;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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

    public void voltarMenu(ActionEvent event) {
        trocarTela(event, "/grupo/trabalho/candidatura-view.fxml");
    }

    private void trocarTela(ActionEvent event, String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Scene scene = new Scene(root, 600, 400);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}