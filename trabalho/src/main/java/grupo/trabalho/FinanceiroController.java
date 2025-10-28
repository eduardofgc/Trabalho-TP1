package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.image.Image;

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
        Stage stage0 = (Stage) exitButton.getScene().getWindow();
        stage0.close();

        Stage stage1 = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grupo/trabalho/financeiro-view.fxml"));
        Parent root = loader.load();
        stage1.getIcons().clear();
        stage1.getIcons().add(new Image(
                getClass().getResourceAsStream("/images/logoFinanceiro.png")
        ));
        Scene scene = new Scene(root);
        stage1.setScene(scene);
        stage1.setTitle("Financeiro");
        stage1.setResizable(false);
        stage1.show();
    }

    @FXML
    private void gocadastroFunc() throws IOException {
        Stage stage = (Stage) cadastroButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grupo/trabalho/cadastroFunc-view.fxml"));
        Parent root = loader.load();
        stage.getIcons().clear();
        stage.getIcons().add(new Image(
                getClass().getResourceAsStream("/images/logoCadastroFunc.png")
        ));

        CadastroFunc cadastroFunc = loader.getController();
        cadastroFunc.setFinanceiroController(this);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Cadastro de Funcionário");
        stage.setResizable(false);
        stage.show();
    }
    @FXML
        private void goConfigRegras() throws IOException {
        Stage stage = (Stage) configRegrasButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grupo/trabalho/configRegras-view.fxml"));
        Parent root = loader.load();
        stage.getIcons().clear();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logoFinanceiro.png")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Configuração de Regras Salariais");
        stage.setResizable(false);
        stage.show();

        ConfigRegrasController controller = loader.getController();
        controller.setFinanceiroController(this);
    }
    @FXML
    private void goFolhaPagamento() throws IOException {
        Stage stage = (Stage) folhaPagamentoButton.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("#"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Gerar Folha de Pagamento");
        stage.setResizable(false);
        stage.show();
    }
}
