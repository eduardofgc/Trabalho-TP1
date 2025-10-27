package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class FinanceiroController {

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private Button menuButton;

    @FXML
    private Button cadastroButton;

    @FXML
    private Button configRegrasButton;

    @FXML
    private Button folhaPagamentoButton;

    @FXML
    private void handleMenuButton() throws IOException {
        if (mainController != null) {
            mainController.goBackMenu(menuButton);
        } else {
            System.err.println("MainController não foi configurado!");
        }
    }

    @FXML
    public void goBackFinanceiro(Button exitButton) throws IOException {
        Stage stage = (Stage) exitButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grupo/trabalho/financeiro-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Financeiro");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void gocadastroFunc() throws IOException {
        Stage stage = (Stage) cadastroButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grupo/trabalho/cadastroFunc-view.fxml"));
        Parent root = loader.load();

        CadastroFunc cadastroFunc = loader.getController();
        cadastroFunc.setFinanceiroController(this);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Cadastro de Funcionário");
        stage.setResizable(false);
        stage.show();
    }
}
