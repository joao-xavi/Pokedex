package com.example.pokedex.model

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.model.PokeInfoViewModel
import com.example.pokedex.api.PokeResult
import com.example.pokedex.database.*

import com.example.pokedex.databinding.PokeListBinding
import kotlinx.coroutines.*


class PokeListAdapter(private var context: Context, private val pokemonClick: (Int) -> Unit): RecyclerView.Adapter<PokeListAdapter.SearchViewHolder>() {
    private var pokemonList: List<PokeResult> = emptyList<PokeResult>()

    fun setData(list: List<PokeResult>) {
        pokemonList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = PokeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val binding = holder.binding
        val pokemonList = pokemonList[position]
        binding.pokemonText.text = "#${position + 1} - ${pokemonList.name}"
        Glide.with(holder.itemView.context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${position + 1}.png").into(binding.pokeView)
        holder.itemView.setOnClickListener { pokemonClick(position + 1) }

        binding.favButton.setOnClickListener {
            val userDatabase = UserDatabase.getInstance(context)
            val currentUser = userDatabase.userDao()

            runBlocking {
                val user = withContext(Dispatchers.IO) {
                    currentUser.getUserByEmail(MySingleton.currentUserEmail)
                }

                val dao = UserDatabase.getInstance(context).favoritePokemonDao()
                val favoritePokemonRepository = FavoritePokemonRepository(dao)
                if (user != null) {
                    favoritePokemonRepository.addFavoritePokemon(pokemonId = position + 1, userId = user.id)
                }
                Toast.makeText(context, "Pokemon favoritado!", Toast.LENGTH_SHORT).show()
            }
        }
        val dao = UserDatabase.getInstance(context).favoritePokemonDao()

    }

    class SearchViewHolder(val binding: PokeListBinding): RecyclerView.ViewHolder(binding.root)
}
