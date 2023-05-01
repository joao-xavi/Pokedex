package com.example.pokedex.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoritePokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoritePokemon(favoritePokemon: FavoritePokemon)

    @Query("SELECT * FROM favorite_pokemon WHERE userId = :userId")
    fun getFavoritePokemonByUserId(userId: Int): LiveData<List<FavoritePokemon>>

}
