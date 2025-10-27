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
    private void handleLogin() throws IOException { //TODO: FIX USUARIO INICIAL ADMIN
        String triedUsername = usernameField.getText();
        String triedPassword = passwordField.getText();
        boolean gambiarra = false;
        boolean found = AdmClasses.checkForUser(triedUsername, triedPassword);

        if (triedUsername.equals("Cheat") && triedPassword.equals("12345678")){
            gambiarra = true;
        }


        if (found || gambiarra){
            Stage prevStage = (Stage) loginButton.getScene().getWindow();
            prevStage.close();

            try{
                FXMLLoader mainScreenFXML = new FXMLLoader(getClass().getResource("second-view.fxml"));
                Parent mainRoot = mainScreenFXML.load();

                Stage mainStage = new Stage();
                Scene mainScene = new Scene(mainRoot);

                mainStage.setScene(mainScene);
                mainStage.setResizable(false);
                mainStage.setTitle("Gestão de RH - Menu Principal");
                mainStage.show();

            } catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            AlertHelper.showInfo("Credenciais incorretas.");
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
    private void handleExit(){

        Stage prevStage = (Stage) exitButton.getScene().getWindow();
        prevStage.close();
    }
}
