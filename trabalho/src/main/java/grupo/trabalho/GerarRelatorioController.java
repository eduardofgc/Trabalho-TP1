package grupo.trabalho;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GerarRelatorioController {

    @FXML
    private ListView<String> listaUsuarios;

    @FXML
    private Button btnGerarPDF;

    @FXML
    public void initialize() {
        AdmClasses.fetchUsersFromArchive();
        listaUsuarios.getItems().clear();

        for (Usuario u : AdmClasses.usuariosArray) {
            String perms =
                    (u.isAdmin ? "Admin " : "") +
                            (u.isGestor ? "Gestor " : "") +
                            (u.isCandidato ? "Candidato " : "") +
                            (u.isRecrutador ? "Recrutador " : "");

            listaUsuarios.getItems().add(u.getLogin() + "  |  " + perms.trim());
        }
    }

    @FXML
    public void gerarPDF() {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream content = new PDPageContentStream(document, page);

            content.setFont(PDType1Font.HELVETICA_BOLD, 18);
            content.beginText();
            content.newLineAtOffset(50, 800);
            content.showText("Relatório de Usuários - Gamma RH");
            content.endText();

            content.setFont(PDType1Font.HELVETICA, 12);
            content.beginText();
            content.newLineAtOffset(50, 780);
            String data = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            content.showText("Gerado em: " + data);
            content.endText();

            content.moveTo(50, 765);
            content.lineTo(550, 765);
            content.stroke();

            content.setFont(PDType1Font.HELVETICA_BOLD, 12);
            content.beginText();
            content.newLineAtOffset(50, 740);
            content.showText("Login");
            content.newLineAtOffset(250, 0);
            content.showText("Permissões");
            content.endText();

            int y = 715;
            content.setFont(PDType1Font.HELVETICA, 12);

            for (Usuario u : AdmClasses.usuariosArray) {
                if (y < 50) break;

                String perms =
                        (u.isAdmin ? "Admin " : "") +
                                (u.isGestor ? "Gestor " : "") +
                                (u.isCandidato ? "Candidato " : "") +
                                (u.isRecrutador ? "Recrutador " : "");

                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText(u.getLogin());
                content.newLineAtOffset(250, 0);
                content.showText(perms.trim());
                content.endText();

                y -= 20;
            }

            content.close();

            document.save("Relatorio_Usuarios.pdf");
            document.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF Gerado");
            alert.setHeaderText(null);
            alert.setContentText("Relatório salvo como Relatorio_Usuarios.pdf");
            alert.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao gerar PDF");
            alert.show();
        }
    }
}
