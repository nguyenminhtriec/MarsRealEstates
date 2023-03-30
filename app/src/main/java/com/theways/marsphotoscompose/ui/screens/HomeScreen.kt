package com.theways.marsphotoscompose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.theways.marsphotoscompose.R
import com.theways.marsphotoscompose.data.MarsPhoto
import com.theways.marsphotoscompose.network.ApiState
import com.theways.marsphotoscompose.ui.ContentFilter
import com.theways.marsphotoscompose.ui.ContentType
import com.theways.marsphotoscompose.ui.MarsPhotoViewModel


@Composable
fun HomeScreen(
    apiState: ApiState,
    modifier: Modifier = Modifier,
    viewModel: MarsPhotoViewModel, // = viewModel(factory = MarsPhotoViewModel.Factory),
    onPhotoClick: (MarsPhoto) -> Unit,
) {

    val defaultList = viewModel.homeUiState.photos
    val mapped = defaultList.groupBy { it.type }
    val filter: ContentFilter = viewModel.contentFilter

    Scaffold(
        modifier = modifier,
        topBar = {
            MarsPhotoTopBar(
                title = when (filter) {
                    ContentFilter.RENT -> ContentFilter.RENT.description
                    ContentFilter.BUY -> ContentFilter.BUY.description
                    else -> ContentFilter.ALL.description
                },
                canNavigateUp = false,
                onNavigateUp = {},
                viewModel = viewModel,
            ) }
    ) {
        when (apiState) {
            ApiState.Loading -> LoadingScreen()
            ApiState.Error -> ErrorScreen()
            ApiState.Success -> PhotosGrid(
                photos = mapped[filter.name.lowercase()] ?: defaultList,
                onPhotoClick = onPhotoClick,
            )
        }
    }
}

@Composable
fun PhotosGrid(
    photos: List<MarsPhoto>,
    onPhotoClick: (MarsPhoto) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(200.dp),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(items = photos, key = { it.id }) {
            PhotoCard(photo = it, onPhotoClick = onPhotoClick)
        }
    }
}

@Composable
fun PhotoCard(
    photo: MarsPhoto,
    onPhotoClick: (MarsPhoto) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable(onClick = { onPhotoClick(photo) }) ,
        elevation = 4.dp
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(photo.imgSrc.replace("http", "https", false))
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.mars_photo),
            //contentScale = ContentScale.Crop
        )
        Text(photo.id)
    }
}

@Composable
fun LoadingScreen(
    //@DrawableRes icon: Int,
    modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = modifier.size(200.dp),
            painter = painterResource(ApiState.Loading.icon),
            contentDescription = null
        )
    }
}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = modifier.size(200.dp),
            painter = painterResource(ApiState.Error.icon),
            contentDescription = null
        )
    }
}