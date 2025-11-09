package grupo.trabalho;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.image.Image;

import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;

public class FinanceiroController {
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void goBackFinanceiro(Button exitButton) throws IOException {
        Stage stage0 = (Stage) exitButton.getScene().getWindow();
        stage0.close();

        Stage stage1 = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grupo/trabalho/financeiro-view.fxml"));
        Parent root = loader.load();
        stage1.getIcons().clear();
        stage1.getIcons().add(new Image(
                getClass().getResourceAsStream("/images/logoFinanceiro.png")
        ));
        Scene scene = new Scene(root);
        stage1.setScene(scene);
        stage1.setTitle("Financeiro");
        stage1.setResizable(false);
        stage1.show();
    }
    @FXML private Button menuButton;
    @FXML private TableView<Funcionario> tabelaFuncionarios;
    @FXML private TableColumn<Funcionario, String> colNome;
    @FXML private TableColumn<Funcionario, String> colCpf;
    @FXML private TableColumn<Funcionario, String> colMatricula;
    @FXML private TableColumn<Funcionario, String> colCargo;
    @FXML private TableColumn<Funcionario, String> colDepartamento;
    @FXML private TableColumn<Funcionario, String> colRegime;
    @FXML private TableColumn<Funcionario, Double> colSalarioBase;
    @FXML private TableColumn<Funcionario, Double> colSalarioLiquido;
    @FXML private TableColumn<Funcionario, String> colDataAdmissao;
    @FXML private Label labelTotal;
    @FXML private ComboBox<String> comboCargo;
    @FXML private ComboBox<String> comboRegime;
    @FXML private ComboBox<String> comboStatus;
    @FXML private ComboBox<String> comboDepartamento;

    @FXML
    private void handleMenuButton() throws IOException {
        if (mainController != null) {
            mainController.goBackMenu(menuButton);
        } else {
            System.err.println("MainController não foi configurado!");
        }
    }

    private final String CAMINHO_ARQUIVO = "dados_Funcionarios.txt";

    @FXML
    public void atualizarTabela() {
        ObservableList<Funcionario> lista = FXCollections.observableArrayList();
        double total = 0;

        try {
            System.out.println("Procurando arquivo em: " + Paths.get(CAMINHO_ARQUIVO).toAbsolutePath());

            List<String> linhas = Files.readAllLines(Paths.get(CAMINHO_ARQUIVO));
            for (String linha : linhas) {
                String[] p = linha.split(";");
                if (p.length == 10) {
                    String nome = p[0];
                    String cpf = p[1];
                    int matricula = Integer.parseInt(p[2]);
                    LocalDate dataAdmissao = LocalDate.parse(p[3]);
                    double salarioBase = Double.parseDouble(p[4]);
                    double salarioLiquido = Double.parseDouble(p[5]);
                    RegimeContratacao regime = RegimeContratacao.valueOf(p[6].trim().toUpperCase());
                    StatusFuncionario status = StatusFuncionario.valueOf(p[7].trim().toUpperCase());
                    String cargo = p[8];
                    String departamento = p[9];

                    if (status == StatusFuncionario.ATIVO) { // só haverá funcionários ATIVO na tabela
                        Funcionario f = new Funcionario(nome, cpf, matricula, dataAdmissao, salarioBase,
                                salarioLiquido, regime, status, cargo, departamento);
                        lista.add(f);
                        total += salarioLiquido;
                    }
                }
            }
            tabelaFuncionarios.setItems(lista);
            labelTotal.setText(String.format("Total da folha: R$ %.2f", total));

        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        }
    }

