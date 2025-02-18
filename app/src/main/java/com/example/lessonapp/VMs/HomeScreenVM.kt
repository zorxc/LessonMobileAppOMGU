package com.example.lessonapp.VMs

import android.net.http.NetworkException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lessonapp.database.AppDatabase
import com.example.lessonapp.marvel.client.MarvelApiClient
import com.example.lessonapp.marvel.client.generateHash
import com.example.lessonapp.models.Character
import com.example.lessonapp.models.CharacterUI
import com.example.lessonapp.models.toUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class HomeScreenVM(
    private val db: AppDatabase
) : ViewModel(), IViewModel {
    private val _state = MutableStateFlow<HeroesState>(HeroesState.Loading)
    val state: StateFlow<HeroesState> = _state

    var offset: Int = 0
    var limit: Int = 14

    init {
        load()
    }

    override fun load() {
        viewModelScope.launch {
            try {
                val timestamp = System.currentTimeMillis().toString()

                val heroes = db.characterDao().getAll()

                if (!heroes.isEmpty()) {
                    _state.value = HeroesState.Success(heroes.map { it.toUI() })

                    return@launch
                }

                val response = MarvelApiClient.service.getCharacters(
                    timestamp = timestamp,
                    apiKey = MarvelApiClient.PUBLIC_KEY,
                    hash = generateHash(timestamp, MarvelApiClient.PRIVATE_KEY, MarvelApiClient.PUBLIC_KEY),
                    offset = offset,
                    limit = limit
                )

                val res = response.body()?.data?.results

                if (res != null) {
                    if (!res.isEmpty())
                        db.characterDao().insertAll(res)
                }

                if (response.isSuccessful)
                    _state.value = HeroesState.Success(response.body()?.data?.results?.map { it.toUI() } ?: emptyList())
                else
                    _state.value = HeroesState.Error("Ошибка: ${response.code()}")
            } catch (e: NetworkException) {
                _state.value = HeroesState.Error("Проблемы с сетью: ${e.message}")
            }
        }
    }
}

sealed class HeroesState {
    object Loading : HeroesState()
    data class Success(val heroes: List<CharacterUI>) : HeroesState()
    data class Error(val message: String) : HeroesState()
}
