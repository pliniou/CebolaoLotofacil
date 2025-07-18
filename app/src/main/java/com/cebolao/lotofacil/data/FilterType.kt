package com.cebolao.lotofacil.data

enum class FilterType(
    val title: String,
    val description: String,
    val fullRange: ClosedFloatingPointRange<Float>,
    val defaultRange: ClosedFloatingPointRange<Float>
) {
    SOMA_DEZENAS(
        title = "Soma das Dezenas",
        description = "Um dos filtros mais clássicos e eficazes. A soma dos 15 números sorteados raramente resulta em valores muito baixos ou muito altos. A análise histórica mostra que mais de 70% dos resultados se concentram na faixa de 170 a 220. Usar este filtro elimina milhares de combinações estatisticamente improváveis.",
        fullRange = 37f..267f,
        defaultRange = 170f..220f
    ),
    PARES(
        title = "Números Pares",
        description = "O equilíbrio entre dezenas pares e ímpares é a espinha dorsal de um bom jogo na Lotofácil. Sorteios com excesso de números pares (mais de 9) ou falta deles (menos de 6) são extremamente raros. Manter o jogo dentro desta faixa de equilíbrio é uma estratégia fundamental.",
        fullRange = 2f..11f,
        defaultRange = 6f..9f
    ),
    IMPARES(
        title = "Números Ímpares",
        description = "Complementar ao filtro de pares, este garante que seu jogo mantenha o equilíbrio entre os dois tipos de dezenas. A grande maioria dos concursos premia jogos que contêm entre 6 e 9 números ímpares, refletindo a distribuição natural dos números no volante.",
        fullRange = 4f..13f,
        defaultRange = 6f..9f
    ),
    PRIMOS(
        title = "Números Primos",
        description = "Existem 9 números primos no universo da Lotofácil (2, 3, 5, 7, 11, 13, 17, 19, 23). É estatisticamente improvável que um sorteio contenha muitos ou poucos deles. A faixa mais comum, presente na maioria dos resultados, é de 4 a 7 números primos por jogo.",
        fullRange = 1f..9f,
        defaultRange = 4f..7f
    ),
    MOLDURA(
        title = "Dezenas na Moldura",
        description = "A 'moldura' do volante são os 16 números das bordas. Como a moldura contém mais números que o centro (miolo), é natural que mais dezenas sorteadas venham dela. A maioria dos sorteios apresenta entre 8 e 11 dezenas da moldura, tornando este um excelente filtro de posicionamento.",
        fullRange = 5f..13f,
        defaultRange = 8f..11f
    ),
    RETRATO(
        title = "Dezenas no Retrato (Miolo)",
        description = "O 'retrato' ou 'miolo' são os 9 números centrais do volante. Em contrapartida à moldura, a tendência é que menos dezenas sejam sorteadas nesta área. A faixa de 4 a 7 dezenas no retrato é a mais observada nos resultados históricos, ajudando a equilibrar o seu jogo.",
        fullRange = 2f..9f,
        defaultRange = 4f..7f
    ),
    FIBONACCI(
        title = "Sequência de Fibonacci",
        description = "Os números da sequência de Fibonacci presentes no volante são 1, 2, 3, 5, 8, 13 e 21. Embora seja um padrão matemático, a análise dos sorteios mostra uma clara tendência de 3 a 5 desses números por concurso. É um filtro refinado para otimizar ainda mais a sua aposta.",
        fullRange = 1f..7f,
        defaultRange = 3f..5f
    ),
    MULTIPLOS_DE_3(
        title = "Múltiplos de 3",
        description = "Existem 8 múltiplos de 3 no volante. Manter a quantidade desses números em equilíbrio (geralmente entre 3 e 6 por jogo) é uma estratégia eficaz para eliminar combinações com padrões de repetição pouco prováveis de serem sorteadas.",
        fullRange = 1f..8f,
        defaultRange = 3f..6f
    ),
    REPETIDAS_CONCURSO_ANTERIOR(
        title = "Repetidas do Concurso Anterior",
        description = "Este é considerado por muitos o filtro mais poderoso. É uma característica marcante da Lotofácil a repetição de dezenas de um concurso para o outro. É extremamente comum que entre 8 e 10 números do sorteio anterior reapareçam. O app carrega o último resultado oficial para aplicar este filtro automaticamente.",
        fullRange = 5f..12f,
        defaultRange = 8f..10f
    );
}