package grupo.trabalho;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        AdmClasses.ensureAdminUser();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/grupo/trabalho/hello-view.fxml"));
        Parent root = fxmlLoader.load();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo trabalho tp.png")));
        Scene scene = new Scene(root);
        stage.setTitle("Gestão de RH");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
