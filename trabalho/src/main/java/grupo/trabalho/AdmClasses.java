package grupo.trabalho;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static void deletarUsuario(Usuario usuario, ListView<String> listView) throws IOException {
        usuariosArray.removeIf(u -> u.getLogin().equals(usuario.getLogin()));

        if (listView != null) {
            listView.getItems().removeIf(item -> item.startsWith(usuario.getLogin() + ","));
        }

        Path path = Path.of("usuariosInfo.txt");
        if (Files.exists(path)) {
            List<String> updatedLines = Files.readAllLines(path)
                    .stream()
                    .filter(line -> !line.startsWith(usuario.getLogin() + ","))
                    .collect(Collectors.toList());

            Files.write(path, updatedLines);
        }
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
