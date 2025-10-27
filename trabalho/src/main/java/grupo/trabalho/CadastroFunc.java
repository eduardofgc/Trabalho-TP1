package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

public class CadastroFunc {

    private FinanceiroController financeiroController;

    public void setFinanceiroController(FinanceiroController financeiroController) {
        this.financeiroController = financeiroController;
    }

    @FXML
    private TextField nomeField, cpfField, cargoField, departamentoField, salarioField, matriculaField;

    @FXML
    private DatePicker dataAdmissaoPicker;

    @FXML
    private ComboBox<RegimeContratacao> regimeCombo;

    @FXML
    private ComboBox<StatusFuncionario> statusCombo;

    @FXML
    private Button salvarButton, financButton;

    @FXML
    public void initialize() {
        regimeCombo.getItems().setAll(RegimeContratacao.values());
        statusCombo.getItems().setAll(StatusFuncionario.values());
    }

    @FXML
    private void handleMenuButton() {
        try {
            financeiroController.goBackFinanceiro(financButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void salvarFuncionario() {
        try {
            Funcionario f = new Funcionario(
                    nomeField.getText(),
                    cpfField.getText(),
                    Integer.parseInt(matriculaField.getText()),
                    dataAdmissaoPicker.getValue(),
                    Double.parseDouble(salarioField.getText()),
                    regimeCombo.getValue(),
                    statusCombo.getValue(),
                    cargoField.getText(),
                    departamentoField.getText()
            );

            BancoDeDados.adicionarFuncionario(f);
            mostrarAlerta("Sucesso", "Funcionário cadastrado com sucesso!");
            limparCampos();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Verifique os dados informados.");
        }
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void limparCampos() {
        nomeField.clear();
        cpfField.clear();
        cargoField.clear();
        departamentoField.clear();
        salarioField.clear();
        matriculaField.clear();
        dataAdmissaoPicker.setValue(null);
        regimeCombo.setValue(null);
        statusCombo.setValue(null);
    }
}
