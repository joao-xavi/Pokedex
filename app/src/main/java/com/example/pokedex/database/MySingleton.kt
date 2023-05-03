package com.example.pokedex.database

import com.example.pokedex.api.PokeResult

object MySingleton {
    var currentUserId: Int = 0
    var pokemonList: List<PokeResult> = listOf()
    var currentUserEmail : String = ""
}
