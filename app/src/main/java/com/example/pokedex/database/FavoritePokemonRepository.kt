package com.example.pokedex.database


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoritePokemonRepository(private val dao: FavoritePokemonDao) {

    suspend fun addFavoritePokemon(pokemonId: Int, userId: Int) = withContext(Dispatchers.IO) {
        dao.insertFavoritePokemon(FavoritePokemon(pokemonId = pokemonId, userId = userId))
    }


    fun getFavoritePokemons(userId: Int) = dao.getFavoritePokemonByUserId(userId)

}
