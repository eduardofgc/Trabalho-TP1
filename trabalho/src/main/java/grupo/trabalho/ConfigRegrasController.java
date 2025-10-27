package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;

import java.io.IOException;

public class ConfigRegrasController {

    private FinanceiroController financeiroController;

    public void setFinanceiroController(FinanceiroController financeiroController) {
        this.financeiroController = financeiroController;
    }

    @FXML
    private TextField salarioField, descricaoField;

    @FXML
    private Button voltarButton, financButton, salvarButton;

    @FXML
    private ComboBox<TipoRegraSalario> tipoCombo;
    @FXML
    public void initialize() {
        tipoCombo.getItems().setAll(TipoRegraSalario.values());
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
    private void salvarRegras() {
        try {
            RegraSalarial rs = new RegraSalarial(
                    descricaoField.getText(),
                    Double.parseDouble(salarioField.getText()),
                    tipoCombo.getValue()
            );

            BancoDeDados.addRegraSalarial(rs);
            mostrarAlerta("Sucesso", "Regras salvas com sucesso!");
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
        salarioField.clear();
        descricaoField.clear();
        tipoCombo.setValue(null);
    }
    @FXML
    private void voltar() throws IOException {
        Stage stage = (Stage) voltarButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grupo.trabalho/financeiro-view.fxml"));
        Parent root = loader.load();
        stage.getIcons().clear();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logoFinanceiro.png")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Financeiro");
        stage.setResizable(false);
        stage.show();
    }
}
