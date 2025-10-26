package grupo.trabalho;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AdmClasses {
    public static ArrayList<Usuario> usuariosArray = new ArrayList<>();

    public static void addToUserList(Usuario meuUsuario){
        usuariosArray.add(meuUsuario);
    }

    public static Usuario searchFor(String meuLogin){
        boolean found = false;

        for (Usuario u : usuariosArray){
            if (u.getLogin().equals(meuLogin)){
                found = true;
                return u;
            }
        }

        return null;
    }

    public static boolean checkForUser(String meuLogin, String minhaSenha) {
        try {
            List<String> lines = Files.readAllLines(Path.of("usuariosInfo.txt"));

            for (String line : lines) {
                String[] parts = line.split(",\\s*");
                if (parts.length >= 2) {
                    String login = parts[0];
                    String senha = parts[1];
                    if (login.equals(meuLogin) && senha.equals(minhaSenha)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}
