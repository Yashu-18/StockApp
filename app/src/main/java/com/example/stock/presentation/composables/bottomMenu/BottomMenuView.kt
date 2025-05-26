package com.example.stock.presentation.composables.bottomMenu

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.stock.presentation.ui.theme.DarkLightPreviews
import com.example.stock.presentation.ui.theme.StockTheme

@Composable
fun BottomMenuView(
    screens: List<BottomMenuItem>,
    onNavigateTo: (BottomMenuItem) -> Unit,
    currentDestination: NavDestination?
) {

    AnimatedVisibility(
        visible = true,
        enter = slideInHorizontally(initialOffsetX = { it }),
        exit = slideOutHorizontally(targetOffsetX = { it }),
    ) {

        AppBottomBar {
            screens.forEach { screen ->
                currentDestination?.label = stringResource(id = screen.titleRes)
                val selected: Boolean =
                    currentDestination?.hierarchy?.any { it.route == screen.route } ?: false

                AppBottomBarItem(
                    selected = selected,
                    onClick = {
                        onNavigateTo(screen)
                    },
                    icon = {
                        Icon(
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            painter = painterResource(id = screen.iconRes),
                            contentDescription = null
                        )
                    },
                    selectedIcon = {
                        Icon(
                            tint = MaterialTheme.colorScheme.primary,
                            painter = painterResource(id = screen.iconRes),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = screen.titleRes),
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                )
            }
        }
    }
}


@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.onPrimary,
        tonalElevation = 4.dp,
        content = content
    )
}

@Composable
fun RowScope.AppBottomBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            indicatorColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}


@DarkLightPreviews
@Composable
fun HomeScreenPreview() {
    StockTheme {
        val navController = NavHostController(LocalContext.current)
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        BottomMenuView(
            screens = listOf(
                BottomMenuItem.TopGainers,
                BottomMenuItem.TopLosers,
            ),
            onNavigateTo = navController::navigateTo,
            currentDestination = navBackStackEntry?.destination
        )
    }
}


fun NavController.navigateTo(
    screen: BottomMenuItem
) {

    navigate(screen.route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
    Log.d("navigation", "navigateTo: ${screen.route}")


}


