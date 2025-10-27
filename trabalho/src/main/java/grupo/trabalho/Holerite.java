package grupo.trabalho;

import java.util.List;

public class Holerite {
    private Funcionario funcionario;
    private int mes;
    private int ano;
    private double salarioBruto;
    private double totalProventos;
    private double totalDescontos;
    private double salarioLiquido;

    public Holerite(Funcionario funcionario, int mes, int ano,
                    double salarioLiquido, List<RegraSalarial> regras) {
        this.funcionario = funcionario;
        this.mes = mes;
        this.ano = ano;
        this.salarioBruto = funcionario.getSalarioBase();


        for (RegraSalarial r : regras) {
            if (r.getTipo() == TipoRegraSalario.PROVENTO) totalProventos += r.getValor();
            else totalDescontos += r.getValor();
        }

        this.salarioLiquido = salarioLiquido;
    }

    @Override
    public String toString() {
        return """
               HOLERITE - %s
               Mês/Ano: %d/%d
               Salário Base: R$ %.2f
               Proventos: R$ %.2f
               Descontos: R$ %.2f
               Salário Líquido: R$ %.2f
               """.formatted(
                funcionario.getNome(), mes, ano, salarioBruto,
                totalProventos, totalDescontos, salarioLiquido);
    }
}
