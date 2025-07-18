package com.example.ui.screens

import android.content.res.Configuration
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.models.ActorModel
import com.example.domain.models.Gender
import com.example.ui.ActorState
import com.example.actors.ui.R

@Composable
fun ActorsScreen(
    actorState: ActorState,
    onNavigateToActorDetails: (ActorModel) -> Unit,
    onError: () -> Unit,
    onLoadActors: () -> Unit
) {
    val lazyGridState = rememberLazyGridState()
    val actorScreen = stringResource(R.string.actors_screen)
    val actorList = stringResource(R.string.actors_list_grid)

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .semantics { contentDescription = actorScreen }
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        if (actorState.isFirstLoad) {
            LoadingFullScreen()
        } else if (actorState.errorMessage != null) {
            LaunchedEffect(Unit) {
                onError()
            }
        } else if (actorState.actors.isNotEmpty()) {
            LazyVerticalGrid(
                columns = if (isLandscape) GridCells.Fixed(4) else GridCells.Fixed(2),
                state = lazyGridState,
                modifier = Modifier
                    .fillMaxSize()
                    .semantics { contentDescription = actorList },
                contentPadding = PaddingValues(horizontal = if (isLandscape) 50.dp else 50.dp),
                horizontalArrangement = Arrangement.spacedBy(if (isLandscape) 16.dp else 32.dp)
            ) {
                items(
                    actorState.actors,
                    key = { actor -> actor.id }
                ) { actor ->
                    ActorItemScreen(actorModel = actor) {
                        onNavigateToActorDetails(actor)
                    }
                }
                item {
                    val lastVisibleItemIndex =
                        lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
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

@Preview(showBackground = true)
@Composable
fun ActorsScreenSuccessPreview() {
    val sampleActors = listOf(
        ActorModel(1, "Name 1", "", Gender.Male, 75.0, "Biography 1"),
        ActorModel(2, "Name 2", "", Gender.Female, 82.5, "Biography 2"),
        ActorModel(3, "Name 3", "", Gender.Male, 68.0, "Biography 3")
    )

    ActorsScreen(
        actorState = ActorState(
            actors = sampleActors,
            isFirstLoad = false,
            isPartialLoading = false
        ),
        onNavigateToActorDetails = { },
        onError = { },
        onLoadActors = { }
    )
}

@Preview(showBackground = true, device = "spec:parent=pixel_5,orientation=landscape")
@Composable
fun ActorsScreenSuccessLandPreview() {
    val sampleActors = listOf(
        ActorModel(1, "Name 1", "", Gender.Male, 75.0, "Biography 1"),
        ActorModel(2, "Name 2", "", Gender.Female, 82.5, "Biography 2"),
        ActorModel(3, "Name 3", "", Gender.Male, 68.0, "Biography 3"),
        ActorModel(4, "Name 4", "", Gender.Male, 75.0, "Biography 4"),
        ActorModel(5, "Name 5", "", Gender.Female, 82.5, "Biography 5"),
        ActorModel(6, "Name 6", "", Gender.Male, 68.0, "Biography 6"),
        ActorModel(7, "Name 7", "", Gender.Male, 75.0, "Biography 7"),
        ActorModel(8, "Name 8", "", Gender.Female, 82.5, "Biography 8"),
        ActorModel(9, "Name 9", "", Gender.Male, 68.0, "Biography 9")
    )

    ActorsScreen(
        actorState = ActorState(
            actors = sampleActors,
            isFirstLoad = false,
            isPartialLoading = false
        ),
        onNavigateToActorDetails = { },
        onError = { },
        onLoadActors = { }
    )
}

