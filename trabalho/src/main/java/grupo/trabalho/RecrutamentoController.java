package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class RecrutamentoController {

    @FXML
    public Button menuButton;
    @FXML
    public Button cadastrarVagasButton;
    @FXML
    public Button listarVagasButton;
    @FXML
    public Button agendarEntrevistaButton;
    @FXML
    public AnchorPane contentArea;
    @FXML
    private Button activeButton;
    @FXML
    public Button cadastrarVagaButton;
    @FXML
    public TextField cargoTextfield;
    @FXML
    public TextField statusTextfield;
    @FXML
    public DatePicker dataAberturaDataPicker;
    @FXML
    public TextField requisitosTextfield;
    @FXML
    public TextField departamentoTextfield;
    @FXML
    public TextField salarioBaseTextfield;

    private MainController mainController;

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    @FXML
    private void voltarMenu() throws IOException {
        mainController.goBackMenu(menuButton);
    }

    public Object loadUI(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            AnchorPane pane = loader.load();

            contentArea.getChildren().clear();
            contentArea.getChildren().add(pane);

            return loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setActiveButton(Button button){
        if (activeButton != null){
            activeButton.setId("buttonLateral");
            activeButton = button;
        }

        button.setId("buttonLateralActive");
        activeButton = button;
    }

    @FXML
    public void clickCadastrarVagaButton(){
        String cargo = cargoTextfield.getText();
        String requisitos = requisitosTextfield.getText();
        String departamento = departamentoTextfield.getText();
        String status = statusTextfield.getText();
        double salarioBase = Double.parseDouble(salarioBaseTextfield.getText());
        LocalDate dataAbertura = dataAberturaDataPicker.getValue();

        Vaga novaVaga = new Vaga(cargo, requisitos, departamento, status, salarioBase, dataAbertura);
        RecrutamentoClasses.addToVagaList(novaVaga);


    }

    @FXML
    public void showCadastrarVagas(){
        loadUI("/grupo/trabalho/cadastrarVaga-view.fxml");
        setActiveButton(cadastrarVagasButton);
    }

    @FXML
    public void showListarVagas(){
        loadUI("/grupo/trabalho/listarVagas-view.fxml");
        setActiveButton(listarVagasButton);
    }

    @FXML
    public void showAgendarEntrevista(){
        loadUI("/grupo/trabalho/agendarEntrevista-view.fxml");
        setActiveButton(agendarEntrevistaButton);
    }
}