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
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts.length >= 6){
                String nome = parts[0];
                String email = "";
                try { email = Files.readAllLines(Path.of("emailInfo.txt")).get(i); } catch (IOException ignored) {}
                boolean admin = Boolean.parseBoolean(parts[2]);
                boolean gestor = Boolean.parseBoolean(parts[3]);
                boolean candidato = Boolean.parseBoolean(parts[4]);
                boolean recrutador = Boolean.parseBoolean(parts[5]);
                StringBuilder roles = new StringBuilder();
                if (admin) roles.append("Admin ");
                if (gestor) roles.append("Gestor ");
                if (candidato) roles.append("Candidato ");
                if (recrutador) roles.append("Recrutador ");
                String formatted = String.format("%s (%s) — %s", nome, email, roles.toString().trim());
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

        String nomeSelecionado = selected.split(" \\(")[0];

        int indexToRemove = -1;

        for (int i = 0; i < AdmClasses.usuariosArray.size(); i++) {
            if (AdmClasses.usuariosArray.get(i).getLogin().equals(nomeSelecionado)) {
                indexToRemove = i;
                break;
            }
        }

        if (indexToRemove == -1) {
            AlertHelper.showInfo("Usuário não encontrado no sistema.");
            return;
        }

        AdmClasses.usuariosArray.remove(indexToRemove);

        listaElementos.getItems().remove(selected);

        try {
            List<String> allLines = Files.readAllLines(Path.of("usuariosInfo.txt"));
            List<String> updatedUsers = allLines.stream()
                    .filter(line -> !line.startsWith(nomeSelecionado + ","))
                    .collect(Collectors.toList());
            Files.write(Path.of("usuariosInfo.txt"), updatedUsers);

            List<String> allEmails = Files.readAllLines(Path.of("emailInfo.txt"));
            if (indexToRemove < allEmails.size()) {
                allEmails.remove(indexToRemove);
            }
            Files.write(Path.of("emailInfo.txt"), allEmails);

        } catch (IOException e) {
            e.printStackTrace();
            AlertHelper.showInfo("Erro ao atualizar os arquivos.");
            return;
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
            try (FileWriter fw = new FileWriter("usuariosInfo.txt", false);
                 FileWriter emailFw = new FileWriter("emailInfo.txt", false)) {
                fw.write("");
                emailFw.write("");
            } catch (IOException e) {
                e.printStackTrace();
            }
            AlertHelper.showInfo("Todos os usuários foram removidos.");
        }
    }
}