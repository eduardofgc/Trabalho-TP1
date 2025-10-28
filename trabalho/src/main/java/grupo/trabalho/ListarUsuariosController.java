package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ListarUsuariosController {

    @FXML
    public ListView<String> listaElementos;
    @FXML
    public Button botaoExcluir;
    @FXML
    public Button botaoLimparTudo;

    @FXML
    public void clickBotaoExcluir(){ //TODO: não funciona
        String loginSelecionado = listaElementos.getSelectionModel().getSelectedItem();
        AdmClasses.usuariosArray.removeIf(u -> u.getLogin().equals(loginSelecionado));
    }

    @FXML
    public void clickBotaoLimparTudo(){
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar limpa");
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
        }
    }

    public void loadUsuarios(List<String> lines) {
        listaElementos.getItems().setAll(lines);
    }
}
