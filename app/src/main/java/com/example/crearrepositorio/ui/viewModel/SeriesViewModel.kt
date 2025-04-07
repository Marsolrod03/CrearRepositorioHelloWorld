package com.example.crearrepositorio.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crearrepositorio.R
import com.example.crearrepositorio.domain.SerieModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SeriesViewModel : ViewModel() {
    private val _seriesList = MutableStateFlow<SeriesState>(SeriesState.Idle)
    val seriesList: StateFlow<SeriesState> = _seriesList.asStateFlow()

    init {
        loadSeries()
    }

    private fun loadSeries() {
        viewModelScope.launch {
            val series = createList()
            _seriesList.update { SeriesState.Created(series) }
        }
    }

    private fun createList(): List<SerieModel> {
        return listOf(
            SerieModel(
                "Wandavision",
                R.drawable.series1,
                "WandaVision es una serie de Marvel que trata sobre la vida de Wanda Maximoff (Bruja Escarlata) y Visión, dos superhéroes que viven en Westview, Nueva Jersey. La serie combina comedia de situación con el universo de Marvel. ",
                "1 temporada",
                "Superhéroes"
            ),
            SerieModel(
                "Daredevil : Born Again",
                R.drawable.series2,
                "La serie cuenta la historia de Matt Murdock, un abogado no vidente con habilidades potenciadas. Cuando sus identidades pasadas comienzan a salir a la luz, se encuentra en un conflicto con Wilson Fisk, el ex jefe de la mafia. ",
                "1 temporada",
                "Acción"
            ),
            SerieModel(
                "Game of Thrones",
                R.drawable.series3,
                "La novela transcurre en un mundo fantástico con reminiscencias de la Europa de la Edad Media en el que la magia y las criaturas míticas del pasado han quedado en el olvido. En el continente de Poniente, donde las estaciones duran décadas y los inviernos son tiempos duros, se acerca el final del largo verano.",
                "8 temporadas",
                "Fantasía"
            ),
            SerieModel(
                "Breaking Bad",
                R.drawable.series4,
                "Un profesor de química con cáncer terminal se asocia con un exalumno suyo para fabricar y vender metanfetamina a fin de que su familia no pase apuros económicos.",
                "5 temporadas",
                "Drama"
            ),
            SerieModel(
                "Arcane",
                R.drawable.series5,
                "Arcane es una serie animada de Netflix que trata de dos hermanas que luchan en una guerra entre las ciudades de Piltover y Zaun. La serie está basada en el juego League of Legends de Riot Games.",
                "2 temporadas",
                "Animación"
            ),
            SerieModel(
                "Stranger Things",
                R.drawable.series6,
                "Stranger Things es una serie de Netflix que trata sobre la desaparición de un niño en un pueblo de los años 80. El misterio que se desvela lleva a los protagonistas a enfrentarse a fuerzas sobrenaturales y a una dimensión alternativa.",
                "4 temporadas",
                "Drama"
            ),
            SerieModel(
                "The Witcher",
                R.drawable.series7,
                "The Witcher* se desarrolla en un mundo medieval, en un continente poblado de criaturas sobrenaturales, intrigas políticas y magia. Geralt es un mutante mágico que fue creado para cazar y matar monstruos.",
                "3 temporadas",
                "Fantasía"
            ),

            )
    }
}


    sealed class SeriesState {
        data object Idle : SeriesState()
        data class Created(val series: List<SerieModel>) : SeriesState()
    }

