package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class CadastrarController {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtEmail;

    @FXML
    private ComboBox<String> comboVaga;

    private CandidaturaController candidaturaController;

    public void setCandidaturaController(CandidaturaController candidaturaController) {
        this.candidaturaController = candidaturaController;
    }

    @FXML
    private void initialize() {
        comboVaga.getItems().addAll("Desenvolvedor", "Analista", "Designer", "Gerente de Projetos");
    }

    @FXML
    private void salvarCandidato() {
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String vaga = comboVaga.getValue();

        if (nome.isEmpty() || email.isEmpty() || vaga == null) {
            System.out.println("Preencha todos os campos!");
            return;
        }

        Candidato novo = new Candidato(nome, email, vaga);
        CandidatoRepository.adicionarCandidato(novo);

        System.out.println("Candidato salvo com sucesso!");
        System.out.println("Nome: " + nome);
        System.out.println("Email: " + email);
        System.out.println("Vaga: " + vaga);

        txtNome.clear();
        txtEmail.clear();
        comboVaga.setValue(null);
    }

    @FXML
    private void voltarMenu() throws IOException {
        Stage stageAtual = (Stage) txtNome.getScene().getWindow();
        stageAtual.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grupo/trabalho/candidatura-view.fxml"));
        Parent root = loader.load();

        CandidaturaController candidaturaController = loader.getController();

        Stage stage = new Stage();
        stage.setTitle("Candidatura");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}