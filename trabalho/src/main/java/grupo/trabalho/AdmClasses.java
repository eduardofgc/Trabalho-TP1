package grupo.trabalho;

import java.io.*;
import java.util.ArrayList;

public class AdmClasses {

    public static ArrayList<Usuario> usuariosArray = new ArrayList<>();

    public static void addToUserList(Usuario u){
        usuariosArray.add(u);
    }

    public static void ensureAdminUser() {
        boolean adminExists = false;
        for (Usuario u : usuariosArray) {
            if (u.isAdmin) {
                adminExists = true;
                return;
            }
        }
        if (!adminExists) {
            Usuario defaultAdmin = new Usuario("admin@admin.com", "admin", "admin123");
            defaultAdmin.isAdmin = true;
            addToUserList(defaultAdmin);
            try (FileWriter writer = new FileWriter("usuariosInfo.txt", true);
                 FileWriter emailWriter = new FileWriter("emailInfo.txt", true)) {
                writer.write("admin,admin123,true,false,false,false" + System.lineSeparator());
                emailWriter.write("admin@admin.com" + System.lineSeparator());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void fetchUsersFromArchive() {
        usuariosArray.clear();
        try (BufferedReader userReader = new BufferedReader(new FileReader("usuariosInfo.txt"));
             BufferedReader emailReader = new BufferedReader(new FileReader("emailInfo.txt"))) {
            String userLine;
            while ((userLine = userReader.readLine()) != null) {
                String emailLine = emailReader.readLine();
                String[] p = userLine.split(",");
                if (p.length == 6){
                    Usuario u = new Usuario(
                            emailLine != null ? emailLine : "",
                            p[0],
                            p[1],
                            Boolean.parseBoolean(p[2]),
                            Boolean.parseBoolean(p[3]),
                            Boolean.parseBoolean(p[4]),
                            Boolean.parseBoolean(p[5])
                    );
                    usuariosArray.add(u);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveUser(Usuario novoUsuario){
        String permissions =
                novoUsuario.isAdmin + "," +
                        novoUsuario.isGestor + "," +
                        novoUsuario.isCandidato + "," +
                        novoUsuario.isRecrutador;
        try (FileWriter writer = new FileWriter("usuariosInfo.txt", true);
             FileWriter emailWriter = new FileWriter("emailInfo.txt", true)) {
            writer.write(novoUsuario.getLogin() + "," + novoUsuario.getSenha() + "," + permissions + System.lineSeparator());
            emailWriter.write(novoUsuario.getEmail() + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkForUser(String login, String senha) {
        return usuariosArray.stream().anyMatch(u -> u.getLogin().equals(login) && u.getSenha().equals(senha));
    }

    public static Usuario searchFor(String login){
        return usuariosArray.stream().filter(u -> u.getLogin().equals(login)).findFirst().orElse(null);
    }
}
