package grupo.trabalho;

import java.time.LocalDate;

public class Funcionario extends Usuario {
    private int matricula;
    private LocalDate dataAdmissao;
    private double salarioBase;
    private RegimeContratacao regime;
    private StatusFuncionario status;
    private String cargo;
    private String departamento;

    public Funcionario(String nome, String cpf, int matricula, LocalDate dataAdmissao,
                       double salarioBase, RegimeContratacao regime,
                       StatusFuncionario status, String cargo, String departamento) {
        super(nome, cpf);
        this.matricula = matricula;
        this.dataAdmissao = dataAdmissao;
        this.salarioBase = salarioBase;
        this.regime = regime;
        this.status = status;
        this.cargo = cargo;
        this.departamento = departamento;
    }

    public int getMatricula() { return matricula; }
    public LocalDate getDataAdmissao() { return dataAdmissao; }
    public double getSalarioBase() { return salarioBase; }
    public RegimeContratacao getRegime() { return regime; }
    public StatusFuncionario getStatus() { return status; }
    public String getCargo() { return cargo; }
    public String getDepartamento() { return departamento; }

    public void setStatus(StatusFuncionario status) { this.status = status; }
    /*
    @Override
    public String toString() {
        return getNome() + " - " + cargo + " - " + departamento + " - R$ " + salarioBase;
    }*/
}
