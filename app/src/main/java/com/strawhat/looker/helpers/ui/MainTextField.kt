package com.strawhat.looker.helpers.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

object MainTextFieldDefaults {
    fun placeholder(text: String) = @Composable {
        Text(text = text, style = MaterialTheme.typography.bodySmall)
    }

    var roundedShape = RoundedCornerShape(16.dp)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTextField(
    colorScheme: ColorScheme,
    typography: Typography,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = typography.bodySmall,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MainTextFieldDefaults.roundedShape,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        textColor = colorScheme.onSurface,
        containerColor = colorScheme.surface,
        disabledPlaceholderColor = colorScheme.outline,
        placeholderColor = colorScheme.outline,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
    )
) {
    var isFocused by remember {
        mutableStateOf(false)
    }

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .onFocusChanged { isFocused = it.isFocused }
            .border(
                if (isFocused) 1.dp else 0.dp,
                colorScheme.primaryContainer,
                MainTextFieldDefaults.roundedShape
            )
        ,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        supportingText = supportingText,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
    )
}

@Preview
@Composable
fun MainTextFieldPreview() {
    MainTextField(
        colorScheme = MaterialTheme.colorScheme,
        typography = MaterialTheme.typography,
        value = "",
        onValueChange = {},
        placeholder = MainTextFieldDefaults.placeholder("Enter your name")
    )
}