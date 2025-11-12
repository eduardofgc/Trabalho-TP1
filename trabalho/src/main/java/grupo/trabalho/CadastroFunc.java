package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class CadastroFunc {

    private CandidaturaController candidaturaController;

    public void setCandidaturaController(CandidaturaController candidaturaController) {
        this.candidaturaController = candidaturaController;
    }
    AlertHelper alertHelper = new AlertHelper();
    @FXML
    private TextField nomeField, cpfField, cargoField, salarioField, departamentoField, matriculaField;
    @FXML
    private DatePicker dataAdmissaoPicker;
    @FXML
    private ComboBox<RegimeContratacao> regimeCombo;
    @FXML
    private ComboBox<StatusFuncionario> statusCombo;
    @FXML
    private Button salvarButton, financButton, botaoVoltar;

    @FXML
    public void initialize() {
        regimeCombo.getItems().setAll(RegimeContratacao.values());
        statusCombo.getItems().setAll(StatusFuncionario.values());
    }

    @FXML
    private void voltarPaginaAnterior() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("candidatura-view.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) botaoVoltar.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    @FXML
    private void salvarFuncionario() {
        try {
            String salarioStr = salarioField.getText();
            NumberFormat format = NumberFormat.getInstance(new Locale("pt", "BR"));
            double salario = format.parse(salarioStr).doubleValue();

            Funcionario f = new Funcionario(
                    nomeField.getText(),
                    cpfField.getText(),
                    Integer.parseInt(matriculaField.getText()),
                    dataAdmissaoPicker.getValue(),
                    salario,
                    salario,
                    regimeCombo.getValue(),
                    statusCombo.getValue(),
                    cargoField.getText(),
                    departamentoField.getText()
            );
            String caminhoArquivo = "dados_Funcionarios.txt";

            String linha = String.format("%s;%s;%03d;%s;%.2f;%.2f;%s;%s;%s;%s",
                    f.getNome(),
                    f.getCpf(),
                    f.getMatricula(),
                    f.getDataAdmissao(),
                    f.getSalarioBase(),
                    f.getSalarioLiquido(),
                    f.getRegime(),
                    f.getStatus(),
                    f.getCargo(),
                    f.getDepartamento()
            );

            try (FileWriter fw = new FileWriter(caminhoArquivo, true)) {
                fw.write(linha + System.lineSeparator());
            }

            alertHelper.mostrarAlerta("Sucesso", "Funcionário cadastrado com sucesso!");
            limparCampos();

        } catch (ParseException e) {
            e.printStackTrace();
            alertHelper.mostrarAlerta("Erro", "Formato de salário inválido. Use vírgula ou ponto.");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            alertHelper.mostrarAlerta("Erro", "Formato de número inválido. Verifique matrícula e salários.");
        } catch (Exception e) {
            e.printStackTrace();
            alertHelper.mostrarAlerta("Erro", "Verifique os dados informados.");
        }
    }


    private void limparCampos() {
        nomeField.clear();
        cpfField.clear();
        cargoField.clear();
        departamentoField.clear();
        salarioField.clear();
        matriculaField.clear();
        dataAdmissaoPicker.setValue(null);
        regimeCombo.setValue(null);
        statusCombo.setValue(null);
    }
}
