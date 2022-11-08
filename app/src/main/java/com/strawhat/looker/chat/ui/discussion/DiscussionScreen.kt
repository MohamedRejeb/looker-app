package com.strawhat.looker.chat.ui.discussion

import androidx.compose.foundation.background
import com.strawhat.looker.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.strawhat.looker.helpers.ui.MainTextField

@Composable
fun DiscussionScreen(
    colorScheme: ColorScheme = MaterialTheme.colorScheme,
    typography: Typography = MaterialTheme.typography,
    viewModel: DiscussionViewModel = hiltViewModel(),
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        DiscussionHeader(
            colorScheme = colorScheme,
            typography = typography,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .background(Color.White)
            ,
        )

        DiscussionRow(
            colorScheme = colorScheme,
            typography = typography,
            modifier = Modifier
                .fillMaxWidth()
            ,
        )
    }

}

@Composable
fun DiscussionHeader(
    colorScheme: ColorScheme,
    typography: Typography,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
        ,
    ) {
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_left),
                contentDescription = "Navigate back",
            )
        }

        Box(
            modifier = Modifier
                .padding(14.dp)
                .clip(CircleShape)
                .size(50.dp)
                .background(colorScheme.outline)
        )

        Column {
            Text(
                text = "Mohamed Ben Rejeb",
                color = colorScheme.onSurface,
                style = typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Online",
                color = colorScheme.primary,
                style = typography.bodyMedium
            )
        }
    }
}

@Composable
fun DiscussionRow(
    colorScheme: ColorScheme,
    typography: Typography,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 20.dp, vertical = 12.dp)
        ,
    ) {
        repeat(5) {
            DiscussionRowItem(
                colorScheme = colorScheme,
                typography = typography,
            )
        }
    }
}

@Composable
fun DiscussionRowItem(
    colorScheme: ColorScheme,
    typography: Typography,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(vertical = 12.dp)
            .clip(
                RoundedCornerShape(
                    topEnd = 16.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp,
                )
            )
            .background(colorScheme.primary)
            .padding(16.dp)
    ) {
        Text(
            text = "Hi how are you",
            color = colorScheme.onPrimary,
            style = typography.bodyMedium,
        )
    }
}

@Composable
fun DiscussionMessageField(
    colorScheme: ColorScheme,
    typography: Typography,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        MainTextField(
            colorScheme = colorScheme,
            typography = typography,
            value = "",
            onValueChange = {  },
            placeholder = { Text(text = "Type here...") },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}