    @FXML
    public void initialize() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        colDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));
        colRegime.setCellValueFactory(new PropertyValueFactory<>("regime"));
        colSalarioBase.setCellValueFactory(new PropertyValueFactory<>("salarioBase"));
        colSalarioLiquido.setCellValueFactory(new PropertyValueFactory<>("salarioLiquido"));
        colDataAdmissao.setCellValueFactory(new PropertyValueFactory<>("dataAdmissao"));
        comboRegime.setItems(FXCollections.observableArrayList("CLT", "ESTAGIO", "PJ"));
        comboStatus.setItems(FXCollections.observableArrayList("ATIVO", "INATIVO"));
        comboCargo.setItems(FXCollections.observableArrayList("Analista", "Estagiário", "Consultor", "Secretária"));
        comboDepartamento.setItems(FXCollections.observableArrayList("Financeiro", "TI", "RH", "Administração"));

        atualizarTabela();

        tabelaFuncionarios.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // clique duplo
                Funcionario selecionado = tabelaFuncionarios.getSelectionModel().getSelectedItem();
                if (selecionado != null) {
                    abrirTelaDetalhes(selecionado);
                }
            }
        });
    }
    @FXML
    private void filtrarFuncionarios() {
        ObservableList<Funcionario> listaFiltrada = FXCollections.observableArrayList();
        double total = 0;

        try {
            List<String> linhas = Files.readAllLines(Paths.get(CAMINHO_ARQUIVO));

            for (String linha : linhas) {
                String[] p = linha.split(";");
                if (p.length == 10) {
                    String nome = p[0];
                    String cpf = p[1];
                    int matricula = Integer.parseInt(p[2]);
                    LocalDate dataAdmissao = LocalDate.parse(p[3]);
                    double salarioBase = Double.parseDouble(p[4]);
                    double salarioLiquido = Double.parseDouble(p[5]);
                    RegimeContratacao regime = RegimeContratacao.valueOf(p[6].trim().toUpperCase());
                    StatusFuncionario status = StatusFuncionario.valueOf(p[7].trim().toUpperCase());
                    String cargo = p[8];
                    String departamento = p[9];

                    // 🔍 FILTROS
                    boolean condicao = true;

                    if (comboCargo.getValue() != null && !comboCargo.getValue().isEmpty())
                        condicao &= cargo.equalsIgnoreCase(comboCargo.getValue());

                    if (comboRegime.getValue() != null && !comboRegime.getValue().isEmpty())
                        condicao &= regime.name().equalsIgnoreCase(comboRegime.getValue());

                    if (comboStatus.getValue() != null && !comboStatus.getValue().isEmpty())
                        condicao &= status.name().equalsIgnoreCase(comboStatus.getValue());

                    if (comboDepartamento.getValue() != null && !comboDepartamento.getValue().isEmpty())
                        condicao &= departamento.equalsIgnoreCase(comboDepartamento.getValue());

                    if (condicao) {
                        Funcionario f = new Funcionario(nome, cpf, matricula, dataAdmissao,salarioBase,
                                salarioLiquido, regime, status, cargo, departamento);
                        listaFiltrada.add(f);
                        total += salarioLiquido;
                    }
                }
            }

            tabelaFuncionarios.setItems(listaFiltrada);
            labelTotal.setText(String.format("Total da folha: R$ %.2f", total));

        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        }
    }
    @FXML
    private void limparFiltros() {
        comboCargo.setValue(null);
        comboRegime.setValue(null);
        comboStatus.setValue(null);
        comboDepartamento.setValue(null);
        atualizarTabela(); // recarrega todos os funcionários
    }

    @FXML
    private void abrirTelaDetalhes(Funcionario funcionario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("detalhes-salario-view.fxml"));
            Parent root = loader.load();

            // Pega o controller da nova tela e passa o funcionário selecionado
            DetalhesSalarioController controller = loader.getController();
            controller.setFuncionario(funcionario);

            Stage stage = new Stage();
            stage.setTitle("Detalhes do Salário - " + funcionario.getNome());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exportarCSV() {
        try {
            StringBuilder sb = new StringBuilder();
            for (Funcionario f : tabelaFuncionarios.getItems()) {
                sb.append(String.format("%s;%s;%s;%s;%s;%.2f;%s%n",
                        f.getNome(), f.getCpf(), f.getMatricula(),
                        f.getCargo(), f.getDepartamento(),
                        f.getSalarioBase(), f.getRegime()));
            }

            Files.write(Paths.get("dadosFuncionarios.txt"), sb.toString().getBytes());
            System.out.println("Relatório exportado para dados/relatorio_financeiro.csv");
        } catch (IOException e) {
            System.err.println("Erro ao exportar CSV: " + e.getMessage());
        }
    }
}