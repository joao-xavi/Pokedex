package com.example.pokedex.model

import android.app.Application
import androidx.lifecycle.*
import com.example.pokedex.api.PokeResult

import com.example.pokedex.database.FavoritePokemonRepository
import com.example.pokedex.database.MySingleton
import com.example.pokedex.database.UserDatabase
import kotlinx.coroutines.launch


class FavoritePokemonViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FavoritePokemonRepository
    val favoritePokemonList = MediatorLiveData<List<PokeResult>>()

    init {
        val favoritePokemonDao = UserDatabase.getInstance(application).favoritePokemonDao()
        repository = FavoritePokemonRepository(favoritePokemonDao)
        favoritePokemonList.addSource(repository.getFavoritePokemons(MySingleton.currentUserId)) { favorites ->
            val result = favorites.map { favorite ->
                val pokemonId = favorite.pokemonId
                MySingleton.pokemonList[pokemonId - 1]
            }
            favoritePokemonList.value = result
        }
    }

    fun getFavoritePokemonList(userId: Int) {
        repository.getFavoritePokemons(userId)
    }

    fun getFavoritePokemonListLiveData(): LiveData<List<PokeResult>> {
        return favoritePokemonList
    }
}
