package grupo.trabalho;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CandidatoRepository {
    private static ObservableList<Candidato> candidatos = FXCollections.observableArrayList();

    public static ObservableList<Candidato> getCandidatos() {
        return candidatos;
    }

    public static void adicionarCandidato(Candidato c) {
        candidatos.add(c);
    }
}