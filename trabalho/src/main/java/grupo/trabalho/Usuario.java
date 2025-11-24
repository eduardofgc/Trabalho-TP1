package grupo.trabalho;

public class Usuario {
    String email, login, senha;
    boolean isAdmin, isCandidato, isRecrutador, isGestor;

    Usuario(String email, String login, String senha) {
        this.email = email;
        this.login = login;
        this.senha = senha;
    }

    Usuario(String login, String senha){
        this.login = login;
        this.senha = senha;
    }

    Usuario(String login, String senha, boolean admin, boolean gestor, boolean candidato, boolean recrutador){
        this.login = login;
        this.senha = senha;
        this.isAdmin = admin;
        this.isGestor = gestor;
        this.isCandidato = candidato;
        this.isRecrutador = recrutador;
    }

    public Usuario(String email, String login, String senha, boolean admin, boolean gestor, boolean candidato, boolean recrutador) {
        this.email = email;
        this.login = login;
        this.senha = senha;
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

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }

    public String getCargo(){
        if (isAdmin){
            return "Administrador";
        }
        else if (isRecrutador){
            return "Recrutador";
        }
        else if (isCandidato){
            return "Candidato";
        }
        else{
            return "Gestor";
        }
    }

    public boolean fazerLogin(String loginTentado, String senhaTentada) {
        return loginTentado.equals(this.login) && senhaTentada.equals(this.senha);
    }
}

