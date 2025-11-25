package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ListarUsuariosController {

    @FXML private TableView<UsuarioLinha> tabelaUsuarios;
    @FXML private TableColumn<UsuarioLinha, String> colNome;
    @FXML private TableColumn<UsuarioLinha, String> colEmail;
    @FXML private TableColumn<UsuarioLinha, String> colRoles;
    @FXML private TextField campoBusca;
    @FXML private ListView<String> listaElementos;
    @FXML private Button botaoExcluir;
    @FXML private Button botaoLimparTudo;

    private javafx.collections.transformation.FilteredList<UsuarioLinha> filteredData;
    private List<String> originalUsersData;
    private List<String> originalEmailsData;

    @FXML
    public void initialize() {
        colNome.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNome()));
        colEmail.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));
        colRoles.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRoles()));

        campoBusca.textProperty().addListener((obs, oldValue, newValue) -> {
            handleBusca();
        });

        campoBusca.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleBusca();
            }
        });
        loadAllUsers();
    }

    public void loadUsuarios(List<String> lines) {
        javafx.collections.ObservableList<UsuarioLinha> listaOriginal =
                javafx.collections.FXCollections.observableArrayList();

        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");

            if (parts.length >= 6) {
                String nome = parts[0];
                String email = "";
                try {
                    if (i < originalEmailsData.size()) {
                        email = originalEmailsData.get(i);
                    }
                } catch (Exception ignored) {}
                boolean admin = Boolean.parseBoolean(parts[2]);
                boolean gestor = Boolean.parseBoolean(parts[3]);
                boolean candidato = Boolean.parseBoolean(parts[4]);
                boolean recrutador = Boolean.parseBoolean(parts[5]);

                StringBuilder roles = new StringBuilder();
                if (admin) roles.append("Admin ");
                if (gestor) roles.append("Gestor ");
                if (candidato) roles.append("Candidato ");
                if (recrutador) roles.append("Recrutador ");

                listaOriginal.add(new UsuarioLinha(nome, email, roles.toString().trim()));
            }
        }

        filteredData = new javafx.collections.transformation.FilteredList<>(listaOriginal, p -> true);
        tabelaUsuarios.setItems(filteredData);
    }

    private void loadAllUsers() {
        try {
            originalUsersData = Files.readAllLines(Path.of("usuariosInfo.txt"));
            originalEmailsData = Files.readAllLines(Path.of("emailInfo.txt"));
            loadUsuarios(originalUsersData);
        } catch (IOException e) {
            e.printStackTrace();
            AlertHelper.showInfo("Erro ao carregar usuários.");
        }
    }

    @FXML
    public void handleBusca() {
        String searchTerm = campoBusca.getText().trim().toLowerCase();

        if (searchTerm.isEmpty()) {
            loadUsuarios(originalUsersData);
            return;
        }

        List<String> filteredUsers = originalUsersData.stream()
                .filter(line -> {
                    String[] parts = line.split(",");
                    if (parts.length >= 6) {
                        String nome = parts[0].toLowerCase();
                        return nome.contains(searchTerm);
                    }
                    return false;
                })
                .collect(Collectors.toList());

        loadUsuarios(filteredUsers);

        if (filteredUsers.isEmpty()) {
            AlertHelper.showInfo("Nenhum usuário encontrado com o nome: " + searchTerm);
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

        AdmClasses.usuariosArray.removeIf(u -> u.getLogin().equals(nomeSelecionado));

        if (filteredData != null) {
            filteredData.getSource().remove(selected);
        }

        try {
            List<String> allUsers = new java.util.ArrayList<>(Files.readAllLines(Path.of("usuariosInfo.txt")));
            List<String> allEmails = new java.util.ArrayList<>(Files.readAllLines(Path.of("emailInfo.txt")));

            int indexToRemove = -1;
            for (int i = 0; i < allUsers.size(); i++) {
                String[] parts = allUsers.get(i).split(",");
                if (parts.length >= 1 && parts[0].equals(nomeSelecionado)) {
                    indexToRemove = i;
                    break;
                }
            }

            if (indexToRemove != -1) {
                allUsers.remove(indexToRemove);
                if (indexToRemove < allEmails.size()) {
                    allEmails.remove(indexToRemove);
                }
            }

            Files.write(Path.of("usuariosInfo.txt"), allUsers);
            Files.write(Path.of("emailInfo.txt"), allEmails);

            loadAllUsers();

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

                if (filteredData != null) {
                    filteredData.getSource().clear();
                }
                tabelaUsuarios.getItems().clear();

                try {
                    Files.write(Path.of("usuariosInfo.txt"), new java.util.ArrayList<>());
                    Files.write(Path.of("emailInfo.txt"), new java.util.ArrayList<>());
                } catch (IOException e) {
                    e.printStackTrace();
                    AlertHelper.showInfo("Erro ao limpar os arquivos: " + e.getMessage());
                    return;
                }

                AlertHelper.showInfo("Todos os usuários foram removidos.");
                loadAllUsers();
            }
        });
    }
}