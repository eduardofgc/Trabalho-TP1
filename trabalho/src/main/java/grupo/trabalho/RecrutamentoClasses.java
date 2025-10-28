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

    public static ArrayList<Vaga> vagasArray = new ArrayList<>();

    public static void addToVagaList(Vaga minhaVaga){
        vagasArray.add(minhaVaga);
    }

    public static void removeFromVagaList(Vaga minhaVaga){
        vagasArray.remove(minhaVaga);
    }
}
