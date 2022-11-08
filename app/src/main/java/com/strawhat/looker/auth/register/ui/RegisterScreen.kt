package com.strawhat.looker.auth.register.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.strawhat.looker.R
import com.strawhat.looker.helpers.ui.MainTextField
import com.strawhat.looker.helpers.ui.MainTextFieldDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navigateToMap: () -> Unit,
    navigateToLogin: () -> Unit,
    colorScheme: ColorScheme = MaterialTheme.colorScheme,
    typography: Typography = MaterialTheme.typography,
    viewModel: RegisterViewModel = hiltViewModel(),
) {

    LaunchedEffect(key1 = viewModel.isSuccess) {
        if (viewModel.isSuccess) navigateToMap()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colorScheme.primary,
                        colorScheme.primaryContainer
                    )
                )
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.backround),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.1f,
        )

        Scaffold(
            topBar = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(vertical = 8.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(onClick = navigateToLogin) {
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_left),
                                contentDescription = "arrow",
                                tint = colorScheme.onPrimary,
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Register",
                            color = colorScheme.onPrimary,
                            style = typography.titleLarge
                        )
                    }
                }
            },
            containerColor = Color.Transparent,
        ) { paddingValues ->
            RegisterScreenContent(
                colorScheme = colorScheme,
                typography = typography,
                viewModel = viewModel,
                navigateToLogin = navigateToLogin,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding()
                    )
            )
        }
    }


}

@Composable
fun RegisterScreenContent(
    colorScheme: ColorScheme,
    typography: Typography,
    viewModel: RegisterViewModel,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val insets = WindowInsets

    val imeBottom = with(LocalDensity.current) { insets.ime.getBottom(this).toDp() }

    val focusManager = LocalFocusManager.current

    var isPasswordHidden by remember {
        mutableStateOf(true)
    }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(bottom = imeBottom)
        ,
    ) {

        item {

            Spacer(modifier = Modifier.height(14.dp))

            Image(
                painter = painterResource(id = R.drawable.logo3),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .shadow(12.dp, RoundedCornerShape(16.dp))
                    .background(colorScheme.surface)
                ,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Looker",
                color = colorScheme.onPrimary,
                style = typography.titleLarge
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Search Is What We Do",
                color = colorScheme.onPrimary,
                style = typography.bodyMedium
            )


            Spacer(modifier = Modifier.height(36.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                MainTextField(
                    colorScheme = colorScheme,
                    typography = typography,
                    value = viewModel.firstName,
                    onValueChange = { viewModel.firstName = it },
                    placeholder = MainTextFieldDefaults.placeholder("First name"),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Next)
                    }),
                    modifier = Modifier.weight(1f),
                )

                Spacer(modifier = Modifier.width(14.dp))

                MainTextField(
                    colorScheme = colorScheme,
                    typography = typography,
                    value = viewModel.lastName,
                    onValueChange = { viewModel.lastName = it },
                    placeholder = MainTextFieldDefaults.placeholder("Last name"),
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
                value = viewModel.email,
                onValueChange = { viewModel.email = it },
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

            Spacer(modifier = Modifier.height(14.dp))

            MainTextField(
                colorScheme = colorScheme,
                typography = typography,
                value = viewModel.password,
                onValueChange = { viewModel.password = it },
                placeholder = MainTextFieldDefaults.placeholder("Password"),
                singleLine = true,
                maxLines = 1,
                visualTransformation =
                if (isPasswordHidden) PasswordVisualTransformation()
                else VisualTransformation.None
                ,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                trailingIcon = {
                    IconButton(onClick = { isPasswordHidden = !isPasswordHidden }) {
                        Icon(
                            painter =
                            if (isPasswordHidden) painterResource(id = R.drawable.eye)
                            else painterResource(id = R.drawable.eye_off),
                            contentDescription = "Password eye",
                            tint = colorScheme.primary,
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(14.dp))

            MainTextField(
                colorScheme = colorScheme,
                typography = typography,
                value = viewModel.passwordConfirm,
                onValueChange = { viewModel.passwordConfirm = it },
                placeholder = MainTextFieldDefaults.placeholder("Confirm password"),
                singleLine = true,
                visualTransformation =
                if (isPasswordHidden) PasswordVisualTransformation()
                else VisualTransformation.None
                ,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                trailingIcon = {
                    IconButton(onClick = { isPasswordHidden = !isPasswordHidden }) {
                        Icon(
                            painter =
                            if (isPasswordHidden) painterResource(id = R.drawable.eye)
                            else painterResource(id = R.drawable.eye_off),
                            contentDescription = "Password eye",
                            tint = colorScheme.primary,
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )

            if (viewModel.isError) {
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = viewModel.errorMessage,
                        style = typography.labelMedium,
                        color = colorScheme.error,
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.registerEvent() },
                contentPadding = PaddingValues(vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.primary,
                    contentColor = colorScheme.onPrimary,
                    disabledContainerColor = colorScheme.primaryContainer,
                    disabledContentColor = colorScheme.outline,
                ),
                enabled = !viewModel.isLoading,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                if (viewModel.isLoading) {
                    CircularProgressIndicator(
                        strokeWidth = 1.dp,
                        color = colorScheme.primary,
                        modifier = Modifier
                            .size(22.dp)
                    )

                    Spacer(modifier = Modifier.width(18.dp))
                }

                Text(
                    text = "Register",
                    style = typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "You already have an account? ",
                    style = typography.labelMedium,
                    color = colorScheme.onPrimary,
                )
                TextButton(onClick = navigateToLogin) {
                    Text(
                        text = "Login now",
                        style = typography.labelMedium,
                        color = colorScheme.inversePrimary,
                    )
                }
            }

        }


    }

}