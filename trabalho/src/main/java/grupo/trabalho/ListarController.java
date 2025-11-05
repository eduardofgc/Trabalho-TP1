package grupo.trabalho;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ListarController {

    @FXML
    private TableView<Candidato> tabela;

    @FXML
    private TableColumn<Candidato, String> colNome;

    @FXML
    private TableColumn<Candidato, String> colEmail;

    @FXML
    private TableColumn<Candidato, String> colVaga;

    @FXML
    private Button voltarButton;

    @FXML
    public void initialize() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colVaga.setCellValueFactory(new PropertyValueFactory<>("vaga"));

        ObservableList<Candidato> lista = CandidatoRepository.getCandidatos();
        tabela.setItems(lista);
    }

    @FXML
    private void voltarMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grupo/trabalho/candidatura-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Candidatura");
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}