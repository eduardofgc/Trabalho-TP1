package grupo.trabalho;

import javafx.scene.control.ListView;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList; //aqui temos uma implementação concreta da interface list, e ao longo desse código teremos arraylists e lists representando interfaces
import java.util.List;
import java.util.stream.Collectors;


// TÉCNICA: MODULARIZAÇÃO
// Esta classe atua como um "Repositório" ou uma classe de serviço.
// Ela centraliza e agrupa a lógica de gerenciamento das listas
// de Vagas e Entrevistas, separando essa responsabilidade de outras
// classes (como os Controladores).

public class RecrutamentoClasses {

// TÉCNICA: ESCOPO DE CLASSE (static)
    // Estas listas são 'static', o que significa que elas pertencem à CLASSE
    // 'RecrutamentoClasses', e não a uma instância dela. Só existe uma
    // cópia dessas listas compartilhada por todo o programa.

    // TÉCNICA: COMPOSIÇÃO
    // A classe 'RecrutamentoClasses' "tem um" ArrayList de Vagas.
    // Ela contém e gerencia o ciclo de vida dessa coleção.
    //TÉCNICA: Encapsulamento
    public static ArrayList<Vaga> vagasArray = new ArrayList<>(); //exemplo do uso de interface

    // (Mesmas técnicas acima: Static, Composição, Encapsulamento Fraco)
    public static ArrayList<Entrevista> entrevistasArray = new ArrayList<>(); //exemplo do uso de interface

    // TÉCNICA: MODULARIZAÇÃO (Metodo de Serviço)
    // Este metodo encapsula a *ação* de adicionar uma vaga à lista.
    // Em vez de outras classes fazerem 'vagasArray.add()', elas
    // chamam este metodo, o que é uma prática de organização melhor.
    public static void addToVagaList(Vaga minhaVaga){
        vagasArray.add(minhaVaga);
    }

    // TÉCNICA: MODULARIZAÇÃO (Metodo de Serviço)
    // Da mesma forma, este metodo centraliza a lógica de remoção.
    public static void removeFromVagaList(Vaga minhaVaga){
        vagasArray.remove(minhaVaga);
    }


    // TÉCNICA: MODULARIZAÇÃO (Metodo de Serviço)
    public static void addToEntrevistaList(Entrevista minhaEntrevista){
        entrevistasArray.add(minhaEntrevista);
    }

    public static void removeFromEntrevistaList(Entrevista minhaEntrevista){
        entrevistasArray.remove(minhaEntrevista);
    }

    //TÉCNICAS AUSENTES NESSE CÓDIGO:

    // TÉCNICA (Ausente): HERANÇA
    // A classe 'RecrutamentoClasses' não usa 'extends',
    // portanto, não está herdando de nenhuma superclasse personalizada.

    // TÉCNICA (Ausente): POLIMORFISMO
    // Nenhum metodo está sendo sobrescrito (@Override) e não há
    // tratamento de objetos genéricos (como tratar um 'ArrayList'
    // como uma 'List' genérica, o que seria polimórfico).

    // TÉCNICA (Ausente): TRATAMENTO DE EXCEÇÕES
    // Não há blocos 'try-catch' ou declarações 'throws' neste arquivo.
}