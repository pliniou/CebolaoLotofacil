package com.example.cebolaolotofacil.data

// Adicionamos um novo campo 'description'
enum class FilterType(
    val title: String,
    val description: String,
    val fullRange: ClosedFloatingPointRange<Float>,
    val defaultRange: ClosedFloatingPointRange<Float>
) {
    SOMA_DEZENAS(
        title = "Soma Total das Dezenas",
        description = "Filtra jogos pela soma dos 15 números selecionados. O filtro elimina combinações com somas muito baixas (abaixo de 170) ou muito altas (acima de 220), focando na faixa onde 68% dos sorteios historicamente ocorrem.\n\nClaro, você pode ignorar completamente este filtro e apostar que desta vez será diferente. Quem sabe a Lotofácil não resolve inovar e sortear só números baixinhos somando 50, né?",
        fullRange = 37f..267f,
        defaultRange = 170f..220f
    ),
    PARES(
        title = "Quantidade de Números Pares",
        description = "Filtra por quantidade de dezenas pares (02, 04, 06, 08, 10, 12, 14, 16, 18, 20, 22, 24). O filtro busca equilíbrio estatístico, eliminando jogos com muito poucos (menos de 6) ou muitos pares (mais de 9).\n\nMas se você acredita que os números ímpares estão conspirando contra você, fique à vontade para apostar só em pares. A matemática agradece a contribuição para as estatísticas de 'como não ganhar na loteria'.",
        fullRange = 2f..11f,
        defaultRange = 6f..9f
    ),
    IMPARES(
        title = "Quantidade de Números Ímpares",
        description = "Filtra por quantidade de dezenas ímpares (01, 03, 05, 07, 09, 11, 13, 15, 17, 19, 21, 23, 25). O filtro mantém equilíbrio estatístico, eliminando jogos com muito poucos (menos de 6) ou muitos ímpares (mais de 9).\n\nE não, apostar só em ímpares porque 'eles são mais exclusivos' não é uma estratégia. É apenas uma forma criativa de doar dinheiro para a Caixa Econômica Federal.",
        fullRange = 4f..13f,
        defaultRange = 6f..9f
    ),
    PRIMOS(
        title = "Números Primos Presentes",
        description = "Filtra pela quantidade de números primos presentes no jogo (02, 03, 05, 07, 11, 13, 17, 19, 23). O filtro elimina jogos com poucos primos (menos de 4) ou excesso (mais de 7), baseado em análise estatística histórica.\n\nSe você acha que números primos são 'especiais' porque são indivisíveis, lembre-se: na loteria, todos os números são igualmente inúteis para prever o futuro. Mas pelo menos os primos têm uma personalidade matemática interessante!",
        fullRange = 1f..9f,
        defaultRange = 4f..7f
    ),
    MOLDURA(
        title = "Dezenas da Borda do Volante",
        description = "Filtra pela quantidade de números nas bordas do cartão de apostas (01, 02, 03, 04, 05, 06, 10, 11, 15, 16, 20, 21, 22, 23, 24, 25). O filtro elimina jogos com concentração excessiva ou ausência na moldura, mantendo entre 8 e 11 dezenas dessa região.\n\nPorque obviamente as bolinhas do sorteio sabem exatamente onde estão posicionadas no seu cartão e decidem pular para as bordas por pura rebeldia. A geografia do volante é tudo na vida de uma dezena sortuda!",
        fullRange = 5f..13f,
        defaultRange = 8f..11f
    ),
    RETRATO(
        title = "Dezenas do Centro do Volante",
        description = "Filtra pela quantidade de números da região central do cartão de apostas (07, 08, 09, 12, 13, 14, 17, 18, 19). O filtro elimina jogos com poucos (menos de 4) ou muitos números centrais (mais de 7), mantendo distribuição espacial equilibrada.\n\nAs dezenas do centro são meio que os 'populares' da escola: todo mundo quer estar perto delas, mas no final das contas, elas são tão normais quanto qualquer outra. Spoiler: o centro do volante não é um portal mágico para a sorte.",
        fullRange = 2f..9f,
        defaultRange = 4f..7f
    ),
    FIBONACCI(
        title = "Números da Sequência Fibonacci",
        description = "Filtra pela quantidade de números da sequência de Fibonacci presentes no jogo (01, 02, 03, 05, 08, 13, 21). O filtro elimina jogos com poucos (menos de 3) ou muitos números (mais de 5) dessa sequência matemática.\n\nAh sim, porque claramente o universo segue padrões matemáticos na hora de sortear bolinhas. Se a natureza usa Fibonacci, por que a Lotofácil não usaria também? Próxima teoria: as dezenas também seguem a proporção áurea!",
        fullRange = 1f..7f,
        defaultRange = 3f..5f
    ),
    MULTIPLOS_DE_3(
        title = "Múltiplos de Três",
        description = "Filtra pela quantidade de números divisíveis por 3 presentes no jogo (03, 06, 09, 12, 15, 18, 21, 24). O filtro elimina jogos com poucos (menos de 3) ou excesso (mais de 6) de múltiplos de três, mantendo distribuição equilibrada.\n\nTalvez você esteja pensando que múltiplos de 3 têm algum poder especial por serem 'organizadinhos'. Newsflash: ser divisível por 3 não torna um número mais sortável. Mas hey, pelo menos você está praticando tabuada!",
        fullRange = 1f..8f,
        defaultRange = 3f..6f
    ),
    REPETIDAS_CONCURSO_ANTERIOR(
        title = "Dezenas Repetidas do Sorteio Anterior",
        description = "Filtra pela quantidade de números que se repetem do último concurso realizado. O filtro elimina jogos com repetições muito baixas (menos de 8) ou muito altas (mais de 10), baseado no padrão estatístico onde 8-10 dezenas tendem a se repetir entre sorteios consecutivos.\n\nPorque é óbvio que os números têm memória e alguns ficam com preguiça de sair do sorteio anterior. 'Ah, eu já saí ontem, vou dar uma descansada.' Funciona assim mesmo, confia! Em breve você poderá até escolher quais números merecem uma segunda chance.",
        fullRange = 5f..12f,
        defaultRange = 8f..10f
    );
}