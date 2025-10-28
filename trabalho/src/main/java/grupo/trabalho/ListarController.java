package grupo.trabalho;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ListarController {
    private CandidaturaController candidaturaController;

    public void setCandidaturaController(CandidaturaController candidaturaController){
        this.candidaturaController = candidaturaController;
    }

    @FXML
    private TableView<Candidato> tabela;

    @FXML
    private TableColumn<Candidato, String> colNome;

    @FXML
    private TableColumn<Candidato, String> colEmail;

    @FXML
    private TableColumn<Candidato, String> colVaga;

    @FXML
    public void initialize() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colVaga.setCellValueFactory(new PropertyValueFactory<>("vaga"));

        ObservableList<Candidato> lista = CandidatoRepository.getCandidatos();
        tabela.setItems(lista);
    }

    public void voltarMenu(ActionEvent event) {
        trocarTela(event, "candidatura-view.fxml");
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