package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloController {

    static Usuario currentUser;

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private Button loginButton;
    @FXML private Button exitButton;
    @FXML private Button logoutButton;

    @FXML
    public void initialize() {
        AdmClasses.fetchUsersFromArchive();
    }

    @FXML
    private void handleLogin() throws IOException {
        AdmClasses.fetchUsersFromArchive();

        String triedUsername = usernameField.getText();
        String triedPassword = passwordField.getText();

        if (triedUsername.isEmpty() || triedPassword.isEmpty()) {
            AlertHelper.showInfo("Preencha o login e a senha.");
            return;
        }
        
        boolean found = AdmClasses.checkForUser(triedUsername, triedPassword);
        currentUser = AdmClasses.searchFor(triedUsername);

        if (found && currentUser != null) {
            Stage prevStage = (Stage) loginButton.getScene().getWindow();
            prevStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("second-view.fxml"));
            Parent root = loader.load();
            Stage mainStage = new Stage();
            mainStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo trabalho tp.png")));
            mainStage.setScene(new Scene(root));
            mainStage.setResizable(false);
            mainStage.setTitle("Gestão de RH - Menu Principal");
            mainStage.show();
        } else {
            AlertHelper.showInfo("Login ou senha incorretos.");
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
    private void handleExit() {
        Stage prevStage = (Stage) exitButton.getScene().getWindow();
        prevStage.close();
    }
}