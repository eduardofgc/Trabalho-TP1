package grupo.trabalho;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class CandidatoRepository {

    private static final String FILE_PATH = "candidatos.dat";
    private static final ObservableList<Candidato> candidatos = FXCollections.observableArrayList();

    static {
        carregar();
    }

    public static void adicionarCandidato(Candidato candidato) {
        candidatos.add(candidato);
        salvar();
    }

    public static ObservableList<Candidato> getCandidatos() {
        return candidatos;
    }

    public static void removerCandidato(Candidato candidato) {
        candidatos.remove(candidato);
        salvar();
    }

    public static void limpar() {
        candidatos.clear();
        salvar();
    }

    private static void salvar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(new ArrayList<>(candidatos));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static void carregar() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
                candidatos.addAll((java.util.List<Candidato>) ois.readObject());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}