package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    private AdmController admController;
    private HelloController helloController;

    @FXML
    public Label currentUserLabel;
    @FXML
    public Label currentCargoLabel;
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

    public void setUserInfo(String nome, String cargo) {
        currentUserLabel.setText(nome);
        currentCargoLabel.setText(cargo);
    }

    @FXML
    public void initialize(){
        setUserInfo(
                HelloController.currentUser.getLogin(),
                HelloController.currentUser.getCargo()
        );
    }


    @FXML
    public void onHover(javafx.scene.input.MouseEvent e) {
        ((Button)e.getSource()).setStyle(
                "-fx-background-color: #e1e8f0; -fx-text-fill: #2c3e50; -fx-font-size: 14px; -fx-padding: 10 20 10 20; -fx-background-radius: 8; -fx-cursor: hand; -fx-scale-x: 1.03; -fx-scale-y: 1.03;"
        );
    }

    @FXML
    public void onExit(javafx.scene.input.MouseEvent e) {
        ((Button)e.getSource()).setStyle(
                "-fx-background-color: white; -fx-text-fill: #2c3e50; -fx-font-size: 14px; -fx-padding: 10 20 10 20; -fx-background-radius: 8; -fx-cursor: hand; -fx-scale-x: 1; -fx-scale-y: 1;"
        );
    }

    @FXML
    public void onPress(javafx.scene.input.MouseEvent e) {
        ((Button)e.getSource()).setStyle(((Button)e.getSource()).getStyle() + "-fx-scale-x: 0.97; -fx-scale-y: 0.97;");
    }

    @FXML
    public void onRelease(javafx.scene.input.MouseEvent e) {
        ((Button)e.getSource()).setStyle(((Button)e.getSource()).getStyle() + "-fx-scale-x: 1.03; -fx-scale-y: 1.03;");
    }

    @FXML
    public void onHoverDark(javafx.scene.input.MouseEvent e) {
        ((Button)e.getSource()).setStyle(
                "-fx-background-color: #1a252f; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 18 8 18; -fx-background-radius: 8; -fx-cursor: hand; -fx-scale-x: 1.05; -fx-scale-y: 1.05;"
        );
    }

    @FXML
    public void onExitDark(javafx.scene.input.MouseEvent e) {
        ((Button)e.getSource()).setStyle(
                "-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 18 8 18; -fx-background-radius: 8; -fx-cursor: hand; -fx-scale-x: 1; -fx-scale-y: 1;"
        );
    }

    @FXML
    public void onReleaseDark(javafx.scene.input.MouseEvent e) {
        ((Button)e.getSource()).setStyle(
                "-fx-background-color: #1a252f; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 18 8 18; -fx-background-radius: 8; -fx-cursor: hand; -fx-scale-x: 1.05; -fx-scale-y: 1.05;"
        );
    }


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
        Stage prevStage = (Stage) candidaturaButton.getScene().getWindow();
        prevStage.close();

        Stage candidaturaStage = new Stage();
        FXMLLoader candidaturaFXMLLoader = new FXMLLoader(getClass().getResource("/grupo/trabalho/candidatura-view.fxml"));
        Parent candidaturaRoot = candidaturaFXMLLoader.load();

        CandidaturaController candidaturaController = candidaturaFXMLLoader.getController();

        Scene scene = new Scene(candidaturaRoot);
        candidaturaStage.setTitle("Candidatura");
        candidaturaStage.setScene(scene);
        candidaturaStage.setResizable(false);
        candidaturaStage.show();
    }

    @FXML
    private void goToRecrutamento() throws IOException {

        if (HelloController.currentUser != null && HelloController.currentUser.isRecrutador || HelloController.currentUser.isAdmin || HelloController.currentUser.isGestor){
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

        if (HelloController.currentUser.isAdmin){
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
        else{
            AlertHelper.showInfo("Erro: você não tem permissão pra isso.");
        }


    }
}
