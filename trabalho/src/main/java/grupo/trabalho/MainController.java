package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    private AdmController admController;
    private HelloController helloController;

    @FXML
    private Button logoutButton;
    @FXML
    private Button admButton;
    @FXML
    private Button candidaturaButton;
    @FXML
    private Button recrutamentoButton;
    @FXML
    private Button financeiroButton;

    @FXML
    public void goBackMenu(Button exitButton) throws IOException {
        Stage prevStage = (Stage) exitButton.getScene().getWindow();
        prevStage.close();

        Stage mainStage = new Stage();
        FXMLLoader loginFXMLLoader = new FXMLLoader(getClass().getResource("/grupo/trabalho/second-view.fxml"));
        Parent loginRoot = loginFXMLLoader.load();

        Scene scene = new Scene(loginRoot);
        mainStage.setTitle("Gestão de RH - Menu Principal");
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }

    @FXML
    private void goToAdm() throws IOException {

        if (HelloController.currentUser.isAdmin || HelloController.currentUser.isGestor){
            Stage prevStage = (Stage) admButton.getScene().getWindow();
            prevStage.close();

            Stage admStage = new Stage();
            FXMLLoader admFXMLLoader = new FXMLLoader((getClass().getResource("/grupo/trabalho/adm-view.fxml")));
            Parent admRoot = admFXMLLoader.load();

            AdmController admController = admFXMLLoader.getController();
            admController.setMainController(this);

            Scene scene = new Scene(admRoot);
            admStage.setTitle("Administração/Gestão");
            admStage.setScene(scene);
            admStage.setResizable(false);
            admStage.show();
        }
        else{
            AlertHelper.showInfo("Erro: você não tem permissão pra isso.");
        }
    }

    @FXML
    private void handleLogout() throws IOException {
        Stage prevStage = (Stage) logoutButton.getScene().getWindow();
        prevStage.close();

        Stage loginStage = new Stage();
        FXMLLoader loginFXMLLoader = new FXMLLoader(getClass().getResource("/grupo/trabalho/hello-view.fxml"));
        Parent loginRoot = loginFXMLLoader.load();
        loginStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo trabalho tp.png")));

        Scene scene = new Scene(loginRoot);
        loginStage.setTitle("Gestão de RH");
        loginStage.setScene(scene);
        loginStage.setResizable(false);
        loginStage.show();
    }

    @FXML
    private void goToCandidatura() throws IOException {

        if (HelloController.currentUser.isRecrutador || HelloController.currentUser != null || HelloController.currentUser.isAdmin && HelloController.currentUser != null){
            Stage prevStage = (Stage) candidaturaButton.getScene().getWindow();
            prevStage.close();

            Stage candidaturaStage = new Stage();
            FXMLLoader candidaturaFXMLLoader = new FXMLLoader(getClass().getResource("/grupo/trabalho/candidatura-view.fxml"));
            Parent candidaturaRoot = candidaturaFXMLLoader.load();

            CandidaturaController candidaturaController = candidaturaFXMLLoader.getController();
            candidaturaController.setMainController(this);

            Scene scene = new Scene(candidaturaRoot);
            candidaturaStage.setTitle("Candidatura");
            candidaturaStage.setScene(scene);
            candidaturaStage.setResizable(false);
            candidaturaStage.show();
        }
        else{
            AlertHelper.showInfo("Erro: você não tem permissão pra isso.");
        }
    }

    @FXML
    private void goToRecrutamento() throws IOException {

        if (HelloController.currentUser != null && HelloController.currentUser.isRecrutador || HelloController.currentUser.isAdmin){
            Stage prevStage = (Stage) recrutamentoButton.getScene().getWindow();
            prevStage.close();

            Stage candidaturaStage = new Stage();
            FXMLLoader recrutamentoFXMLLoader = new FXMLLoader(getClass().getResource("/grupo/trabalho/recrutamento-view.fxml"));
            Parent recrutamentoRoot = recrutamentoFXMLLoader.load();

            RecrutamentoController recrutamentoControllerController = recrutamentoFXMLLoader.getController();
            recrutamentoControllerController.setMainController(this);

            Scene scene = new Scene(recrutamentoRoot);
            candidaturaStage.setTitle("Recrutamento");
            candidaturaStage.setScene(scene);
            candidaturaStage.setResizable(false);
            candidaturaStage.show();
        }
        else{
            AlertHelper.showInfo("Erro: você não tem permissão pra isso.");
        }
    }

    @FXML
    private void goToFinanceiro() throws IOException{

        Stage prevStage = (Stage) financeiroButton.getScene().getWindow();
        prevStage.close();

        Stage financeiroStage = new Stage();
        FXMLLoader financeiroFXMLLoader = new FXMLLoader(getClass().getResource("/grupo/trabalho/financeiro-view.fxml"));
        Parent financeiroRoot = financeiroFXMLLoader.load();
        financeiroStage.getIcons().clear();
        financeiroStage.getIcons().add(new Image(
                getClass().getResourceAsStream("/images/logoFinanceiro.png")
        ));
        FinanceiroController financeiroController = financeiroFXMLLoader.getController();
        financeiroController.setMainController(this);

        Scene scene = new Scene(financeiroRoot);
        financeiroStage.setTitle("Financeiro");
        financeiroStage.setScene(scene);
        financeiroStage.setResizable(false);
        financeiroStage.show();
    }
}
