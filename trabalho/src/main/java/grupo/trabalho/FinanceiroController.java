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
import javafx.stage.FileChooser;
import java.io.IOException;
import javafx.scene.image.Image;

import java.nio.file.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.text.NumberFormat;
import java.util.Locale;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

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
        stage1.getIcons().add(new Image(getClass().getResourceAsStream("/images/logoFinanceiro.png")));
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
    @FXML private Button exportarButton;

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
            List<String> linhas = Files.readAllLines(Paths.get(CAMINHO_ARQUIVO));
            NumberFormat format = NumberFormat.getInstance(new Locale("pt", "BR"));

            for (String linha : linhas) {
                String[] p = linha.split(";");
                if (p.length == 10) {
                    String nome = p[0];
                    String cpf = p[1];
                    int matricula = Integer.parseInt(p[2]);
                    LocalDate dataAdmissao = LocalDate.parse(p[3]);

                    double salarioBase = 0;
                    double salarioLiquido = 0;
                    try {
                        salarioBase = format.parse(p[4].replaceAll("\\s","")).doubleValue();
                        salarioLiquido = format.parse(p[5].replaceAll("\\s","")).doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    RegimeContratacao regime = RegimeContratacao.valueOf(p[6].trim().toUpperCase());
                    StatusFuncionario status = StatusFuncionario.valueOf(p[7].trim().toUpperCase());
                    String cargo = p[8];
                    String departamento = p[9];

                    if (status == StatusFuncionario.ATIVO) {
                        Funcionario f = new Funcionario(nome, cpf, matricula, dataAdmissao,
                                salarioBase, salarioLiquido, regime, status, cargo, departamento);
                        lista.add(f);
                        total += salarioLiquido;
                    }
                }
            }

            tabelaFuncionarios.setItems(lista);
            labelTotal.setText(String.format("Total da folha: R$ %.2f", total));

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlertaErro("Erro ao ler o arquivo de funcionários: " + e.getMessage());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            mostrarAlertaErro("Erro ao converter número: " + e.getMessage());
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
        atualizarTabela();
    }
    @FXML
    private void abrirTelaDetalhes(Funcionario funcionario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("detalhes-salario-view.fxml"));
            Parent root = loader.load();

            DetalhesSalarioController controller = loader.getController();
            controller.setFuncionario(funcionario);

            Stage stage = new Stage();
            stage.setTitle("Detalhes do Salário - " + funcionario.getNome());
            Image icon = new Image(getClass().getResourceAsStream("/images/logoGerarRelatorios.png"));
            stage.getIcons().add(icon);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleExportarRelatorio() {

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Visual", "Visual", "CSV", "PDF");
        dialog.setTitle("Exportar Relatório");
        dialog.setHeaderText("Escolha o formato de exportação");
        dialog.setContentText("Formato:");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logoGerarRelatorios.png")));
        String resultado = dialog.showAndWait().orElse("");

        switch (resultado) {
            case "Visual":
                exportarRelatorioVisual();
                break;
            case "CSV":
                exportarRelatorioCSV();
                break;
            case "PDF":
                exportarRelatorioPDF();
                break;
        }
    }
    private void exportarRelatorioVisual() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("relatorio-folha-view.fxml"));
            Parent root = loader.load();
            RelatorioFolhaController controller = loader.getController();
            controller.carregarDados(tabelaFuncionarios.getItems(), calcularTotalFolha());

            Stage stage = new Stage();
            stage.setTitle("Relatório de Folha de Pagamento - Visual");
            Image icon = new Image(getClass().getResourceAsStream("/images/logoGerarRelatorios.png"));
            stage.getIcons().add(icon);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlertaErro("Erro ao abrir relatório visual: " + e.getMessage());
        }
    }
    private void exportarRelatorioCSV() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Relatório CSV");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Arquivos CSV", "*.csv")
        );
        fileChooser.setInitialFileName("relatorio_folha_" + LocalDate.now() + ".csv");

        File file = fileChooser.showSaveDialog(exportarButton.getScene().getWindow());

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("Nome;CPF;Matricula;Cargo;Departamento;Regime;Salario Base;Salario Liquido;Data Admissao\n");
                for (Funcionario func : tabelaFuncionarios.getItems()) {
                    writer.write(String.format("%s;%s;%d;%s;%s;%s;%.2f;%.2f;%s\n",
                            func.getNome(),
                            func.getCpf(),
                            func.getMatricula(),
                            func.getCargo(),
                            func.getDepartamento(),
                            func.getRegime().toString(),
                            func.getSalarioBase(),
                            func.getSalarioLiquido(),
                            func.getDataAdmissao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    ));
                }
                writer.write(String.format("TOTAL;;;;;;;%.2f;\n", calcularTotalFolha()));

                mostrarAlertaSucesso("Relatório CSV exportado com sucesso!\n" + file.getAbsolutePath());

            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlertaErro("Erro ao exportar CSV: " + e.getMessage());
            }
        }
        try {
            java.awt.Desktop.getDesktop().browse(file.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void exportarRelatorioPDF() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Relatório PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos PDF", "*.pdf"));
        fileChooser.setInitialFileName("relatorio_folha_" + LocalDate.now() + ".pdf");
        File file = fileChooser.showSaveDialog(exportarButton.getScene().getWindow());

        if (file != null) {

            try (PDDocument document = new PDDocument()) {

                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);

                PDPageContentStream content = new PDPageContentStream(document, page);

                float margin = 50;
                float yStart = page.getMediaBox().getHeight() - margin;
                float pageWidth = page.getMediaBox().getWidth();
                float usableWidth = pageWidth - 2 * margin;

                content.setNonStrokingColor(38, 70, 83); // azul escuro
                content.addRect(0, yStart - 20, pageWidth, 40);
                content.fill();

                content.beginText();
                content.setNonStrokingColor(255, 255, 255);
                content.setFont(PDType1Font.HELVETICA_BOLD, 16);
                float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth("RELATÓRIO DE FOLHA DE PAGAMENTO") / 1000 * 16;
                content.newLineAtOffset((pageWidth - titleWidth) / 2, yStart - 10);
                content.showText("RELATÓRIO DE FOLHA DE PAGAMENTO");
                content.endText();

                content.beginText();
                content.setFont(PDType1Font.HELVETICA, 11);
                content.setNonStrokingColor(0, 0, 0);
                content.newLineAtOffset(pageWidth - margin - 200, yStart - 60);
                content.showText("Data: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                content.newLineAtOffset(0, -15);
                content.showText("Preparado por: Recursos Humanos");
                content.newLineAtOffset(0, -15);
                content.showText("Departamento: Administração");
                content.endText();

                float tableTopY = yStart - 130;
                float tableX = margin;
                float rowHeight = 25;

                float[] colWidths = {
                        usableWidth * 0.11f,
                        usableWidth * 0.23f,
                        usableWidth * 0.18f,
                        usableWidth * 0.18f,
                        usableWidth * 0.15f,
                        usableWidth * 0.15f
                };
                String[] headers = {"Matrícula", "Nome", "Cargo", "Departamento", "Salário Bruto", "Salário Líquido"};
                float nextY = tableTopY;

                content.setNonStrokingColor(69, 123, 157);
                content.addRect(tableX, nextY - rowHeight, usableWidth, rowHeight);
                content.fill();

                content.setNonStrokingColor(255, 255, 255);
                content.beginText();
                content.setFont(PDType1Font.HELVETICA_BOLD, 10);

                float currentX = tableX + 5;
                content.newLineAtOffset(currentX, nextY - 13);

                for (int i = 0; i < headers.length; i++) {
                    content.showText(headers[i]);
                    currentX += colWidths[i];
                    content.newLineAtOffset(colWidths[i], 0);
                }
                content.endText();

                nextY -= rowHeight;
                content.setFont(PDType1Font.HELVETICA, 9);
                content.setNonStrokingColor(0, 0, 0);

                for (int index = 0; index < tabelaFuncionarios.getItems().size(); index++) {
                    Funcionario func = tabelaFuncionarios.getItems().get(index);

                    if (nextY < margin + 50) {
                        content.close();
                        page = new PDPage(PDRectangle.A4);
                        document.addPage(page);
                        content = new PDPageContentStream(document, page);
                        nextY = page.getMediaBox().getHeight() - margin - 20;
                        content.setFont(PDType1Font.HELVETICA, 9);
                    }
                    nextY -= rowHeight;
                    if (index % 2 == 0) {
                        content.setNonStrokingColor(240, 240, 240);
                        content.addRect(tableX, nextY, usableWidth, rowHeight);
                        content.fill();
                    }

                    content.beginText();
                    content.setNonStrokingColor(0, 0, 0);
                    content.newLineAtOffset(tableX + 5, nextY + 6);

                    String[] row = {
                            String.valueOf(func.getMatricula()),
                            func.getNome(),
                            func.getCargo(),
                            func.getDepartamento(),
                            String.format("R$ %.2f", func.getSalarioBase()),
                            String.format("R$ %.2f", func.getSalarioLiquido())
                    };

                    float textX = tableX + 5;
                    for (int i = 0; i < row.length; i++) {
                        String text = row[i];
                        if (text.length() > 20) {
                            text = text.substring(0, 17) + "...";
                        }
                        content.showText(text);
                        textX += colWidths[i];
                        content.newLineAtOffset(colWidths[i], 0);
                    }
                    content.endText();
                }
                double totalFolha = calcularTotalFolha();
                double mediaSalarial = tabelaFuncionarios.getItems().isEmpty() ? 0 : totalFolha / tabelaFuncionarios.getItems().size();
                float footerY = Math.max(nextY - 35, margin + 40);

                content.setNonStrokingColor(69, 123, 157);
                content.addRect(tableX, footerY, usableWidth, 25);
                content.fill();
                content.setNonStrokingColor(255, 255, 255);
                content.beginText();
                content.setFont(PDType1Font.HELVETICA_BOLD, 11);
                String totalText = String.format("Total da Folha: R$ %.2f", totalFolha);
                String mediaText = String.format("Média Salarial: R$ %.2f", mediaSalarial);

                float totalWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(totalText) / 1000 * 11;
                float mediaWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(mediaText) / 1000 * 11;
                float spacing = (usableWidth - totalWidth - mediaWidth) / 3;

                content.newLineAtOffset(tableX + spacing, footerY + 8);
                content.showText(totalText);
                content.newLineAtOffset(totalWidth + spacing, 0);
                content.showText(mediaText);
                content.endText();
                content.beginText();
                content.setFont(PDType1Font.HELVETICA_OBLIQUE, 9);
                content.setNonStrokingColor(120, 120, 120);
                content.newLineAtOffset(margin, 30);
                content.showText("Relatório gerado automaticamente em " +
                        LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                content.endText();
                content.close();
                document.save(file);

                mostrarAlertaSucesso("Relatório PDF exportado com sucesso!\n" + file.getAbsolutePath());

            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlertaErro("Erro ao exportar PDF: " + e.getMessage());
            }
        }
    }
    private double calcularTotalFolha() {

        double total = 0;
        for (Funcionario func : tabelaFuncionarios.getItems()) {
            total += func.getSalarioLiquido();
        }
        return total;
    }
    private void mostrarAlertaSucesso(String mensagem) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logoSucesso.png")));
        alert.showAndWait();
    }
    private void mostrarAlertaErro(String mensagem) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}