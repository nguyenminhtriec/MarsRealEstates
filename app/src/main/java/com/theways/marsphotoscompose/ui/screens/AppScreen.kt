package com.theways.marsphotoscompose.ui.screens

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.theways.marsphotoscompose.ui.*

@Composable
fun AppScreen() {

    val navController: NavHostController = rememberNavController()
    val viewModel: MarsPhotoViewModel = viewModel(factory = MarsPhotoViewModel.Factory)


    NavHost(navController = navController, startDestination = ContentType.HOME.name) {
        composable(route = ContentType.HOME.name) {
            HomeScreen(
                apiState = viewModel.apiState,
                viewModel = viewModel,
                onPhotoClick = {
                    viewModel.updateItemUiState(it.toItemUiState(selected = true))
                    navController.navigate(ContentType.DETAILS.name)
                }
            )
        }
        composable(route = ContentType.DETAILS.name) {
            ItemScreen(
                photo = viewModel.itemUiState.toMarsPhoto(),
                onNavigateUp = { navController.navigateUp() },
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun MarsPhotoTopBar(
    title: String,
    canNavigateUp: Boolean,
    onNavigateUp: () -> Unit,
    viewModel: MarsPhotoViewModel,
    modifier: Modifier = Modifier,
    isShowingHomeScreen: Boolean = true,
) {

    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(title) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateUp) {
                IconButton(onClick = onNavigateUp) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        },
        actions = {
            if (isShowingHomeScreen) {
                IconButton(onClick = { expanded = !expanded  }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                }
                DropdownMenu(expanded = expanded , onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(onClick = { viewModel.updateContentFilter(ContentFilter.BUY) }) {
                        Text(ContentFilter.BUY.name)
                    }
                    DropdownMenuItem(onClick = { viewModel.updateContentFilter(ContentFilter.RENT) }) {
                        Text(ContentFilter.RENT.name)
                    }
                    DropdownMenuItem(onClick = { viewModel.updateContentFilter(ContentFilter.ALL) }) {
                        Text(ContentFilter.ALL.name)
                    }
                }
            }
        }
    )
}

////
//@Composable
//fun MyTopAppBar(
//    iconAndTextColor: Color = Color.DarkGray
//) {
//    val listItems = getMenuItemsList()
//
//    val contextForToast = LocalContext.current.applicationContext
//
//    // state of the menu
//    var expanded by remember {
//        mutableStateOf(false)
//    }
//
//    TopAppBar(
//        title = {
//            Text(text = "SemicolonSpace")
//        },
//        actions = {
//
//            // 3 vertical dots icon
//            IconButton(onClick = {
//                expanded = true
//            }) {
//                Icon(
//                    imageVector = Icons.Default.MoreVert,
//                    contentDescription = "Open Options"
//                )
//            }
//
//            // drop down menu
//            DropdownMenu(
//                modifier = Modifier.width(width = 150.dp),
//                expanded = expanded,
//                onDismissRequest = {
//                    expanded = false
//                },
//                // adjust the position
//                offset = DpOffset(x = (-102).dp, y = (-64).dp),
//                properties = PopupProperties()
//            ) {
//
//                // adding each menu item
//                listItems.forEach { menuItemData ->
//                    DropdownMenuItem(
//                        onClick = {
//                            Toast.makeText(contextForToast, menuItemData.text, Toast.LENGTH_SHORT)
//                                .show()
//                            expanded = false
//                        },
//                        enabled = true
//                    ) {
//
//                        Icon(
//                            imageVector = menuItemData.icon,
//                            contentDescription = menuItemData.text,
//                            tint = iconAndTextColor
//                        )
//
//                        Spacer(modifier = Modifier.width(width = 8.dp))
//
//                        Text(
//                            text = menuItemData.text,
//                            fontWeight = FontWeight.Medium,
//                            fontSize = 16.sp,
//                            color = iconAndTextColor
//                        )
//                    }
//                }
//            }
//        }
//    )
//}
//
//fun getMenuItemsList(): ArrayList<MenuItemData> {
//    val listItems = ArrayList<MenuItemData>()
//
//    listItems.add(MenuItemData(text = "Notes", icon = Icons.Outlined.Bookmarks))
//    listItems.add(MenuItemData(text = "Options", icon = Icons.Outlined.Apps))
//    listItems.add(MenuItemData(text = "Mail", icon = Icons.Outlined.Mail))
//    listItems.add(MenuItemData(text = "About", icon = Icons.Outlined.Info))
//
//    return listItems
//}
//
//data class MenuItemData(val text: String, val icon: ImageVector)