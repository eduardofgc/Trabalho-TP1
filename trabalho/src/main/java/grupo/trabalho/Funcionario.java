package grupo.trabalho;

import java.time.LocalDate;

public class Funcionario{
    private String nome;
    private String cpf;
    private int matricula;
    private LocalDate dataAdmissao;
    private double salarioBase;
    private double salarioLiquido;
    private RegimeContratacao regime;
    private StatusFuncionario status;
    private String cargo;
    private String departamento;

    public Funcionario(String nome, String cpf, int matricula, LocalDate dataAdmissao, double salarioB, double salarioL, RegimeContratacao regime, StatusFuncionario status, String cargo, String departamento) {
        this.nome = nome;
        this.cpf = cpf;
        this.matricula = matricula;
        this.dataAdmissao = dataAdmissao;
        this.regime = regime;
        this.status = status;
        this.cargo = cargo;
        this.departamento = departamento;
        this.salarioLiquido = salarioL;
        this.salarioBase = salarioB;
    }

    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public int getMatricula() { return matricula; }
    public LocalDate getDataAdmissao() { return dataAdmissao; }
    public double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(double salario) { this.salarioBase = salario; }
    public double getSalarioLiquido() { return salarioLiquido; }
    public void setSalarioLiquido(double novosalario) { this.salarioLiquido = novosalario; }
    public RegimeContratacao getRegime() { return regime; }
    public StatusFuncionario getStatus() { return status; }
    public String getCargo() { return cargo; }
    public String getDepartamento() { return departamento; }

    public void setStatus(StatusFuncionario status) { this.status = status; }

    public double calcularSalario(double totalProventos, double totalDescontos) {
        double salarioLiquido = salarioBase + totalProventos - totalDescontos;
        setSalarioLiquido(salarioLiquido);
        return salarioLiquido;
    }

}
