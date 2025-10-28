package grupo.trabalho;

public class RegraSalarial {
    private String descricao;
    private double valor;
    private TipoRegraSalario tipo;

    public RegraSalarial(String descricao, double valor, TipoRegraSalario tipo) {
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
    }

    public String getDescricao() { return descricao; }
    public double getValor() { return valor; }
    public TipoRegraSalario getTipo() { return tipo; }

    @Override
    public String toString() {
        return tipo + " - " + descricao + ": R$ " + valor;
    }
}
