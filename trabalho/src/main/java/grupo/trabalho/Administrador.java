package grupo.trabalho;
import java.util.ArrayList;

public class Administrador extends Usuario{

    Administrador(String meuEmail, String meuLogin, String minhaSenha) {
        super(meuEmail, meuLogin, minhaSenha);
    }

    public Usuario cadastrarUsuario(String meuEmail, String meuLogin, String minhaSenha){
        Usuario novoUsuario = new Usuario(meuEmail, meuLogin, minhaSenha);



        return novoUsuario;
    }

    public void excluirUsuario(Usuario meuUsuario){ //unfinished

    }

    public void editarUsuario(Usuario meuUsuario, String novoEmail, String novoLogin, String novaSenha){
        meuUsuario.email = novoEmail;
        meuUsuario.senha = novaSenha;
    }

    public void listarUsuarios(ArrayList<Usuario> usuariosArray){
        for (Usuario u : usuariosArray){
            //temp
            System.out.println(u.getLogin());
        }
    }
}
