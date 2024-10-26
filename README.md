![logo do projeto](https://github.com/user-attachments/assets/e07f7d1b-05de-4d3c-9a79-fa831f723680)

<div align="center">

![licensa do repositorio](https://img.shields.io/badge/LICENSA-MIT-blue)
![status do projeto](https://img.shields.io/badge/STATUS-FINALIZADO-GREEN)
![estrelas do projeto](https://img.shields.io/github/stars/yo-melloo?style=social)

Este projeto é um desafio proposto pelo programa ONE (Oracle Next Education) para consolidação e avaliação de conhecimentos em Java. Pondo em prática noções básicas de manipulação de APIs e Orientação a Objetos. Trata-se de um conversor de moedas, que consulta dados atualizados em tempo real na internet e realiza conversão solicitada para o usuário.

</div>

## 📍 Índice

* [Funcionalidades](#Funcionalidades)
* [Acesso ao Projeto](#Acesso-ao-projeto)
* [Funcionalidades e Tecnologias utilizddas](#Funcionalidades-e-Tecnologias-utilizadas)
* [Autores do Projeto](#Autores-do-projeto)

## ✔︎ Funcionalidades e Tecnologias utilizadas
### Funcionalidades:
- `Conversão de moedas geral`: A aplicação dispõe de alguma escolhas de conversão rápidas, que são pré-programadas para interação com a API de cotação (oque indiretamente miniminiza as chances de erro).
- `Conversão de moedas extra`: Ainda é possível realizar uma conversão com moedas não dispostas no menu (oque significa que pode ocorrer erros na busca de uma moeda específica).
- `Histórico de conversão`: Após a primeira conversão, a qualquer momento no menu é possível acessar o histórico de conversões da sessão atual, ao finalizar a aplicação, é disponibilizado um arquivo .txt com o histórico.

### Tecnicas e Tecnologias utilizadas:
- [`Exchange-Rate API`](https://app.exchangerate-api.com/sign-up): API proposta pela ONE, realiza a busca pelas informações da moeda, sendo ela código, e relação com outras moedas (valor usado de base para a conversão)
- `Gson`: Biblioteca do Google para manipulação de elementos `.json` no projeto.
- `java.time`: Utilizado na escrita do histórico, para ajudar a determinar quando a conversão relatada se fez válida.
- `InetAddress`: Utilizado na leitura do nome do dispositivo, servindo apenas para identificar o usuário e rotular o arquivo gerado para o histórico.

## 📂 Acesso ao projeto
### Clonando repositório e configurando projeto:

* Clone o repositório:

  ```
    git clone https://github.com/yo-melloo/Conversor-de-Moedas-ONE-Challenge.git
  ```
* Abra o projeto no `IntelliJ IDEA` (recomendado);
* Acesse o site da [`Exchange-Rate API`](https://app.exchangerate-api.com/sign-up), crie uma conta para obter uma chave de acesso;
* Configure a sua variável de ambiente `"API_KEY"` com o valor da chave de acesso do passo anterior;
* Compile e execute o programa pelo método principal localizado em `br.com.mello.conversor.main.CoinConverterAppPlay`.

## 👤 Autores do projeto

<div align="center">

<img src="https://github.com/user-attachments/assets/7c6bd326-3334-44f6-8817-bd187423012b" height="350" float="left" alt="octocat de Gustavo"></img>
<img src="https://github.com/user-attachments/assets/3d1c3db8-290a-47b3-a3c3-63a298b324f8" height="185" float="right" alt="badge do desafio"></img>

### Gustavo Mello 🍫
#### Aluno ONE, Turma 7
"Meu octocat não parece comigo (ainda)"

</div>

## Consideração final:
* O projeto cumpriu o desafio proposto, mas vou continuar adicionando e melhorando funções dele para melhorar minhas habilidades.
