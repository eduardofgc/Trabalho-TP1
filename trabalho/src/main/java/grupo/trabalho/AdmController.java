package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class AdmController {

    private MainController mainController;
    public BorderPane root;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void onHover(javafx.scene.input.MouseEvent e) {
        ((Button) e.getSource()).setStyle(
                "-fx-background-color: #e8eef5; -fx-text-fill: #2c3e50; -fx-font-size: 14px; " +
                        "-fx-padding: 10 20 10 20; -fx-background-radius: 8; -fx-cursor: hand; -fx-scale-x: 1.03; -fx-scale-y: 1.03;"
        );
    }

    @FXML
    public void onExit(javafx.scene.input.MouseEvent e) {
        ((Button) e.getSource()).setStyle(
                "-fx-background-color: white; -fx-text-fill: #2c3e50; -fx-font-size: 14px; " +
                        "-fx-padding: 10 20 10 20; -fx-background-radius: 8; -fx-cursor: hand; -fx-scale-x: 1; -fx-scale-y: 1;"
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
                "-fx-background-color: #1f2b38; -fx-text-fill: white; -fx-font-size: 14px; " +
                        "-fx-padding: 10 20 10 20; -fx-background-radius: 8; -fx-cursor: hand; -fx-scale-x: 1.04; -fx-scale-y: 1.04;"
        );
    }

    @FXML
    public void onExitDark(javafx.scene.input.MouseEvent e) {
        ((Button) e.getSource()).setStyle(
                "-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-font-size: 14px; " +
                        "-fx-padding: 10 20 10 20; -fx-background-radius: 8; -fx-cursor: hand; -fx-scale-x: 1; -fx-scale-y: 1;"
        );
    }

    @FXML
    public void onReleaseDark(javafx.scene.input.MouseEvent e) {
        ((Button) e.getSource()).setStyle(
                "-fx-background-color: #1f2b38; -fx-text-fill: white; -fx-font-size: 14px; " +
                        "-fx-padding: 10 20 10 20; -fx-background-radius: 8; -fx-cursor: hand; -fx-scale-x: 1.04; -fx-scale-y: 1.04;"
        );
    }

    @FXML public TextField emailTextField;
    @FXML private Button cadastrarUsuarioButton;
    @FXML private Button listarUsuariosButton;
    @FXML private Button gerarRelatoriosButton;
    @FXML private Button voltarMenuButton;
    @FXML private AnchorPane contentArea;
    @FXML private Button activeButton;
    @FXML private Button cadastrarUsuarioFinal;
    @FXML private TextField senhaTextField;
    @FXML private TextField loginTextField;
    @FXML private CheckBox adminCheckBox;
    @FXML private CheckBox gestorCheckBox;
    @FXML private CheckBox recrutadorCheckBox;
    @FXML private CheckBox candidatoCheckBox;

    @FXML
    public void showGerarRelatorios() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grupo/trabalho/gerarRelatorio-view.fxml"));
            Parent view = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    private void setActiveButton(Button button) {
        if (activeButton != null) {
            activeButton.setId("buttonLateral");
        }
        button.setId("buttonLateralActive");
        activeButton = button;
    }

    @FXML
    public void showCadastrarUsuario() {
        loadUI("/grupo/trabalho/cadastrarUsuario-view.fxml");
        setActiveButton(cadastrarUsuarioButton);
    }

    @FXML
    public void showListarUsuarios() {
        Object controllerObj = loadUI("/grupo/trabalho/listarUsuarios-view.fxml");
        setActiveButton(listarUsuariosButton);
        if (controllerObj instanceof ListarUsuariosController controller) {
            try {
                List<String> lines = Files.readAllLines(Path.of(AppConfig.USUARIOS_INFO));
                controller.loadUsuarios(lines);
            } catch (IOException e) {
                AlertHelper.showInfo("Erro: impossível acessar usuariosInfo.txt");
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void clickCadastrarUsuarioFinal() throws IOException {
        String novaSenha = senhaTextField.getText();
        String novoLogin = loginTextField.getText();
        String novoEmail = emailTextField.getText();

        if (novaSenha.isEmpty() || novoLogin.isEmpty() || novoEmail.isEmpty()) {
            AlertHelper.showInfo("Erro: por favor, preencha todos os campos.");
            return;
        }

        boolean isAdmin = adminCheckBox.isSelected();
        boolean isGestor = gestorCheckBox.isSelected();
        boolean isCandidato = candidatoCheckBox.isSelected();
        boolean isRecrutador = recrutadorCheckBox.isSelected();

        // FIX: validação de permissões ANTES de criar o usuário
        int totalPermissoes = (isAdmin ? 1 : 0) + (isGestor ? 1 : 0)
                + (isCandidato ? 1 : 0) + (isRecrutador ? 1 : 0);
        if (totalPermissoes > 1) {
            AlertHelper.showInfo("Erro: selecione apenas um perfil por usuário.");
            return;
        }
        if (totalPermissoes == 0) {
            AlertHelper.showInfo("Erro: selecione pelo menos um perfil.");
            return;
        }

        String senhaHasheada = PasswordUtil.hash(novaSenha);

        Usuario novoUsuario = new Usuario(novoEmail, novoLogin, senhaHasheada);
        novoUsuario.isAdmin = isAdmin;
        novoUsuario.isGestor = isGestor;
        novoUsuario.isCandidato = isCandidato;
        novoUsuario.isRecrutador = isRecrutador;

        String permissions = isAdmin + "," + isGestor + "," + isCandidato + "," + isRecrutador;

        try (FileWriter writer = new FileWriter(AppConfig.USUARIOS_INFO, true);
             FileWriter emailWriter = new FileWriter(AppConfig.EMAIL_INFO, true)) {
            writer.write(novoLogin + "," + senhaHasheada + "," + permissions + System.lineSeparator());
            emailWriter.write(novoEmail + System.lineSeparator());
            // Só adiciona à lista em memória após salvar com sucesso
            AdmClasses.addToUserList(novoUsuario);
            AlertHelper.showInfo("Usuário cadastrado com sucesso!");
        } catch (IOException e) {
            AlertHelper.showInfo("Erro ao cadastrar usuário: não foi possível gravar o arquivo.");
            e.printStackTrace();
        }
    }
}