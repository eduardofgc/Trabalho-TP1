package grupo.trabalho;

import java.util.Date;

public class Contratacao {
    private Date dataSolicitacao, dataAutorizacao;
    private String status;
    public boolean estaAutorizado;

    Contratacao(Date minhaDataSolicitacao){
        this.dataSolicitacao = minhaDataSolicitacao;
    }

    //-regime: RegimeContratacao
    //+efetivar(): Funcionario
}
