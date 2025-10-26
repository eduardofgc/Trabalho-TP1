package grupo.trabalho;
import java.util.ArrayList;

public class AdmClasses {
    public static ArrayList<Usuario> usuariosArray = new ArrayList<>();

    public static void addToUserList(Usuario meuUsuario){
        usuariosArray.add(meuUsuario);
    }

    public Usuario searchFor(String meuLogin){
        boolean found = false;

        for (Usuario u : usuariosArray){
            if (u.getLogin().equals(meuLogin)){
                found = true;
                return u;
            }
        }

        return null;
    }


}
