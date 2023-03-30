package com.theways.marsphotoscompose.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.theways.marsphotoscompose.data.MarsPhoto
import com.theways.marsphotoscompose.ui.ContentType
import com.theways.marsphotoscompose.ui.MarsPhotoViewModel
import com.theways.marsphotoscompose.ui.theme.MarsPhotosComposeTheme
import com.theways.marsphotoscompose.ui.toCurrency

@Composable
fun ItemScreen(
    photo: MarsPhoto,
    onNavigateUp: () -> Unit,
    viewModel: MarsPhotoViewModel,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MarsPhotoTopBar(
                title = ContentType.DETAILS.title,
                canNavigateUp = true,
                onNavigateUp = onNavigateUp,
                viewModel = viewModel,
                isShowingHomeScreen = false
            )
        }
    ) {
        Column(
            modifier = modifier.fillMaxSize().padding(it),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            PhotoCard(photo = photo, onPhotoClick = {})
            Spacer(modifier = modifier.height(8.dp))
            PhotoInfo(photo = photo)
        }
    }
}

@Composable
private fun PhotoInfo(photo: MarsPhoto, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Text("Type: ${photo.type}")
        Text("Price: ${photo.price.toCurrency()}")
    }
}
