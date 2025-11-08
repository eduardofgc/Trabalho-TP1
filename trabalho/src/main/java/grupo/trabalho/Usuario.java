package grupo.trabalho;

public class Usuario {
    String email, login, senha;
    boolean isAdmin, isCandidato, isRecrutador, isGestor;

    Usuario(String meuEmail, String meuLogin, String minhaSenha) {
        this.email = meuEmail;
        this.login = meuLogin;
        this.senha = minhaSenha;
    }

    Usuario(String meuLogin, String minhaSenha){
        this.login = meuLogin;
        this.senha = minhaSenha;
    }

    Usuario(String meuLogin, String minhaSenha, boolean admin, boolean gestor, boolean candidato, boolean recrutador){
        this.login = meuLogin;
        this.senha = minhaSenha;
        this.isAdmin = admin;
        this.isGestor = gestor;
        this.isCandidato = candidato;
        this.isRecrutador = recrutador;
    }

    public String getLogin(){
        return this.login;
    }

    public String getSenha(){
        return this.senha;
    }

    public boolean fazerLogin(String loginTentado, String senhaTentada) {
        return loginTentado.equals(this.login) && senhaTentada.equals(this.senha);
    }
}
