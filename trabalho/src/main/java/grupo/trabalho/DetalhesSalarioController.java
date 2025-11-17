package grupo.trabalho;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.util.Locale;

public class DetalhesSalarioController {
    AlertHelper alertHelper = new AlertHelper();
    @FXML private Button btnAlterarStatus;
    @FXML private Label labelNome;
    @FXML private Label labelSalarioBase;
    @FXML private Label labelSalarioLiquido;
    @FXML private Label labelProventos;
    @FXML private Label labelDescontos;
    @FXML private Label labelLiquido;
    @FXML private TableView<LancamentoTabela> tabelaLancamentos;
    @FXML private TableColumn<LancamentoTabela, String> colTipo;
    @FXML private TableColumn<LancamentoTabela, String> colDescricao;
    @FXML private TableColumn<LancamentoTabela, Double> colValor;
    private static final String CAMINHO_LANCAMENTOS = "lancamentos.txt";
    private static final String CAMINHO_FUNCIONARIOS = "trabalho/dados_Funcionarios.txt";
    private Funcionario funcionario;

    private List<Lancamento> proventos = new ArrayList<>();
    private List<Lancamento> descontos = new ArrayList<>();
    private ObservableList<LancamentoTabela> listaTabela = FXCollections.observableArrayList();


    @FXML
    private void initialize() {
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tabelaLancamentos.setItems(listaTabela);
    }


