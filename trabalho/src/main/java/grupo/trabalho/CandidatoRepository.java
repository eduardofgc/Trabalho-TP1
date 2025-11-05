package grupo.trabalho;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CandidatoRepository {

    private static final ObservableList<Candidato> candidatos = FXCollections.observableArrayList();

    public static void adicionarCandidato(Candidato candidato) {
        candidatos.add(candidato);
    }

    public static ObservableList<Candidato> getCandidatos() {
        return candidatos;
    }

    public static void limpar() {
        candidatos.clear();
    }
}