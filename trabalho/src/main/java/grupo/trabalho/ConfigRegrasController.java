package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private TextField salarioBaseField;

    @FXML
    private TextField bonusField;

    @FXML
    private TextField descontoField;

    @FXML
    private Button salvarButton;

    @FXML
    private Button voltarButton;

    @FXML
    private void initialize() {
        // Aqui você pode inicializar valores padrão ou carregar do banco
    }

    @FXML
    private void salvarRegras() {
        // Pega os valores dos campos
        String salarioBase = salarioBaseField.getText();
        String bonus = bonusField.getText();
        String desconto = descontoField.getText();

        // Aqui você faria a lógica para salvar esses valores, por exemplo:
        System.out.println("Salário Base: " + salarioBase);
        System.out.println("Bonificação: " + bonus);
        System.out.println("Desconto: " + desconto);

        // Exemplo: salvar em um banco de dados ou arquivo
    }

    @FXML
    private void voltar() throws IOException {
        Stage stage = (Stage) voltarButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grupo/trabalho/financeiro-view.fxml"));
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
