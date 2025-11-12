package grupo.trabalho;

import java.time.LocalDate;
import java.util.Date;

public class Recrutador extends Usuario {

    Recrutador(String meuEmail, String meuLogin, String minhaSenha) {
        super(meuEmail, meuLogin, minhaSenha);
    }

    public Contratacao solicitarContratacao(Date dataSolicitacao){
        Contratacao minhaContratacao = new Contratacao(dataSolicitacao);
        return minhaContratacao;
    }

    public Entrevista agendarEntrevista(LocalDate dataMarcada, String meuAvaliador){
        Entrevista minhaEntrevista = new Entrevista(dataMarcada, meuAvaliador);
        return minhaEntrevista;
    }

    //+gerenciarProcessoSeletivo(vaga: Vaga): void
    //+cadastrarCandidatoAprovado(contratacao: Contratacao): Funcionario
}
