package grupo.trabalho;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXML;

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

public class DetalhesSalarioController {

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
    private static final String CAMINHO_FUNCIONARIOS = "dados_Funcionarios.txt";
    private Funcionario funcionario;

    private List<Lancamento> proventos = new ArrayList<>();
    private List<Lancamento> descontos = new ArrayList<>();
    private ObservableList<LancamentoTabela> listaTabela = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tabelaLancamentos.setItems(listaTabela);
    }

    public void setFuncionario(Funcionario f) {
        this.funcionario = f;
        carregarLancamentos();
        atualizarTela();
    }
    private void carregarLancamentos() {
        proventos.clear();
        descontos.clear();
        listaTabela.clear();

        try {
            List<String> linhas = Files.readAllLines(Paths.get(CAMINHO_LANCAMENTOS));

            for (String linha : linhas) {
                String[] p = linha.split(";");
                if (p.length == 4) {
                    String cpf = p[0];
                    String tipo = p[1];
                    String descricao = p[2];
                    double valor = Double.parseDouble(p[3]);

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
                        // Substitui o salário (índice 5)
                        p[5] = String.format(Locale.US, "%.2f", novoSalario);
                        funcionario.setSalarioLiquido(novoSalario);
                        linha = String.join(";", p);
                        atualizado = true;
                        System.out.println("Salário atualizado para " + funcionario.getNome() + ": R$ " + novoSalario);
                    }
                }
                novasLinhas.add(linha);
            }

            if (!atualizado) {
                System.err.println("Funcionário não encontrado no arquivo: " + funcionario.getNome() + " (" + funcionario.getCpf() + ")");
            }

            Files.write(caminho, novasLinhas);
            System.out.println("Arquivo atualizado com sucesso em: " + caminho.toAbsolutePath());

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

            // Remove lançamentos antigos desse funcionário
            linhasExistentes = linhasExistentes.stream()
                    .filter(l -> !l.startsWith(funcionario.getCpf() + ";"))
                    .collect(Collectors.toList());

            // Adiciona os novos lançamentos
            for (Lancamento p : proventos) {
                linhasExistentes.add(funcionario.getCpf() + ";Provento;" + p.getDescricao() + ";" + p.getValor());
            }
            for (Lancamento d : descontos) {
                linhasExistentes.add(funcionario.getCpf() + ";Desconto;" + d.getDescricao() + ";" + d.getValor());
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

        // Atualiza salário líquido com método do funcionário
        double salarioLiquido = funcionario.calcularSalario(totalProventos, totalDescontos);
        labelLiquido.setText(String.format("R$ %.2f", salarioLiquido));

        // Atualiza o salário no arquivo
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
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecione um lançamento para excluir.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Deseja realmente excluir o lançamento selecionado?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> resultado = confirm.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.YES) {
            // Remove da tabela
            listaTabela.remove(selecionado);

            // Remove da lista de proventos ou descontos
            if (selecionado.getTipo().equalsIgnoreCase("Provento")) {
                proventos.removeIf(p -> p.getDescricao().equals(selecionado.getDescricao()) &&
                        p.getValor() == selecionado.getValor());
            } else {
                descontos.removeIf(d -> d.getDescricao().equals(selecionado.getDescricao()) &&
                        d.getValor() == selecionado.getValor());
            }

            // Salva alterações e atualiza tela
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
                    double valor = Double.parseDouble(valorField.getText());
                    return new Lancamento(descricaoField.getText(), valor);
                } catch (NumberFormatException e) {
                    System.err.println("Valor inválido!");
                    return null;
                }
            }
            return null;
        });

        return dialog.showAndWait();
    }
    @FXML
    private void salvarFuncionario() {
        String caminho = "funcionarios.txt";  // você pode usar um caminho absoluto se quiser
        try (PrintWriter pw = new PrintWriter(new FileWriter(caminho, true))) {
            pw.println("Nome: " + funcionario.getNome());

            // Proventos
            pw.println("Proventos:");
            for (Lancamento p : proventos) {
                pw.printf("%.2f;%s;%.2f%n", funcionario.getSalarioBase(), p.getDescricao(), p.getValor());
            }

            // Descontos
            pw.println("Descontos:");
            for (Lancamento d : descontos) {
                pw.printf("%.2f;%s;%.2f%n", funcionario.getSalarioBase(), d.getDescricao(), d.getValor());
            }

            // Totais
            double totalP = proventos.stream().mapToDouble(Lancamento::getValor).sum();
            double totalD = descontos.stream().mapToDouble(Lancamento::getValor).sum();

            pw.println("Total Proventos: " + totalP);
            pw.println("Total Descontos: " + totalD);
            pw.println(); // linha em branco entre funcionários

            System.out.println("Dados salvos com sucesso!");

        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }
}
