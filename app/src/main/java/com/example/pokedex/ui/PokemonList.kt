package com.example.pokedex.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.api.ApiService
import com.example.pokedex.api.PokeResult
import com.example.pokedex.api.Pokemon
import com.example.pokedex.api.Sprites
import com.example.pokedex.model.PokeListAdapter
import com.example.pokedex.model.PokeListViewModel
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.databinding.PokeListBinding
import com.example.pokedex.model.PokeInfoViewModel
import okhttp3.OkHttpClient

class PokemonList : AppCompatActivity() {

    private lateinit var viewModel: PokeListViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingList: PokeListBinding
    private lateinit var pokeListAdapter: PokeListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[PokeListViewModel::class.java]

        val starredButton = findViewById<Button>(R.id.starredButton)
        starredButton.setOnClickListener {
            startActivity(Intent(this, FavList::class.java))
        }

        initUI()
    }

    private fun initUI() {

        binding.pokelistRecyclerView.layoutManager = GridLayoutManager(this, 2)



        pokeListAdapter = PokeListAdapter(this.applicationContext) {
            val intent = Intent(this, PokeInfoActivity::class.java)
            intent.putExtra("id", it)
            intent.putExtra("sprites", true)
            startActivity(intent)
        }


        binding.pokelistRecyclerView.adapter = pokeListAdapter

        viewModel.getPokemonList()
        viewModel.pokemonList.observe(this, Observer { list ->
            pokeListAdapter.setData(list)
        })

    }

}
