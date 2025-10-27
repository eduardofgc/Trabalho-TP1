package grupo.trabalho;

public class Usuario {
    String email, login, senha;
    boolean isAdmin, isCandidato, isRecrutador, isGestor;

    Usuario(String meuEmail, String meuLogin, String minhaSenha) {
        this.email = meuEmail;
        this.login = meuLogin;
        this.senha = minhaSenha;
    }

    //Overload pra cadastrar usuario (setar email depois)
    Usuario(String meuLogin, String minhaSenha){
        this.login = meuLogin;
        this.senha = minhaSenha;
    }

    public String getLogin(){
        return this.login;
    }

    public String getSenha(){return this.senha;}

    public boolean fazerLogin(String loginTentado, String senhaTentada) {
        if (loginTentado.equals(this.login) && senhaTentada.equals(this.senha)) {
            return true;
        } else {
            return false;
        }
    }
}
