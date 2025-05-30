package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.domain.model.SerieModel
import com.example.ui.components.ErrorDialog
import com.example.ui.components.LoadingItem
import com.example.ui.components.SerieCard
import com.example.ui.model.SeriesViewModel


@Composable
fun SeriesScreen(
    viewModel: SeriesViewModel,
    onSeriesClicked: (SerieModel) -> Unit,
    onNavigateHome: () -> Unit
) {
    val state by viewModel.seriesList.collectAsState()
    val listState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        when {
            state.isFirsLoading -> {
                LoadingFullScreen()
            }

            state.series.isNotEmpty() -> {
                SeriesList(
                    series = state.series,
                    listState = listState,
                    onSeriesClicked = onSeriesClicked,
                    isLoading = state.isPartialLoading
                )
            }

            else -> {
                Text(
                    text = "No Data",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
        }
        if (state.error != null) {
            ErrorDialog(
                error = state.error!!,
                onDismiss = { viewModel.clearError() },
                onNavigateHome = onNavigateHome
            )
        }



        LaunchedEffect(listState) {
            snapshotFlow { listState.layoutInfo.visibleItemsInfo }
                .collect { visibleItems ->
                    val lastVisible = visibleItems.lastOrNull()?.index ?: return@collect
                    val total = listState.layoutInfo.totalItemsCount
                    if (lastVisible >= total - 1) {
                        viewModel.loadSeries()
                    }
                }
        }
    }
}


@Composable
private fun SeriesList(
    series: List<SerieModel>,
    listState: LazyListState,
    onSeriesClicked: (SerieModel) -> Unit,
    isLoading: Boolean
) {
    LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
        items(series) { serie ->
            SerieCard(serie = serie, onClick = { onSeriesClicked(serie) })
        }

        if (isLoading) {
            item {
                LoadingItem()
            }
        }
    }
}

@Composable
private fun LoadingFullScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f)),
        contentAlignment = Alignment.Center
    ) {
        LoadingItem()
    }
}