package grupo.trabalho;

// Importações - Aqui eu tô "puxando" as caixas de ferramentas do JavaFX que eu preciso.
import javafx.fxml.FXML; // A "etiqueta mágica" que liga o Java ao FXML.
import javafx.fxml.FXMLLoader; // O "construtor mestre" que lê o FXML e monta a tela.
import javafx.scene.Parent;   // <-- Importante! Esse é o "Pai" de todos os layouts, vou usar pra POLIMORFISMO.
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane; // <-- Esse é um "Filho" do Parent, um tipo de layout.
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException; // <-- Esse é o "alarme de erro" que eu preciso tratar.

// -----------------------------------------------------------------
// TÉCNICA: MODULARIZAÇÃO (Nível da Classe)
// -----------------------------------------------------------------
// Essa é a minha classe RecrutamentoController.
// Em vez de fazer um "super código" com tudo, eu quebrei o projeto.
// Essa classe (módulo) só cuida das telas de recrutamento.
public class RecrutamentoController {

    // --- ATRIBUTOS ---

    // O @FXML é a "etiqueta mágica" que liga essa variável Java
    // ao botão que eu desenhei lá no FXML (no Scene Builder).
    @FXML
    public Button cadastrarVagasButton; // Ligado ao fx:id="cadastrarVagasButton"
    @FXML
    public Button menuButton; // Ligado ao fx:id="menuButton"


    // -----------------------------------------------------------------
    // TÉCNICA: ENCAPSULAMENTO (Atributos Privados)
    // -----------------------------------------------------------------
    // Esses são atributos internos da minha classe. Eu declarei eles como 'private'.
    // É o "capô do carro": ninguém de fora pode mexer neles diretamente.
    // Isso protege o estado da minha classe.
    private MainController mainController;

    @FXML
    private AnchorPane contentArea; // O FXML consegue injetar aqui por causa do @FXML


    // -----------------------------------------------------------------
    // TÉCNICA: ENCAPSULAMENTO (Método Setter)
    // -----------------------------------------------------------------
    // Como o 'mainController' é privado, eu criei um "portão" público.
    // Esse é um método 'setter', que permite que outra classe (o MainController)
    // se "apresente" pra mim e me entregue a referência dele.
    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }


    @FXML
    private void voltarMenu() {
        // -----------------------------------------------------------------
        // TÉCNICA: TRATAMENTO DE EXCEÇÕES (Try-Catch)
        // -----------------------------------------------------------------
        // Carregar FXML é uma operação arriscada (o arquivo pode sumir).
        // Então, eu coloco o código perigoso dentro de um bloco 'try' (tentar).
        try {
            // -----------------------------------------------------------------
            // TÉCNICA: DELEGAÇÃO (Composição)
            // -----------------------------------------------------------------
            // Meu RecrutamentoController não sabe como voltar ao menu.
            // Em vez de tentar, eu "deleto" a tarefa pro 'mainController',
            // que é o especialista em navegação. É o chefe mandando o padeiro fazer o pão.
            mainController.goBackMenu(menuButton);

        } catch (IOException e) {
            // Esse é o "plano B". Se o 'try' falhar (a "panela explodir"),
            // o 'catch' pega o erro e o programa não quebra.
            // Aqui eu só imprimo o erro pra eu saber o que deu errado.
            e.printStackTrace();
        }
    }


    @FXML
    private void showCadastrarVagas() {
        // -----------------------------------------------------------------
        // TÉCNICA: MODULARIZAÇÃO (Reutilização de Código)
        // -----------------------------------------------------------------
        // Em vez de repetir o código de carregar FXML em todo botão,
        // eu criei um método só pra isso (o 'loadSubView') e chamo ele aqui.
        // Isso é modularizar e reutilizar o código.
        loadSubView("/grupo/trabalho/cadastrarVaga-view.fxml");
    }

    @FXML
    private void showListarVagas() {
        // E aqui eu reutilizo o *mesmo* método pra carregar outra tela.
        loadSubView("/grupo/trabalho/listarVagas-view.fxml");
    }

    @FXML
    private void showAgendarEntrevista() {
        // TRATAMENTO DE EXCEÇÕES (de novo, porque vou carregar FXML)
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/grupo/trabalho/agendarEntrevista-view.fxml"));

            // -----------------------------------------------------------------
            // TÉCNICA: POLIMORFISMO (A grande mágica do JavaFX)
            // -----------------------------------------------------------------
            // O 'loader.load()' vai me devolver o layout que eu desenhei no FXML.
            // Podia ser um AnchorPane, um VBox, um Pane...
            // Mas eu não preciso saber! Eu guardo ele numa variável 'Parent',
            // que é a "superclasse" (o "pai") de todos os layouts.
            // É o meu "controle remoto universal": eu trato todos os layouts
            // diferentes do mesmo jeito genérico.
            Parent view = loader.load();


            // -----------------------------------------------------------------
            // TÉCNICA: COMPOSIÇÃO E DELEGAÇÃO
            // -----------------------------------------------------------------
            // 1. Eu pego o controlador da tela que eu acabei de carregar.
            EntrevistaController controller = loader.getController();
            // 2. Eu "converso" com ele: passo uma referência de mim mesmo ('this') pra ele,
            //    assim ele pode se comunicar de volta comigo.
            //    Minha classe está "composta" por outra classe controladora.
            controller.setRecrutamentoController(this);

            // MODULARIZAÇÃO (da Interface)
            // Agora eu pego a tela (view) que eu carreguei e coloco ela
            // dentro da minha "área de conteúdo" (o 'contentArea').
            contentArea.getChildren().clear(); // Limpo o que estava antes
            contentArea.getChildren().add(view); // Adiciono a nova tela

            // ... código de layout ...

        } catch (IOException e) {
            // O "plano B" se o FXML de entrevista não for encontrado.
            e.printStackTrace();
        }
    }


    // Este é o meu método auxiliar privado.
    // Por ser 'private', ele é parte do ENCAPSULAMENTO. Só a minha classe usa ele.
    private void loadSubView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

            // POLIMORFISMO (de novo, o mesmo conceito)
            Parent view = loader.load();

            // MODULARIZAÇÃO (de novo)
            // Pego a tela carregada e encaixo ela no espaço 'contentArea'.
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);
            // ... código de layout ...

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}