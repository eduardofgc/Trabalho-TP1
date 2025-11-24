package grupo.trabalho;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/*
TALVEZ no meu pc dê um erro do intelliJ na hora de iniciar envolvendo o javafx controls
Nesse caso, substituir o module path POR

--module-path "C:\Users\dudur\Downloads\openjfx-25.0.1_windows-x64_bin-sdk\javafx-sdk-25.0.1\lib"
--add-modules javafx.controls,javafx.fxml

ALÉM DISSO, lembrar de mudar os caminhos em CadastroFunc e FinanceiroController para "dados_Funcionarios.txt" pra mim
(original é "trabalho/dados_Funcionarios.txt")
 */

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        AdmClasses.ensureAdminUser(); //admin e admin123

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
