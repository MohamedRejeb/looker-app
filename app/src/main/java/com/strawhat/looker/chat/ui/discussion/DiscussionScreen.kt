package com.strawhat.looker.chat.ui.discussion

import androidx.compose.foundation.Image
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
import com.strawhat.looker.chat.ui.discussions.discussions
import com.strawhat.looker.helpers.ui.MainTextField

@Composable
fun DiscussionScreen(
    colorScheme: ColorScheme = MaterialTheme.colorScheme,
    typography: Typography = MaterialTheme.typography,
    viewModel: DiscussionViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        DiscussionHeader(
            colorScheme = colorScheme,
            typography = typography,
            navigateUp = navigateUp,
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
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
        ,
    ) {
        IconButton(onClick = navigateUp) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_left),
                contentDescription = "Navigate back",
            )
        }

        Image(
            painter = painterResource(id = R.drawable.market),
            contentDescription = "Supermarket",
            modifier = Modifier
                .padding(14.dp)
                .clip(CircleShape)
                .size(50.dp)
        )

        Column {
            Text(
                text = "Supermarket",
                color = colorScheme.onSurface,
                style = typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "9 Online",
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
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 12.dp)
            ,
        ) {
            DiscussionRowItem(
                colorScheme = colorScheme,
                typography = typography,
                isMe = true,
                message = "Hi, I'm looking for sugar" ,
            )

            DiscussionRowItem(
                colorScheme = colorScheme,
                typography = typography,
                isMe = true,
                message = "I'm living at Sahloul 4" ,
            )

            DiscussionRowItem(
                colorScheme = colorScheme,
                typography = typography,
                isMe = false,
                message = "Go to the supermarket next to Flore, they have sugar" ,
            )

            DiscussionRowItem(
                colorScheme = colorScheme,
                typography = typography,
                isMe = true,
                message = "Okay thanks" ,
            )
        }

        DiscussionMessageField(
            colorScheme = colorScheme,
            typography = typography,
        )
    }
}

@Composable
fun DiscussionRowItem(
    colorScheme: ColorScheme,
    typography: Typography,
    isMe: Boolean,
    message: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(vertical = 12.dp)
            .clip(
                RoundedCornerShape(
                    topStart = if (isMe) 0.dp else 16.dp,
                    topEnd = if (isMe) 16.dp else 0.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp,
                )
            )
            .background(
                if (isMe) colorScheme.primary else Color.White
            )
            .padding(16.dp)
    ) {
        Text(
            text = message,
            color = if (isMe) colorScheme.onPrimary else colorScheme.onSurface,
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
            .padding(bottom = 60.dp)
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