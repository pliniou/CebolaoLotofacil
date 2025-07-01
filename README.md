# 🎲 Cebolao Lotofácil Generator

![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![Platform](https://img.shields.io/badge/platform-Android-blue)
![Kotlin Version](https://img.shields.io/badge/Kotlin-2.0.0-purple.svg)
![Compose Version](https://img.shields.io/badge/Compose-2024.06.00-blueviolet)

Um assistente inteligente e sofisticado para apostadores da Lotofácil, desenvolvido em Kotlin com Jetpack Compose. Este aplicativo vai além da sorte, utilizando filtros estatísticos para gerar jogos com maior probabilidade de seguir padrões históricos e permitindo uma análise detalhada de qualquer combinação de dezenas.

---

## 📱 Telas do Aplicativo

| ![Tela Inicial](screenshots/tela1.jpg "1") | ![Tela do Gerador](screenshots/tela2.jpg "2") | ![Tela de Jogos Gerados](screenshots/tela3.jpg "3") | ![Tela de Conferencia de Jogos](screenshots/tela4.jpg "4") |
|:------------------------------------------:|:---------------------------------------------:|:---------------------------------------------------:|:----------------------------------------------------------:|
|                Tela Inicial                |                    Gerador                    |                    Jogos Gerados                    |                    Conferência de Jogos                    |

---

## 🚀 Funcionalidades Principais

*   **Gerador Inteligente:** Crie jogos que atendem a múltiplos filtros estatísticos simultaneamente.
    *   **Filtros Expansíveis:** Interface limpa que revela os controles apenas quando um filtro é ativado.
    *   **9 Filtros Detalhados:** Soma das Dezenas, Pares/Ímpares, Primos, Fibonacci, Moldura/Retrato, Múltiplos de 3 e Repetidas do Último Concurso.
    *   **Filtro de Repetidas Interativo:** Selecione as 15 dezenas do último sorteio em um grid e defina quantas devem se repetir.
    *   **Feedback Inteligente:** Se a geração falhar, o app informa qual filtro é o mais restritivo.
*   **Conferidor Histórico:**
    *   **Análise Completa:** Insira qualquer combinação de 15 dezenas e veja seu desempenho contra a base de dados completa da Lotofácil (mais de 3400 concursos).
    *   **Relatório Detalhado:** Descubra quantas vezes seu jogo fez 11, 12, 13, 14 e 15 pontos e quando foi a última vez que ele pontuou.
*   **Interface Moderna e Otimizada:**
    *   **Tema Escuro Personalizado:** Uma paleta de cores única e elegante.
    *   **Fonte Customizada (Inter):** Alta legibilidade e design moderno.
    *   **Microanimações:** Interações suaves, como a expansão dos cards e a seleção de dezenas, enriquecem a experiência.
    *   **Performance Otimizada:** Recomposições minimizadas com `derivedStateOf`, threading correto com `Dispatchers.IO` para I/O, e um algoritmo de geração otimizado para prevenir congelamentos.
    *   **Totalmente Acessível:** Suporte completo a leitores de tela (TalkBack) com descrições de conteúdo semânticas em todos os elementos interativos.
*   **Atalhos de Usabilidade:** Envie um jogo gerado diretamente para o conferidor com um único clique.

---

## 🛠️ Stack de Tecnologia e Arquitetura

Este projeto foi construído utilizando as tecnologias mais modernas do ecossistema Android, com foco em performance, escalabilidade e manutenibilidade.

*   **Linguagem:** [Kotlin](https://kotlinlang.org/)
*   **Interface:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (UI Declarativa)
*   **Arquitetura:** MVVM (Model-View-ViewModel) seguindo os princípios SOLID.
*   **Assincronia:** Kotlin Coroutines & Flow para um gerenciamento de dados reativo e eficiente.
*   **Gerenciamento de Estado:** `StateFlow` e `collectAsState` com otimizações de recomposição (`derivedStateOf`).
*   **Navegação:** `HorizontalPager` e `BottomNavigationBar` para uma experiência de usuário fluida.
*   **Splash Screen:** `core-splashscreen` API para uma inicialização elegante.

A arquitetura do aplicativo separa claramente as responsabilidades:
-   **UI Layer (Views/Composables):** Camada reativa e otimizada, responsável apenas por exibir o estado e capturar as interações do usuário.
-   **ViewModel Layer:** Atua como uma ponte, contendo a lógica de UI, gerenciando o estado e delegando as operações de negócio.
-   **Data/Logic Layer (Repository/Generator):** Camada desacoplada contendo a lógica de negócio (geração de jogos) e o acesso aos dados (leitura do histórico), garantindo alta coesão e baixo acoplamento.

---

## 🧪 Testes

O projeto possui uma suíte de testes robusta para garantir a qualidade e a estabilidade do código, cobrindo as camadas de lógica e UI.

*   **Testes de Unidade:** Validam os `ViewModels` e a lógica de negócio (`GameGenerator`), utilizando:
    *   **JUnit4:** Framework base para os testes.
    *   **Mockk:** Para criação de mocks e stubs.
    *   **Turbine:** Para testar `StateFlow` de forma reativa e assertiva.
    *   **Coroutines Test:** Para controlar o fluxo de execução de coroutines.
*   **Testes de Instrumentação (UI):** Verificam o comportamento das telas (`Composables`) com:
    *   **Compose Test Rule:** Para renderizar e interagir com componentes Compose.
    *   **Espresso:** Para validações de UI e interações do usuário.

---

## ⚙️ Como Executar o Projeto

1.  Clone este repositório:
    ```bash
    git clone https://github.com/SEU-USUARIO/cebolao-generator.git
    ```
2.  Abra o projeto no [Android Studio](https://developer.android.com/studio) (versão Iguana ou superior recomendada).
3.  Aguarde o Gradle sincronizar as dependências.
4.  Execute o aplicativo em um emulador ou dispositivo físico (API 26+).

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

---

## 👨‍💻 Autor

*   GitHub: [pliniou](https://github.com/pliniou)

Feito com ❤️ por Cebola Studios.