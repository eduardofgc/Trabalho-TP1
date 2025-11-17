package grupo.trabalho;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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
    private TableColumn<Candidato, Void> colExcluir;

    @FXML
    private Button voltarButton;

    private ObservableList<Candidato> lista;

    @FXML
    public void initialize() {

        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colVaga.setCellValueFactory(new PropertyValueFactory<>("vaga"));

        lista = CandidatoRepository.getCandidatos();
        tabela.setItems(lista);

        adicionarColunaExcluir();
    }

    private void adicionarColunaExcluir() {
        colExcluir.setCellFactory(param -> new TableCell<>() {
            private final Button btnExcluir = new Button("X");

            {
                btnExcluir.setStyle(
                        "-fx-background-color: #e74c3c; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-weight: bold; " +
                                "-fx-background-radius: 8;"
                );
                btnExcluir.setOnAction(event -> {
                    Candidato candidato = getTableView().getItems().get(getIndex());
                    excluirCandidato(candidato);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnExcluir);
                }
            }
        });
    }

    private void excluirCandidato(Candidato candidato) {
        CandidatoRepository.removerCandidato(candidato);
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