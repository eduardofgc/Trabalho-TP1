package grupo.trabalho;

public class UsuarioLinha {
    private String nome;
    private String email;
    private String roles;

    public UsuarioLinha(String nome, String email, String roles) {
        this.nome = nome;
        this.email = email;
        this.roles = roles;
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getRoles() { return roles; }
}

