package com.cebolao.lotofacil.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cebolao.lotofacil.R
import com.cebolao.lotofacil.navigation.Screen
import com.cebolao.lotofacil.ui.components.HtmlText
import com.cebolao.lotofacil.ui.components.InfoDialog
import com.cebolao.lotofacil.ui.components.NumberBall
import com.cebolao.lotofacil.ui.components.ProbabilitiesTable
import com.cebolao.lotofacil.viewmodels.HomeUiState
import com.cebolao.lotofacil.viewmodels.HomeViewModel
import com.cebolao.lotofacil.viewmodels.InfoDialogContent
import com.cebolao.lotofacil.viewmodels.LastDrawStats

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onNavigate: (Screen) -> Unit
) {
    val uiState by homeViewModel.uiState.collectAsState()
    val dialogContent by homeViewModel.dialogContent.collectAsState()

    dialogContent?.let { content ->
        InfoDialog(
            onDismissRequest = { homeViewModel.dismissDialog() },
            dialogTitle = stringResource(id = content.titleRes)
        ) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                HtmlText(html = stringResource(id = content.textRes))
                if (content.titleRes == R.string.about_probs_title) {
                    ProbabilitiesTable(modifier = Modifier.padding(top = 24.dp))
                }
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item { Header() }

        item {
            AnimatedContent(
                targetState = uiState,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
                },
                label = "contentState"
            ) { state ->
                when (state) {
                    is HomeUiState.Loading -> LoadingState()
                    is HomeUiState.Success -> SuccessState(
                        stats = state.lastDrawStats,
                        onNavigate = onNavigate,
                        onShowInfo = { homeViewModel.showDialogFor(it) }
                    )
                }
            }
        }
    }
}

@Composable
private fun Header() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        Color.Transparent
                    )
                )
            )
            .padding(start = 24.dp, end = 24.dp, top = 64.dp, bottom = 24.dp)
    ) {
        Text(
            text = "Bem-vindo ao",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Cebolão Generator",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun SuccessState(
    stats: LastDrawStats?,
    onNavigate: (Screen) -> Unit,
    onShowInfo: (InfoDialogContent) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        stats?.let {
            LastDrawCard(stats = it, modifier = Modifier.padding(horizontal = 16.dp))
        }

        SectionTitle(title = "Principais Ferramentas", modifier = Modifier.padding(start = 24.dp, top = 16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ActionCard(
                title = "Gerador Inteligente",
                icon = Icons.Default.Casino,
                onClick = { onNavigate(Screen.Filters) },
                modifier = Modifier.weight(1f)
            )
            ActionCard(
                title = "Conferidor Manual",
                icon = Icons.Default.CheckCircle,
                onClick = { onNavigate(Screen.Checker) },
                modifier = Modifier.weight(1f)
            )
        }

        SectionTitle(title = "Fique por Dentro", modifier = Modifier.padding(start = 24.dp, top = 16.dp))
        InfoCard(
            title = "Como Jogar e Prêmios",
            icon = Icons.Default.EmojiEvents,
            onClick = { onShowInfo(InfoDialogContent.RULES) },
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        InfoCard(
            title = "Suas Chances de Ganhar",
            icon = Icons.AutoMirrored.Filled.TrendingUp,
            onClick = { onShowInfo(InfoDialogContent.PROBS) },
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        InfoCard(
            title = "Dicas para Bolão",
            icon = Icons.Default.Groups,
            onClick = { onShowInfo(InfoDialogContent.BOLOES) },
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun LastDrawCard(stats: LastDrawStats, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(
                text = "ÚLTIMO SORTEIO: Nº ${stats.contest}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(16.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                stats.numbers.sorted().forEach { NumberBall(number = it, size = 32.dp) }
            }
            HorizontalDivider(Modifier.padding(vertical = 16.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                StatItem(label = "SOMA", value = stats.sum.toString())
                StatItem(label = "PARES", value = stats.evens.toString())
                StatItem(label = "ÍMPARES", value = stats.odds.toString())
            }
        }
    }
}

@Composable
private fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun ActionCard(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 17.sp
            )
        }
    }
}

@Composable
private fun InfoCard(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 17.sp,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Ver mais",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun LoadingState() {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "shimmerAlpha"
    )

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f * alpha),
                    MaterialTheme.shapes.large
                )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .height(80.dp)
                    .background(
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f * alpha),
                        MaterialTheme.shapes.large
                    )
            )
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .height(80.dp)
                    .background(
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f * alpha),
                        MaterialTheme.shapes.large
                    )
            )
        }
    }
}