package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.domain.models.ActorModel
import com.example.ui.ActorState

@Composable
fun ActorsScreen(
    actorState: ActorState,
    onNavigateToActorDetails: (ActorModel) -> Unit,
    onError: () -> Unit,
    onLoadActors: () -> Unit
) {
    val lazyGridState = rememberLazyGridState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        if (actorState.isIdle) {
        } else if (actorState.isFirstLoad) {
            LoadingFullScreen()
        } else if (actorState.errorMessage != null) {
            LaunchedEffect(Unit) {
                onError()
            }
        } else if (actorState.actors.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                state = lazyGridState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 50.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                items(actorState.actors) { actor ->
                    ActorItemScreen(actorModel = actor) {
                        onNavigateToActorDetails(actor)
                    }
                }
                item {
                    val lastVisibleItemIndex = lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                    if (lastVisibleItemIndex != null && lastVisibleItemIndex >= actorState.actors.size - 1 && !actorState.isPartialLoading) {
                        LaunchedEffect(Unit) {
                            onLoadActors()
                        }
                    }
                }
                if (actorState.isPartialLoading) {
                    item {
                        LoadingPartial()
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

