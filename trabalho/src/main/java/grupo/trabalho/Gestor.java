package grupo.trabalho;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Gestor extends Usuario{

    Gestor(String meuEmail, String meuLogin, String minhaSenha) {
        super(meuEmail, meuLogin, minhaSenha);
    }

    public Vaga criarVaga(String meuCargo, String meusRequisitos, String meuDepartamento, String meuStatus, double meuSalarioBase, LocalDate minhaDataAbertura){
        Vaga novaVaga = new Vaga(meuCargo, meusRequisitos, meuDepartamento, meuStatus, meuSalarioBase, minhaDataAbertura);
        return novaVaga;
    }

    public void atribuirRecrutador(Vaga minhaVaga, Recrutador meuRecrutador){
        minhaVaga.recrutador = meuRecrutador;
    }

    public void autorizarContratacao(Contratacao minhaContratacao){
        minhaContratacao.estaAutorizado = true;
    }

    //+gerarRelatorioGestao(): Relatorio
}
