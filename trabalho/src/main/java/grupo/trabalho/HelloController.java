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
    static boolean gambiarra;

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button logoutButton;

    @FXML
    public void initialize() {
        AdmClasses.fetchUsersFromArchive();
    }

    @FXML
    private void handleLogin() throws IOException {
        AdmClasses.fetchUsersFromArchive();

        String triedUsername = usernameField.getText();
        String triedPassword = passwordField.getText();

        boolean found = AdmClasses.checkForUser(triedUsername, triedPassword);
        currentUser = AdmClasses.searchFor(triedUsername);

        if (currentUser != null) {
            System.out.println(currentUser.getLogin());
            System.out.println(currentUser.isAdmin);
            System.out.println(currentUser.isGestor);
            System.out.println(currentUser.isCandidato);
            System.out.println(currentUser.isRecrutador);
        }

        if (found && currentUser != null) {
            Stage prevStage = (Stage) loginButton.getScene().getWindow();
            prevStage.close();

            FXMLLoader mainScreenFXML = new FXMLLoader(getClass().getResource("second-view.fxml"));
            Parent mainRoot = mainScreenFXML.load();
            Stage mainStage = new Stage();
            mainStage.setScene(new Scene(mainRoot));
            mainStage.setResizable(false);
            mainStage.setTitle("Gestão de RH - Menu Principal");
            mainStage.show();
        }
    }

    @FXML
    private void handleLogout() throws IOException {
        Stage prevStage = (Stage) logoutButton.getScene().getWindow();
        prevStage.close();
        Stage loginStage = new Stage();
        FXMLLoader loginFXMLLoader = new FXMLLoader(getClass().getResource("/grupo/trabalho/hello-view.fxml"));
        Parent loginRoot = loginFXMLLoader.load();
        Scene scene = new Scene(loginRoot);
        loginStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo trabalho tp.png")));
        loginStage.setTitle("Gestão de RH");
        loginStage.setScene(scene);
        loginStage.setResizable(false);
        loginStage.show();
    }

    @FXML
    private void handleExit() {
        Stage prevStage = (Stage) exitButton.getScene().getWindow();
        prevStage.close();
    }
}

