package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EntrevistaController {

    @FXML
    private ListView<String> listaEntrevistas;
    @FXML
    private Button cadastrarButton;
    @FXML
    private Button removerButton;
    @FXML
    private Button removerTodosButton;

    private RecrutamentoController recrutamentoController;

    public void setRecrutamentoController(RecrutamentoController controller) {
        this.recrutamentoController = controller;
    }

    public static ArrayList<Entrevista> entrevistasArray = new ArrayList<>();
    private final DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    public void initialize() {
        fetchEntrevistasFromFile();
        refreshList();
    }

    private LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, formatter1);
        } catch (Exception e) {
            return LocalDate.parse(dateString, formatter2);
        }
    }

    @FXML
    public void clickCadastrarEntrevista() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grupo/trabalho/cadastrarEntrevista-view.fxml"));
            Parent root = loader.load();

            CadastrarEntrevistaController controller = loader.getController();
            controller.setEntrevistaController(this); // pass reference

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Cadastrar Entrevista");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEntrevista(Entrevista e) {
        entrevistasArray.add(e);
        saveEntrevistasToFile();
        refreshList();
    }

    @FXML
    public void clickRemoverEntrevista() {
        int index = listaEntrevistas.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Selecione uma entrevista para remover.");
            alert.show();
            return;
        }

        entrevistasArray.remove(index);
        saveEntrevistasToFile();
        refreshList();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Entrevista removida com sucesso!");
        alert.show();
    }

    @FXML
    public void clickRemoverTodasEntrevistas() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar limpeza");
        confirm.setHeaderText("Tem certeza?");
        confirm.setContentText("Isso vai remover todas as entrevistas.");
        if (confirm.showAndWait().get() == javafx.scene.control.ButtonType.OK) {
            entrevistasArray.clear();
            saveEntrevistasToFile();
            refreshList();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Todas as entrevistas foram removidas.");
            alert.show();
        }
    }

    private void refreshList() {
        listaEntrevistas.getItems().clear();
        for (Entrevista e : entrevistasArray) {
            String item = e.getData() + " — " + e.getAvaliador() + (e.getParecer() != null && !e.getParecer().isEmpty() ? " | " + e.getParecer() : "");
            listaEntrevistas.getItems().add(item);
        }
    }

    private void saveEntrevistasToFile() {
        try (FileWriter fw = new FileWriter("entrevistasInfo.txt")) {
            for (Entrevista e : entrevistasArray) {
                fw.write(e.getData() + "," + e.getAvaliador() + "," + (e.getParecer() != null ? e.getParecer() : "") + "," + e.getNota() + System.lineSeparator());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void fetchEntrevistasFromFile() {
        entrevistasArray.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("entrevistasInfo.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",", 4);
                if (p.length >= 2) {
                    LocalDate data = parseDate(p[0]);
                    String avaliador = p[1];
                    Entrevista e = new Entrevista(data, avaliador);
                    if (p.length > 2 && !p[2].isEmpty() && !p[3].isEmpty()) {
                        e.registrarResultado(Double.parseDouble(p[3]), p[2]);
                    }
                    entrevistasArray.add(e);
                }
            }
        } catch (IOException ignored) {}
    }
}



