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


}
