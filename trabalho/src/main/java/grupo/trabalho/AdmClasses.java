package grupo.trabalho;

import javafx.scene.control.ListView;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdmClasses {

    public static ArrayList<Usuario> usuariosArray = new ArrayList<>();

    public static void addToUserList(Usuario usuario) {
        usuariosArray.add(usuario);
    }

    public static void fetchUsersFromArchive(){ //TODO

    }

    public static Usuario searchFor(String login){
        return usuariosArray.stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst()
                .orElse(null);
    }

    public static void ensureAdminUser() { //TODO: FIX ADMIN INICIAL
        Path path = Path.of("usuariosInfo.txt");
        try {
            if (Files.readAllLines(path).isEmpty()) {
                try (FileWriter writer = new FileWriter(path.toFile(), true)) {
                    writer.write("Admin,12345678,true,false,false,false" + System.lineSeparator());
                    System.out.println("Default admin user created: Admin / 12345678");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkForUser(String login, String senha) {
        Path path = Path.of("usuariosInfo.txt");
        if (!Files.exists(path)) return false;

        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                String[] parts = line.split(",\\s*");
                if (parts.length >= 2) {
                    String fileLogin = parts[0];
                    String fileSenha = parts[1];
                    if (fileLogin.equals(login) && fileSenha.equals(senha)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void deletarUsuario(Usuario usuario, ListView<Usuario> listView) throws IOException {
        usuariosArray.removeIf(u -> u.getLogin().equals(usuario.getLogin()));

        if (listView != null) {
            listView.getItems().removeIf(item -> item.getLogin().equals(usuario.getLogin()));
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
}
