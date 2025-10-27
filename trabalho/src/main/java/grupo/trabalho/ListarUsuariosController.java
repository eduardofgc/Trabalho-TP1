package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import java.util.List;

public class ListarUsuariosController {

    @FXML
    public static ListView<String> listaElementos;

    public void loadUsuarios(List<String> lines) {
        listaElementos.getItems().setAll(lines);
    }
}
