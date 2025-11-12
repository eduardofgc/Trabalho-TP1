package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ListarVagasController {

    @FXML
    public Button botaoExcluir;
    @FXML
    public Button botaoLimparTudo;
    @FXML
    public ListView<String> listaElementos;
    @FXML
    private TextField campoBusca;

    public static class Vaga {
        String cargo, requisitos, departamento, status;
        double salarioBase;
        String dataAbertura;

        Vaga(String cargo, String requisitos, String departamento, String status, double salarioBase, String dataAbertura) {
            this.cargo = cargo;
            this.requisitos = requisitos;
            this.departamento = departamento;
            this.status = status;
            this.salarioBase = salarioBase;
            this.dataAbertura = dataAbertura;
        }

        @Override
        public String toString() {
            return cargo + " | " + departamento + " | " + status + " | " + salarioBase + " | " + dataAbertura;
        }
    }

    public static List<Vaga> vagasArray = new ArrayList<>();

    @FXML
    public void initialize() {
        fetchVagasFromArchive();
        renderVagas();
    }

    public void renderVagas() {
        listaElementos.getItems().clear();
        for (Vaga v : vagasArray) {
            listaElementos.getItems().add(v.toString());
        }
    }

    public void fetchVagasFromArchive() {
        vagasArray.clear();
        try {
            List<String> lines = Files.readAllLines(Path.of("vagasInfo.txt"));
            for (String line : lines) {
                String[] p = line.split(",");
                if (p.length == 6) {
                    Vaga v = new Vaga(
                            p[0],
                            p[1],
                            p[2],
                            p[3],
                            Double.parseDouble(p[4]),
                            p[5]
                    );
                    vagasArray.add(v);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveVaga(Vaga v) {
        try (FileWriter writer = new FileWriter("vagasInfo.txt", true)) {
            writer.write(String.join(",", v.cargo, v.requisitos, v.departamento, v.status, String.valueOf(v.salarioBase), v.dataAbertura) + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickBotaoExcluir() {
        String selected = listaElementos.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.INFORMATION, "Selecione uma vaga para excluir.").show();
            return;
        }

        String cargoSelecionado = selected.split(" \\| ")[0];

        Iterator<Vaga> it = vagasArray.iterator();
        while (it.hasNext()) {
            Vaga v = it.next();
            if (v.cargo.equals(cargoSelecionado)) {
                it.remove();
                break;
            }
        }

        listaElementos.getItems().remove(selected);

        try {
            List<String> allLines = Files.readAllLines(Path.of("vagasInfo.txt"));
            List<String> updated = allLines.stream()
                    .filter(line -> !line.startsWith(cargoSelecionado + ","))
                    .collect(Collectors.toList());
            Files.write(Path.of("vagasInfo.txt"), updated);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Alert(Alert.AlertType.INFORMATION, "Vaga removida com sucesso!").show();
    }

    @FXML
    public void clickBotaoLimparTudo() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Isso vai remover todas as vagas.", ButtonType.OK, ButtonType.CANCEL);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                vagasArray.clear();
                listaElementos.getItems().clear();
                try (FileWriter fw = new FileWriter("vagasInfo.txt", false)) {
                    fw.write("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                new Alert(Alert.AlertType.INFORMATION, "Todas as vagas foram removidas.").show();
            }
        });
    }
}
