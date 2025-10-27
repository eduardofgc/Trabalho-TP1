package grupo.trabalho;

import java.util.ArrayList;
import java.util.List;

public class BancoDeDados {
    private static final List<Funcionario> funcionarios = new ArrayList<>();

    public static void adicionarFuncionario(Funcionario f) {
        funcionarios.add(f);
    }

    public static List<Funcionario> listarFuncionarios() {
        return funcionarios;
    }
}
