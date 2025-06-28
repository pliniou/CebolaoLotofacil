# 🎲 Cebolao Generator

![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![Platform](https://img.shields.io/badge/platform-Android-blue)
![License](https://img.shields.io/badge/license-MIT-purple)

Um assistente inteligente e sofisticado para apostadores da Lotofácil, desenvolvido em Kotlin com Jetpack Compose. Este aplicativo vai além da sorte, utilizando filtros estatísticos para gerar jogos com maior probabilidade de seguir padrões de resultados e permitindo uma análise histórica detalhada de qualquer combinação de dezenas.

---

## 📱 Telas do Aplicativo

| ![Tela Inicial](screenshots/tela1.jpg "1") | ![Tela do Gerador](screenshots/tela2.jpg "2") | ![Tela de Jogos Gerados](screenshots/tela3.jpg "3") | ![Tela de Conferencia de Jogos](screenshots/tela4.jpg "4") |
|:------------------------------------------:|:---------------------------------------------:|:---------------------------------------------------:|:----------------------------------------------------------:|
| Tela Inicial                               | Gerador                                       | Jogos Gerados                                      | Conferência de Jogos                                       |

---

## 🚀 Funcionalidades Principais

*   **Gerador Inteligente:** Crie jogos que atendem a múltiplos filtros estatísticos simultaneamente.
    *   **Filtros Expansíveis:** Interface limpa que revela os controles apenas quando um filtro é ativado.
    *   **8 Filtros Detalhados:** Soma das Dezenas, Pares/Ímpares, Números Primos, Fibonacci, Moldura/Retrato, Múltiplos de 3 e Repetidas do Último Concurso.
    *   **Filtro de Repetidas Interativo:** Selecione as 15 dezenas do último sorteio em um grid e defina quantas devem se repetir.
    *   **Feedback Inteligente:** Se a geração falhar, o app informa qual filtro é o mais restritivo.
*   **Conferidor Histórico:**
    *   **Análise Completa:** Insira qualquer combinação de 15 dezenas e veja seu desempenho contra a base de dados completa da Lotofácil (mais de 3400 concursos).
    *   **Relatório Detalhado:** Descubra quantas vezes seu jogo fez 11, 12, 13, 14 e 15 pontos e quando foi a última vez que ele pontuou.
*   **Interface Moderna e Polida:**
    *   **Tema Escuro Personalizado:** Uma paleta de cores única e elegante.
    *   **Fonte Customizada (Inter):** Alta legibilidade e design moderno.
    *   **Microanimações:** Interações suaves, como a expansão dos cards e a seleção de dezenas, enriquecem a experiência.
    *   **Otimização de Performance:** Transições de tela fluidas com `AnimatedContent` e carregamento otimizado com `Splash Screen API`.
*   **Atalhos de Usabilidade:** Envie um jogo gerado diretamente para o conferidor com um único clique.

---

## 🛠️ Stack de Tecnologia e Arquitetura

Este projeto foi construído utilizando as tecnologias mais modernas do ecossistema Android:

*   **Linguagem:** [Kotlin](https://kotlinlang.org/)
*   **Interface:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (UI Declarativa)
*   **Arquitetura:** MVVM (Model-View-ViewModel)
*   **Assincronia:** Kotlin Coroutines & Flow
*   **Gerenciamento de Estado:** `StateFlow` e `collectAsState`
*   **Navegação:** `HorizontalPager` e `BottomNavigationBar`
*   **Splash Screen:** `core-splashscreen` API para uma inicialização elegante.

A arquitetura do aplicativo separa claramente as responsabilidades:
-   **UI Layer (Views/Composables):** Responsável por exibir os dados e capturar as interações do usuário.
-   **ViewModel Layer:** Atua como uma ponte, contendo a lógica de UI e expondo os estados para a View.
-   **Data/Logic Layer (Repository/Generator):** Responsável pela lógica de negócio, como a geração de jogos e a leitura/análise do histórico de sorteios.

---

## ⚙️ Como Executar o Projeto

1.  Clone este repositório:
    ```bash
    git clone https://github.com/SEU-USUARIO/cebolao-generator.git
    ```
2.  Abra o projeto no [Android Studio](https://developer.android.com/studio).
3.  Aguarde o Gradle sincronizar as dependências.
4.  Execute o aplicativo em um emulador ou dispositivo físico (API 26+).

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👨‍💻 Autor

*   GitHub: [pliniou](https://github.com/pliniou)

Feito com ❤ por Cebola Studios.