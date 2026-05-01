Trabalho final de Técnicas de Programação 1
# Sistema de Gestão de Recrutamento

<<<<<<< HEAD
# Sistema de Gestão de Recrutamento

Trabalho Final — Técnicas de Programação 1
**Grupo 6** | Universidade de Brasília

---

## Integrantes

| Nome                       |
| -------------------------- |
| Eduardo Rocha Biagini      |
| Lucas Centurion Netto      |
| Filipe Araújo Lopes Grillo |
| João Vitor Lopes Rocha     |

=======
Trabalho Final — Técnicas de Programação 1
**Grupo 6** | Universidade de Brasília

---

## Integrantes

| Nome |
|---|
| Eduardo Rocha Biagini |
| Lucas Centurion Netto |
| Filipe Araújo Lopes Grillo |
| João Vitor Lopes Rocha |

>>>>>>> 3655ed0fff5b859bd0b683464546785430093f26
**Professora:** Roberta Barbosa Oliveira

---

## Sobre o Projeto

Sistema desktop de gerenciamento de recrutamento e seleção desenvolvido em Java com interface gráfica JavaFX. A aplicação permite o controle de vagas, candidatos, entrevistas e funcionários, além da geração de relatórios em PDF.

---

## Tecnologias Utilizadas

<<<<<<< HEAD
- **Java 17** — Linguagem principal
- **JavaFX 17** — Interface gráfica
- **Maven** — Gerenciamento de dependências e execução
- **Apache PDFBox** — Geração de relatórios em PDF

---

## Pré-requisitos

Apenas o **Java JDK 17 ou superior** é necessário. O Maven e o JavaFX são gerenciados automaticamente pelo projeto — não é necessário instalá-los nem configurá-los manualmente.

- [Java JDK 17+](https://www.oracle.com/java/technologies/downloads/)
=======
- **Java** — Linguagem principal
- **JavaFX** — Interface gráfica
- **Maven** — Gerenciamento de dependências
- **Apache PDFBox** — Geração de relatórios em PDF
- **IntelliJ IDEA** — IDE recomendada para execução

---

## ⚙️ Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

- [Java JDK 17+](https://www.oracle.com/java/technologies/downloads/)
- [Maven 3.8+](https://maven.apache.org/download.cgi)
- [JavaFX SDK](https://openjfx.io/) (caso não esteja sendo gerenciado pelo Maven)
- IntelliJ IDEA (recomendado) ou outra IDE com suporte a Maven
>>>>>>> 3655ed0fff5b859bd0b683464546785430093f26

---

## Como Executar

<<<<<<< HEAD
1. Clone o repositório:

```bash
git clone https://github.com/eduardofgc/Trabalho-TP1.git
cd Trabalho-TP1/trabalho
```

2. Execute via Maven Wrapper:

**Linux / macOS:**

```bash
./mvnw javafx:run
```

**Windows (PowerShell):**

```powershell
$env:JAVA_HOME = "C:\caminho\para\seu\jdk"
.\mvnw javafx:run
```

O Maven baixará todas as dependências automaticamente na primeira execução.
=======
1. **Clone o repositório:**

```bash
git clone https://github.com/eduardofgc/Trabalho-TP1.git
cd Trabalho-TP1
```

2. **Abra o projeto na sua IDE** e navegue até:

```
trabalho/src/main/java/grupo/trabalho/HelloApplication.java
```

3. **Configure o JavaFX** — adicione a biblioteca ao classpath e defina o VM Options com o module path correto:

```
--module-path /caminho/para/javafx/lib --add-modules javafx.controls,javafx.fxml
```

4. **Execute a função `main()`** dentro de `HelloApplication.java`.
>>>>>>> 3655ed0fff5b859bd0b683464546785430093f26

---

## Estrutura do Projeto

```
Trabalho-TP1/
<<<<<<< HEAD
├── trabalho/                          # Modulo Maven principal
│   ├── src/main/java/grupo/trabalho/  # Codigo-fonte
│   ├── src/main/resources/            # FXMLs, imagens e CSS
│   ├── data/                          # Arquivos de dados em runtime
│   └── pom.xml                        # Configuracao do Maven
├── docs/                              # Relatorios e diagramas
=======
├── trabalho/                    # Código-fonte principal (Maven)
│   └── src/main/java/grupo/trabalho/
├── Diagramas e seus códigos/    # Diagramas UML e PlantUML
├── Relatório_Final_Grupo_6.pdf  # Relatório final do projeto
├── Relatório_Parcial_*.pdf      # Relatórios parciais de entrega
>>>>>>> 3655ed0fff5b859bd0b683464546785430093f26
└── README.md
```

---

<<<<<<< HEAD
## Documentacao

Os relatórios com a especificação completa do sistema, diagramas de classe, casos de uso e decisões de projeto estão disponíveis na pasta `docs/`.

---

## Licenca
=======
## Documentação

Os relatórios com a especificação completa do sistema, diagramas de classe, casos de uso e decisões de projeto estão disponíveis nos arquivos PDF na raiz do repositório.

---

## Licença
>>>>>>> 3655ed0fff5b859bd0b683464546785430093f26

Projeto acadêmico desenvolvido para fins educacionais — Universidade de Brasília, 2024.
