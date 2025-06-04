package com.example.ui.details

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.domain.models.ActorModel
import com.example.domain.models.Gender
import com.example.ui.R
import androidx.core.net.toUri
import androidx.compose.ui.platform.LocalConfiguration


@Composable
fun ActorDetailsScreen(
    actorImage: String?,
    actorGender: String?,
    detailsState: DetailsState,
    onError: () -> Unit
) {
    val actorText = stringResource(R.string.actors_details)
    val image = stringResource(R.string.image)
    val biography = stringResource(R.string.biography)
    val popularity = stringResource(R.string.popularity)
    val gender = stringResource(R.string.gender)
    val actualContext = LocalContext.current

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    Column(
        modifier = Modifier
            .background(Color.Black)
            .padding(16.dp)
            .fillMaxSize()
            .semantics { contentDescription = actorText }
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        if (detailsState.errorMessage != null) {
            LaunchedEffect(Unit) {
                onError()
            }
        } else if (detailsState.details != null) {
            val details = detailsState.details

            if(isLandscape){
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        AsyncImage(
                            model = actorImage,
                            contentDescription = image + details.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.7f)
                                .clip(RectangleShape)
                                .clickable {
                                    val imageUrl = details.image
                                    val intent = Intent(Intent.ACTION_VIEW, imageUrl.toUri())
                                    actualContext.startActivity(intent)
                                },
                            contentScale = ContentScale.Inside
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = details.name,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )

                        Text(
                            text = gender + actorGender,
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        )

                        Text(
                            text = popularity + details.popularity,
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = details.biography,
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.White
                            ),
                            modifier = Modifier
                                .weight(0.6f)
                                .verticalScroll(rememberScrollState())
                                .semantics { biography + details.biography }
                        )
                    }
                }
            }else{
                AsyncImage(
                    model = actorImage,
                    contentDescription = image + details.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)
                        .clip(RectangleShape)
                        .clickable {
                            val imageUrl = details.image
                            val intent = Intent(Intent.ACTION_VIEW, imageUrl.toUri())
                            actualContext.startActivity(intent)
                        },
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = details.name,
                    style = TextStyle(
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )

                Text(
                    text = gender + actorGender,
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.White
                    )
                )

                Text(
                    text = popularity + details.popularity,
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = details.biography,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White
                    ),
                    modifier = Modifier
                        .weight(0.6f)
                        .verticalScroll(rememberScrollState())
                        .semantics { biography + details.biography }
                )
            }
        }
    }
}

@Preview(showBackground = true, device = "spec:parent=pixel_5,orientation=landscape")
@Composable
fun ActorDetailsScreenErrorPreview() {
    ActorDetailsScreen(
        actorImage = null,
        actorGender = Gender.Male.toString(),
        detailsState = DetailsState(ActorModel(1, "Name", "", Gender.Male, 10.0)),
        onError = {
            println("Error occurred in preview")
        }
    )
}