    public void setFuncionario(Funcionario f) {
        this.funcionario = f;
        carregarLancamentos();
        atualizarTela();
        btnAlterarStatus.setText("Status: " + funcionario.getStatus());
    }
    private void carregarLancamentos() {
        proventos.clear();
        descontos.clear();
        listaTabela.clear();

        try {
            if (!Files.exists(Paths.get(CAMINHO_LANCAMENTOS))) return;
            List<String> linhas = Files.readAllLines(Paths.get(CAMINHO_LANCAMENTOS));

            for (String linha : linhas) {
                String[] p = linha.split(";");
                if (p.length == 4) {
                    String cpf = p[0];
                    String tipo = p[1];
                    String descricao = p[2];
                    double valor = parseDoubleComVirgula(p[3]);

                    if (cpf.equals(funcionario.getCpf())) {
                        Lancamento l = new Lancamento(descricao, valor);
                        if (tipo.equalsIgnoreCase("Provento")) {
                            proventos.add(l);
                        } else {
                            descontos.add(l);
                        }
                        listaTabela.add(new LancamentoTabela(tipo, descricao, valor));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler lançamentos: " + e.getMessage());
        }
    }
    private void atualizarSalarioNoArquivo(double novoSalario) {
        Path caminho = Paths.get(CAMINHO_FUNCIONARIOS);

        if (!Files.exists(caminho)) {
            System.err.println("Arquivo de funcionários não encontrado em: " + caminho.toAbsolutePath());
            return;
        }

        try {
            List<String> linhas = Files.readAllLines(caminho);
            List<String> novasLinhas = new ArrayList<>();
            boolean atualizado = false;

            for (String linha : linhas) {
                String[] p = linha.split(";");
                if (p.length == 10) {
                    String cpfLinha = p[1].trim();

                    if (cpfLinha.equals(funcionario.getCpf().trim())) {
                        p[5] = String.format(new Locale("pt","BR"), "%.2f", novoSalario);
                        funcionario.setSalarioLiquido(novoSalario);
                        linha = String.join(";", p);
                        atualizado = true;
                    }
                }
                novasLinhas.add(linha);
            }

            if (!atualizado) {
                System.err.println("Funcionário não encontrado no arquivo: " + funcionario.getNome() + " (" + funcionario.getCpf() + ")");
            }
            Files.write(caminho, novasLinhas);
        } catch (IOException e) {
            System.err.println("Erro ao atualizar salário: " + e.getMessage());
        }
    }

    private void salvarLancamentos() {
        try {
            List<String> linhasExistentes = new ArrayList<>();
            if (Files.exists(Paths.get(CAMINHO_LANCAMENTOS))) {
                linhasExistentes = Files.readAllLines(Paths.get(CAMINHO_LANCAMENTOS));
            }
            linhasExistentes = linhasExistentes.stream()
                    .filter(l -> !l.startsWith(funcionario.getCpf() + ";"))
                    .collect(Collectors.toList());
            for (Lancamento p : proventos) {
                linhasExistentes.add(funcionario.getCpf() + ";Provento;" + p.getDescricao() + ";" + String.format(new Locale("pt","BR"), "%.2f", p.getValor()));
            }
            for (Lancamento d : descontos) {
                linhasExistentes.add(funcionario.getCpf() + ";Desconto;" + d.getDescricao() + ";" + String.format(new Locale("pt","BR"), "%.2f", d.getValor()));
            }

            Files.write(Paths.get(CAMINHO_LANCAMENTOS), linhasExistentes);

        } catch (IOException e) {
            System.err.println("Erro ao salvar lançamentos: " + e.getMessage());
        }
    }
    private void atualizarTela() {

        labelNome.setText(funcionario.getNome());
        labelSalarioBase.setText(String.format("R$ %.2f", funcionario.getSalarioBase()));
        double totalProventos = proventos.stream().mapToDouble(Lancamento::getValor).sum();
        double totalDescontos = descontos.stream().mapToDouble(Lancamento::getValor).sum();
        labelProventos.setText(String.format("R$ %.2f", totalProventos));
        labelDescontos.setText(String.format("R$ %.2f", totalDescontos));
        double salarioLiquido = funcionario.calcularSalario(totalProventos, totalDescontos);
        labelLiquido.setText(String.format("R$ %.2f", salarioLiquido));
        atualizarSalarioNoArquivo(salarioLiquido);
    }

    @FXML
    private void adicionarProvento() {
        Optional<Lancamento> resultado = mostrarDialogoLancamento("Novo Provento");
        resultado.ifPresent(l -> {
            proventos.add(l);
            listaTabela.add(new LancamentoTabela("Provento", l.getDescricao(), l.getValor()));
            salvarLancamentos();
            atualizarTela();
        });
    }

    @FXML
    private void adicionarDesconto() {
        Optional<Lancamento> resultado = mostrarDialogoLancamento("Novo Desconto");
        resultado.ifPresent(l -> {
            descontos.add(l);
            listaTabela.add(new LancamentoTabela("Desconto", l.getDescricao(), l.getValor()));
            salvarLancamentos();
            atualizarTela();
        });
    }
    @FXML
    private void excluirLancamento() {
        LancamentoTabela selecionado = tabelaLancamentos.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            alertHelper.mostrarAlerta("Aviso","Selecione um lançamento para excluir.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,"Deseja realmente excluir o lançamento selecionado?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> resultado = confirm.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.YES) {
            listaTabela.remove(selecionado);
            if (selecionado.getTipo().equalsIgnoreCase("Provento")) {
                proventos.removeIf(p -> p.getDescricao().equals(selecionado.getDescricao()) &&
                        Double.compare(p.getValor(), selecionado.getValor()) == 0);
            } else {
                descontos.removeIf(d -> d.getDescricao().equals(selecionado.getDescricao()) &&
                        Double.compare(d.getValor(), selecionado.getValor()) == 0);
            }
            salvarLancamentos();
            atualizarTela();
        }
    }

    private Optional<Lancamento> mostrarDialogoLancamento(String titulo) {
        Dialog<Lancamento> dialog = new Dialog<>();
        dialog.setTitle(titulo);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField descricaoField = new TextField();
        descricaoField.setPromptText("Descrição");

        TextField valorField = new TextField();
        valorField.setPromptText("Valor (R$)");

        grid.add(new Label("Descrição:"), 0, 0);
        grid.add(descricaoField, 1, 0);
        grid.add(new Label("Valor:"), 0, 1);
        grid.add(valorField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    double valor = parseDoubleComVirgula(valorField.getText());
                    return new Lancamento(descricaoField.getText(), valor);
                } catch (NumberFormatException e) {
                    alertHelper.mostrarAlerta("Aviso","Valor inválido! Use apenas números");
                    return null;
                }
            }
            return null;
        });
        return dialog.showAndWait();
    }
    @FXML
    private void salvarFuncionario() {
        String caminho = "funcionarios.txt";
        try (PrintWriter pw = new PrintWriter(new FileWriter(caminho, true))) {
            pw.println("Nome: " + funcionario.getNome());

            pw.println("Proventos:");
            for (Lancamento p : proventos) {
                pw.printf(new Locale("pt","BR"), "%.2f;%s;%.2f%n", funcionario.getSalarioBase(), p.getDescricao(), p.getValor());
            }
            pw.println("Descontos:");
            for (Lancamento d : descontos) {
                pw.printf(new Locale("pt","BR"), "%.2f;%s;%.2f%n", funcionario.getSalarioBase(), d.getDescricao(), d.getValor());
            }

            double totalP = proventos.stream().mapToDouble(Lancamento::getValor).sum();
            double totalD = descontos.stream().mapToDouble(Lancamento::getValor).sum();

            pw.println("Total Proventos: " + totalP);
            pw.println("Total Descontos: " + totalD);
            pw.println();

            alertHelper.mostrarAlerta("Sucesso","Dados salvos com sucesso!");

        } catch (IOException e) {
            alertHelper.mostrarAlerta("Erro","Erro ao salvar dados: " + e.getMessage());
        }
    }
    @FXML
    private TableView<Funcionario> tabelaFuncionarios;

