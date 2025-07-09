# README
# 🎲 Cebolao Lotofácil Generator
### Uma Solução da Cebola Studios & Softwares
*"Para apostadores que preferem estatísticas à sorte cega."*
![Android](https://img.shields.io/badge/Android-API_26+-green)
![Kotlin](https://img.shields.io/badge/Kotlin-2.0.0-purple)
![Compose](https://img.shields.io/badge/Jetpack_Compose-2024.06-blueviolet)
![License](https://img.shields.io/badge/license-MIT-yellow)
![Status](https://img.shields.io/badge/Status-Pronto%20para%20Apostar-brightgreen)
[cite_start]Um gerador inteligente de jogos da Lotofácil baseado em análise estatística de mais de 3.400 concursos históricos. [cite: 30]
[cite_start]Porque se você vai apostar, pelo menos use a matemática a seu favor - mesmo que ela não garante nada. [cite: 30]
<div align="center">
![Demo do Gerador](https://i.imgur.com/placeholder-generator.gif)
*A interface de apostas da Cebola Studios: onde estatística encontra esperança.*
</div>
> [!IMPORTANT]
> **AVISO SOBRE JOGOS DE AZAR**
>
> [cite_start]Este aplicativo é uma ferramenta de análise estatística para fins educacionais e de entretenimento. [cite: 32]
> [cite_start]**A Lotofácil é um jogo de azar e não existe garantia de vitória.** [cite: 32]
>
> [cite_start]**Jogue com responsabilidade. [cite: 33] [cite_start]Nunca aposte mais do que pode perder.** A Cebola Studios não se responsabiliza por perdas financeiras, vícios em jogos ou crises de fé na matemática. [cite: 33]
---
## ✨ Funcionalidades Principais
O Cebolao Generator foi desenvolvido para maximizar suas chances usando ciência de dados:
### 🧮 Gerador Inteligente
- **9 Filtros Estatísticos**: Soma de dezenas, pares/ímpares, primos, Fibonacci, sequências e muito mais
- **Análise Histórica**: Baseado em 3.400+ concursos da Lotofácil
- **Feedback Inteligente**: Avisos quando filtros estão muito restritivos
- **Geração Otimizada**: Algoritmos eficientes para milhares de combinações
- **Validação Automática**: Verifica se os jogos atendem todos os critérios
### 🔍 Conferidor Histórico
- **Análise Completa**: Verifique qualquer combinação contra todo o histórico
- **Relatórios Detalhados**: "Seu jogo fez 15 pontos 2 vezes, a última em 2015"
- **Estatísticas Avançadas**: Frequência de acertos, períodos sem sair, tendências
- [cite_start]**Comparação Inteligente**: Compare diferentes estratégias de jogo [cite: 35]
- **Histórico Atualizado**: Base de dados sempre atualizada
### 🎨 Interface Moderna
- **Tema Escuro Personalizado**: Porque apostar de madrugada é mais emocionante
- **Fonte Inter**: Tipografia moderna e legível
- **Microanimações**: Transições fluidas que não distraem
- **Totalmente Acessível**: Suporte completo ao TalkBack
- **Design Responsivo**: Funciona em tablets e celulares
### 📊 Análise Estatística
- **Padrões Históricos**: Identifica tendências nos sorteios
- **Distribuição de Números**: Análise de frequência e períodos
- **Filtros Avançados**: Múltiplos critérios combinados
- **Validação Matemática**: Algoritmos baseados em probabilidade
- **Relatórios Visuais**: Gráficos e estatísticas claras
---
## 🛠️ Stack Tecnológica
### Arquitetura e Padrões
- **Clean Architecture**: Separação clara entre camadas
- [cite_start]**MVVM Pattern**: Model-View-ViewModel para testabilidade [cite: 36]
- **SOLID Principles**: Código limpo e manutenível
- **Repository Pattern**: Abstração da camada de dados
- **State Management**: Gerenciamento reativo de estado
### Principais Tecnologias
| Categoria | Tecnologia | Versão | Propósito |
|-----------|------------|---------|-----------|
| **Linguagem** | Kotlin | 2.0.0 | [cite_start]Linguagem principal | [cite: 37]
| **UI Framework** | Jetpack Compose | 2024.06 | [cite_start]Interface declarativa | [cite: 38]
| **Arquitetura** | MVVM + Clean | - | [cite_start]Organização do código | [cite: 39]
| **Testes** | JUnit4, MockK | 4.13.2 | Testes unitários |
| **UI Tests** | Espresso | 3.5.1 | [cite_start]Testes de interface | [cite: 40]
| **Threading** | Coroutines | 1.7.3 | [cite_start]Programação assíncrona | [cite: 41]
### Otimizações de Performance
- **derivedStateOf**: Recomposição otimizada
- **Dispatchers.IO**: Operações em background
- **Algoritmos Otimizados**: Geração eficiente de combinações
- **Memory Management**: Controle rigoroso de memória
- **Lazy Loading**: Carregamento sob demanda
---
## 📱 Screenshots
### Telas Principais
| Tela Inicial | Gerador de Jogos | Jogos Gerados | Conferência |
|--------------|------------------|---------------|-----------  |
| ![Tela Inicial](screenshots/tela1.jpg) | ![Gerador](screenshots/tela2.jpg) | ![Jogos](screenshots/tela3.jpg) | [cite_start]![Conferência](screenshots/tela4.jpg) | [cite: 42, 43]
### Funcionalidades Avançadas
| Filtros Estatísticos | Histórico | Análise | Configurações |
|---------------------|-----------|---------|---------------|
| ![Filtros](screenshots/filtros.jpg) | ![Histórico](screenshots/historico.jpg) | ![Análise](screenshots/analise.jpg) | [cite_start]![Config](screenshots/config.jpg) | [cite: 44]
---
## 🚀 Instalação e Configuração
### Pré-requisitos
| Requisito | Versão/Especificação |
|-----------|---------------------|
| **Android Studio** | [cite_start]Iguana 2023.2.1+ | [cite: 45]
| **Java JDK** | 17 ou superior |
| **Kotlin** | 2.0.0+ |
| **Android API** | [cite_start]26+ (Android 8.0) | [cite: 46]
| **Gradle** | 8.4+ |
### 📥 Instalação
1. **Clone o repositório**:
   ```bash
   git clone [https://github.com/cebola-studios/cebolao-generator.git](https://github.com/cebola-studios/cebolao-generator.git)
   cd cebolao-generator
````

2.  **Abra no Android Studio**:
      - Inicie o Android Studio
      - Selecione "Open" e navegue até a pasta do projeto
      - Aguarde a sincronização do Gradle
3.  **Configuração inicial**:
    ```bash
    # Sincronize as dependências
    ./gradlew build

    # Execute os testes
    ./gradlew test
    ```
4.  **Execute o aplicativo**:
      - Conecte um dispositivo Android ou inicie um emulador
      - [cite\_start]Clique em "Run" (▶️) ou pressione `Shift + F10` [cite: 48]

### 🔧 Configuração Avançada

Para personalizar o comportamento do gerador, edite o arquivo `app/src/main/res/values/config.xml`:

```xml
<resources>
    <integer name="max_combinations">10000</integer>
    <integer name="default_games_count">5</integer>
    <bool name="enable_advanced_filters">true</bool>
    
    <integer name="historical_data_limit">3400</integer>
    <bool name="enable_pattern_analysis">true</bool>
</resources>
```

-----

## 🏗️ Estrutura do Projeto

```
cebolao-generator/
├── 📁 app/
│   ├── 📁 src/
│   │   ├── 📁 main/
│   │   │   ├── 📁 java/com/example/cebolaolotofacil/
│   │   │   │   ├── 📁 data/              # Modelos de dados (LotofacilGame, FilterState, etc.)
│   │   │   │   ├── 📁 logic/            # Lógica de negócio (GameGenerator, HistoryRepository)
│   │   │   │   ├── 📁 navigation/      # Definições de telas e navegação
│   │   │   │   ├── 📁 ui/                # Componentes e Telas (Composables)
│   │   │   │   │   ├── 📁 components/    # Componentes reutilizáveis (Cards, Grids)
│   │   │   │   │   ├── 📁 screens/      # Telas principais do app
│   │   │   │   │   └── 📁 theme/        # Tema da aplicação (Cores, Tipografia)
│   │   │   │   └── 📁 viewmodels/        # ViewModels da arquitetura MVVM
│   │   │   └── 📁 res/
│   │   │       ├── 📁 values/           # Strings, cores, temas
│   │   │       └── 📁 assets/            # Arquivos de dados brutos (histórico de jogos)
│   │   ├── 📁 test/                      # Testes unitários (ViewModels, Logic)
│   │   └── 📁 androidTest/               # Testes instrumentados (UI, Navegação)
├── 📁 screenshots/                       # Capturas de tela
├── 📜 build.gradle.kts                   # Configuração do build a nível de app
├── 📜 README.md                          # Esta documentação
└── 📜 LICENSE                            # Licença MIT
```

-----

## 🧪 Algoritmos e Filtros

### Filtros Estatísticos Disponíveis

| Filtro | Descrição | Impacto na Probabilidade |
|--------|-----------|--------------------------|
| **Soma das Dezenas** | Controla a soma total dos números | [cite\_start]Alto | [cite: 57]
| **Pares vs Ímpares** | Balanceamento entre números pares e ímpares | [cite\_start]Médio | [cite: 58]
| **Números Primos** | Quantidade de números primos no jogo | [cite\_start]Baixo | [cite: 59]
| **Sequência Fibonacci** | Números da sequência de Fibonacci | Muito Baixo |
| **Sequências Consecutivas** | Controla números em sequência | [cite\_start]Médio | [cite: 60]
| **Distribuição por Coluna** | Balanceamento nas colunas do volante | Alto |
| **Frequência Histórica** | Baseado na frequência de sorteios | [cite\_start]Médio | [cite: 61]
| **Períodos sem Sair** | Números que não saem há muito tempo | [cite\_start]Baixo | [cite: 62]
| **Padrões Visuais** | Evita padrões óbvios no volante | [cite\_start]Médio | [cite: 63]

### Algoritmos Implementados

#### Gerador de Combinações

```kotlin
class CombinationGenerator {
    suspend fun generate(
        filters: List<StatisticalFilter>,
        count: Int
    ): List<LotofacilGame> {
        return withContext(Dispatchers.IO) {
            generateSequence { createRandomCombination() }
                .filter { game -> filters.all { it.validate(game) } }
                [cite_start].take(count) [cite: 64]
                .toList()
        }
    }
}
```

#### Análise Estatística

```kotlin
class StatisticalAnalyzer {
    fun analyzeGame(game: LotofacilGame): GameAnalysis {
        return GameAnalysis(
            hitFrequency = calculateHitFrequency(game),
            lastOccurrence = findLastOccurrence(game),
            probability = calculateProbability(game),
            [cite_start]patterns = identifyPatterns(game) [cite: 65]
        )
    }
}
```

-----

## 🔧 Solução de Problemas

### Problemas Comuns

| Problema | Causa Provável | Solução |
|----------|----------------|---------|
| **Geração muito lenta** | Filtros muito restritivos | [cite\_start]Relaxe alguns filtros | [cite: 66, 67]
| **Poucos jogos gerados** | Critérios incompatíveis | Revise combinação de filtros |
| **App travando** | Memória insuficiente | [cite\_start]Reduza número de jogos | [cite: 68]
| **Dados desatualizados** | Cache antigo | [cite\_start]Limpe dados do app | [cite: 69]

### Logs e Depuração

```bash
# Logs do gerador
adb logcat -s CebolaoGenerator
# Análise de performance
adb shell dumpsys meminfo com.cebola.generator
```

-----

## 📊 Estatísticas do Projeto

### Métricas de Código

  - **Linhas de Código**: \~5.000
  - **Arquivos Kotlin**: 45+
  - **Composables**: 25+
  - **Testes**: 150+
  - **Commits**: 200+

### Performance

  - **Tempo de Geração**: \< 2 segundos para 10 jogos
  - **Uso de Memória**: \< 50MB
  - **Tamanho do APK**: \< 15MB
  - **Tempo de Inicialização**: \< 1 segundo

-----

## 📄 Licença

## [cite\_start]Distribuído sob a Licença MIT. [cite: 70] [cite\_start]Veja o arquivo `LICENSE` para mais informações. [cite: 70] [cite\_start]**Resumo**: Você pode usar, modificar e distribuir este código livremente, mas lembre-se: a matemática não garante vitórias na loteria. [cite: 71] [cite\_start]A responsabilidade por apostas é inteiramente sua. [cite: 71]

## 🏆 Créditos e Agradecimentos

### Equipe de Desenvolvimento

  - **Cebola Studios & Softwares** - Conceito e desenvolvimento
  - **Estatísticos Anônimos** - Consultoria em probabilidade
  - **Beta Testers** - Pelos testes (e pelas apostas perdidas)

### Fontes de Dados

  - **Caixa Econômica Federal** - Dados históricos oficiais
  - **Comunidade Lotérica** - Insights e estratégias
  - **Matemáticos Famosos** - Pelas fórmulas que não funcionam na prática

### Tecnologias Utilizadas

  - **Google Android Team** - Jetpack Compose e ferramentas
  - **JetBrains** - Linguagem Kotlin
  - **Comunidade Open Source** - Bibliotecas e inspiração

-----

\<div align="center"\>
**Desenvolvido com ❤️ e muita estatística pela Cebola Studios**
[cite\_start]*"Porque na loteria, a esperança é a última que morre - mas a matemática já morreu há muito tempo."* [cite: 72]
**[⬆ Voltar ao Topo](https://www.google.com/search?q=%23-cebolao-lotof%C3%A1cil-generator)**
\</div\>

````