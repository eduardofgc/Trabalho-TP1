package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ListarUsuariosController {

    @FXML private TableView<UsuarioLinha> tabelaUsuarios;
    @FXML private TableColumn<UsuarioLinha, String> colNome;
    @FXML private TableColumn<UsuarioLinha, String> colEmail;
    @FXML private TableColumn<UsuarioLinha, String> colRoles;

    @FXML
    public void initialize() {
        colNome.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNome()));
        colEmail.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));
        colRoles.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRoles()));
    }

    @FXML
    private ListView<String> listaElementos;
    @FXML
    private Button botaoExcluir;
    @FXML
    private Button botaoLimparTudo;

    public void loadUsuarios(List<String> lines) {
        tabelaUsuarios.getItems().clear();

        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");

            if (parts.length >= 6) {
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

                tabelaUsuarios.getItems().add(
                        new UsuarioLinha(nome, email, roles.toString().trim())
                );
            }
        }
    }


    @FXML
    public void clickBotaoExcluir() {
        UsuarioLinha selected = tabelaUsuarios.getSelectionModel().getSelectedItem();

        if (selected == null) {
            AlertHelper.showInfo("Selecione um usuário para excluir.");
            return;
        }

        String nomeSelecionado = selected.getNome();


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

        indexToRemove = tabelaUsuarios.getSelectionModel().getSelectedIndex();
        tabelaUsuarios.getItems().remove(indexToRemove);


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

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {

                AdmClasses.usuariosArray.clear();

                if (tabelaUsuarios != null) {
                    tabelaUsuarios.getItems().clear();
                }

                try (FileWriter fw = new FileWriter("usuariosInfo.txt", false);
                     FileWriter emailFw = new FileWriter("emailInfo.txt", false)) {

                    fw.write("");
                    emailFw.write("");
                } catch (IOException e) {
                    e.printStackTrace();
                    AlertHelper.showInfo("Erro ao limpar os arquivos: " + e.getMessage());
                    return;
                }

                AlertHelper.showInfo("Todos os usuários foram removidos.");
            }
        });
    }

}