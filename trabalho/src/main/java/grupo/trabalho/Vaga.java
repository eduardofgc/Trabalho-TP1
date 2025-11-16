package grupo.trabalho;
import java.time.LocalDate;
import java.util.Date;

public class Vaga {
    private String cargo, requisitos, departamento, status;
    private double salarioBase;
    private LocalDate dataAbertura, dataFechamento;
    public boolean estaAberta;
    public Recrutador recrutador;

    Vaga(String meuCargo, String meusRequisitos, String meuDepartamento, String meuStatus, double meuSalarioBase, LocalDate minhaDataAbertura){
        this.cargo = meuCargo;
        this.requisitos = meusRequisitos;
        this.departamento = meuDepartamento;
        this.status = meuStatus;
        this.salarioBase = meuSalarioBase;
        this.dataAbertura = minhaDataAbertura;

        this.estaAberta = true;
    }

    public String getCargo() { return cargo; }
    public String getRequisitos() { return requisitos; }
    public String getDepartamento() { return departamento; }
    public String getStatus() { return status; }
    public double getSalarioBase() { return salarioBase; }
    public LocalDate getDataAbertura() { return dataAbertura; }

    public void fecharVaga(){
        this.estaAberta = false;
    }

    //-regime: RegimeContratacao
    //+listarCandidaturas(): Candidatura[]
    //+filtrarVagas(...): Vaga[]
}
