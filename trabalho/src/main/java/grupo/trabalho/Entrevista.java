package grupo.trabalho;

import java.util.Date;

public class Entrevista {
    private Date data;
    private String avaliador, parecer;
    private double nota;

    public void registrarResultado(double notaDada, String meuParecer){
        this.nota = notaDada;
        this.parecer = meuParecer;
    }

    Entrevista(Date dataMarcada, String avaliadorDado){
        this.data = dataMarcada;
        this.avaliador = avaliadorDado;
    }
}
