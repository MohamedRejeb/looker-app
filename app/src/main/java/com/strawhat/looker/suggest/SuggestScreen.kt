package com.strawhat.looker.suggest

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.strawhat.looker.R
import com.strawhat.looker.auth.register.ui.RegisterScreenContent
import com.strawhat.looker.helpers.ui.MainTextField
import com.strawhat.looker.helpers.ui.MainTextFieldDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuggestScreen(
    colorScheme: ColorScheme = MaterialTheme.colorScheme,
    typography: Typography = MaterialTheme.typography,
) {

    Scaffold(
        containerColor = Color.Transparent,
    ) { paddingValues ->
        SuggestScreenContent(
            colorScheme = colorScheme,
            typography = typography,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuggestScreenContent(
    colorScheme: ColorScheme,
    typography: Typography,
    modifier: Modifier = Modifier,
){

    val insets = WindowInsets

    val imeBottom = with(LocalDensity.current) { insets.ime.getBottom(this).toDp() }

    val focusManager = LocalFocusManager.current

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(bottom = imeBottom)
        ,
    ) {

        item {

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = "Suggest a new place",
                color = colorScheme.onPrimary,
                style = typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                MainTextField(
                    colorScheme = colorScheme,
                    typography = typography,
                    value = "",
                    onValueChange = { },
                    placeholder = MainTextFieldDefaults.placeholder("Name"),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Next)
                    }),
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = colorScheme.onSurface
                    )
                )

                Spacer(modifier = Modifier.width(14.dp))

                MainTextField(
                    colorScheme = colorScheme,
                    typography = typography,
                    value = "",
                    onValueChange = { },
                    placeholder = MainTextFieldDefaults.placeholder("Description"),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }),
                    modifier = Modifier.weight(1f),
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            MainTextField(
                colorScheme = colorScheme,
                typography = typography,
                value = "",
                onValueChange = {  },
                placeholder = MainTextFieldDefaults.placeholder("Email"),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { },
                contentPadding = PaddingValues(vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.primary,
                    contentColor = colorScheme.onPrimary,
                    disabledContainerColor = colorScheme.primaryContainer,
                    disabledContentColor = colorScheme.outline,
                ),
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = "Suggest",
                    style = typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

        }


    }

}