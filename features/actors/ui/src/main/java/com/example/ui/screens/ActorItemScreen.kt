package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.domain.models.ActorModel
import com.example.domain.models.Gender

@Composable
fun ActorItemScreen(
    actorModel: ActorModel,
    onActorClicked: (ActorModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onActorClicked(actorModel) }
    ) {
        Box(
            modifier = Modifier.size(100.dp)
        ) {
            AsyncImage(
                model = actorModel.image,
                contentDescription = "Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = actorModel.name,
            color = Color.White,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun ActorItemScreenPreview() {
    val actor = ActorModel(
        id = 1,
        name = "Name",
        image = "a",
        popularity = 10.0,
        gender = Gender.Male
    )
    ActorItemScreen(actorModel = actor) {
    }
}