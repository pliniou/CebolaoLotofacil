package com.example.cebolaolotofacil.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.automirrored.filled.Rule
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cebolaolotofacil.R
import com.example.cebolaolotofacil.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
    val showWelcomeAnimation by homeViewModel.showWelcomeAnimation
    val isCardsAnimationEnabled by homeViewModel.isCardsAnimationEnabled

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Cebolão Lotofácil Generator 1.0",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Spacer(modifier = Modifier.height(6.dp))

            // Logo animado
            val infiniteTransition = rememberInfiniteTransition(label = "logoPulse")
            val scale by infiniteTransition.animateFloat(
                initialValue = 1f,
                // CORREÇÃO: O comentário foi ajustado para refletir a lógica correta.
                // A animação pulsa com `targetValue = 1.08f` e para quando o `targetValue` se torna `1f`.
                targetValue = if (showWelcomeAnimation) 1.08f else 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = if (showWelcomeAnimation) 1000 else 2200,
                        easing = FastOutSlowInEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "logoScale"
            )

            Image(
                painter = painterResource(id = R.drawable.icone_lotofacil),
                contentDescription = "Logo Cebolão Generator",
                modifier = Modifier
                    .size(70.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    },
                contentScale = ContentScale.Fit
            )

            // Título principal
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Seu Assistente",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp
                    ),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "da Lotofácil",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Cards informativos
            InfoCard(
                icon = Icons.AutoMirrored.Filled.Rule,
                title = "Como Jogar",
                content = "Marque de 15 a 20 números dos 25 disponíveis no volante. Você ganha acertando a partir de 11 dezenas sorteadas.",
                animationEnabled = isCardsAnimationEnabled,
                delay = 0
            )

            InfoCard(
                icon = Icons.Default.FilterAlt,
                title = "Filtros Estatísticos",
                content = "Use nossas ferramentas avançadas baseadas em análise de milhares de sorteios para criar jogos otimizados.",
                animationEnabled = isCardsAnimationEnabled,
                delay = 150
            )

            // Card de dica rotativa
            TipCard(
                currentTip = homeViewModel.getCurrentTip(),
                onNextTip = { homeViewModel.nextTip() },
                animationEnabled = isCardsAnimationEnabled,
                delay = 250
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun InfoCard(
    icon: ImageVector,
    title: String,
    content: String,
    animationEnabled: Boolean = true,
    delay: Int = 0
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(delay.toLong())
        isVisible = true
    }

    val alpha by animateFloatAsState(
        targetValue = if (isVisible && animationEnabled) 1f else if (!animationEnabled) 1f else 0f,
        animationSpec = tween(durationMillis = 600, delayMillis = delay),
        label = "cardAlpha"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(alpha),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        )
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null, // Ícone decorativo, o texto do card explica
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun TipCard(
    currentTip: String,
    onNextTip: () -> Unit,
    animationEnabled: Boolean = true,
    @Suppress("SameParameterValue") delay: Int = 0
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(delay.toLong())
        isVisible = true
    }

    val alpha by animateFloatAsState(
        targetValue = if (isVisible && animationEnabled) 1f else if (!animationEnabled) 1f else 0f,
        animationSpec = tween(durationMillis = 600, delayMillis = delay),
        label = "tipCardAlpha"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(alpha),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
        )
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Lightbulb,
                contentDescription = null, // Ícone decorativo, o texto do card explica
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Dica do Cebolão",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                AnimatedContent(
                    targetState = currentTip,
                    transitionSpec = {
                        fadeIn(tween(400)) togetherWith fadeOut(tween(200))
                    },
                    label = "tip_animation"
                ) { tip ->
                    Text(
                        text = tip,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            IconButton(
                onClick = onNextTip,
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.NavigateNext,
                    contentDescription = "Próxima dica",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}