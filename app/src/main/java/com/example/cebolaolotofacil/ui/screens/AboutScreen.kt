package com.example.cebolaolotofacil.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cebolaolotofacil.ui.components.InfoDialog
import com.example.cebolaolotofacil.viewmodels.AboutViewModel

// Data class para gerenciar o conteúdo do diálogo de forma mais segura e legível
private data class DialogContent(val title: String, val text: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(aboutViewModel: AboutViewModel = viewModel()) {
    val appInfo by aboutViewModel.appInfo.collectAsState()
    var showDialogContent by remember { mutableStateOf<DialogContent?>(null) }

    showDialogContent?.let { content ->
        InfoDialog(
            onDismissRequest = { showDialogContent = null },
            dialogTitle = content.title,
            dialogText = content.text
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Sobre o App",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = appInfo.appName,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Ferramenta estatística para geração de jogos da Lotofácil",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            ClickableInfoSection(
                title = "Nossa História",
                subtitle = "Conheça a Cebola Studios",
                icon = Icons.Default.Info,
                onClick = { showDialogContent = getHistoryContent() }
            )
            ClickableInfoSection(
                title = "Como Funciona",
                subtitle = "Metodologia e algoritmos",
                icon = Icons.Default.Code,
                onClick = { showDialogContent = getMethodologyContent() }
            )
            ClickableInfoSection(
                title = "Aviso Legal",
                subtitle = "Termos de uso e responsabilidades",
                icon = Icons.Default.VerifiedUser,
                onClick = { showDialogContent = getLegalContent() }
            )

            Spacer(modifier = Modifier.weight(1f))

            AppFooter(appInfo = appInfo)
        }
    }
}

@Composable
private fun AppFooter(appInfo: com.example.cebolaolotofacil.viewmodels.AppInfo) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 24.dp)
    ) {
        Text(
            text = "Desenvolvido com 💜 por",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 12.sp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Cebola Studios",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 4.dp)
        )
        Text(
            text = "Versão ${appInfo.versionName}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun ClickableInfoSection(
    title: String,
    subtitle: String? = null,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Ícone da seção $title",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(end = 16.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                subtitle?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

private fun getHistoryContent() = DialogContent(
    "Nossa História",
    """A Cebola Studios nasceu da fusão entre paixão por tecnologia e fascínio pelas probabilidades matemáticas. Nosso time acredita que por trás da aparente aleatoriedade dos sorteios existe um universo de padrões estatísticos esperando para ser explorado.
O Cebolão Generator é nosso primeiro grande projeto, desenvolvido para democratizar o acesso a análises estatísticas avançadas da Lotofácil. Utilizamos décadas de dados históricos e algoritmos inteligentes para oferecer uma experiência única de geração de jogos.
Nosso objetivo é simples: transformar apostadores casuais em jogadores estratégicos, fornecendo as melhores ferramentas disponíveis no mercado. Porque acreditamos que conhecimento + estratégia > sorte pura.
E sim, escolhemos a cebola como símbolo porque, assim como ela, nossos algoritmos têm várias camadas de complexidade! 🧅"""
)

private fun getMethodologyContent() = DialogContent(
    "Como Funciona",
    """O Cebolão Generator utiliza análise estatística avançada baseada em mais de 3.000 sorteios históricos da Lotofácil para gerar combinações otimizadas.
📊 FILTROS ESTATÍSTICOS:
- Soma das dezenas (padrão: 170-220)
- Distribuição par/ímpar equilibrada
- Quantidade de números primos
- Análise de posicionamento no volante
- Sequências matemáticas (Fibonacci)
- Múltiplos e repetições do sorteio anterior
🎯 PROCESSO DE GERAÇÃO:
1. Aplica filtros configurados pelo usuário
2. Elimina combinações estatisticamente improváveis
3. Prioriza padrões com maior frequência histórica
4. Gera jogos únicos e otimizados
⚠️ IMPORTANTE: Nossos algoritmos aumentam as chances estatísticas, mas não garantem prêmios. A Lotofácil continua sendo um jogo de probabilidades."""
)

private fun getLegalContent() = DialogContent(
    "Aviso Legal",
    """📋 TERMOS DE USO:
Este aplicativo é uma ferramenta de análise estatística e entretenimento educativo. O Cebolão Generator não possui qualquer vínculo oficial com a Caixa Econômica Federal ou com os jogos da Lotofácil.
⚖️ RESPONSABILIDADES:
- As apostas são de inteira responsabilidade do usuário
- Não garantimos prêmios ou resultados específicos
- Os custos das apostas são por conta do apostador
- Jogue sempre com responsabilidade e moderação
🔒 PRIVACIDADE:
- Não coletamos dados pessoais dos usuários
- Não armazenamos informações de apostas
- Todos os cálculos são feitos localmente no dispositivo"""
)