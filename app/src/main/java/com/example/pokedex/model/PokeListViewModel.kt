package com.example.pokedex.model

import android.content.Context
import android.content.Intent
import android.graphics.PointF.length
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.pokedex.api.ApiService
import com.example.pokedex.api.PokeApiResponse
import com.example.pokedex.api.PokeResult
import com.example.pokedex.api.Pokemon
import com.example.pokedex.database.FavoritePokemonRepository
import com.example.pokedex.database.UserDatabase
import com.example.pokedex.databinding.ActivityFavListBinding
import com.example.pokedex.ui.PokeInfoActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokeListViewModel() : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: ApiService = retrofit.create(ApiService::class.java)

    val pokemonList = MutableLiveData<List<PokeResult>>()

    fun getPokemonList(){
        val call = service.getPokemonList(300,0)

        call.enqueue(object : Callback<PokeApiResponse> {
            override fun onResponse(call: Call<PokeApiResponse>, response: Response<PokeApiResponse>) {
                response.body()?.results?.let { list ->
                    pokemonList.postValue(list)
                }

            }

            override fun onFailure(call: Call<PokeApiResponse>, t: Throwable) {
                call.cancel()
            }

        })
    }


}