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
        // TÉCNICA: CHAMADA À SUPERCLASSE (super)
        // A palavra 'super' chama o construtor da classe pai ('Usuario').
        // Em vez de reinicializar os atributos aqui, delegamos a
        // responsabilidade para a superclasse, reutilizando sua lógica.
        super(meuEmail, meuLogin, minhaSenha);
    }

    // TÉCNICA: MODULARIZAÇÃO (Definição de Comportamento)
    // Este metodo define um comportamento específico do 'Recrutador'.
    public Contratacao solicitarContratacao(Date dataSolicitacao){


        // O metodo centraliza a lógica de criação (instanciação)
        // de um novo objeto 'Contratacao', retornando-o pronto.
        Contratacao minhaContratacao = new Contratacao(dataSolicitacao);
        return minhaContratacao;
    }


    // TÉCNICA: MODULARIZAÇÃO (Definição de Comportamento)
    // Outro metodo que define uma responsabilidade única do 'Recrutador'.
    public Entrevista agendarEntrevista(LocalDate dataMarcada, String meuAvaliador){

        // O metodo atua como uma "fábrica" simples para o objeto 'Entrevista',
        // garantindo que ele seja criado com os dados corretos.
        Entrevista minhaEntrevista = new Entrevista(dataMarcada, meuAvaliador);
        return minhaEntrevista;
    }


}