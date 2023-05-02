package com.example.pokedex.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.api.PokeResult
import com.example.pokedex.database.MySingleton
import com.example.pokedex.database.UserDatabase
import com.example.pokedex.databinding.ActivityFavListBinding
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.model.*
import kotlinx.coroutines.launch


class FavList : AppCompatActivity() {

    private lateinit var viewModel: FavoritePokemonViewModel
    private lateinit var binding: ActivityFavListBinding
    private lateinit var favListAdapter: FavListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[FavoritePokemonViewModel::class.java]

        lifecycleScope.launch {
            initUI()
        }
    }

    private suspend fun initUI() {

        binding.favlistRecyclerView.layoutManager = LinearLayoutManager(this)

        favListAdapter = FavListAdapter(this.applicationContext) {
            val intent = Intent(this, PokeInfoActivity::class.java)
            intent.putExtra("id", it)
            intent.putExtra("sprites", true)
            startActivity(intent)
        }

        binding.favlistRecyclerView.adapter = favListAdapter

        val userDatabase = UserDatabase.getInstance(applicationContext)
        val currentUser = userDatabase.userDao()

        currentUser.getUserByEmail(MySingleton.currentUserEmail)
            ?.let { viewModel.getFavoritePokemonList(it.id) }

        viewModel.favoritePokemonList.observe(this) { list ->
            favListAdapter.setData(list)
        }
    }
}

