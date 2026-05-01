package grupo.trabalho;

import java.io.*;
import java.util.ArrayList;

public class AdmClasses {

    public static ArrayList<Usuario> usuariosArray = new ArrayList<>();

    public static void addToUserList(Usuario u) {
        usuariosArray.add(u);
    }

    public static void ensureAdminUser() {
        // Carrega usuários do arquivo antes de verificar
        fetchUsersFromArchive();

        boolean adminExists = usuariosArray.stream().anyMatch(u -> u.isAdmin);
        if (adminExists) return;

        // Cria admin padrão com senha hasheada
        String senhaHasheada = PasswordUtil.hash("admin123");
        Usuario defaultAdmin = new Usuario("admin@admin.com", "admin", senhaHasheada);
        defaultAdmin.isAdmin = true;
        addToUserList(defaultAdmin);

        try (FileWriter writer = new FileWriter(AppConfig.USUARIOS_INFO, true);
             FileWriter emailWriter = new FileWriter(AppConfig.EMAIL_INFO, true)) {
            writer.write("admin," + senhaHasheada + ",true,false,false,false" + System.lineSeparator());
            emailWriter.write("admin@admin.com" + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fetchUsersFromArchive() {
        usuariosArray.clear();
        try (BufferedReader userReader = new BufferedReader(new FileReader(AppConfig.USUARIOS_INFO));
             BufferedReader emailReader = new BufferedReader(new FileReader(AppConfig.EMAIL_INFO))) {
            String userLine;
            while ((userLine = userReader.readLine()) != null) {
                String emailLine = emailReader.readLine();
                String[] p = userLine.split(",");
                if (p.length == 6) {
                    Usuario u = new Usuario(
                            emailLine != null ? emailLine : "",
                            p[0],
                            p[1], // senha já armazenada como hash
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

    public static void saveUser(Usuario novoUsuario) {
        String permissions =
                novoUsuario.isAdmin + "," +
                        novoUsuario.isGestor + "," +
                        novoUsuario.isCandidato + "," +
                        novoUsuario.isRecrutador;
        try (FileWriter writer = new FileWriter(AppConfig.USUARIOS_INFO, true);
             FileWriter emailWriter = new FileWriter(AppConfig.EMAIL_INFO, true)) {
            writer.write(novoUsuario.getLogin() + "," + novoUsuario.getSenha() + "," + permissions + System.lineSeparator());
            emailWriter.write(novoUsuario.getEmail() + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Verifica login usando hash seguro — compatível com senhas antigas em texto puro. */
    public static boolean checkForUser(String login, String senha) {
        return usuariosArray.stream()
                .anyMatch(u -> u.getLogin().equals(login)
                        && PasswordUtil.verificar(senha, u.getSenha()));
    }

    public static Usuario searchFor(String login) {
        return usuariosArray.stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst()
                .orElse(null);
    }
}