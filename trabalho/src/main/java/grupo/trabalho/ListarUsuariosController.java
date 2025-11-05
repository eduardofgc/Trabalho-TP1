package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ListarUsuariosController {

    @FXML
    private ListView<String> listaElementos;
    @FXML
    private Button botaoExcluir;
    @FXML
    private Button botaoLimparTudo;

    public void loadUsuarios(List<String> lines) {
        listaElementos.getItems().clear();

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                String nome = parts[0];
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
    }

    @FXML
    public void clickBotaoExcluir() {
        String selected = listaElementos.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertHelper.showInfo("Selecione um usuário para excluir.");
            return;
        }

        String nomeSelecionado = selected.split(" — ")[0];

        Iterator<Usuario> it = AdmClasses.usuariosArray.iterator();
        while (it.hasNext()) {
            Usuario u = it.next();
            if (u.getLogin().equals(nomeSelecionado)) {
                it.remove();
                break;
            }
        }

        listaElementos.getItems().remove(selected);

        try {
            List<String> allLines = Files.readAllLines(Path.of("usuariosInfo.txt"));
            List<String> updated = allLines.stream()
                    .filter(line -> !line.startsWith(nomeSelecionado + ","))
                    .collect(Collectors.toList());

            Files.write(Path.of("usuariosInfo.txt"), updated);
        } catch (IOException e) {
            e.printStackTrace();
        }

        AlertHelper.showInfo("Usuário removido com sucesso!");
    }

    @FXML
    public void clickBotaoLimparTudo() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar limpeza");
        confirm.setHeaderText("Tem certeza?");
        confirm.setContentText("Isso vai remover TODOS os usuários.");

        if (confirm.showAndWait().get() == ButtonType.OK) {
            AdmClasses.usuariosArray.clear();
            listaElementos.getItems().clear();

            try (FileWriter fw = new FileWriter("usuariosInfo.txt", false)) {
                fw.write("");
            } catch (IOException e) {
                e.printStackTrace();
            }

            AlertHelper.showInfo("Todos os usuários foram removidos.");
        }
    }
}
