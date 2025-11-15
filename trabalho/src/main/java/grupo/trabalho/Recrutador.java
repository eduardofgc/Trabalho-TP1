package grupo.trabalho;

import java.time.LocalDate;
import java.util.Date;

// -----------------------------------------------------------------
// TÉCNICA: HERANÇA
// -----------------------------------------------------------------
// Esta é a aplicação direta de Herança.
// A classe 'Recrutador' é declarada como uma subclasse de 'Usuario'
// usando a palavra-chave 'extends'.
// Isso significa que 'Recrutador' herda todos os atributos e métodos
// públicos e protegidos de 'Usuario', como 'email', 'login', etc.
// Estabelece a relação "Recrutador É UM Usuario".
public class Recrutador extends Usuario {

    // Este é o Construtor da classe Recrutador.
    Recrutador(String meuEmail, String meuLogin, String minhaSenha) {

        // -----------------------------------------------------------------
        // TÉCNICA: CHAMADA À SUPERCLASSE (super)
        // -----------------------------------------------------------------
        // A palavra-chave 'super' chama o construtor da classe pai ('Usuario').
        // Em vez de recriar a lógica para armazenar email, login e senha,
        // estamos REUTILIZANDO o código da superclasse.
        // Delegamos a ela a responsabilidade de inicializar esses atributos.
        super(meuEmail, meuLogin, minhaSenha);
    }

    // -----------------------------------------------------------------
    // TÉCNICA: MODULARIZAÇÃO (Definição de Comportamento)
    // -----------------------------------------------------------------
    // Este é um método que define um comportamento específico
    // que apenas o 'Recrutador' pode executar.
    public Contratacao solicitarContratacao(Date dataSolicitacao){

        // O método centraliza a lógica de criação de um novo objeto 'Contratacao'.
        // Ele recebe os dados necessários, instancia o objeto e o retorna.
        Contratacao minhaContratacao = new Contratacao(dataSolicitacao);
        return minhaContratacao;
    }

    // -----------------------------------------------------------------
    // TÉCNICA: MODULARIZAÇÃO (Definição de Comportamento)
    // -----------------------------------------------------------------
    // Outro método que define uma responsabilidade única do Recrutador.
    public Entrevista agendarEntrevista(LocalDate dataMarcada, String meuAvaliador){

        // Assim como o método anterior, ele atua como uma "fábrica"
        // para o objeto 'Entrevista', garantindo que ele seja criado
        // com os dados corretos.
        Entrevista minhaEntrevista = new Entrevista(dataMarcada, meuAvaliador);
        return minhaEntrevista;
    }


}