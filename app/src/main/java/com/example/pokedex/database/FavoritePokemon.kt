package com.example.pokedex.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_pokemon_table",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["userId"])
    ]
)
data class FavoritePokemon(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pokemonId: Int,
    val userId: Int
    )
