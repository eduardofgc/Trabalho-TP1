# Sistema de Gestão de Recrutamento

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

**Professora:** Roberta Barbosa Oliveira

---

## Sobre o Projeto

Sistema desktop de gerenciamento de recrutamento e seleção desenvolvido em Java com interface gráfica JavaFX. A aplicação permite o controle de vagas, candidatos, entrevistas e funcionários, além da geração de relatórios em PDF.

---

## Tecnologias Utilizadas

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

---

## Como Executar

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

---

## Estrutura do Projeto

```
Trabalho-TP1/
├── trabalho/                    # Código-fonte principal (Maven)
│   └── src/main/java/grupo/trabalho/
├── Diagramas e seus códigos/    # Diagramas UML e PlantUML
├── Relatório_Final_Grupo_6.pdf  # Relatório final do projeto
├── Relatório_Parcial_*.pdf      # Relatórios parciais de entrega
└── README.md
```

---

## Documentação

Os relatórios com a especificação completa do sistema, diagramas de classe, casos de uso e decisões de projeto estão disponíveis nos arquivos PDF na raiz do repositório.

---

## Licença

Projeto acadêmico desenvolvido para fins educacionais — Universidade de Brasília, 2024.
