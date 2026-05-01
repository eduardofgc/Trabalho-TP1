package grupo.trabalho;

import java.time.LocalDate;
import java.util.Date;
// TÉCNICA: HERANÇA
// A classe 'Recrutador' herda (extends) da superclasse 'Usuario'.
// Isso significa que 'Recrutador' é um tipo de 'Usuario' e herda
// seus atributos e métodos (como email, login, etc.),
// promovendo o reúso de código.
public class Recrutador extends Usuario

{
    // Este é o Construtor da classe 'Recrutador'.
    Recrutador(String meuEmail, String meuLogin, String minhaSenha)

    {

        super(meuEmail, meuLogin, minhaSenha);
    }


    public Contratacao solicitarContratacao(Date dataSolicitacao){


        // O metodo centraliza a lógica de criação (instanciação)
        // de um novo objeto 'Contratacao', retornando-o pronto.
        Contratacao minhaContratacao = new Contratacao(dataSolicitacao);
        return minhaContratacao;
    }



    public Entrevista agendarEntrevista(LocalDate dataMarcada, String meuAvaliador){


        Entrevista minhaEntrevista = new Entrevista(dataMarcada, meuAvaliador);
        return minhaEntrevista;
    }


}