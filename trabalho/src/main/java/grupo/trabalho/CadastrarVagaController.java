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

    @FXML
    private TextField cargoTextfield;
    @FXML
    private TextField requisitosTextfield;
    @FXML
    private TextField departamentoTextfield;
    @FXML
    private TextField statusTextfield;
    @FXML
    private TextField salarioBaseTextfield;
    @FXML
    private DatePicker dataAberturaDataPicker;

    public static ArrayList<Vaga> vagasArray = new ArrayList<>();

    @FXML
    public void clickCadastrarVaga() {
        String cargo = cargoTextfield.getText();
        String requisitos = requisitosTextfield.getText();
        String departamento = departamentoTextfield.getText();
        String status = statusTextfield.getText();
        String salarioStr = salarioBaseTextfield.getText();
        LocalDate dataAbertura = dataAberturaDataPicker.getValue();

        if (cargo.isEmpty() || requisitos.isEmpty() || departamento.isEmpty() || status.isEmpty() || salarioStr.isEmpty() || dataAbertura == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos!");
            alert.show();
            return;
        }

        double salario;
        try {
            salario = Double.parseDouble(salarioStr);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Salário inválido!");
            alert.show();
            return;
        }

        Vaga novaVaga = new Vaga(cargo, requisitos, departamento, status, salario, dataAbertura);
        vagasArray.add(novaVaga);
        saveVagasToFile();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Vaga cadastrada");
        alert.setHeaderText(null);
        alert.setContentText("Vaga cadastrada com sucesso!");
        alert.show();

        cargoTextfield.clear();
        requisitosTextfield.clear();
        departamentoTextfield.clear();
        statusTextfield.clear();
        salarioBaseTextfield.clear();
        dataAberturaDataPicker.setValue(null);
    }

    private void saveVagasToFile() {
        try (FileWriter writer = new FileWriter("vagasInfo.txt")) {
            for (Vaga v : vagasArray) {
                writer.write(v.getCargo() + "," + v.getRequisitos() + "," + v.getDepartamento() + "," +
                        v.getStatus() + "," + v.getSalarioBase() + "," + v.getDataAbertura() + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fetchVagasFromFile() {
        vagasArray.clear();
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader("vagasInfo.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String cargo = parts[0];
                    String requisitos = parts[1];
                    String departamento = parts[2];
                    String status = parts[3];
                    double salario = Double.parseDouble(parts[4]);
                    LocalDate data = LocalDate.parse(parts[5]);
                    Vaga v = new Vaga(cargo, requisitos, departamento, status, salario, data);
                    vagasArray.add(v);
                }
            }
        } catch (IOException ignored) {}
    }
}
