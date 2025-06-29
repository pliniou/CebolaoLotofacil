package com.example.cebolaolotofacil

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.cebolaolotofacil.navigation.Screen
import org.junit.Rule
import org.junit.Test

/**
 * Testes de UI para a CheckerScreen.
 * Valida o fluxo de interação do usuário: seleção de números, habilitação do botão,
 * limpeza da seleção e estado inicial.
 */
class CheckerScreenTest {

    // Inicia a MainActivity, que é o ponto de entrada do app.
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * Navega para a tela do Conferidor antes de cada teste.
     */
    private fun navigateToCheckerScreen() {
        // Encontra o item da barra de navegação pelo seu título e clica nele.
        composeTestRule.onNodeWithText(Screen.Checker.title).performClick()
        // Garante que a tela carregou verificando o título da TopAppBar.
        composeTestRule.onNodeWithText("Conferidor Manual").assertIsDisplayed()
    }

    @Test
    fun checkerScreen_initialState_checkButtonIsDisabled() {
        // Arrange
        navigateToCheckerScreen()

        // Act & Assert
        // O botão deve estar desabilitado pois nenhum número foi selecionado.
        composeTestRule.onNodeWithText("Conferir Jogo").assertIsNotEnabled()
    }

    @Test
    fun checkerScreen_select15Numbers_enablesCheckButton() {
        // Arrange
        navigateToCheckerScreen()

        // Act
        // Simula o clique do usuário em 15 números diferentes.
        (1..15).forEach { number ->
            val numberText = "%02d".format(number)
            composeTestRule.onNodeWithText(numberText).performClick()
        }

        // Assert
        // Após selecionar 15 números, o botão deve ser habilitado.
        composeTestRule.onNodeWithText("Conferir Jogo").assertIsEnabled()
    }

    @Test
    fun checkerScreen_selectAndClearNumbers_disablesCheckButton() {
        // Arrange
        navigateToCheckerScreen()
        (1..15).forEach { number ->
            val numberText = "%02d".format(number)
            composeTestRule.onNodeWithText(numberText).performClick()
        }
        // Garante que o botão foi habilitado antes de limpar.
        composeTestRule.onNodeWithText("Conferir Jogo").assertIsEnabled()

        // Act
        // Clica no botão de limpar, encontrando-o pela sua descrição de conteúdo (acessibilidade).
        composeTestRule.onNodeWithContentDescription("Limpar Seleção").performClick()

        // Assert
        // Após limpar, o botão deve voltar ao estado desabilitado.
        composeTestRule.onNodeWithText("Conferir Jogo").assertIsNotEnabled()
        // Verifica também se o contador de badge sumiu, o que indica que não há mais números selecionados.
        composeTestRule.onNodeWithContentDescription("Limpar Seleção").assertDoesNotExist()
    }
}