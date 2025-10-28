package grupo.trabalho;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FolhaPagamento {

    private Date dataGeracao;
    private int mesReferencia;
    private int anoReferencia;
    private double valorTotal;

    private Holerite holerite;
    private List<Holerite> holerites;

    public void FolhaPagamento() {
        this.dataGeracao = new Date();
        this.mesReferencia = mesReferencia;
        this.anoReferencia = anoReferencia;
        this.holerites = new ArrayList<>();
        this.valorTotal = 0.0;
    }


    public void gerarFolha(List<Funcionario> funcionarios) {

        for (Funcionario f : funcionarios) {
            if (f.getStatus() == StatusFuncionario.ATIVO) {

            }
        }
    }

    public File exportar(String formato) throws IOException {
        String nomeArquivo = String.format("FolhaPagamento_%02d-%04d.%s", mesReferencia, anoReferencia, formato);
        File arquivo = new File(nomeArquivo);

        try (FileWriter writer = new FileWriter(arquivo)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            writer.write("Folha de Pagamento\n");
            writer.write("Data de Geração: " + sdf.format(dataGeracao) + "\n");
            writer.write("Referência: " + String.format("%02d/%04d\n", mesReferencia, anoReferencia));
            writer.write("===========================================\n");

            for (Holerite h : holerites) {
                writer.write(h.toString() + "\n");
            }

            writer.write("===========================================\n");
            writer.write(String.format("Valor Total da Folha: R$ %.2f\n", valorTotal));
        }

        return arquivo;
    }

    public Date getDataGeracao() {
        return dataGeracao;
    }

    public int getMesReferencia() {
        return mesReferencia;
    }

    public int getAnoReferencia() {
        return anoReferencia;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public List<Holerite> getHolerites() {
        return holerites;
    }

    @Override
    public String toString() {
        return String.format("FolhaPagamento{%02d/%04d, total=R$ %.2f}", mesReferencia, anoReferencia, valorTotal);
    }
}
