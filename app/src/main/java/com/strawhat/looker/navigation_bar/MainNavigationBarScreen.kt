package com.strawhat.looker.navigation_bar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun MainNavigationBarScreen(
    items: List<NavigationBarItemModel>,
    selectedRoute: String,
    onRouteSelected: (route: String) -> Unit,
    modifier: Modifier = Modifier,
    colorScheme: ColorScheme = MaterialTheme.colorScheme,
    typography: Typography = MaterialTheme.typography,
) {

    NavigationBar(
        windowInsets = NavigationBarDefaults.windowInsets,
        containerColor = Color.White,
        contentColor = colorScheme.primary,
        tonalElevation = 8.dp,
        modifier = modifier
            .fillMaxWidth()
        ,
    ) {
        items.forEachIndexed { _, item ->
            BasicNavigationBarItem(
                colorScheme = colorScheme,
                typography = typography,
                item = item,
                modifier = Modifier,
                isItemSelected = { it.route in selectedRoute },
                onItemSelected = { onRouteSelected(it.route) }
            )
        }
    }

}
@Composable
fun RowScope.BasicNavigationBarItem(
    colorScheme: ColorScheme,
    typography: Typography,
    item: NavigationBarItemModel,
    modifier: Modifier = Modifier,
    isItemSelected: (item: NavigationBarItemModel) -> Boolean,
    onItemSelected: (item: NavigationBarItemModel) -> Unit
) {

    NavigationBarItem(
        icon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = item.resourceId),
                    contentDescription = item.label,
                    modifier = Modifier
                )

                if (isItemSelected(item)) {
                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        item.label,
                        style = typography.labelMedium,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }
            }
        },
        alwaysShowLabel = false,
        selected = isItemSelected(item),
        onClick = { onItemSelected(item) },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = colorScheme.onPrimary,
            selectedTextColor = colorScheme.primaryContainer,
            indicatorColor = colorScheme.primaryContainer,
            unselectedTextColor = colorScheme.primaryContainer,
            unselectedIconColor = colorScheme.primaryContainer,
        ),
        modifier = modifier.weight(
            if (isItemSelected(item)) 2f else 1f
        ),
    )
}