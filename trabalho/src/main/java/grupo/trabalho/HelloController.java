package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloController {

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
    private void handleLogin() throws IOException {
        String triedUsername = usernameField.getText();
        String triedPassword = passwordField.getText();

        Stage prevStage = (Stage) loginButton.getScene().getWindow();
        prevStage.close();

        try{
            FXMLLoader mainScreenFXML = new FXMLLoader(getClass().getResource("second-view.fxml"));
            Parent mainRoot = mainScreenFXML.load();

            Stage mainStage = new Stage();
            Scene mainScene = new Scene(mainRoot);
            mainStage.setScene(mainScene);
            mainStage.setResizable(false);
            mainStage.show();

        } catch (Exception e){
            e.printStackTrace();
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
        loginStage.setTitle("Gestão de RH");
        loginStage.setScene(scene);
        loginStage.setResizable(false);
        loginStage.show();
    }

    @FXML
    private void handleExit(){

        Stage prevStage = (Stage) exitButton.getScene().getWindow();
        prevStage.close();
    }
}
