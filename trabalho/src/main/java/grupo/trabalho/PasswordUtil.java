package grupo.trabalho;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


public class PasswordUtil {

    private static final String ALGORITHM = "SHA-256";
    private static final int SALT_BYTES = 16;


    public static String hash(String senhaTexto) {
        byte[] salt = gerarSalt();
        byte[] hashBytes = hashComSalt(senhaTexto, salt);
        return Base64.getEncoder().encodeToString(salt)
                + ":" + Base64.getEncoder().encodeToString(hashBytes);
    }

    public static boolean verificar(String senhaTexto, String hashArmazenado) {
        try {
            String[] partes = hashArmazenado.split(":", 2);
            if (partes.length != 2) {

                return senhaTexto.equals(hashArmazenado);
            }
            byte[] salt = Base64.getDecoder().decode(partes[0]);
            byte[] hashEsperado = Base64.getDecoder().decode(partes[1]);
            byte[] hashCalculado = hashComSalt(senhaTexto, salt);
            return MessageDigest.isEqual(hashCalculado, hashEsperado);
        } catch (Exception e) {

            return senhaTexto.equals(hashArmazenado);
        }
    }

    private static byte[] gerarSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTES];
        random.nextBytes(salt);
        return salt;
    }

    private static byte[] hashComSalt(String senha, byte[] salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            digest.update(salt);
            return digest.digest(senha.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo SHA-256 não disponível", e);
        }
    }
}