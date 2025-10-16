package grupo.trabalho;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastrarController {
    private CandidaturaController candidaturaController;

    public void setCandidaturaController(CandidaturaController candidaturaController){
        this.candidaturaController = candidaturaController;
    }

    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtEmail;
    @FXML
    private ComboBox<String> comboVaga;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnVoltar;
    @FXML
    public void initialize() {
        comboVaga.getItems().addAll("Analista", "Desenvolvedor", "Gerente", "Estagiário");
    }

    public void salvarCandidato(ActionEvent event) {
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String vaga = comboVaga.getValue();

        if (nome.isEmpty() || email.isEmpty() || vaga == null) {
            System.out.println("Preencha todos os campos!");
            return;
        }

        Candidato candidato = new Candidato(nome, email, vaga);
        CandidatoRepository.adicionarCandidato(candidato);

        System.out.println("Candidato cadastrado: " + nome);

        txtNome.clear();
        txtEmail.clear();
        comboVaga.setValue(null);
    }

    public void voltarMenu(){

    }

    private void trocarTela(ActionEvent event, String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Scene scene = new Scene(root, 600, 400);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}