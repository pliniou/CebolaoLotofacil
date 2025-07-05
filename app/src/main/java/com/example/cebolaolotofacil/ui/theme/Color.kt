package com.example.cebolaolotofacil.ui.theme

import androidx.compose.ui.graphics.Color

// PALETA ORIGINAL (BASE PARA AMBOS OS TEMAS)
val DeepPurple = Color(0xFF5C1A4A)
val LightPink = Color(0xFFD1A2C3)
val VibrantMagenta = Color(0xFFD81B60)

// --- TEMA ESCURO (DARK MODE) ---
val PrimaryDark = VibrantMagenta
val OnPrimaryDark = Color.White
val PrimaryContainerDark = VibrantMagenta.copy(alpha = 0.8f)
val OnPrimaryContainerDark = Color.White
val SecondaryDark = LightPink
val OnSecondaryDark = Color.Black
val TertiaryDark = DeepPurple
val OnTertiaryDark = Color.White
val BackgroundDark = Color(0xFF1A0B15)
val OnBackgroundDark = Color(0xFFE8DDE3)
val SurfaceDark = Color(0xFF1A0B15) // Fundo dos cards e dialogs
val OnSurfaceDark = Color(0xFFE8DDE3)
val SurfaceVariantDark = Color.White.copy(alpha = 0.1f) // Cor de fundo da BottomBar
val OnSurfaceVariantDark = Color(0xFFCEC1C9)
val ErrorDark = Color(0xFFCF6679)
val OnErrorDark = Color.Black

// --- NOVO: TEMA CLARO (LIGHT MODE) ---
val PrimaryLight = Color(0xFFB71C1C) // Um vermelho escuro para bom contraste
val OnPrimaryLight = Color.White
val PrimaryContainerLight = Color(0xFFFDE7E7)
val OnPrimaryContainerLight = Color(0xFF400003)
val SecondaryLight = Color(0xFF7D5757)
val OnSecondaryLight = Color.White
val TertiaryLight = Color(0xFF755B2F)
val OnTertiaryLight = Color.White
val BackgroundLight = Color(0xFFFFF8F7)
val OnBackgroundLight = Color(0xFF211A1A)
val SurfaceLight = Color(0xFFFFF8F7)
val OnSurfaceLight = Color(0xFF211A1A)
val SurfaceVariantLight = Color(0xFFF4DDDD)
val OnSurfaceVariantLight = Color(0xFF534343)
val ErrorLight = Color(0xFFBA1A1A)
val OnErrorLight = Color.White