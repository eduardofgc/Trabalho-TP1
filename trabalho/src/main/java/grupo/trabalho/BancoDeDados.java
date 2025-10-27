package grupo.trabalho;

import java.util.ArrayList;
import java.util.List;

public class BancoDeDados {
    private static final List<Funcionario> funcionarios = new ArrayList<>();
    private static final List<RegraSalarial> regraSalarial = new ArrayList<>();

    public static void adicionarFuncionario(Funcionario f) {
        funcionarios.add(f);
    }

    public static List<Funcionario> listarFuncionarios() {
        return funcionarios;
    }

    public static void addRegraSalarial(RegraSalarial rs) {
        regraSalarial.add(rs);
    }
}
