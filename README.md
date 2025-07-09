# 🎲 Cebolao Lotofácil Generator

### Uma Solução da Cebola Studios & Softwares

*"Para apostadores que preferem estatísticas à sorte cega."*

![Android](https://img.shields.io/badge/Android-API_26+-green)
![Kotlin](https://img.shields.io/badge/Kotlin-2.0.0-purple)
![Compose](https://img.shields.io/badge/Jetpack_Compose-2024.06-blueviolet)
![License](https://img.shields.io/badge/license-MIT-yellow)
![Status](https://img.shields.io/badge/Status-Pronto%20para%20Apostar-brightgreen)

Um gerador inteligente de jogos da Lotofácil baseado em análise estatística de mais de 3.400 concursos históricos. Porque se você vai apostar, pelo menos use a matemática a seu favor - mesmo que ela não garante nada.

<div align="center">

![Demo do Gerador](https://i.imgur.com/placeholder-generator.gif)

*A interface de apostas da Cebola Studios: onde estatística encontra esperança.*

</div>

> [!IMPORTANT]
> **AVISO SOBRE JOGOS DE AZAR**
>
> Este aplicativo é uma ferramenta de análise estatística para fins educacionais e de entretenimento. **A Lotofácil é um jogo de azar e não existe garantia de vitória.**
>
> **Jogue com responsabilidade. Nunca aposte mais do que pode perder.** A Cebola Studios não se responsabiliza por perdas financeiras, vícios em jogos ou crises de fé na matemática.

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
- **Comparação Inteligente**: Compare diferentes estratégias de jogo
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
- **MVVM Pattern**: Model-View-ViewModel para testabilidade
- **SOLID Principles**: Código limpo e manutenível
- **Repository Pattern**: Abstração da camada de dados
- **State Management**: Gerenciamento reativo de estado

### Principais Tecnologias

| Categoria | Tecnologia | Versão | Propósito |
|-----------|------------|---------|-----------|
| **Linguagem** | Kotlin | 2.0.0 | Linguagem principal |
| **UI Framework** | Jetpack Compose | 2024.06 | Interface declarativa |
| **Arquitetura** | MVVM + Clean | - | Organização do código |
| **Testes** | JUnit4, MockK | 4.13.2 | Testes unitários |
| **UI Tests** | Espresso | 3.5.1 | Testes de interface |
| **Threading** | Coroutines | 1.7.3 | Programação assíncrona |

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
| ![Tela Inicial](screenshots/tela1.jpg) | ![Gerador](screenshots/tela2.jpg) | ![Jogos](screenshots/tela3.jpg) | ![Conferência](screenshots/tela4.jpg) |

### Funcionalidades Avançadas
| Filtros Estatísticos | Histórico | Análise | Configurações |
|---------------------|-----------|---------|---------------|
| ![Filtros](screenshots/filtros.jpg) | ![Histórico](screenshots/historico.jpg) | ![Análise](screenshots/analise.jpg) | ![Config](screenshots/config.jpg) |

---

## 🚀 Instalação e Configuração

### Pré-requisitos

| Requisito | Versão/Especificação |
|-----------|---------------------|
| **Android Studio** | Iguana 2023.2.1+ |
| **Java JDK** | 17 ou superior |
| **Kotlin** | 2.0.0+ |
| **Android API** | 26+ (Android 8.0) |
| **Gradle** | 8.4+ |

### 📥 Instalação

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/cebola-studios/cebolao-generator.git
   cd cebolao-generator
   ```

2. **Abra no Android Studio**:
   - Inicie o Android Studio
   - Selecione "Open" e navegue até a pasta do projeto
   - Aguarde a sincronização do Gradle

3. **Configuração inicial**:
   ```bash
   # Sincronize as dependências
   ./gradlew build
   
   # Execute os testes
   ./gradlew test
   ```

4. **Execute o aplicativo**:
   - Conecte um dispositivo Android ou inicie um emulador
   - Clique em "Run" (▶️) ou pressione `Shift + F10`

### 🔧 Configuração Avançada

Para personalizar o comportamento do gerador, edite o arquivo `app/src/main/res/values/config.xml`:

```xml
<resources>
    <!-- Configurações do gerador -->
    <integer name="max_combinations">10000</integer>
    <integer name="default_games_count">5</integer>
    <bool name="enable_advanced_filters">true</bool>
    
    <!-- Configurações de análise -->
    <integer name="historical_data_limit">3400</integer>
    <bool name="enable_pattern_analysis">true</bool>
</resources>
```

---

## 🏗️ Estrutura do Projeto

```
cebolao-generator/
├── 📁 app/
│   ├── 📁 src/
│   │   ├── 📁 main/
│   │   │   ├── 📁 java/com/cebola/generator/
│   │   │   │   ├── 📁 data/              # Camada de dados
│   │   │   │   │   ├── 📜 database/      # Room database
│   │   │   │   │   ├── 📜 repository/    # Repositórios
│   │   │   │   │   └── 📜 datasource/    # Fontes de dados
│   │   │   │   ├── 📁 domain/            # Lógica de negócio
│   │   │   │   │   ├── 📜 model/         # Modelos
│   │   │   │   │   ├── 📜 usecase/       # Casos de uso
│   │   │   │   │   └── 📜 repository/    # Interfaces
│   │   │   │   ├── 📁 presentation/      # UI e ViewModels
│   │   │   │   │   ├── 📜 ui/            # Composables
│   │   │   │   │   ├── 📜 viewmodel/     # ViewModels
│   │   │   │   │   └── 📜 theme/         # Temas
│   │   │   │   └── 📁 util/              # Utilitários
│   │   │   │       ├── 📜 algorithms/    # Algoritmos
│   │   │   │       └── 📜 extensions/    # Extensions
│   │   │   └── 📁 res/
│   │   │       ├── 📁 values/            # Cores, strings
│   │   │       └── 📁 raw/               # Dados históricos
│   │   └── 📁 test/                      # Testes unitários
│   └── 📁 androidTest/                   # Testes de UI
├── 📁 screenshots/                       # Capturas de tela
├── 📜 build.gradle                       # Configuração do projeto
├── 📜 README.md                          # Esta documentação
└── 📜 LICENSE                            # Licença MIT
```

---

## 🧪 Algoritmos e Filtros

### Filtros Estatísticos Disponíveis

| Filtro | Descrição | Impacto na Probabilidade |
|--------|-----------|--------------------------|
| **Soma das Dezenas** | Controla a soma total dos números | Alto |
| **Pares vs Ímpares** | Balanceamento entre números pares e ímpares | Médio |
| **Números Primos** | Quantidade de números primos no jogo | Baixo |
| **Sequência Fibonacci** | Números da sequência de Fibonacci | Muito Baixo |
| **Sequências Consecutivas** | Controla números em sequência | Médio |
| **Distribuição por Coluna** | Balanceamento nas colunas do volante | Alto |
| **Frequência Histórica** | Baseado na frequência de sorteios | Médio |
| **Períodos sem Sair** | Números que não saem há muito tempo | Baixo |
| **Padrões Visuais** | Evita padrões óbvios no volante | Médio |

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
                .take(count)
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
            patterns = identifyPatterns(game)
        )
    }
}
```

---

## 🔧 Solução de Problemas

### Problemas Comuns

| Problema | Causa Provável | Solução |
|----------|----------------|---------|
| **Geração muito lenta** | Filtros muito restritivos | Relaxe alguns filtros |
| **Poucos jogos gerados** | Critérios incompatíveis | Revise combinação de filtros |
| **App travando** | Memória insuficiente | Reduza número de jogos |
| **Dados desatualizados** | Cache antigo | Limpe dados do app |

### Logs e Depuração
```bash
# Logs do gerador
adb logcat -s CebolaoGenerator

# Análise de performance
adb shell dumpsys meminfo com.cebola.generator
```

---

## 📊 Estatísticas do Projeto

### Métricas de Código
- **Linhas de Código**: ~5.000
- **Arquivos Kotlin**: 45+
- **Composables**: 25+
- **Testes**: 150+
- **Commits**: 200+

### Performance
- **Tempo de Geração**: < 2 segundos para 10 jogos
- **Uso de Memória**: < 50MB
- **Tamanho do APK**: < 15MB
- **Tempo de Inicialização**: < 1 segundo

---

## 📄 Licença

Distribuído sob a Licença MIT. Veja o arquivo `LICENSE` para mais informações.

**Resumo**: Você pode usar, modificar e distribuir este código livremente, mas lembre-se: a matemática não garante vitórias na loteria. A responsabilidade por apostas é inteiramente sua.

---

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

---

<div align="center">

**Desenvolvido com ❤️ e muita estatística pela Cebola Studios**

*"Porque na loteria, a esperança é a última que morre - mas a matemática já morreu há muito tempo."*

**[⬆ Voltar ao Topo](#-cebolao-lotofácil-generator)**

</div>
```