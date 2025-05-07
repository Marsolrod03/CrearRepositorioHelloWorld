package com.example.crearrepositorio.features.actors.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.crearrepositorio.common_ui.BaseFragment
import com.example.crearrepositorio.databinding.DetailsActorsBinding

class DetailsActorFragment : BaseFragment<DetailsActorsBinding>() {
    private val binding get() = _binding!!
    private var actorName: String? = null
    private var actorImage: String? = null
    private var actorGender: String? = null
    private var actorBiography: String? = null
    private var actorPopularity: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsActorsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            actorName = it.getString("actorName")
            actorImage = it.getString("actorImage")
            actorBiography = it.getString("actorBiography")
            actorGender = it.getString("actorGender")
            actorPopularity = it.getString("actorPopularity")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            textViewName.text = actorName
            textViewBiography.text = actorBiography
            textViewPopularity.text = actorPopularity
            textViewGender.text = actorGender
            imageViewActor.load(actorImage)
        }
    }
}