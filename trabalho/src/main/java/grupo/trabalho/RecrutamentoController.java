package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

// TÉCNICA: POLIMORFISMO (Importação da Superclasse Parent)
// 'Parent' é uma superclasse para todos os layouts (AnchorPane, VBox, etc.).
// Vamos usá-lo para tratar diferentes layouts de forma genérica.
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane; // Uma subclasse de Parent
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException; // A classe de exceção que vamos tratar



// TÉCNICA: MODULARIZAÇÃO
// Esta classe é um "Controlador". Sua responsabilidade é clara e única:
// gerenciar a lógica e os eventos da tela de Recrutamento.
public class RecrutamentoController {

    @FXML
    public Button listarVagasButton;
    @FXML
    public Button cadastrarVagasButton;
    @FXML
    public Button agendarEntrevistaButton;
    @FXML
    public Button menuButton;

    @FXML
    public void onHover(javafx.scene.input.MouseEvent e) {
        ((Button)e.getSource()).setStyle(
                "-fx-background-color: #e8eef5; -fx-text-fill: #2c3e50; -fx-font-size: 14px; " +
                        "-fx-padding: 10 20 10 20; -fx-background-radius: 8; -fx-cursor: hand; -fx-scale-x: 1.03; -fx-scale-y: 1.03;"
        );
    }

    @FXML
    public void onExit(javafx.scene.input.MouseEvent e) {
        ((Button)e.getSource()).setStyle(
                "-fx-background-color: white; -fx-text-fill: #2c3e50; -fx-font-size: 14px; " +
                        "-fx-padding: 10 20 10 20; -fx-background-radius: 8; -fx-cursor: hand; -fx-scale-x: 1; -fx-scale-y: 1;"
        );
    }

    @FXML
    public void onPress(javafx.scene.input.MouseEvent e) {
        ((Button)e.getSource()).setStyle(((Button)e.getSource()).getStyle() + "-fx-scale-x: 0.97; -fx-scale-y: 0.97;");
    }

    @FXML
    public void onRelease(javafx.scene.input.MouseEvent e) {
        ((Button)e.getSource()).setStyle(((Button)e.getSource()).getStyle() + "-fx-scale-x: 1.03; -fx-scale-y: 1.03;");
    }

    @FXML
    public void onHoverDark(javafx.scene.input.MouseEvent e) {
        ((Button)e.getSource()).setStyle(
                "-fx-background-color: #1f2b38; -fx-text-fill: white; -fx-font-size: 14px; " +
                        "-fx-padding: 10 20 10 20; -fx-background-radius: 8; -fx-cursor: hand; -fx-scale-x: 1.04; -fx-scale-y: 1.04;"
        );
    }

    @FXML
    public void onExitDark(javafx.scene.input.MouseEvent e) {
        ((Button)e.getSource()).setStyle(
                "-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-font-size: 14px; " +
                        "-fx-padding: 10 20 10 20; -fx-background-radius: 8; -fx-cursor: hand; -fx-scale-x: 1; -fx-scale-y: 1;"
        );
    }

    @FXML
    public void onReleaseDark(javafx.scene.input.MouseEvent e) {
        ((Button)e.getSource()).setStyle(
                "-fx-background-color: #1f2b38; -fx-text-fill: white; -fx-font-size: 14px; " +
                        "-fx-padding: 10 20 10 20; -fx-background-radius: 8; -fx-cursor: hand; -fx-scale-x: 1.04; -fx-scale-y: 1.04;"
        );
    }


    // TÉCNICA: ENCAPSULAMENTO
    // Este atributo é 'private', protegendo-o do acesso direto
    // por outras classes. O acesso será controlado via metodo 'setMainController'.
    // TÉCNICA: COMPOSIÇÃO
    // A classe 'RecrutamentoController' "tem um" 'MainController',
    // o que indica composição (uma classe contendo outra para colaborar).
    private MainController mainController;

    @FXML


    // TÉCNICA: ENCAPSULAMENTO
    // Outro atributo 'private' que só pode ser manipulado
    // de dentro desta classe.
    private AnchorPane contentArea;



