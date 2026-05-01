package grupo.trabalho;

import java.nio.file.Path;
import java.nio.file.Paths;


public class AppConfig {

    private static final String DATA_DIR = "data";

    public static final String USUARIOS_INFO     = path("usuariosInfo.txt");
    public static final String EMAIL_INFO         = path("emailInfo.txt");

    public static final String VAGAS_INFO         = path("vagasInfo.txt");
    public static final String ENTREVISTAS_INFO   = path("entrevistasInfo.txt");
    public static final String CANDIDATOS_DAT     = path("candidatos.dat");

    public static final String DADOS_FUNCIONARIOS      = path("dados_Funcionarios.txt");
    public static final String DADOS_FUNCIONARIOS_ALT  = path("dadosFuncionarios.txt");
    public static final String LANCAMENTOS             = path("lancamentos.txt");
    public static final String FUNCIONARIOS            = path("funcionarios.txt");

    private static String path(String fileName) {
        return Paths.get(DATA_DIR, fileName).toString();
    }
}