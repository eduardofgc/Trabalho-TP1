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

    @FXML public Label currentUserLabel;
    @FXML public Label currentCargoLabel;
    @FXML private Button logoutButton;
    @FXML private Button admButton;
    @FXML private Button candidaturaButton;
    @FXML private Button recrutamentoButton;
    @FXML private Button financeiroButton;

    public void setUserInfo(String nome, String cargo) {
        currentUserLabel.setText(nome);
        currentCargoLabel.setText(cargo);
    }

    @FXML
    public void initialize() {

        Usuario user = HelloController.currentUser;
        if (user != null) {
            setUserInfo(user.getLogin(), user.getCargo());
        }
    }

    @FXML
    public void onHover(javafx.scene.input.MouseEvent e) {
        ((Button) e.getSource()).setStyle(
                "-fx-background-color: #e1e8f0; -fx-text-fill: #2c3e50; -fx-font-size: 14px; -fx-padding: 10 20 10 20; -fx-background-radius: 8; -fx-cursor: hand; -fx-scale-x: 1.03; -fx-scale-y: 1.03;"
        );
    }

    @FXML
    public void onExit(javafx.scene.input.MouseEvent e) {
        ((Button) e.getSource()).setStyle(
                "-fx-background-color: white; -fx-text-fill: #2c3e50; -fx-font-size: 14px; -fx-padding: 10 20 10 20; -fx-background-radius: 8; -fx-cursor: hand; -fx-scale-x: 1; -fx-scale-y: 1;"
        );
    }

    @FXML
    public void onPress(javafx.scene.input.MouseEvent e) {
        ((Button) e.getSource()).setStyle(((Button) e.getSource()).getStyle() + "-fx-scale-x: 0.97; -fx-scale-y: 0.97;");
    }

    @FXML
    public void onRelease(javafx.scene.input.MouseEvent e) {
        ((Button) e.getSource()).setStyle(((Button) e.getSource()).getStyle() + "-fx-scale-x: 1.03; -fx-scale-y: 1.03;");
    }

    @FXML
    public void onHoverDark(javafx.scene.input.MouseEvent e) {
        ((Button) e.getSource()).setStyle(
                "-fx-background-color: #1a252f; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 18 8 18; -fx-background-radius: 8; -fx-cursor: hand; -fx-scale-x: 1.05; -fx-scale-y: 1.05;"
        );
    }

    @FXML
    public void onExitDark(javafx.scene.input.MouseEvent e) {
        ((Button) e.getSource()).setStyle(
                "-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 18 8 18; -fx-background-radius: 8; -fx-cursor: hand; -fx-scale-x: 1; -fx-scale-y: 1;"
        );
    }

    @FXML
    public void onReleaseDark(javafx.scene.input.MouseEvent e) {
        ((Button) e.getSource()).setStyle(
                "-fx-background-color: #1a252f; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 18 8 18; -fx-background-radius: 8; -fx-cursor: hand; -fx-scale-x: 1.05; -fx-scale-y: 1.05;"
        );
    }

    @FXML
    public void goBackMenu(Button exitButton) throws IOException {
        Stage prevStage = (Stage) exitButton.getScene().getWindow();
        prevStage.close();

        Stage mainStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grupo/trabalho/second-view.fxml"));
        Parent root = loader.load();
        mainStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo trabalho tp.png")));
        mainStage.setTitle("Gestão de RH - Menu Principal");
        mainStage.setScene(new Scene(root));
        mainStage.setResizable(false);
        mainStage.show();
    }

    @FXML
    private void goToAdm() throws IOException {
        Usuario user = HelloController.currentUser;
        if (user != null && (user.isAdmin || user.isGestor)) {
            Stage prevStage = (Stage) admButton.getScene().getWindow();
            prevStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grupo/trabalho/adm-view.fxml"));
            Parent root = loader.load();
            AdmController admController = loader.getController();
            admController.setMainController(this);

            Stage admStage = new Stage();
            admStage.setTitle("Administração/Gestão");
            admStage.setScene(new Scene(root));
            admStage.setResizable(false);
            admStage.show();
        } else {
            AlertHelper.showInfo("Erro: você não tem permissão para isso.");
        }
    }

    @FXML
    private void handleLogout() throws IOException {
        Stage prevStage = (Stage) logoutButton.getScene().getWindow();
        prevStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grupo/trabalho/hello-view.fxml"));
        Parent root = loader.load();
        Stage loginStage = new Stage();
        loginStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo trabalho tp.png")));
        loginStage.setTitle("Gestão de RH");
        loginStage.setScene(new Scene(root));
        loginStage.setResizable(false);
        loginStage.show();
    }

    @FXML
    private void goToCandidatura() throws IOException {
        Usuario user = HelloController.currentUser;
        if (user != null && !user.isCandidato) {
            Stage prevStage = (Stage) candidaturaButton.getScene().getWindow();
            prevStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grupo/trabalho/candidatura-view.fxml"));
            Parent root = loader.load();
            CandidaturaController ctrl = loader.getController();
            ctrl.setMainController(this);

            Stage stage = new Stage();
            stage.setTitle("Candidatura");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } else {
            AlertHelper.showInfo("Erro: você não tem permissão para isso.");
        }
    }

    @FXML
    private void goToRecrutamento() throws IOException {
        Usuario user = HelloController.currentUser;

        if (user != null && (user.isRecrutador || user.isAdmin || user.isGestor)) {
            Stage prevStage = (Stage) recrutamentoButton.getScene().getWindow();
            prevStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grupo/trabalho/recrutamento-view.fxml"));
            Parent root = loader.load();
            RecrutamentoController ctrl = loader.getController();
            ctrl.setMainController(this);

            Stage stage = new Stage();
            stage.setTitle("Recrutamento");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } else {
            AlertHelper.showInfo("Erro: você não tem permissão para isso.");
        }
    }

    @FXML
    private void goToFinanceiro() throws IOException {
        Usuario user = HelloController.currentUser;
        if (user != null && user.isAdmin) {
            Stage prevStage = (Stage) financeiroButton.getScene().getWindow();
            prevStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grupo/trabalho/financeiro-view.fxml"));
            Parent root = loader.load();
            FinanceiroController ctrl = loader.getController();
            ctrl.setMainController(this);

            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logoFinanceiro.png")));
            stage.setTitle("Financeiro");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } else {
            AlertHelper.showInfo("Erro: você não tem permissão para isso.");
        }
    }
}