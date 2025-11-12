package grupo.trabalho;

import java.time.LocalDate;

public class Entrevista {
    private LocalDate data;
    private String avaliador, parecer;
    private double nota;

    public Entrevista(LocalDate dataMarcada, String avaliadorDado){
        this.data = dataMarcada;
        this.avaliador = avaliadorDado;
    }

    public void registrarResultado(double notaDada, String meuParecer){
        this.nota = notaDada;
        this.parecer = meuParecer;
    }

    public LocalDate getData() { return data; }
    public String getAvaliador() { return avaliador; }
    public String getParecer() { return parecer; }
    public double getNota() { return nota; }
}
