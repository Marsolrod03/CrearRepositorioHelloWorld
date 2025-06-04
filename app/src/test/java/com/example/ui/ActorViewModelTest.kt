package com.example.ui

import com.example.domain.use_cases.GetActorsUseCase
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach

class ActorViewModelTest {

    private lateinit var getActorUseCase: GetActorsUseCase

    private lateinit var stringResourceProvider: StringResourceProvider

    private lateinit var actorViewModel: ActorViewModel

    @BeforeEach
    fun setUp() {
        getActorUseCase = mockk(relaxed = true)
        stringResourceProvider = mockk(relaxed = true)
        actorViewModel = ActorViewModel(getActorUseCase, stringResourceProvider)
    }


}