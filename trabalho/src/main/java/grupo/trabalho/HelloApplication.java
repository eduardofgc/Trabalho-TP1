package grupo.trabalho;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.awt.*;
import javafx.scene.image.Image;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo trabalho tp.png")));
        Scene scene = new Scene(root);
        stage.setTitle("Gestão de RH");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        AdmClasses.ensureAdmin();

        //TODO: colocar icone em todas as paginas
    }

    public static void main(String[] args) {
        launch();
    }
}
