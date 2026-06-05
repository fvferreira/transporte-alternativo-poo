# Sistema de Gerenciamento de Transporte Alternativo

Este sistema foi desenvolvido como projeto final para a disciplina de **Programação Orientada a Objetos (POO)** na **Universidade Federal Rural do Semi-Árido (UFERSA)**. O objetivo principal é automatizar a gestão de frotas, motoristas e vendas de passagens em uma cooperativa de vans.

## 👥 Equipe
* Alicia Samilly Oliveira Bento 
* João Victor Ferreira Da Silva 
* Lyrian Fernandes França Ribeiro 
* Rafael Victor Rocha Arnaud 

---

## 🛠️ Tecnologias e Arquitetura
* **Linguagem:** Java 17
* **Interface Gráfica:** FlatLaf UI (v3.4.1)
* **Arquitetura:** Padrão MVC (Model-View-Controller)
* **Modelagem:** Diagrama de Classes UML desenvolvido no Lucidchart 

---

##  Funcionalidades Principais (Regras de Negócio)
* **Gestão de Passageiros e Motoristas:** Cadastro de usuários, vínculo obrigatório de veículo ao motorista e controle de carteira virtual.
* **Controle de Viagens:** Criação, inicialização, finalização e cancelamento de rotas com tratamento de conflitos de horários sincronos.
* **Motor de Regras Restritivas:** * Limite de peso para bagagens (30 kg no bagageiro / 5 kg de mão).
  * Regras de biossegurança para transporte de pets e acessibilidade para cão-guia.
  * Retenção de taxa de 10% para cancelamentos de passagens com menos de 24 horas de antecedência.

Ao todo, o sistema conta com **26 funcionalidades implementadas** seguindo fielmente a modelagem UML proposta.

---

## 🚀 Como Executar o Projeto

Certifique-se de ter o **JDK 17** (ou superior) instalado em sua máquina e de estar na raiz do diretório do projeto antes de executar os comandos no terminal.

### 1. Compilação
Para compilar todos os pacotes do projeto direcionando os arquivos binários para a pasta `bin` e incluindo a biblioteca de interface gráfica:

```bash
javac -cp "lib/flatlaf-3.4.1.jar" -d bin src/model/*.java src/controller/*.java src/repository/*.java src/view/*.java
```
### 2. Execução
Para rodar a aplicação a partir da classe principal (Main):

```bash
java -cp "bin;lib/flatlaf-3.4.1.jar" view.Main
```
Caso o sistema opercional seja baseado em Linux:
```bash
java -cp "bin:lib/flatlaf-3.4.1.jar" view.Main
```


