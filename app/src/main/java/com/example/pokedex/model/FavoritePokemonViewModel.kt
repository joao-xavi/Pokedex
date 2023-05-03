package com.example.pokedex.model

import android.app.Application
import androidx.lifecycle.*
import com.example.pokedex.api.PokeResult
import com.example.pokedex.database.FavoritePokemon

import com.example.pokedex.database.FavoritePokemonRepository
import com.example.pokedex.database.MySingleton
import com.example.pokedex.database.MySingleton.pokemonList
import com.example.pokedex.database.UserDatabase
import kotlinx.coroutines.launch


class FavoritePokemonViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FavoritePokemonRepository
    val favoritePokemonList = MutableLiveData<List<PokeResult>>()

    init {
        val favoritePokemonDao = UserDatabase.getInstance(application).favoritePokemonDao()
        repository = FavoritePokemonRepository(favoritePokemonDao)
        val favIdList:List<FavoritePokemon> = repository.getFavoritePokemons(MySingleton.currentUserId)
        val favTemp = mutableListOf<PokeResult>()
        favIdList.forEach { favoritePokemon ->
            pokemonList[favoritePokemon.pokemonId].id = favoritePokemon.pokemonId
            favTemp.add(pokemonList[favoritePokemon.pokemonId])
        }
        favoritePokemonList.value = favTemp
    }

    fun getFavoritePokemonList(userId: Int) {
        repository.getFavoritePokemons(userId)
    }

}
