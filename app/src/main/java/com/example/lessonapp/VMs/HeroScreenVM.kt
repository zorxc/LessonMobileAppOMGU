package com.example.lessonapp.VMs

import android.net.http.NetworkException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lessonapp.marvel.client.MarvelApiClient
import com.example.lessonapp.marvel.client.generateHash
import com.example.lessonapp.models.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class HeroScreenVM(private val characterId: Long) : ViewModel(), IViewModel {
    private val _state = MutableStateFlow<HeroState>(HeroState.Loading)
    val state: StateFlow<HeroState> = _state

    init {
        load()
    }

    override fun load() {
        viewModelScope.launch {
            try {
                val timestamp = System.currentTimeMillis().toString()
                val response = MarvelApiClient.service.getCharacter(
                    characterId = characterId,
                    timestamp = timestamp,
                    apiKey = MarvelApiClient.PUBLIC_KEY,
                    hash = generateHash(timestamp, MarvelApiClient.PRIVATE_KEY, MarvelApiClient.PUBLIC_KEY)
                )

                if (response.isSuccessful)
                    _state.value = HeroState.Success(response.body()?.data?.results?.first())
                else
                    _state.value = HeroState.Error("Ошибка: ${response.code()}")
            } catch (e: NetworkException) {
                _state.value = HeroState.Error("Проблемы с сетью: ${e.message}")
            }
        }
    }
}

sealed class HeroState {
    object Loading : HeroState()
    data class Success(val hero: Character?) : HeroState()
    data class Error(val message: String) : HeroState()
}
