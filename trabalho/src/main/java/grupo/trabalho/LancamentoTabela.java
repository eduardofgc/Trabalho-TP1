package grupo.trabalho;

public class LancamentoTabela {
    private String tipo;
    private String descricao;
    private Double valor;

    public LancamentoTabela(String tipo, String descricao, Double valor) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
    }

    public String getTipo() { return tipo; }
    public String getDescricao() { return descricao; }
    public Double getValor() { return valor; }
}