    @FXML
    private void alterarStatusFuncionario() {

        if (funcionario.getStatus() == StatusFuncionario.ATIVO) {
            funcionario.setStatus(StatusFuncionario.INATIVO);
        } else {
            funcionario.setStatus(StatusFuncionario.ATIVO);
        }

        btnAlterarStatus.setText("Status: " + funcionario.getStatus());

        salvarStatusNoArquivo(funcionario);

        alertHelper.mostrarAlerta("Sucesso", "Status alterado para: " + funcionario.getStatus());
    }


    private void salvarStatusNoArquivo(Funcionario funcionarioAtualizado) {
        try {
            File arquivo = new File(CAMINHO_FUNCIONARIOS);

            if (!arquivo.exists()) return;

            List<String> linhas = Files.readAllLines(arquivo.toPath());
            List<String> novasLinhas = new ArrayList<>();

            for (String linha : linhas) {
                String[] campos = linha.split(";");

                if (campos[1].equals(funcionarioAtualizado.getCpf())) {
                    linha = campos[0] +";"+
                            campos[1] +";"+
                            campos[2] +";"+
                            campos[3] +";"+
                            campos[4] +";"+
                            campos[5] +";"+
                            campos[6] +";"+
                            funcionarioAtualizado.getStatus() +";"+
                            campos[8] +";"+
                            campos[9];
                }
                novasLinhas.add(linha);
            }
            Files.write(arquivo.toPath(), novasLinhas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void gerarHoleritePDF() {
        if (funcionario == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Nenhum funcionário selecionado.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Holerite em PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos PDF", "*.pdf"));
        fileChooser.setInitialFileName("holerite_" + funcionario.getNome().replace(" ", "_") + ".pdf");
        File arquivo = fileChooser.showSaveDialog(null);

        if (arquivo == null) return;

        try (PDDocument doc = new PDDocument()) {
            PDPage pagina = new PDPage(PDRectangle.A4);
            doc.addPage(pagina);

            PDPageContentStream conteudo = new PDPageContentStream(doc, pagina);

            float margin = 50;
            float pageWidth = pagina.getMediaBox().getWidth();
            float yStart = pagina.getMediaBox().getHeight() - margin;

            conteudo.setNonStrokingColor(38, 70, 83);
            conteudo.addRect(0, yStart - 20, pageWidth, 40);
            conteudo.fill();

            conteudo.beginText();
            conteudo.setNonStrokingColor(255, 255, 255);
            conteudo.setFont(PDType1Font.HELVETICA_BOLD, 16);
            String titulo = "HOLERITE - " + LocalDate.now().getMonth().getDisplayName(java.time.format.TextStyle.FULL, new java.util.Locale("pt", "BR")).toUpperCase() + "/" + LocalDate.now().getYear();
            float tituloWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(titulo) / 1000 * 16;
            conteudo.newLineAtOffset((pageWidth - tituloWidth) / 2, yStart - 10);
            conteudo.showText(titulo);
            conteudo.endText();
            float currentY = yStart - 80;

            conteudo.setNonStrokingColor(240, 240, 240);
            conteudo.addRect(margin, currentY - 70, pageWidth - 2 * margin, 70);
            conteudo.fill();

            conteudo.beginText();
            conteudo.setNonStrokingColor(0, 0, 0);
            conteudo.setFont(PDType1Font.HELVETICA_BOLD, 14);
            conteudo.newLineAtOffset(margin + 10, currentY - 20);
            conteudo.showText("Dados do Funcionário");
            conteudo.endText();

            conteudo.beginText();
            conteudo.setFont(PDType1Font.HELVETICA, 11);
            conteudo.newLineAtOffset(margin + 10, currentY - 40);
            conteudo.showText("Nome: " + funcionario.getNome());
            conteudo.newLineAtOffset(0, -15);
            conteudo.showText("Matrícula: " + funcionario.getMatricula());
            conteudo.newLineAtOffset(300, 15);
            conteudo.showText("Cargo: " + funcionario.getCargo());
            conteudo.newLineAtOffset(0, -15);
            conteudo.showText("Departamento: " + funcionario.getDepartamento());
            conteudo.endText();

            currentY -= 120;
            conteudo.beginText();
            conteudo.setNonStrokingColor(69, 123, 157);
            conteudo.setFont(PDType1Font.HELVETICA_BOLD, 12);
            conteudo.newLineAtOffset(margin, currentY);
            conteudo.showText("PROVENTOS");
            conteudo.endText();


            conteudo.setNonStrokingColor(69, 123, 157);
            conteudo.addRect(margin, currentY - 20, pageWidth - 2 * margin, 20);
            conteudo.fill();

            conteudo.beginText();
            conteudo.setNonStrokingColor(255, 255, 255);
            conteudo.setFont(PDType1Font.HELVETICA_BOLD, 10);
            conteudo.newLineAtOffset(margin + 10, currentY - 13);
            conteudo.showText("Descrição");
            conteudo.newLineAtOffset(pageWidth - 2 * margin - 100, 0);
            conteudo.showText("Valor (R$)");
            conteudo.endText();

            currentY -= 40;
            double totalProventos = 0;

            for (Lancamento p : proventos) {
                if (currentY < margin + 50) {
                    conteudo.close();
                    pagina = new PDPage(PDRectangle.A4);
                    doc.addPage(pagina);
                    conteudo = new PDPageContentStream(doc, pagina);
                    currentY = pagina.getMediaBox().getHeight() - margin;
                }

                if (proventos.indexOf(p) % 2 == 0) {
                    conteudo.setNonStrokingColor(250, 250, 250);
                } else {
                    conteudo.setNonStrokingColor(240, 240, 240);
                }
                conteudo.addRect(margin, currentY - 15, pageWidth - 2 * margin, 15);
                conteudo.fill();

                conteudo.beginText();
                conteudo.setNonStrokingColor(0, 0, 0);
                conteudo.setFont(PDType1Font.HELVETICA, 9);
                conteudo.newLineAtOffset(margin + 10, currentY - 10);
                conteudo.showText(p.getDescricao());
                conteudo.newLineAtOffset(pageWidth - 2 * margin - 110, 0);
                conteudo.showText(String.format(new Locale("pt","BR"), "R$ %.2f", p.getValor()));
                conteudo.endText();

                totalProventos += p.getValor();
                currentY -= 15;
            }

            currentY -= 10;
            conteudo.setNonStrokingColor(200, 230, 200);
            conteudo.addRect(margin, currentY - 20, pageWidth - 2 * margin, 20);
            conteudo.fill();

            conteudo.beginText();
            conteudo.setNonStrokingColor(0, 100, 0);
            conteudo.setFont(PDType1Font.HELVETICA_BOLD, 10);
            conteudo.newLineAtOffset(margin + 10, currentY - 13);
            conteudo.showText("TOTAL DE PROVENTOS");
            conteudo.newLineAtOffset(pageWidth - 2 * margin - 110, 0);
            conteudo.showText(String.format(new Locale("pt","BR"), "R$ %.2f", totalProventos));
            conteudo.endText();

            currentY -= 40;
            conteudo.beginText();
            conteudo.setNonStrokingColor(69, 123, 157);
            conteudo.setFont(PDType1Font.HELVETICA_BOLD, 12);
            conteudo.newLineAtOffset(margin, currentY);
            conteudo.showText("DESCONTOS");
            conteudo.endText();

            conteudo.setNonStrokingColor(69, 123, 157);
            conteudo.addRect(margin, currentY - 20, pageWidth - 2 * margin, 20);
            conteudo.fill();

            conteudo.beginText();
            conteudo.setNonStrokingColor(255, 255, 255);
            conteudo.setFont(PDType1Font.HELVETICA_BOLD, 10);
            conteudo.newLineAtOffset(margin + 10, currentY - 13);
            conteudo.showText("Descrição");
            conteudo.newLineAtOffset(pageWidth - 2 * margin - 100, 0);
            conteudo.showText("Valor (R$)");
            conteudo.endText();

            currentY -= 40;
            double totalDescontos = 0;

            for (Lancamento d : descontos) {
                if (currentY < margin + 50) {
                    conteudo.close();
                    pagina = new PDPage(PDRectangle.A4);
                    doc.addPage(pagina);
                    conteudo = new PDPageContentStream(doc, pagina);
                    currentY = pagina.getMediaBox().getHeight() - margin;
                }
                if (descontos.indexOf(d) % 2 == 0) {
                    conteudo.setNonStrokingColor(250, 250, 250);
                } else {
                    conteudo.setNonStrokingColor(240, 240, 240);
                }
                conteudo.addRect(margin, currentY - 15, pageWidth - 2 * margin, 15);
                conteudo.fill();

                conteudo.beginText();
                conteudo.setNonStrokingColor(0, 0, 0);
                conteudo.setFont(PDType1Font.HELVETICA, 9);
                conteudo.newLineAtOffset(margin + 10, currentY - 10);
                conteudo.showText(d.getDescricao());
                conteudo.newLineAtOffset(pageWidth - 2 * margin - 110, 0);
                conteudo.showText(String.format(new Locale("pt","BR"), "R$ %.2f", d.getValor()));
                conteudo.endText();

                totalDescontos += d.getValor();
                currentY -= 15;
            }

            currentY -= 10;
            conteudo.setNonStrokingColor(255, 200, 200);
            conteudo.addRect(margin, currentY - 20, pageWidth - 2 * margin, 20);
            conteudo.fill();

            conteudo.beginText();
            conteudo.setNonStrokingColor(150, 0, 0);
            conteudo.setFont(PDType1Font.HELVETICA_BOLD, 10);
            conteudo.newLineAtOffset(margin + 10, currentY - 13);
            conteudo.showText("TOTAL DE DESCONTOS");
            conteudo.newLineAtOffset(pageWidth - 2 * margin - 110, 0);
            conteudo.showText(String.format(new Locale("pt","BR"), "R$ %.2f", totalDescontos));
            conteudo.endText();

            currentY -= 40;
            double salarioLiquido = funcionario.calcularSalario(totalProventos, totalDescontos);

            conteudo.setNonStrokingColor(38, 70, 83);
            conteudo.addRect(margin, currentY - 40, pageWidth - 2 * margin, 40);
            conteudo.fill();

            conteudo.beginText();
            conteudo.setNonStrokingColor(255, 255, 255);
            conteudo.setFont(PDType1Font.HELVETICA_BOLD, 14);
            conteudo.newLineAtOffset(margin + 10, currentY - 15);
            conteudo.showText("SALÁRIO LÍQUIDO: R$ " + String.format(new Locale("pt","BR"), "%.2f", salarioLiquido));
            conteudo.endText();

            conteudo.beginText();
            conteudo.setFont(PDType1Font.HELVETICA, 10);
            conteudo.newLineAtOffset(margin + 10, currentY - 30);
            conteudo.showText("Salário Base: R$ " + String.format(new Locale("pt","BR"), "%.2f", funcionario.getSalarioBase()) +
                    " | Proventos: R$ " + String.format(new Locale("pt","BR"), "%.2f", totalProventos) +
                    " | Descontos: R$ " + String.format(new Locale("pt","BR"), "%.2f", totalDescontos));
            conteudo.endText();

            conteudo.beginText();
            conteudo.setFont(PDType1Font.HELVETICA_OBLIQUE, 9);
            conteudo.setNonStrokingColor(120, 120, 120);
            conteudo.newLineAtOffset(margin, 30);
            conteudo.showText("Holerite gerado automaticamente em " +
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                    " | " + funcionario.getNome() + " | Matrícula: " + funcionario.getMatricula());
            conteudo.endText();

            conteudo.close();
            doc.save(arquivo);

            alertHelper.mostrarAlerta("Sucesso","Holerite exportado em PDF com SUCESSO!\n" + arquivo.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
            alertHelper.mostrarAlerta("Erro","Erro ao gerar PDF: " + e.getMessage());
        }
    }
    @FXML
    private void fecharTela() {
        Stage stage = (Stage) labelNome.getScene().getWindow();
        stage.close();
    }
    private double parseDoubleComVirgula(String s) {
        if (s == null) throw new NumberFormatException("null");
        String t = s.trim().replace("\\u00A0", "").replace(" ", "");
        t = t.replace(".", "");
        t = t.replace(',', '.');
        return Double.parseDouble(t);
    }
}
