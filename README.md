# Gamma RH

Trabalho Final — Técnicas de Programação 1
**Grupo 6** | Universidade de Brasília

<div align="center">
  <img src="docs/logo.png" alt="Tela principal do sistema"/>
</div>
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

- **Java 17** — Linguagem principal
- **JavaFX 17** — Interface gráfica
- **Maven** — Gerenciamento de dependências e execução
- **Apache PDFBox** — Geração de relatórios em PDF

---

## Pré-requisitos

Apenas o **Java JDK 17 ou superior** é necessário. O Maven e o JavaFX são gerenciados automaticamente pelo projeto — não é necessário instalá-los nem configurá-los manualmente.

- [Java JDK 17+](https://www.oracle.com/java/technologies/downloads/)

---

## Como Executar

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

---

## Estrutura do Projeto

```
Trabalho-TP1/
├── trabalho/                          # Modulo Maven principal
│   ├── src/main/java/grupo/trabalho/  # Codigo-fonte
│   ├── src/main/resources/            # FXMLs, imagens e CSS
│   ├── data/                          # Arquivos de dados em runtime
│   └── pom.xml                        # Configuracao do Maven
├── docs/                              # Relatorios e diagramas
└── README.md
```

---

## Documentação

Os relatórios com a especificação completa do sistema, diagramas de classe, casos de uso e decisões de projeto estão disponíveis na pasta `docs/`.

---

## Licenca

Projeto acadêmico desenvolvido para fins educacionais — Universidade de Brasília, 2024.
