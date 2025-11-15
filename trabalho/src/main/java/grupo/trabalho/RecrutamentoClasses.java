package grupo.trabalho;

import javafx.scene.control.ListView;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecrutamentoClasses {

    /*
       ENCAPSULAMENTO PARCIAL:
       As listas são públicas, o que expõe os dados.
       (Bom para aprender, mas não ideal em sistemas reais).
    */
    public static ArrayList<Vaga> vagasArray = new ArrayList<>();
    public static ArrayList<Entrevista> entrevistasArray = new ArrayList<>();

    /*
       COMPOSIÇÃO:
       A classe RecrutamentoClasses "contém" objetos do tipo Vaga.
       A lista não existe sem esta classe.
    */
    public static void addToVagaList(Vaga minhaVaga){
        vagasArray.add(minhaVaga);
    }

    /*
       COMPOSIÇÃO + MANIPULAÇÃO DE OBJETOS:
       A classe controla quais objetos vivem dentro da lista.
    */
    public static void removeFromVagaList(Vaga minhaVaga){
        vagasArray.remove(minhaVaga);
    }

    /*
       COMPOSIÇÃO:
       Armazena objetos da classe Entrevista dentro de uma lista.
    */
    public static void addToEntrevistaList(Entrevista minhaEntrevista){
        entrevistasArray.add(minhaEntrevista);
    }

    /*
        COMPOSIÇÃO:
        A classe gerencia o ciclo de vida dos objetos Entrevista.
    */
    public static void removeFromEntrevistaList(Entrevista minhaEntrevista){
        entrevistasArray.remove(minhaEntrevista);
    }
}
