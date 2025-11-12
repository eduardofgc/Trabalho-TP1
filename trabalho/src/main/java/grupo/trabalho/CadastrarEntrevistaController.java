package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class CadastrarEntrevistaController {

    private EntrevistaController entrevistaController;

    public void setEntrevistaController(EntrevistaController controller) {
        this.entrevistaController = controller;
    }

    @FXML
    private DatePicker dataPicker;
    @FXML
    private TextField avaliadorField;
    @FXML
    private TextField notaField;
    @FXML
    private TextArea parecerField;

    @FXML
    private void clickCadastrar() {
        LocalDate data = dataPicker.getValue();
        String avaliador = avaliadorField.getText();
        String parecer = parecerField.getText();
        double nota;

        try {
            nota = Double.parseDouble(notaField.getText());
        } catch (NumberFormatException e) {
            nota = 0.0;
        }

        Entrevista e = new Entrevista(data, avaliador);
        e.registrarResultado(nota, parecer);

        if (entrevistaController != null) {
            entrevistaController.addEntrevista(e);
        }

        Stage stage = (Stage) dataPicker.getScene().getWindow();
        stage.close();
    }
}