    // TÉCNICA: ENCAPSULAMENTO (Metodo Setter)
    // Este metodo 'public' é o "portão" de acesso controlado
    // ao atributo 'private mainController'. É assim que
    // outra classe nos "injeta" a referência do controlador principal.
    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    @FXML
    private void voltarMenu() {

        // TÉCNICA: TRATAMENTO DE EXCEÇÕES (Checked Exception)
        // O metodo 'goBackMenu' provavelmente lança uma 'IOException' (exceção checada),
        // que é um risco ao carregar arquivos FXML.
        // O bloco 'try' tenta executar o código arriscado.
        try {
            // TÉCNICA: DELEGAÇÃO
            // Este controlador não sabe como voltar ao menu. Ele "delega"
            // (passa a responsabilidade) para o 'mainController', que é
            // o especialista em navegação principal.
            mainController.goBackMenu(menuButton);
        } catch (IOException e) {
            // O bloco 'catch' é o "plano de contingência". Se o 'try' falhar,
            // o programa não quebra. Ele captura o erro e o imprime no console
            e.printStackTrace();
        }
    }

    @FXML
    private void showCadastrarVagas() {

        // TÉCNICA: LÓGICA DE NEGÓCIO (Controle de Acesso)
        // Esta é a implementação de uma regra de negócio do projeto:
        // "Somente Gestores podem criar vagas".
        if (HelloController.currentUser.isGestor){

            // TÉCNICA: MODULARIZAÇÃO (Reúso de Código)
            // Chama um metodo auxiliar privado para carregar a tela,
            // evitando repetir o código de carregamento várias vezes.
            loadSubView("/grupo/trabalho/cadastrarVaga-view.fxml");
        }
        else{
            // TÉCNICA: FEEDBACK AO USUÁRIO
            // Informa ao usuário por que a ação falhou (regra de negócio).
            AlertHelper.showInfo("Erro: você não tem permissão de gestor.");
        }
    }

    @FXML
    private void showListarVagas(){

        // TÉCNICA: LÓGICA DE NEGÓCIO (Controle de Acesso)
        // Implementação de outra regra de negócio:
        // Gestores E Recrutadores podem listar vagas.
        if (HelloController.currentUser.isGestor || HelloController.currentUser.isRecrutador){
            loadSubView("/grupo/trabalho/listarVagas-view.fxml");
        }
        else{
            AlertHelper.showInfo("Erro: você não tem permissão de gestor ou recrutador.");
        }
    }

    @FXML
    private void showAgendarEntrevista() {

        // TÉCNICA: LÓGICA DE NEGÓCIO (Controle de Acesso)
        // Implementação da regra: "Somente Recrutadores podem agendar entrevistas".

        if (HelloController.currentUser.isRecrutador){

            // TÉCNICA: TRATAMENTO DE EXCEÇÕES
            // O 'try-catch' é necessário porque 'loader.load()'
            // pode lançar uma 'IOException'.
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/grupo/trabalho/agendarEntrevista-view.fxml"));

                // TÉCNICA: POLIMORFISMO
                // O metodo 'loader.load()' retorna um objeto concreto (ex: AnchorPane).
                // Nós o armazenamos em uma variável do tipo 'Parent' (a superclasse).
                // Isso permite tratar qualquer layout de forma genérica ("controle universal").
                Parent view = loader.load();

                EntrevistaController controller = loader.getController();
                controller.setRecrutamentoController(this);

                // TÉCNICA: MODULARIZAÇÃO (de Interface) outra aparição de interface.
                // A nova tela (view) é injetada na área de conteúdo
                // da tela principal.
                contentArea.getChildren().clear();
                contentArea.getChildren().add(view);
                AnchorPane.setTopAnchor(view, 0.0);
                AnchorPane.setBottomAnchor(view, 0.0);
                AnchorPane.setLeftAnchor(view, 0.0);
                AnchorPane.setRightAnchor(view, 0.0);

            } catch (IOException e) {
                // O "plano B" se o FXML da entrevista não for encontrado.
                e.printStackTrace();
            }
        }
        else{
            AlertHelper.showInfo("Erro: você não tem permissão de recrutador.");
            //aviso se  o catch pegar alguma excessão
        }

    }


    // TÉCNICA: MODULARIZAÇÃO (Metodo Auxiliar Privado)
    // Este metodo encapsula (esconde) a lógica repetitiva de carregar uma tela.
    // Os métodos 'show...' não precisam saber *como* a tela é carregada,
    // apenas *qual* tela carregar. Isso é bom para reutilização e manutenção.
    private void loadSubView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

            // TÉCNICA: POLIMORFISMO (novamente)
            // O mesmo princípio é aplicado aqui, tornando este metodo
            // capaz de carregar qualquer FXML, não importa seu layout raiz.
            Parent view = loader.load();

            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);
            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TÉCNICA (Ausente): HERANÇA
    // Esta classe 'RecrutamentoController' não usa 'extends',
    // portanto, não está herdando de nenhuma classe pai personalizada.
}