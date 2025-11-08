package grupo.trabalho;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static grupo.trabalho.AdmClasses.usuariosArray;

public class AdmController {

    private MainController mainController;
    public BorderPane root;

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    @FXML
    private Button cadastrarUsuarioButton;
    @FXML
    private Button listarUsuariosButton;
    @FXML
    private Button gerarRelatoriosButton;
    @FXML
    private Button voltarMenuButton;
    @FXML
    private AnchorPane contentArea;
    @FXML
    private Button activeButton;
    @FXML
    private Button cadastrarUsuarioFinal;
    @FXML
    private TextField senhaTextField;
    @FXML
    private TextField loginTextField;
    @FXML
    private ListView<String> listaElementos;
    @FXML
    private CheckBox adminCheckBox;
    @FXML
    private CheckBox gestorCheckBox;
    @FXML
    private CheckBox recrutadorCheckBox;
    @FXML
    private CheckBox candidatoCheckBox;

    @FXML
    private void voltarMenu() throws IOException {
        mainController.goBackMenu(voltarMenuButton);
    }

    public Object loadUI(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            AnchorPane pane = loader.load();

            contentArea.getChildren().clear();
            contentArea.getChildren().add(pane);

            return loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setActiveButton(Button button){
        if (activeButton != null){
            activeButton.setId("buttonLateral");
            activeButton = button;
        }

        button.setId("buttonLateralActive");
        activeButton = button;
    }

    @FXML
    public void showCadastrarUsuario(){
        loadUI("/grupo/trabalho/cadastrarUsuario-view.fxml");
        setActiveButton(cadastrarUsuarioButton);

        listarUsuariosEmPag();
    }

    @FXML
    public void showListarUsuarios() {
        Object controllerObj = loadUI("/grupo/trabalho/listarUsuarios-view.fxml");
        setActiveButton(listarUsuariosButton);

        if (controllerObj instanceof ListarUsuariosController controller) {
            try {
                List<String> lines = Files.readAllLines(Path.of("usuariosInfo.txt"));
                controller.loadUsuarios(lines);
            } catch (IOException e) {
                AlertHelper.showInfo("Erro: impossível acessar usuariosInfo.txt");
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void showGerarRelatorios(){
        loadUI("");
        setActiveButton(gerarRelatoriosButton);
    }

    @FXML
    public void clickCadastrarUsuarioFinal() throws IOException {
        String novaSenha = senhaTextField.getText();
        String novoLogin = loginTextField.getText();
        String permissions = null;

        if (novaSenha.isEmpty() || novoLogin.isEmpty()){
            AlertHelper.showInfo("Erro: por favor, preencha todos os campos.");
            return;
        }

        Usuario novoUsuario = new Usuario(novoLogin, novaSenha);

        novoUsuario.isAdmin = adminCheckBox.isSelected();
        novoUsuario.isGestor = gestorCheckBox.isSelected();
        novoUsuario.isCandidato = candidatoCheckBox.isSelected();
        novoUsuario.isRecrutador = recrutadorCheckBox.isSelected();

        AdmClasses.addToUserList(novoUsuario);

        if (novoUsuario.isAdmin){
            permissions = "true,false,false,false";
        }
        else if (novoUsuario.isGestor){
            permissions = "false,true,false,false";
        }
        else if (novoUsuario.isCandidato){
            permissions = "false,false,true,false";
        }
        else if (novoUsuario.isRecrutador){
            permissions = "false,false,false,true";
        }

        try(FileWriter writer = new FileWriter("usuariosInfo.txt", true)){
            writer.write(novoLogin + "," + novaSenha + "," + permissions + System.lineSeparator());

            AlertHelper.showInfo("Usuário salvo para usuariosInfo.txt!");

        } catch (IOException e){
            AlertHelper.showInfo("Erro cadastrando usuário.");
            e.printStackTrace();
        }
    }

    public void listarUsuariosEmPag() {
        try {
            List<String> lines = Files.readAllLines(Path.of("usuariosInfo.txt"));
            listaElementos.getItems().clear();

            for (String line : lines) {
                String[] parts = line.split(",");

                if (parts.length >= 6) {
                    String nome = parts[0];
                    String senha = parts[1];
                    boolean admin = Boolean.parseBoolean(parts[2]);
                    boolean gestor = Boolean.parseBoolean(parts[3]);
                    boolean candidato = Boolean.parseBoolean(parts[4]);
                    boolean recrutador = Boolean.parseBoolean(parts[5]);

                    StringBuilder roles = new StringBuilder();
                    if (admin) roles.append("Admin ");
                    if (gestor) roles.append("Gestor ");
                    if (candidato) roles.append("Candidato ");
                    if (recrutador) roles.append("Recrutador ");

                    String formatted = String.format("%s — %s", nome, roles.toString().trim());
                    listaElementos.getItems().add(formatted);
                }
            }

        } catch (IOException e) {
            AlertHelper.showInfo("Erro: impossível acessar usuariosInfo.txt");
            e.printStackTrace();
        }
    }
}
