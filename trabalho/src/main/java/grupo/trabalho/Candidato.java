package grupo.trabalho;

public class Candidato {
    private String nome;
    private String email;
    private String vaga;

    public Candidato(String nome, String email, String vaga) {
        this.nome = nome;
        this.email = email;
        this.vaga = vaga;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getVaga() { return vaga; }
    public void setVaga(String vaga) { this.vaga = vaga; }
}