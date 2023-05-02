package com.example.pokedex.database

import com.example.pokedex.api.PokeResult

object MySingleton {
    var currentUserId: Int = 0
    lateinit var pokemonList: List<PokeResult>
    var currentUserEmail : String = ""
}
