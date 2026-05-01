package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class CadastrarVagaController {

    @FXML private TextField cargoTextfield;
    @FXML private TextField requisitosTextfield;
    @FXML private TextField departamentoTextfield;
    @FXML private TextField statusTextfield;
    @FXML private TextField salarioBaseTextfield;
    @FXML private DatePicker dataAberturaDataPicker;

    public static ArrayList<Vaga> vagasArray = new ArrayList<>();

    @FXML
    public void clickCadastrarVaga() {
        String cargo = cargoTextfield.getText();
        String requisitos = requisitosTextfield.getText();
        String departamento = departamentoTextfield.getText();
        String status = statusTextfield.getText();
        String salarioStr = salarioBaseTextfield.getText();
        LocalDate dataAbertura = dataAberturaDataPicker.getValue();

        if (cargo.isEmpty() || requisitos.isEmpty() || departamento.isEmpty()
                || status.isEmpty() || salarioStr.isEmpty() || dataAbertura == null) {
            AlertHelper.showInfo("Preencha todos os campos!");
            return;
        }

        double salario;
        try {
            salario = Double.parseDouble(salarioStr);
        } catch (NumberFormatException e) {
            AlertHelper.showInfo("Salário inválido! Insira apenas números.");
            return;
        }

        Vaga novaVaga = new Vaga(cargo, requisitos, departamento, status, salario, dataAbertura);

        vagasArray.add(novaVaga);
        saveVagasToFile(novaVaga);

        AlertHelper.showInfo("Vaga cadastrada com sucesso!");

        cargoTextfield.clear();
        requisitosTextfield.clear();
        departamentoTextfield.clear();
        statusTextfield.clear();
        salarioBaseTextfield.clear();
        dataAberturaDataPicker.setValue(null);
    }

    private void saveVagasToFile(Vaga v) {
        try (FileWriter writer = new FileWriter(AppConfig.VAGAS_INFO, true)) {
            writer.write(v.getCargo() + "," + v.getRequisitos() + "," + v.getDepartamento() + ","
                    + v.getStatus() + "," + v.getSalarioBase() + "," + v.getDataAbertura()
                    + System.lineSeparator());
        } catch (IOException e) {
            AlertHelper.showInfo("Erro ao salvar a vaga no arquivo.");
            e.printStackTrace();
        }
    }

    public static void fetchVagasFromFile() {
        vagasArray.clear();
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(AppConfig.VAGAS_INFO))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    double salario = Double.parseDouble(parts[4]);
                    LocalDate data = LocalDate.parse(parts[5]);
                    vagasArray.add(new Vaga(parts[0], parts[1], parts[2], parts[3], salario, data));
                }
            }
        } catch (IOException ignored) {}
    }
}