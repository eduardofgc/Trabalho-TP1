package grupo.trabalho;

import java.util.List;

public class Holerite {

    private Funcionario funcionario;

    private double salarioBruto;
    private double totalProventos;
    private double totalDescontos;
    private double salarioLiquido;


    public Holerite(Funcionario funcionario, List<RegraSalarial> regras) {
        this.funcionario = funcionario;
        this.salarioBruto = funcionario.getSalarioBase();

        for (RegraSalarial r : regras) {
            if (r.getTipo() == TipoRegraSalario.PROVENTO) totalProventos += r.getValor();
            else totalDescontos += r.getValor();
        }
        this.salarioBruto -= totalProventos;
        this.salarioBruto += totalProventos;
        this.salarioLiquido = salarioBruto;
    }


}
