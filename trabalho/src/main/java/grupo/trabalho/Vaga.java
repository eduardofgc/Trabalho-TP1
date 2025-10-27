package grupo.trabalho;
import java.util.Date;

public class Vaga {
    private String cargo, requisitos, departamento, status;
    private double salarioBase;
    private Date dataAbertura;
    public boolean estaAberta;
    public Recrutador recrutador;

    Vaga(String meuCargo, String meusRequisitos, String meuDepartamento, String meuStatus, double meuSalarioBase, Date minhaDataAbertura){
        this.cargo = meuCargo;
        this.requisitos = meusRequisitos;
        this.departamento = meuDepartamento;
        this.status = meuStatus;
        this.salarioBase = meuSalarioBase;
        this.dataAbertura = minhaDataAbertura;

        this.estaAberta = true;
    }

    public void fecharVaga(){
        this.estaAberta = false;
    }

    //-regime: RegimeContratacao
    //+listarCandidaturas(): Candidatura[]
    //+filtrarVagas(...): Vaga[]
}
