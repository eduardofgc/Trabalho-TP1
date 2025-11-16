package grupo.trabalho;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class RelatorioFolhaController {

    @FXML private TableView<Funcionario> tabelaRelatorio;
    @FXML private TableColumn<Funcionario, String> colRelatorioNome;
    @FXML private TableColumn<Funcionario, String> colRelatorioCargo;
    @FXML private TableColumn<Funcionario, String> colRelatorioDepartamento;
    @FXML private TableColumn<Funcionario, Double> colRelatorioSalarioBase;
    @FXML private TableColumn<Funcionario, Double> colRelatorioSalarioLiquido;
    @FXML private Label labelTotalRelatorio;

    @FXML
    public void initialize() {
        colRelatorioNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colRelatorioCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        colRelatorioDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));
        colRelatorioSalarioBase.setCellValueFactory(new PropertyValueFactory<>("salarioBase"));
        colRelatorioSalarioLiquido.setCellValueFactory(new PropertyValueFactory<>("salarioLiquido"));
    }

    public void carregarDados(ObservableList<Funcionario> funcionarios, double total) {
        tabelaRelatorio.setItems(funcionarios);
        labelTotalRelatorio.setText(String.format("Total da folha: R$ %.2f", total));
    }
    @FXML
    private void fecharTela() {
        Stage stage = (Stage) tabelaRelatorio.getScene().getWindow();
        stage.close();
    }
}